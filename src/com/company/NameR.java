package com.company;

public class NameR {
    String vName;
    Double vValue;
    int vStatus = 0;

    public NameR(String vName, Double vValue, int vStatus) {
        this.vName = vName;
        this.vValue = vValue;
        this.vStatus = vStatus;
    }

    public int getvStatus() {
        return vStatus;
    }

    public void setvStatus(int vStatus) {
        this.vStatus = vStatus;
    }

    public void setvName(String vName) {
        this.vName = vName;
    }

    public void setvValue(Double vValue) {
        this.vValue = vValue;
    }

    public String getvName() {
        return vName;
    }

    public Double getvValue() {
        return vValue;
    }
}
