package com.company;

import java.util.ArrayList;

public class SetParams {
    VecI d = new VecI();
    VecR r = new VecR();
    VecR rv = new VecR();
    double dx, dy, dz;

    public double cube(double d) {
        return d * d * d;
    }
    public double vLenSq(VecR v) {
        return  v.x * v.x + v.y * v.y + v.z * v.z;
    }
    public void VSub (VecR x, VecR y) {
        this.r.x = x.x - y.x;
        this.r.y = x.y - y.y;
        this.r.z = x.z - y.z;
    }
    public void VVSub (VecR y) {
        this.r.x = this.r.x - y.x;
        this.r.y = this.r.y - y.y;
        this.r.z = this.r.z - y.z;
    }
    public void setZeroR() {
        this.r.x = 0;
        this.r.y = 0;
        this.r.z = 0;
    }
    public static void VSCopyR(VecR v2, double s1, VecI v1) {
        v2.x = s1 * v1.x;
        v2.y = s1 * v1.y;
        v2.z = s1 * v1.z;
    }
    public static void VSCopyI(VecI v2, double s1, VecR v1) {
        v2.x = (int) (s1 * v1.x);
        v2.y = (int) (s1 * v1.y);
        v2.z = (int) (s1 * v1.z);
    }
    public void VAddI(VecI m1v, VecI ofs) {
        this.d.x = m1v.x + ofs.x;
        this.d.y = m1v.y + ofs.y;
        this.d.z = m1v.z + ofs.z;
    }
    public void VDiv(Cells v2, Region v3) {
        this.r.x = v2.x / v3.x;
        this.r.y = v2.y / v3.y;
        this.r.z = v2.z / v3.z;
    }
    public int VProdI(Cells v) {
        return v.x * v.y * v.z;
    }
    public double VProdR(Region v) {
        return v.x * v.y * v.z;
    }
    public void VSet(int sx, int sy, int sz) {
        this.d.x = sx;
        this.d.y = sy;
        this.d.z = sz;
    }

    public void VMulR(VecR v2, VecR v3) {
        this.r.x = v2.x * v3.x;
        this.r.y = v2.y * v3.y;
        this.r.z = v2.z * v3.z;
    }
    public void VMulI(VecR v2, VecR v3) {
        this.d.x = (int) (v2.x * v3.x);
        this.d.y = (int) (v2.y * v3.y);
        this.d.z = (int) (v2.z * v3.z);
    }
    public void VSAdd(VecR v2, double s3, VecR v3) {
        this.rv.x = v2.x + s3 * v3.x;
        this.rv.y = v2.y + s3 * v3.y;
        this.rv.z = v2.z + s3 * v3.z;
    }
    public void addRegion(VecR v2, double s3, Region v3) {
        this.rv.x = v2.x + s3 * v3.x;
        this.rv.y = v2.y + s3 * v3.y;
        this.rv.z = v2.z + s3 * v3.z;
    }
    public void VVSAdd(VecR r, double s2, VecR v2) {
        this.r.x = this.r.x + s2 * v2.x;
        this.r.y = this.r.y + s2 * v2.y;
        this.r.z = this.r.z + s2 * v2.z;
    }
    public int setLinear(VecI cc, Cells cells) {
        return ((cc.z * cells.y) + cc.y) * cells.x + cc.x;
    }
}

