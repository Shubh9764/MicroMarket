package com.project.orderService.controller;

import com.project.orderService.dto.OrderRequest;
import com.project.orderService.model.Order;
import com.project.orderService.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order")
public class OrderController {
    @Autowired
    private OrderService orderService;
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String placeOrder(@RequestBody OrderRequest orderRequest){
        orderService.placeOrder(orderRequest);
        return "order Placed Succefully";
    }
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Order> getAllOrder(){
        return orderService.getAllOrder();
    }

}
