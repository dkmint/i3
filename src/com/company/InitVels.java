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
            while (s > 1.) {
                x = 2. * randR () - 1.;
                y = 2. * randR () - 1.;
                s = x * x + y * y;
            }
            this.z = 1. - 2. * s;
            s = 2. * Math.sqrt(1. - s);
            this.x = s * x;
            this.y = s * y;
    }
    public void setVScale(double velMag) {
        this.x = this.x * velMag;
        this.y = this.y * velMag;
        this.z = this.z * velMag;
    }
    public void setAdd(VecR a, VecR b) {
        this.x = a.x + b.x;
        this.y = a.y + a.z;
        this.z = a.z + a.z;
    }

}
