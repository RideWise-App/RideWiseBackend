package com.ridewise.backend.serviceImpl;

import com.ridewise.backend.repository.OrderHaltRepository;
import org.springframework.stereotype.Service;

@Service
public class OrderHaltService {
    private OrderHaltRepository orderHaltRepository;

    OrderHaltService(OrderHaltRepository theOrderHaltRepository) {
        orderHaltRepository = theOrderHaltRepository;
    }
}
