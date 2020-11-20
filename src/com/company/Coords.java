package com.company;

public class Coords {
    double x, y, z;
    double gapX, gapY, gapZ;
//    ArrayList<Mol> mol = new ArrayList<>();

    public Coords() {
        this.x = 0;
        this.y = 0;
        this.z = 0;
    }
    public void setInitCoords(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    public void setGap(Region v2, InitUcell v3) {
        this.gapX = v2.x / v3.x;;
        this.gapY = v2.y / v3.y;;
        this.gapZ = v2.z / v3.z;;
    }
    public void setCoordsGap() {
        this.x = this.x * gapX;
        this.y = this.y * gapY;
        this.z = this.z * gapZ;
    }
    public void setCoordsRegion(double s2, Region region) {
        this.x = this.x + s2 + region.x;
        this.y = this.y + s2 + region.y;
        this.z = this.z + s2 + region.z;
    }

}
