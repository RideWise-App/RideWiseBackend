package com.ridewise.backend.serviceImpl;

import com.ridewise.backend.dto.LocationDto;
import com.ridewise.backend.dto.OrderDto;
import com.ridewise.backend.entity.Driver;
import com.ridewise.backend.entity.Location;
import com.ridewise.backend.entity.Order;
import com.ridewise.backend.mapper.LocationMapper;
import com.ridewise.backend.mapper.OrderMapper;
import com.ridewise.backend.repository.OrderRepository;
import com.ridewise.backend.search.OrderCreationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final ClientService clientService;
    private final LocationService locationService;
    private final DriverService driverService;
    private final ApplicationEventPublisher publisher;

    OrderService(OrderRepository theOrderRepository, ClientService clientService,
                 LocationService locationService, DriverService driverService, ApplicationEventPublisher publisher) {
        orderRepository = theOrderRepository;
        this.clientService = clientService;
        this.locationService = locationService;
        this.driverService = driverService;
        this.publisher = publisher;
    }

    public void initializeOrder(Map<String, LocationDto> locations, Authentication authentication) {
        Map<String, Location> map = LocationMapper.INSTANCE.mapToEntity(locations);
        Order order = new Order();
        order.setClient(clientService.findByEmail(authentication.getName()));
        order.setStartLocation(locationService.save(map.get("start")));
        order.setEndLocation(locationService.save(map.get("finish")));
        saveOrder(order);
        OrderCreationEvent event = new OrderCreationEvent(this, mapToDto(order),
                order.getStartLocation().getLatitude().doubleValue(),
                order.getStartLocation().getLongitude().doubleValue());
        publisher.publishEvent(event);
    }

    void saveOrder(Order order) {
        orderRepository.save(order);
    }

    public List<OrderDto> fetchOrders() {
        return availableOrders(mapListToDto(orderRepository.findAll()));
    }

    List<OrderDto> mapListToDto(List<Order> orders) {
        return OrderMapper.INSTANCE.toDtoList(orders.stream().filter(order ->
                order.getEndLocation() != null && order.getStartLocation() != null).toList());
    }

    OrderDto mapToDto(Order order) {
        return OrderMapper.INSTANCE.toDto(order);
    }

    List<OrderDto> availableOrders(List<OrderDto> orders) {
        return orders.stream().filter(order -> order.driverId() == null).toList();
    }

    public void acceptOrder(Long id, String driver) {
        Driver entity = driverService.findByEmail(driver);
        Order order = orderRepository.findById(id).orElseThrow();
        if (order.getDriverId() == null) {
            order.setDriverId(entity.getId());
        } else throw new RuntimeException("This order is taken");
    }
}
