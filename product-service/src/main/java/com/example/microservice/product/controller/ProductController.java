package com.example.microservice.product.controller;

import com.example.microservice.product.dto.ProductRequest;
import com.example.microservice.product.dto.ProductResponse;
import com.example.microservice.product.model.Product;
import com.example.microservice.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/product")
public class ProductController {

    @Autowired
    private ProductService productService;
    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public ProductResponse createProduct(@RequestBody ProductRequest productRequest){
      return  productService.createProduct(productRequest);
    }

    @GetMapping("/g")
    @ResponseStatus(HttpStatus.OK)
    public List<ProductResponse> getAllProducts(){
        return  productService.getAllProducts();
    }
}
