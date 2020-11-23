package com.company;

public class ApplyBC {
    VecR pr = new VecR();

    double setBC(double c, double r) {
        if (c >= 0.5 * r)
            c -= r;
        else if (c < -0.5 * r) 
            c += r;
        return c;
    }
    void setBCtoAll(VecR c, Region v) {
        this.pr.x = setBC(c.x, v.x);
        this.pr.y = setBC(c.y, v.z);
        this.pr.z = setBC(c.z, v.z);
    }
}
