package com.onlineshop.shop.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class Functions {
    @GetMapping("users/home")
    public String getUsersHtmlPage() {
        return "users/home";
    }

    @GetMapping("/userAddForm")
    public String getUserAddForm() {
        return "users/add_forms";
    }

}
