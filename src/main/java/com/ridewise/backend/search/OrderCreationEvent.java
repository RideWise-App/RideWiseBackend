package com.ridewise.backend.search;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class OrderCreationEvent extends ApplicationEvent {
    final Long orderId;
    final double latitude;
    final double longitude;

    public OrderCreationEvent(Object source, Long orderId, double latitude, double longitude) {
        super(source);
        this.orderId = orderId;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
