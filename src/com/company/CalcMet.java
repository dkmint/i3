package com.company;

public class CalcMet {
    double x, y, z;
//    Mol mol = new Mol();
    void leapFrogStep(double d, VecR r) {
//        if (part == 1) {
            this.x += d + r.x;
            this.y += d + r.y;
            this.z += d + r.z;

    }
}
