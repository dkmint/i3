package com.company;

public class Gap {
    double x, y, z;

    public Gap(Region v2, InitUcell v3) {
        this.x = v2.x / v3.x;;
        this.y = v2.y / v3.y;;
        this.z = v2.z / v3.z;;
    }
}
