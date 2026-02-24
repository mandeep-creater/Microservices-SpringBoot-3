package com.example.microservice.inventory.controller;

import com.example.microservice.inventory.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;

    @GetMapping("/")
    @ResponseStatus(HttpStatus.OK )
    public  boolean isInStock(@RequestParam String skuCode ,@RequestParam Integer quantity){
        return  inventoryService.isInStock(skuCode,quantity);
    }
}
