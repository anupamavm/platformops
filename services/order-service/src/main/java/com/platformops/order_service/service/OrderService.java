package com.platformops.order_service.service;

import com.platformops.order_service.entity.Order;
import com.platformops.order_service.repository.OrderRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepository repository;

    public OrderService(OrderRepository repository) {
        this.repository = repository;
    }

    public Order createOrder(Order order) {
        order.setStatus("CREATED");
        order.setCreatedAt(LocalDateTime.now());
        return repository.save(order);
    }

    public Order getOrder(Long id) {
        return repository.findById(id).orElse(null);
    }

    public List<Order> getOrdersByUser(Long userId) {
        return repository.findByUserId(userId);
    }
}