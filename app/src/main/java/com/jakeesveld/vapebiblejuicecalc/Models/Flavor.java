package com.jakeesveld.vapebiblejuicecalc.Models;

import java.io.Serializable;

public class Flavor implements Serializable {
    private String name;
    private float amount;

    public Flavor(String name, float amount) {
        this.name = name;
        this.amount = amount;
    }

    public Flavor() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }
}
