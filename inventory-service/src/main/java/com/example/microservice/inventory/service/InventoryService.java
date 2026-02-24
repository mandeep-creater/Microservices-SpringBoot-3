package com.example.microservice.inventory.service;

import com.example.microservice.inventory.repo.InventoryRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InventoryService {

    @Autowired
    private InventoryRepo inventoryRepo;
    public  boolean isInStock(String skuCode , Integer quantity){
      return   inventoryRepo.existsBySkuCodeAndQuantityIsGreaterThanEqual(skuCode,quantity);
    }
}
