package com.company;

public class Mol {
    VecR r = new VecR();
    VecR rv = new VecR();
    VecR ra = new VecR();
    public void setZeroR() {
        this.r.x = 0;
        this.r.y = 0;
        this.r.z = 0;
    }
    public void setZeroRV() {
        this.rv.x = 0;
        this.rv.y = 0;
        this.rv.z = 0;
    }
    public void setZeroRA() {
        this.ra.x = 0;
        this.ra.y = 0;
        this.ra.z = 0;
    }
    public void setAvgVel(double d, VeloSum s) {
        this.rv.x += d * s.x;
        this.rv.y += d * s.y;
        this.rv.z += d * s.z;
    }

}
