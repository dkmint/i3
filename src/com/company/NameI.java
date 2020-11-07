package com.company;

import java.util.ArrayList;

public class NameI {
    String vName;
    Integer vValue;
    int vStatus = 0;

    public NameI(String vName, Integer vValue) {
        this.vName = vName;
        this.vValue = vValue;
    }

    public int getvStatus() {
        return vStatus;
    }

    public void setvStatus(int vStatus) {
        this.vStatus = vStatus;
    }

    public String getvName() {
        return vName;
    }

    public void setvName(String vName) {
        this.vName = vName;
    }

    public Integer getvValue() {
        return vValue;
    }

    public void setvValue(Integer vValue) {
        this.vValue = vValue;
    }
}
