package com.jakeesveld.vapebiblejuicecalc.Models;

import android.content.Intent;

import java.io.Serializable;
import java.util.ArrayList;

public class Recipe implements Serializable {
    private int PG, VG, nic, bottleSize;
    private String name;
    private ArrayList<Flavor> flavors;
    private Base baseNic;
    private String profile;

    public Recipe(DBRecipe dbRecipe){
        this.PG = dbRecipe.getPG();
        this.VG = dbRecipe.getVG();
        this.nic = dbRecipe.getNic();
        this.bottleSize = dbRecipe.getBottleSize();
        this.name = dbRecipe.getName();
        this.profile = dbRecipe.getProfile();
        String flavorNamesString = dbRecipe.getFlavorNames();
        String flavorAmountsString = dbRecipe.getFlavorAmounts();
        this.flavors = new ArrayList<>();
        if(!flavorNamesString.equals("") && !flavorAmountsString.equals("")) {
            String[] flavorNamesArray = flavorNamesString.split(",");
            String[] flavorAmountsArray = flavorAmountsString.split(",");
            for(int i = 0; i < flavorNamesArray.length; ++i){
                Flavor flavor = new Flavor(flavorNamesArray[i], Float.valueOf(flavorAmountsArray[i]));
                this.flavors.add(flavor);
            }
        }
        String baseNicString = dbRecipe.getBaseNic();
        String[] baseNicArray = baseNicString.split(",");
        this.baseNic = new Base(Integer.parseInt(baseNicArray[1]), Integer.parseInt(baseNicArray[2]), Integer.parseInt(baseNicArray[0]));
    }

    public Recipe(int PG, int VG, int nic, String name, ArrayList<Flavor> flavors, Base baseNic, int bottleSize) {
        this.PG = PG;
        this.VG = VG;
        this.nic = nic;
        this.name = name;
        this.flavors = flavors;
        this.baseNic = baseNic;
        this.bottleSize = bottleSize;
    }
    public RecipeResult calculateResults(){
        float resultNic = (this.bottleSize * (0.01f * this.nic) / (0.01f * this.baseNic.getStrength()));
        float resultVG = ((this.VG * 0.01f) * this.bottleSize) - ((this.baseNic.getVG() * 0.01f) * resultNic);
        ArrayList<Float> flavorAmounts = new ArrayList<>();
        float totalFlavorAmounts = 0.0f;
        for(int i = 0; i < flavors.size(); ++i){
            float flavorAmount = (flavors.get(i).getAmount() * 0.01f) * this.bottleSize;
            flavorAmounts.add(flavorAmount);
            totalFlavorAmounts += flavorAmount;
        }
        float resultPG = (((this.PG * 0.01f) * this.bottleSize) - ((this.baseNic.getPG() * 0.01f) * resultNic)) - totalFlavorAmounts;

        RecipeResult result = new RecipeResult(resultVG, resultPG, resultNic, flavorAmounts);
        return result;
    }

    public boolean checkCorrectPG(){
        float totalAmountPG = 0.0f;
        for(int i = 0; i < flavors.size(); ++i){
            float flavorAmount = (flavors.get(i).getAmount() * 0.01f) * this.bottleSize;
            totalAmountPG += flavorAmount;
        }
        totalAmountPG += ((this.baseNic.getPG() * 0.01f) * (this.nic * 0.01f)) * this.bottleSize;
        if(totalAmountPG <= ((this.PG * 0.01) * this.bottleSize)){
            return true;
        }else{
            return false;
        }
    }

    public Recipe() {
        this.flavors = new ArrayList<>();
        this.name = "";
        this.profile = "";
    }

    public int getBottleSize() {
        return bottleSize;
    }

    public void setBottleSize(int bottleSize) {
        this.bottleSize = bottleSize;
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

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }
}
