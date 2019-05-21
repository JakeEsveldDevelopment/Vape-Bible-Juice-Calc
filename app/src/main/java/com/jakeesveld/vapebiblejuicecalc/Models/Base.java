package com.jakeesveld.vapebiblejuicecalc.Models;

import java.io.Serializable;

public class Base implements Serializable {
    private int VG, PG, strength;

    public Base(int VG, int PG, int strength) {
        this.VG = VG;
        this.PG = PG;
        this.strength = strength;
    }

    public Base() {
    }

    public int getVG() {
        return VG;
    }

    public void setVG(int VG) {
        this.VG = VG;
    }

    public int getPG() {
        return PG;
    }

    public void setPG(int PG) {
        this.PG = PG;
    }

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }
}
