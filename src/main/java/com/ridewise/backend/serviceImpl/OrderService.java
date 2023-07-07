package com.ridewise.backend.serviceImpl;

import com.ridewise.backend.dto.LocationDto;
import com.ridewise.backend.entity.Location;
import com.ridewise.backend.entity.Order;
import com.ridewise.backend.mapper.LocationMapper;
import com.ridewise.backend.repository.OrderRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final ClientService clientService;
    private final LocationService locationService;

    OrderService(OrderRepository theOrderRepository, ClientService clientService, LocationService locationService) {
        orderRepository = theOrderRepository;
        this.clientService = clientService;
        this.locationService = locationService;
    }

    public void initializeOrder(Map<String, LocationDto> locations, Authentication authentication) {
        Map<String, Location> map = LocationMapper.INSTANCE.mapToEntity(locations);
        Order order = new Order();
        order.setClient(clientService.findByEmail(authentication.getName()));
        order.setStartLocation(locationService.save(map.get("start")));
        order.setEndLocation(locationService.save(map.get("finish")));
        saveOrder(order);
        System.out.println(order);
    }

    void saveOrder(Order order) {
        orderRepository.save(order);
    }
}
