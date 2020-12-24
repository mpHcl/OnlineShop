package com.onlineshop.shop.controllers;

import com.onlineshop.shop.models.Orders;
import com.onlineshop.shop.models.Product;
import com.onlineshop.shop.models.User;
import com.onlineshop.shop.repositories.OrdersRepository;
import com.onlineshop.shop.repositories.ProductRepository;
import com.onlineshop.shop.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.time.LocalDate;

@Controller
@RequestMapping("/orders")
public class OrderController {
    @Autowired
    private OrdersRepository ordersRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private UserRepository userRepository;

    @PostMapping("/add")
    private @ResponseBody String addOrder(@RequestParam int userId,
                                          @RequestParam int productId,
                                          @RequestParam String phoneNumber,
                                          @RequestParam String shippingAddress,
                                          @RequestParam String paymentMethod,
                                          @RequestParam String comments) {
        Orders order = new Orders();
        var product = productRepository.findById(productId);
        var user = userRepository.findById(userId);
        if (user.isEmpty() || product.isEmpty())
            return "Błąd zapisu";
        order.setComments(comments);
        order.setProduct(product.get());
        order.setUser(user.get());
        order.setDateOfPurchase(Date.valueOf(LocalDate.now()));
        order.setDateOfShipping(Date.valueOf(LocalDate.now().plusDays(10)));
        order.setIpAddress("0.0.0.0");
        order.setNotifications(false);
        order.setRealized(false);
        order.setPaymentMethod(paymentMethod);
        order.setPhoneNumber(phoneNumber);
        order.setShippingAddress(shippingAddress);
        order.setStatus("waiting");
        ordersRepository.save(order);
        return "Zapisano";
    }

    @GetMapping("/all")
    public @ResponseBody Iterable<Orders> getAllOrders() {
        return ordersRepository.findAll();
    }
}
