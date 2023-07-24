package com.ridewise.backend.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ridewise.backend.search.Point;
import com.ridewise.backend.serviceImpl.SearchService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;

@Controller
public class DriverWebSocketController {

    final SearchService searchService;

    public DriverWebSocketController(SearchService searchService) {
        this.searchService = searchService;
    }

    @MessageMapping("/order.findOrders")
    @SendTo("/topic/order")
    public List<Point> sendMessage(@Payload String location) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(location);
            JsonNode locationDtoNode = rootNode.path("content");
            double latitude = locationDtoNode.path("latitude").doubleValue();
            double longitude = locationDtoNode.path("longitude").doubleValue();
            return searchService.getNearestOrders(latitude, longitude);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }
}
