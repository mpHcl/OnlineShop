package com.onlineshop.shop.controllers;

import com.onlineshop.shop.models.User;
import com.onlineshop.shop.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.sql.Date;


/**
 * Managing users account via HTTP requests
 * It will be replaced in near future with Spring Security, HTML forms
 */
@Controller
@RequestMapping(path="/users")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @PostMapping(path="/add")
    public @ResponseBody String addNewUser (@RequestParam String fname,
                                            @RequestParam String lname,
                                            @RequestParam String email,
                                            @RequestParam String username,
                                            HttpServletRequest request) {

        User user = new User(fname, lname, username, email, "########");
        userRepository.save(user);

        System.out.println(request.getLocalAddr());

        return "save";
    }

    @GetMapping("/all")
    public @ResponseBody Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }
}
