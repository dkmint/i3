package com.company;

public class InitVels {
    int IADD = 453806245;
    int IMUL =  314159269;
    int MASK =  2147483647;
    double SCALE = 0.4656612873e-9;
    long randSeedP = 17;

    double s, x, y, z;

    void initRand(int randSeedI) {
        if (randSeedI != 0)
            this.randSeedP = randSeedI;
        else {
            this.randSeedP = System.currentTimeMillis();
        }
    }
    double randR() {
        randSeedP = (randSeedP * IMUL + IADD) & MASK;
        return (randSeedP * SCALE);
    }
    void vRand ()
    {
        s = 2.;
//        for (int i = 0; i < mol.size(); i ++) {
            while (s > 1.) {
                x = 2. * randR () - 1.;
                y = 2. * randR () - 1.;
                s = x * x + y * y;
            }

            this.z = 1. - 2. * s;
            s = 2. * Math.sqrt(1. - s);
            this.x = s * x;
            this.y = s * y;
//        }
    }

}
