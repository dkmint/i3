package com.company;

import java.util.ArrayList;

public class Coords {
    double x, y, z;
    double gapX, gapY, gapZ;
//    ArrayList<VecR> vec = new ArrayList<>();

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
        this.x = this.x * this.gapX;
        this.y = this.y * this.gapY;
        this.z = this.z * this.gapZ;
    }
    public void setCoordsRegion(double s2, Region region) {
        this.x = this.x + s2 + region.x;
        this.y = this.y + s2 + region.y;
        this.z = this.z + s2 + region.z;
    }
//    public ArrayList<VecR> getStructure() {
//        return this.;
//    }
//    public void setStructure() {
//    InitUcell initUcell = new InitUcell();
//    Region region = new Region();
//        int n = 0;
//        for (int nz = 0; nz < initUcell.z; nz ++) {
//            for (int ny = 0; ny < initUcell.y; ny ++) {
//                for (int nx = 0; nx < initUcell.x; nx ++) {
//                    setInitCoords(nx + 0.5, ny + 0.5, nz + 0.5);
//                    setCoordsGap();
//                    setCoordsRegion(-0.5, region);
//                    this.vec.add(new VecR());
//                    this.vec.get(n).x = this.x;
//                    this.vec.get(n).y = this.y;
//                    this.vec.get(n).z = this.z;
//                    n ++;
//                    System.out.println("this.vec.x " + this.x);
//                }
//            }
//        }
//    }
}
