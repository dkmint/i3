package com.company;

public class VeloSum {
    double x, y, z;
    public void setZeroR() {
        this.x = 0;
        this.y = 0;
        this.z = 0;
    }
    public void setVAdd(VecR rv) {
        this.x += rv.x;
        this.y += rv.y;
        this.z += rv.z;
    }
}
