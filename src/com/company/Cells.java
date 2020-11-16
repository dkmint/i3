package com.company;

public class Cells {
    int x, y, z;

    public Cells(double rCut, double rNebrShell, Region region) {
        this.x = (int) (1. / (rCut + rNebrShell) * region.x);
        this.y = (int) (1. / (rCut + rNebrShell) * region.y);
        this.z = (int) (1. / (rCut + rNebrShell) * region.z);
    }

}
