package com.orderservice.order.order;

import com.orderservice.order.client.InventoryClient;
import com.orderservice.order.dto.OrderRequest;
import com.orderservice.order.model.Order;
import com.orderservice.order.repo.OrderRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class OrderService {

    @Autowired
    private OrderRepo orderRepo;

    @Autowired
    private InventoryClient inventoryClient;
    public  void placeOrder(OrderRequest orderRequest) {
        var isInStock = inventoryClient.isInStock(orderRequest.skuCode(), orderRequest.quantity());

        if (isInStock) {
            //map orderReq to OrderObject
            Order order = new Order();
            order.setOrderNumber(UUID.randomUUID().toString());
            order.setQuantity(orderRequest.quantity());
            order.setPrice(orderRequest.price());
            order.setSkuCode(orderRequest.skuCode());
            //then save
            orderRepo.save(order);
        } else {
                throw new RuntimeException("Product with skuCode "+ orderRequest.skuCode()+" is not in stock");
        }
    }

}
