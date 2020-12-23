package com.onlineshop.shop.repositories;

import com.onlineshop.shop.models.Product;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<Product, Integer> {
}
