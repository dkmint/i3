package com.company;

public class Region {
    double x, y, z;

    public Region(double density, double x, double y, double z) {
        this.x = 1./ Math.pow(density / 4., 1./3.) * x;
        this.y = 1./ Math.pow(density / 4., 1./3.) * y;
        this.z = 1./ Math.pow(density / 4., 1./3.) * z;
    }
}
