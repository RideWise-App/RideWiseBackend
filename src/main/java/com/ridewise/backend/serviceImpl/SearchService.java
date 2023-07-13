package com.ridewise.backend.serviceImpl;

import com.ridewise.backend.search.OrderCreationEvent;
import com.ridewise.backend.search.Point;
import com.ridewise.backend.search.SpatialIndex;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchService implements ApplicationListener<OrderCreationEvent> {
    private final SpatialIndex spatialIndex;

    public SearchService(SpatialIndex spatialIndex) {
        this.spatialIndex = spatialIndex;
    }


    @Override
    public void onApplicationEvent(OrderCreationEvent event) {
        spatialIndex.addPoint(new Point(event));
    }

    public void addPoint(Point point) {
        spatialIndex.addPoint(point);
    }

    public List<Point> getNearestOrders(double latitude, double longitude) {
        return spatialIndex.findPointsInRange(new Point(null, latitude, longitude), 3000);
    }

    public List<Point> getAll() {
        return spatialIndex.points();
    }
}
