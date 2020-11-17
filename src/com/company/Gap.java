package com.company;

public class Gap {
    double x, y, z;
    InitUcell initUcell = new InitUcell();

    public Gap() {
        this.x = 0;
        this.y = 0;
        this.z = 0;
    }
    public Gap(Region region, InitUcell initUcell) {
        this.x = region.x / initUcell.x;
        this.y = region.y / initUcell.y;
        this.z = region.z / initUcell.z;
    }
}
