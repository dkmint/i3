package com.company;

import java.util.ArrayList;

//import com.company.Gap;
public class InitCoords {
    Region region;
    InitUcell initUcell;
    Gap gap;
    Position position = new Position();
    ArrayList<Mol> mol = new ArrayList<>();
    int n = 0;

    public InitCoords() {
        this.region = null;
        this.initUcell = null;
        this.gap = null;
        this.position = null;
        this.mol = null;
    }
    public void getCoords(InitUcell initUcell) {
       for (int nz = 0; nz < initUcell.z; nz ++) {
           for (int ny = 0; ny < initUcell.y; ny ++) {
               for (int nx = 0; nz < initUcell.x; nx ++) {
                   position = new Position(nx + 0.5, ny + 0.5, nz + 0.5);
                   position.setGap(gap);
                   position = new Position(-0.5, region);
//                   mol.add(new Mol(position));
                   n ++;
               }
           }
       }
    }
}
