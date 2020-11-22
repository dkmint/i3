package com.company;

public class PBC {
    double p;
    VecR pr = new VecR();

    double setPBC(double c, double r) {
        if (c >= 0.5 * r)
            c -= r;
        else if (c < -0.5 * r) 
            c += r;
        return c;
    }
    void applyBC(VecR c, Region v) {
        this.pr.x = setPBC(c.x, v.x);
        this.pr.y = setPBC(c.y, v.z);
        this.pr.z = setPBC(c.z, v.z);
    }
}
