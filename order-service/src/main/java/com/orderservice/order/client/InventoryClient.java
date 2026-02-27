package com.orderservice.order.client;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.LoggerFactoryFriend;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value="inventory" , url ="http://localhost:8083")
public interface InventoryClient {
    Logger log= LoggerFactory.getLogger(InventoryClient.class);
    @RequestMapping(method = RequestMethod.GET, value ="/api/inventory/")
    @CircuitBreaker(name = "inventory" ,fallbackMethod = "fallbackMethod")
    @Retry(name="inventory")
    boolean isInStock(@RequestParam String skuCode , @RequestParam Integer quantity);

    default boolean fallbackMethod(String skuCode,Integer quantity,Throwable throwable){
        log.info("Cannot get Inventory Service ",throwable.getMessage());
        return false;
    }
}
