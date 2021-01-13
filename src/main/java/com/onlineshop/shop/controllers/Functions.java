package com.onlineshop.shop.controllers;

import com.onlineshop.shop.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class Functions {
    @Autowired
    ProductRepository productRepository;

    @GetMapping("/settings")
    public String getSettingsPage() {
        return "settings";
    }

    @GetMapping("/account")
    public String getAccountPage() {
        return "account";
    }

    @GetMapping("/products")
    public String getProductsPage() {
        return "products";
    }

    @GetMapping("/sales")
    public @ResponseBody String getSalesPage() {
        return new HTMLPageCreator().createSalesPage(productRepository);
    }

    @GetMapping("/index")
    public String getIndex() {
        return "index";
    }

    @GetMapping("/search")
    public @ResponseBody String getSearchPage(@RequestParam String value,
                                              @RequestParam(defaultValue = "1") String page,
                                              @RequestParam(defaultValue = "pop") String order) {

        return new HTMLPageCreator().createSearchPage(value, order, productRepository, Integer.parseInt(page));
    }

    @GetMapping("/temp")
    public String temp() {
        return "temp";
    }

    @GetMapping("/product")
    public @ResponseBody String  getProductPage(@RequestParam int id) {
        return new HTMLPageCreator().createProductPage(id, productRepository);
    }


}
