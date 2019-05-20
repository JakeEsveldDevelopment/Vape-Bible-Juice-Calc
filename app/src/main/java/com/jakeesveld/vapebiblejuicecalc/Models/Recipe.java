package com.jakeesveld.vapebiblejuicecalc.Models;

import java.util.ArrayList;

public class Recipe {
    private int PG, VG, nic;
    private String name;
    private ArrayList<Flavor> flavors;
    private Base baseNic;

    public Recipe(int PG, int VG, int nic, String name, ArrayList<Flavor> flavors, Base baseNic) {
        this.PG = PG;
        this.VG = VG;
        this.nic = nic;
        this.name = name;
        this.flavors = flavors;
        this.baseNic = baseNic;
    }

    public Recipe() {
        this.flavors = new ArrayList<>();
    }

    public int getPG() {
        return PG;
    }

    public void setPG(int PG) {
        this.PG = PG;
    }

    public int getVG() {
        return VG;
    }

    public void setVG(int VG) {
        this.VG = VG;
    }

    public int getNic() {
        return nic;
    }

    public void setNic(int nic) {
        this.nic = nic;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Flavor> getFlavors() {
        return flavors;
    }

    public void setFlavors(ArrayList<Flavor> flavors) {
        this.flavors = flavors;
    }

    public Base getBaseNic() {
        return baseNic;
    }

    public void setBaseNic(Base baseNic) {
        this.baseNic = baseNic;
    }

    public void addFlavor(Flavor flavor){
        this.flavors.add(flavor);
    }
}
