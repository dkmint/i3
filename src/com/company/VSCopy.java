package com.company;

class VSCopy {
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
}

