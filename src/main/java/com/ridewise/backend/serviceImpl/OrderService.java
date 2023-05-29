package com.ridewise.backend.serviceImpl;

import com.ridewise.backend.repository.OrderRepository;
import org.springframework.stereotype.Service;

@Service
public class OrderService {
    private OrderRepository orderRepository;

    OrderService(OrderRepository theOrderRepository) {
        orderRepository = theOrderRepository;
    }
}
