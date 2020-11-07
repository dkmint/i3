package com.company;

import java.util.ArrayList;

public class UCell {
    String name;
    Integer x, y, z;
    int size;

    public UCell() {
       
    }

    public UCell(String name, Integer x, Integer y, int size) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.size = size;
    }

    public UCell(String name, Integer x, Integer y, Integer z, int size) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.z = z;
        this.size = size;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getX() {
        return x;
    }

    public void setX(Integer x) {
        this.x = x;
    }

    public Integer getY() {
        return y;
    }

    public void setY(Integer y) {
        this.y = y;
    }

    public Integer getZ() {
        return z;
    }

    public void setZ(Integer z) {
        this.z = z;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
