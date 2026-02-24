package com.example.microservice.product.dto;

import java.math.BigDecimal;

public record ProductResponse(int id , String name , String description , BigDecimal price) {
}
