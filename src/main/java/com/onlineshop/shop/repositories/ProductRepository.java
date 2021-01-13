package com.onlineshop.shop.repositories;

import com.onlineshop.shop.models.Product;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface ProductRepository extends CrudRepository<Product, Integer> {
    @Query ("select id from Product where brand like :brandName")
    Iterable<Integer> getProductsByBrandName(@Param("brandName") String brandName);

    @Query ("select id " +
            "from Product " +
            "where brand like :value or " +
            "productName like :value")
    Iterable<Integer> searchProducts(@Param("value") String value);


    @Query("select id " +
            "from Product as P " +
            "where brand like :value or " +
            "productName like :value " +
            "order by P.sold desc")
    Iterable<Integer> searchProductsPopularity(@Param("value") String value);

    @Query("select id " +
            "from Product as P " +
            "where brand like :value or " +
            "productName like :value " +
            "order by P.price - P.price * (P.onSale / 100   ) asc")
    Iterable<Integer> searchPriceAsc(@Param("value") String value);

    @Query("select id " +
            "from Product as P " +
            "where brand like :value or " +
            "productName like :value " +
            "order by P.price - P.price * (P.onSale / 100) desc")
    Iterable<Integer> searchPriceDesc(@Param("value") String value);

    @Query("select id " +
            "from Product as P " +
            "where brand like :value or " +
            "productName like :value " +
            "order by P.addedDate")
    Iterable<Integer> searchDateAsc(@Param("value") String value);

    @Query("select id " +
            "from Product as P " +
            "where brand like :value or " +
            "productName like :value " +
            "order by P.addedDate desc")
    Iterable<Integer> searchDateDesc(@Param("value") String value);

    @Query("select id " +
            "from Product " +
            "where onSale <> 0" +
            "order by onSale desc")
    Iterable<Integer> searchOnSale();
}
