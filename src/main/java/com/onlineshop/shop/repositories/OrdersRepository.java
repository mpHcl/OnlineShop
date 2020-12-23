package com.onlineshop.shop.repositories;

import com.onlineshop.shop.models.Orders;
import org.springframework.data.repository.CrudRepository;

public interface OrdersRepository extends CrudRepository<Orders, Integer> {
}
