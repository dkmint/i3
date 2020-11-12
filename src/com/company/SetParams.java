package com.company;

public class SetParams {
    public void VSCopyR(VecR v2, double s1, VecI v1) {
        v2.x = s1 * v1.x;
        v2.y = s1 * v1.y;
        v2.z = s1 * v1.z;
    }
    public void VSCopyI(VecI v2, double s1, VecR v1) {
        v2.x = (int) (s1 * v1.x);
        v2.y = (int) (s1 * v1.y);
        v2.z = (int) (s1 * v1.z);
    }
    public void VDiv(VecR v1, VecR v2, VecI v3) {
        v1.x = v2.x / v3.x;
        v1.y = v2.y / v3.y;
        v1.z = v2.z / v3.z;
    }
    public int VProd(VecI v) {
        return v.x * v.y * v.z;
    }
    public void VSet(VecR v, int sx, int sy, int sz) {
        v.x = sx;
        v.y = sy;
        v.z = sz;
    }

    public void VMul(VecR v1, VecR v2, VecR v3) {
        v1.x = v2.x * v3.x;
        v1.y = v2.y * v3.y;
        v1.z = v2.z * v3.z;
    }
    public void VSAdd(VecR v1, VecR v2, double s3, VecR v3) {
        v1.x = v2.x + s3 + v3.x;
        v1.y = v2.y + s3 + v3.y;
        v1.z = v2.z + s3 + v3.z;
    }
    public void VVSAdd(VecR v1, double s2, VecR v2) {
        v1.x = v1.x + s2 + v2.x;
        v1.y = v1.y + s2 + v2.y;
        v1.z = v1.z + s2 + v2.z;
    }
}

