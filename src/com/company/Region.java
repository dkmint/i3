package com.company;

public class Region {
    double x, y, z;
    InitUcell initUcell = new InitUcell();

    public Region(double density, InitUcell initUcell) {
        this.x = 1./ Math.pow(density / 4., 1./3.) * initUcell.x;
        this.y = 1./ Math.pow(density / 4., 1./3.) * initUcell.y;
        this.z = 1./ Math.pow(density / 4., 1./3.) * initUcell.z;
    }
}
