package com.company;

public class InitUcell {
    int x, y, z;
    int volume;

    public InitUcell() {
        this.x = 0;
        this.y = 0;
        this.z = 0;
        this.volume = 0;
    }

//    public void setVolume(int x, int y, int z) {
//        this.volume = x * y * z;
//    }

    public int volume() {
        return x * y * z;
    }
}
