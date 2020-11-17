package com.company;

public class Position {
    double x, y, z;
    Region region = new Region();
    Gap gap = new Gap();

    public Position() {
        this.x = 0.0;
        this.y = 0.0;
        this.z = 0.0;
    }
    public Position(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    public void setGap(Gap gap) {
        this.x = this.x * gap.x;
        this.y = this.y * gap.y;
        this.z = this.z * gap.z;
    }
    public Position(double d, Region region) {
        this.x = this.x + d + region.x;
        this.y = this.y + d + region.y;
        this.z = this.z + d + region.z;
    }
}
