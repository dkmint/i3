package com.company;

public class CellWrapAll {
    int mv, tv;
    double sh;
    VecI m2 = new VecI();
    VecR shif = new VecR();

    int cellWrapI(int m, int cell) {
        if (m >= cell) {
            mv = 0;
        }
        else if (m < 0) {
            tv = cell - 1;
            mv = tv;
        }
        return mv;
    }
    double cellWrapR(int mv, double regio) {
        if (this.mv == 0)
            this.sh = regio;
        else if (this.mv == this.tv)
            this.sh = -regio;
        return sh;
    }
    void cellWrapAllI(VecI m2v, Cells cells) {
        this.m2.x = cellWrapI(m2v.x, cells.x);
        this.m2.y = cellWrapI(m2v.y, cells.y);
        this.m2.z = cellWrapI(m2v.z, cells.z);
    }
    void cellWrapAllIR(VecI m2v, Region region) {
        this.shif.x = cellWrapR(m2v.x, region.x);
        this.shif.y = cellWrapR(m2v.y, region.y);
        this.shif.z = cellWrapR(m2v.z, region.z);
    }
}