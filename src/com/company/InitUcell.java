package com.company;

public class InitUcell {
    int x, y, z;

    public InitUcell() {
        this.x = 0;
        this.y = 0;
        this.z = 0;
    }

    public int volume() {
        return x * y * z;
    }
}
