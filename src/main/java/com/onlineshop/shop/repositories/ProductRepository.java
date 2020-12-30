package com.onlineshop.shop.repositories;

import com.onlineshop.shop.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface ProductRepository extends CrudRepository<Product, Integer> {
    @Query ("select id from Product where brand like :brandName")
    public Iterable<Integer> getProductsByBrandName(@Param("brandName") String brandName);

    @Query ("select id " +
            "from Product " +
            "where brand like :value or " +
            "description like :value or " +
            "productName like :value")
    public Iterable<Integer> searchProducts(@Param("value") String value);
}
