package com.company;

public class Mol {
    VecR r = new VecR();
    VecR rv = new VecR();
    VecR ra = new VecR();
    public void setR(double x, double y, double z) {
        this.r.x = x;
        this.r.y = y;
        this.r.z = z;
    }
    public void setRv(double x, double y, double z) {
        this.rv.x = x;
        this.rv.y = y;
        this.rv.z = z;
    }
    public void setRa(double x, double y, double z) {
        this.ra.x = x;
        this.ra.y = y;
        this.ra.z = z;
    }
}
