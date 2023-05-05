package com.ridewise.backend.service;

import com.ridewise.backend.repository.OrderStatusRepository;
import org.springframework.stereotype.Service;

@Service
public class OrderStatusService {
    private OrderStatusRepository orderStatusRepository;

    OrderStatusService(OrderStatusRepository theOrderStatusRepository) {
        orderStatusRepository = theOrderStatusRepository;
    }
}
