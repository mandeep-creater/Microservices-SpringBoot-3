package com.microservice.notification.service;

import com.microservice.order.event.OrderPlacedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

@Service

public class NotificationService {

//    @Autowired
//    private OrderPlacedEvenet orderPlacedEvenet;
    @Autowired
    private JavaMailSender javaMailSender;
    @KafkaListener(topics = "order_placed")
    public  void listen(OrderPlacedEvent orderPlacedEvenet){
        System.out.println("Got msg  from order-placed topic {} "+orderPlacedEvenet);
        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setFrom("springshop@email.com");
            messageHelper.setTo(orderPlacedEvenet.getEmail());
            messageHelper.setSubject(String.format("Your Order with OrderNumber %s is placed successfully", orderPlacedEvenet.getOrderNumber()  ));
            messageHelper.setText(String.format("""
                            Hi,

                            Your order with order number %s is now placed successfully.

                            Best Regards
                            Spring Shop
                            """,
//                    orderPlacedEvent.getFirstName().toString(),
//                    orderPlacedEvent.getLastName().toString(),
                    orderPlacedEvenet.getOrderNumber()));
        };
        try {
            javaMailSender.send(messagePreparator);
            System.out.println("Order Notifcation email sent!!");
        } catch (MailException e) {
            System.out.println("Exception occurred when sending mail"+e);
            throw new RuntimeException("Exception occurred when sending mail to springshop@email.com", e);
        }


    }
}
