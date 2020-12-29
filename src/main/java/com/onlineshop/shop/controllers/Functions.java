package com.onlineshop.shop.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
/**
 * Class, containing some mapping functions,
 * to push html pages
 */
public class Functions {
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

    @GetMapping("/search")
    public String getSearchPage() {
        return "search";
    }

    @GetMapping("/sales")
    public String getSalesPage() {
        return "sales";
    }

    @GetMapping("/index")
    public String getIndex() {
        return "index";
    }

}
