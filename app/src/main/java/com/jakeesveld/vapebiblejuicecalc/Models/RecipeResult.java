package com.jakeesveld.vapebiblejuicecalc.Models;

import java.util.ArrayList;

public class RecipeResult {
    private float VG, PG, nic;
    ArrayList<Float> flavorAmounts;

    public RecipeResult(float VG, float PG, float nic, ArrayList<Float> flavorAmounts) {
        this.VG = VG;
        this.PG = PG;
        this.nic = nic;
        this.flavorAmounts = flavorAmounts;
    }

    public float getVG() {
        return VG;
    }

    public float getPG() {
        return PG;
    }

    public float getNic() {
        return nic;
    }

    public ArrayList<Float> getFlavorAmounts() {
        return flavorAmounts;
    }
}
