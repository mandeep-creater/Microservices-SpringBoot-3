package com.orderservice.order.order;

import com.orderservice.order.client.InventoryClient;
import com.orderservice.order.dto.OrderRequest;
import com.orderservice.order.event.OrderPlacedEvent;
import com.orderservice.order.model.Order;
import com.orderservice.order.repo.OrderRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.logging.Logger;

@Service
@Slf4j
public class OrderService {

    @Autowired
    private OrderRepo orderRepo;
    @Autowired
    private InventoryClient inventoryClient;

    @Autowired
    private KafkaTemplate<String,OrderPlacedEvent>kafkaTemplate;


    public  void placeOrder(OrderRequest orderRequest) {
        var isInStock = inventoryClient.isInStock(orderRequest.skuCode(), orderRequest.quantity());

        if (isInStock) {
            //map orderReq to OrderObject
            Order order = new Order();
            order.setOrderNumber(UUID.randomUUID().toString());
            order.setQuantity(orderRequest.quantity());
            order.setPrice(orderRequest.price());
            order.setSkuCode(orderRequest.skuCode());
            order.setEmail(orderRequest.email());
            //then save
            orderRepo.save(order);
            System.out.println("Order Save hogya "+order.getEmail() + order.getOrderNumber() +order.getSkuCode());
            //send msg to kafka topic
            //order no and email
            OrderPlacedEvent orderPlacedEvent = new OrderPlacedEvent(order.getOrderNumber(), order.getEmail());
            System.out.println("Start - Sending OrderPlacedEvent {} to kafka topic order-placed"+orderPlacedEvent);
            kafkaTemplate.send("order_placed",orderPlacedEvent);
            System.out.println("End - Sending OrderPlacedEvent {} to kafka topic order-placed"+orderPlacedEvent);

        } else {
                throw new RuntimeException("Product with skuCode "+ orderRequest.skuCode()+" is not in stock");
        }
    }

}
