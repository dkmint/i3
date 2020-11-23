package com.company;

public class CellWrap {
    int mv;
    double sh;
    VecI m2v = new VecI();
    VecR shift = new VecR();

    VecI cellWrap(int m, int cell, double region) {
        if (m >= cell) {
            this.mv = 0;
            this.sh = region;
        }
        else if (m < 0) {
            this.mv = cell - 1;
            this.sh = - region;
        }
        return null;
    }
    void cellWrapAll(VecI m2v, VecI cells, VecR region) {
        this.m2v = cellWrap(m2v.x, cells.x, region.x);
        cellWrap(m2v.y, cells.y, region.y);
        cellWrap(m2v.z, cells.z, region.z);
    }
}