package com.onlineshop.shop.controllers;

import com.onlineshop.shop.models.User;
import com.onlineshop.shop.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.sql.Date;

@Controller
@RequestMapping(path="/users")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @PostMapping(path="/add")
    public @ResponseBody String addNewUser (@RequestParam String name,
                                            @RequestParam String lname,
                                            @RequestParam String email,
                                            @RequestParam String username) {
        User user = new User();
        user.setUsername(username);
        user.setEmailAddress(email);
        user.setFirstName(name);
        user.setLastName(lname);
        user.setPassword("Hasło");
        user.setSex("Male");
        user.setDateOfBirth(Date.valueOf(LocalDate.of(2000, 12, 20)));
        userRepository.save(user);

        return "Zapisano";
    }

    @GetMapping("/all")
    public @ResponseBody Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }
}
