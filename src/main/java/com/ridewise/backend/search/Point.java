package com.ridewise.backend.search;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Point {
    private Long orderId;
    private double latitude;
    private double longitude;
}
