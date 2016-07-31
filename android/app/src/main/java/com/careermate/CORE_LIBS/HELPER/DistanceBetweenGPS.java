package com.careermate.CORE_LIBS.HELPER;

/**
 * Created by TuAnh on 2/19/2016.
 */
public class DistanceBetweenGPS {
    public static final double d2r = 0.0174532925199433;

    public static double DistanceGPS(double lat1, double long1, double lat2, double long2) {
        double dlong = (long2 - long1) * d2r;
        double dlat = (lat2 - lat1) * d2r;
        double a = Math.pow(Math.sin(dlat / 2.0), 2) + Math.cos(lat1 * d2r) * Math.cos(lat2 * d2r) * Math.pow(Math.sin(dlong / 2.0), 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double d = 6367 * c;
        return d;//km
    }
}
