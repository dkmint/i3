package com.company;

public class Coords {
    double x, y, z;
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
    public void setCoordsGap(Gap gap) {
        this.x = this.x * gap.x;
        this.y = this.y * gap.y;
        this.z = this.z * gap.z;
    }
    public void setCoordsRegion(double s2, Region region) {
        this.x = this.x + s2 + region.x;
        this.y = this.y + s2 + region.y;
        this.z = this.z + s2 + region.z;
    }

}
