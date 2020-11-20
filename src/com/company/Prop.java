package com.company;

public class Prop {
    double val, sum, sum2;
    void propZero() {
        this.sum = 0;
        this.sum2 = 0;
    }
    void propAccum() {
        this.sum += this.val;
        this.sum2 += this.val * this.val;
    }
    void propAvg(int n) {
        this.sum /= n;
        this.sum2 = Math.sqrt(Math.max(this.sum2 / (double) n - (this.sum * this.sum), 0.));
    }
    public double getPropEst (int flag) {
        if (flag == 1)
            return this.sum;
        else if (flag == 2)
            return this.sum2;
        else
            System.out.printf("Error! Flag value must be 1 or 2");
        return 0;
    }

}

