package com.orderservice.order.repo;

import com.orderservice.order.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepo extends JpaRepository<Order ,Long> {

}
