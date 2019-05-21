package com.jakeesveld.vapebiblejuicecalc.Models;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.jakeesveld.vapebiblejuicecalc.DAO.StorageDAO;

@Entity
public class DBRecipe {
    @NonNull
    @PrimaryKey
    private String name;
    private int PG, VG, nic, bottleSize;
    private String flavorNames;
    private String flavorAmounts;
    private String baseNic;
    private String userId;

    public DBRecipe(Recipe recipe) {
        this.name = recipe.getName();
        this.PG = recipe.getPG();
        this.VG = recipe.getVG();
        this.nic = recipe.getNic();
        this.bottleSize = recipe.getBottleSize();
        StringBuilder builder = new StringBuilder();
        StringBuilder amountBuilder = new StringBuilder();
        for(Flavor flavor: recipe.getFlavors()){
            builder.append(flavor.getName()).append(",");
            amountBuilder.append(String.valueOf(flavor.getAmount())).append(",");
        }
        this.flavorNames = builder.toString();
        this.flavorAmounts = amountBuilder.toString();
        StringBuilder baseBuilder = new StringBuilder();
        baseBuilder.append(String.valueOf(recipe.getBaseNic().getStrength()))
                .append(",")
                .append(String.valueOf(recipe.getBaseNic().getVG()))
                .append(",")
                .append(String.valueOf(recipe.getBaseNic().getPG()));
        this.baseNic = baseBuilder.toString();
        try{
            this.userId = StorageDAO.user.getUid();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public DBRecipe() {
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
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

    public int getBottleSize() {
        return bottleSize;
    }

    public void setBottleSize(int bottleSize) {
        this.bottleSize = bottleSize;
    }

    public String getFlavorNames() {
        return flavorNames;
    }

    public void setFlavorNames(String flavorNames) {
        this.flavorNames = flavorNames;
    }

    public String getFlavorAmounts() {
        return flavorAmounts;
    }

    public void setFlavorAmounts(String flavorAmounts) {
        this.flavorAmounts = flavorAmounts;
    }

    public String getBaseNic() {
        return baseNic;
    }

    public void setBaseNic(String baseNic) {
        this.baseNic = baseNic;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
