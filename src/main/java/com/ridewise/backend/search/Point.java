package com.ridewise.backend.search;

import com.ridewise.backend.dto.OrderDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Point {
    private OrderDto order;
    private double latitude;
    private double longitude;

    public Point(OrderCreationEvent event) {
        this.order = event.getOrder();
        this.latitude = event.getLatitude();
        this.longitude = event.getLongitude();
    }
}
