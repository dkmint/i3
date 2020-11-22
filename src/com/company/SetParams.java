package com.company;

public class SetParams {
    VecR r = new VecR();
    VecR rv = new VecR();
//    double x, y, z;
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
    public static void VDiv(VecR v1, VecR v2, VecI v3) {
        v1.x = v2.x / v3.x;
        v1.y = v2.y / v3.y;
        v1.z = v2.z / v3.z;
    }
    public static int VProd(VecI v) {
        return v.x * v.y * v.z;
    }
    public static void VSet(VecR v, int sx, int sy, int sz) {
        v.x = sx;
        v.y = sy;
        v.z = sz;
    }

    public static void VMul(VecR v1, VecR v2, VecR v3) {
        v1.x = v2.x * v3.x;
        v1.y = v2.y * v3.y;
        v1.z = v2.z * v3.z;
    }
    public void VSAdd(VecR v2, double s3, VecR v3) {
        this.rv.x = v2.x + s3 * v3.x;
        this.rv.y = v2.y + s3 * v3.y;
        this.rv.z = v2.z + s3 * v3.z;
    }
    public void VVSAdd(VecR r, double s2, VecR v2) {
        this.r.x = r.x + s2 * v2.x;
        this.r.y = r.y + s2 * v2.y;
        this.r.z = r.z + s2 * v2.z;
    }
//    public void VVSAdd(VecR v1, double s2, VecR v2) {
//        VSAdd(v1, v1, s2, v2);
//    }
}

