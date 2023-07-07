package com.ridewise.backend.serviceImpl;

import com.ridewise.backend.dto.LocationDto;
import com.ridewise.backend.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class OrderService {
    private final OrderRepository orderRepository;

    OrderService(OrderRepository theOrderRepository) {
        orderRepository = theOrderRepository;
    }

    public void initializeOrder(Map<String, LocationDto> locations) {
    }
}
