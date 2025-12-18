package com.techie.microservices.product_service.repository;

import com.techie.microservices.product_service.models.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepository extends MongoRepository<Product, String> {
}
