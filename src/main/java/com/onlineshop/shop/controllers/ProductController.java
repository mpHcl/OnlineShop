package com.onlineshop.shop.controllers;

import com.onlineshop.shop.models.Product;
import com.onlineshop.shop.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/products")
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

        return "Saved";
    }

    @GetMapping("/GetByName")
    public @ResponseBody String getByName(@RequestParam String brand) {
        var products =  productRepository.getProductsByBrandName(brand);
        String result = "";
        for (var productId : products) {
            var productOptional = productRepository.findById(productId);
            var product = productOptional.get();
            result += "id: " + product.getID() + "\n";
            result += "brand: " + product.getBrand() + "\n";
            result += "description: " + product.getDescription() + "  |  ";
        }

        return result;
    }

    @GetMapping("/all")
    public @ResponseBody Iterable<Product> getAllProducts() {
        System.out.println(productRepository.findAll().getClass());
        return productRepository.findAll();
    }

    @GetMapping("/GetById")
    public @ResponseBody String getProductById(@RequestParam int id) {
        var optionalProduct = productRepository.findById(id);
        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();
            String result = product.getBrand() + ": \n";
            result += product.getProductName() + ", " + product.getDescription();
            return result;
        }
        else
            return "ID not present";
    }
}
