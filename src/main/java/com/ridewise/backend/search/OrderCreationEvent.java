package com.ridewise.backend.search;

import com.ridewise.backend.dto.OrderDto;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class OrderCreationEvent extends ApplicationEvent {
    final OrderDto order;
    final double latitude;
    final double longitude;

    public OrderCreationEvent(Object source, OrderDto order, double latitude, double longitude) {
        super(source);
        this.order = order;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
