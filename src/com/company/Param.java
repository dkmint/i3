package com.company;

import java.util.ArrayList;

public class Param {
    private int flag;
    String name;
    Integer valI;
    Double valR;

    public Param(String name) {
        this.name = name;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getValI() {
        return valI;
    }

    public void setValI(Integer valI) {
        this.valI = valI;
    }

    public Double getValR() {
        return valR;
    }

    public void setValR(Double valR) {
        this.valR = valR;
    }

}
