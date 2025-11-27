package com.early_express.hub_service.hubservice.domain.entity.route;

import java.util.List;

public class DistanceCalculator {

    private static final double EARTH_RADIUS_KM = 6371;

    public static double haversine(List<Double> coord1, List<Double> coord2) {

        double lat1 = Math.toRadians(coord1.get(1));
        double lon1 = Math.toRadians(coord1.get(0));
        double lat2 = Math.toRadians(coord2.get(1));
        double lon2 = Math.toRadians(coord2.get(0));

        double dlat = lat2 - lat1;
        double dlon = lon2 - lon1;

        double a = Math.sin(dlat / 2) * Math.sin(dlat / 2) +
                Math.cos(lat1) * Math.cos(lat2) * Math.sin(dlon / 2) * Math.sin(dlon / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return EARTH_RADIUS_KM * c;
    }
}
