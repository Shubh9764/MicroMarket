package com.project.orderService.service;

import com.project.orderService.dto.OrderRequest;

import com.project.orderService.model.Order;

import java.util.List;

public interface OrderService {

    void placeOrder(OrderRequest orderRequest);

    List<Order> getAllOrder();
}
