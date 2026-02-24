package com.example.microservice.product.service;

import com.example.microservice.product.dto.ProductRequest;
import com.example.microservice.product.dto.ProductResponse;
import com.example.microservice.product.model.Product;
import com.example.microservice.product.repo.ProductRepo;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepo productRepo;
    private static final Logger log =
            LoggerFactory.getLogger(ProductService.class);
    public ProductResponse createProduct(ProductRequest productRequest){
//        Product product = Product.builder()
//                            .name(productRequest.getName())
//                            .description(productRequest.getDescription())
//                .price(productRequest.getPrice())
//                .build();
        Product product = new Product();
        product.setName(productRequest.getName());
        product.setPrice(productRequest.getPrice());
        product.setDescription(productRequest.getDescription());
        productRepo.save(product);

        log.info("Product is saved ");

        return   new ProductResponse(product.getId(), product.getName(), product.getDescription(),product.getPrice());
    }

    public List<ProductResponse> getAllProducts(){
        return productRepo.findAll()
                .stream()
                .map(product -> new ProductResponse(product.getId(), product.getName(), product.getDescription(),product.getPrice()))
                .toList();
    }
}
