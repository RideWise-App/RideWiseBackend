package com.ridewise.backend.search;

import ch.hsr.geohash.GeoHash;
import ch.hsr.geohash.WGS84Point;
import ch.hsr.geohash.util.VincentyGeodesy;

import java.util.ArrayList;
import java.util.List;


public record SpatialIndex(List<Point> points, int precision) {

    public void addPoint(Point point) {
        points.add(point);
    }

    public List<Point> findPointsInRange(Point centerPoint, double range) {
        List<Point> pointsInRange = new ArrayList<>();

        String centerGeoHash = GeoHash.geoHashStringWithCharacterPrecision(
                centerPoint.getLatitude(), centerPoint.getLongitude(), precision);

        double rangeInMeters = range * 1000;

        for (Point point : points) {
            double distance = calculateDistance(centerPoint, point);
            if (distance <= rangeInMeters) {
                String pointGeoHash = GeoHash.geoHashStringWithCharacterPrecision(
                        point.getLatitude(), point.getLongitude(), precision);

                if (isGeohashInRange(centerGeoHash, pointGeoHash, range)) {
                    pointsInRange.add(point);
                }
            }
        }

        return pointsInRange;
    }

    private double calculateDistance(Point point1, Point point2) {
        WGS84Point wgs84Point1 = new WGS84Point(point1.getLatitude(), point1.getLongitude());
        WGS84Point wgs84Point2 = new WGS84Point(point2.getLatitude(), point2.getLongitude());

        return VincentyGeodesy.distanceInMeters(wgs84Point1, wgs84Point2);
    }

    private boolean isGeohashInRange(String centerGeohash, String pointGeohash, double range) {
        GeoHash centerHash = GeoHash.fromGeohashString(centerGeohash);
        GeoHash pointHash = GeoHash.fromGeohashString(pointGeohash);

        double distance = VincentyGeodesy.distanceInMeters(
                centerHash.getBoundingBoxCenter(), pointHash.getBoundingBoxCenter());

        return distance <= range;
    }

}
