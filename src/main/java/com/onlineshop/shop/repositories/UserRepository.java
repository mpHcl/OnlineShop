package com.onlineshop.shop.repositories;

import com.onlineshop.shop.models.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {
}
