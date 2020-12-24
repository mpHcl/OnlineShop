package com.onlineshop.shop.controllers;

import com.onlineshop.shop.models.Product;
import com.onlineshop.shop.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("controllers/products")
public class ProductController {
    @Autowired
    private ProductRepository productRepository;

    @PostMapping("/add")
    public @ResponseBody String addNewProduct(@RequestParam String brand,
                                              @RequestParam String name,
                                              @RequestParam double price,
                                              @RequestParam int amount) {
        Product product = new Product();
        product.setBrand(brand);
        product.setProductName(name);
        product.setPrice(price);
        product.setAmount(amount);
        product.setDescription("Description");

        productRepository.save(product);

        return "Zapisano";
    }

    @GetMapping("/all")
    public @ResponseBody Iterable<Product> getAllProduct() {
        return productRepository.findAll();
    }
}
