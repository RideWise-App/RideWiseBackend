package com.ridewise.backend.controller;

import com.ridewise.backend.search.Point;
import com.ridewise.backend.serviceImpl.SearchService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/search")
record SearchController(SearchService service) {

    @PostMapping("/add")
    ResponseEntity<HttpStatus> addPoint(@RequestBody Point point) {
        service.addPoint(point);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("")
    ResponseEntity<List<Point>> searchForNearbyOrders(@RequestParam double lat, @RequestParam double lon) {
        List<Point> points = service.getNearestOrders(lat, lon);
        return new ResponseEntity<>(points, HttpStatus.OK);
    }

    @GetMapping("/all")
    ResponseEntity<List<Point>> getAll() {
        return new ResponseEntity<>(service.getAll(), HttpStatus.OK);
    }
}
