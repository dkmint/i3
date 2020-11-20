package com.company;

public class Region {
    double x, y, z;
    String structName;
    InitUcell initUcell = new InitUcell();
    int nMol;
    public Region() {
        this.x = 0;
        this.y = 0;
        this.z = 0;
        this.nMol = 0;
        this.structName = null;
    }
    public Region(double density, InitUcell initUcell, String structName) {
        if (structName.equals("fcc")) {
            this.x = 1. / Math.pow(density / 4., 1. / 3.) * initUcell.x;
            this.y = 1. / Math.pow(density / 4., 1. / 3.) * initUcell.y;
            this.z = 1. / Math.pow(density / 4., 1. / 3.) * initUcell.z;
            this.nMol = 4 * initUcell.volume();
        }
        else if (structName.equals("bcc")) {
            this.x = 1. / Math.pow(density / 2., 1. / 3.) * initUcell.x;
            this.y = 1. / Math.pow(density / 2., 1. / 3.) * initUcell.y;
            this.z = 1. / Math.pow(density / 2., 1. / 3.) * initUcell.z;
            this.nMol = 2 * initUcell.volume();
        }
        else if (structName.equals("sc")) {
            this.x = 1. / Math.pow(density, 1. / 3.) * initUcell.x;
            this.y = 1. / Math.pow(density, 1. / 3.) * initUcell.y;
            this.z = 1. / Math.pow(density, 1. / 3.) * initUcell.z;
            this.nMol = initUcell.volume();
        }
        else if (structName.equals("diamand")) {
            this.x = 1. / Math.pow(density / 8., 1. / 3.) * initUcell.x;
            this.y = 1. / Math.pow(density / 8., 1. / 3.) * initUcell.y;
            this.z = 1. / Math.pow(density / 8., 1. / 3.) * initUcell.z;
            this.nMol = 8 * initUcell.volume();
        }
    }
}
