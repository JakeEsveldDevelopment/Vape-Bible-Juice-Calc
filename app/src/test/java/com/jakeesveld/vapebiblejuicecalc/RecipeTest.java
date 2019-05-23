package com.jakeesveld.vapebiblejuicecalc;

import com.jakeesveld.vapebiblejuicecalc.Models.Base;
import com.jakeesveld.vapebiblejuicecalc.Models.Flavor;
import com.jakeesveld.vapebiblejuicecalc.Models.Recipe;
import com.jakeesveld.vapebiblejuicecalc.Models.RecipeResult;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;

public class RecipeTest {
    public static final String RECIPE_NAME = "Test Recipe";
    public static final String RECIPE_PROFILE = "A zesty test";
    Recipe recipe;

    @Before
    public void setUp(){
        recipe = new Recipe();
        Base base = new Base();
        base.setPG(0);
        base.setVG(100);
        base.setStrength(100);
        recipe.setBaseNic(base);
        ArrayList<Flavor> flavors = new ArrayList<>();
        flavors.add(new Flavor("Strawberry", 5));
        flavors.add(new Flavor("Blueberry", 2));
        recipe.setFlavors(flavors);
        recipe.setNic(3);
        recipe.setPG(30);
        recipe.setVG(70);
        recipe.setBottleSize(120);
        recipe.setName(RECIPE_NAME);
        recipe.setProfile(RECIPE_PROFILE);
    }

    @Test
    public void calculateResults() {
        RecipeResult result = recipe.calculateResults();
        assertThat(String.format("%.1f mL", result.getNic()), equalTo("3.6 mL"));
        assertThat(String.format("%.1f mL", result.getPG()), equalTo("27.6 mL"));
        assertThat(String.format("%.1f mL", result.getVG()), equalTo("80.4 mL"));

    }

    @Test
    public void checkCorrectPG() {
        boolean correct = recipe.checkCorrectPG();
        assertThat(correct, equalTo(true));
        ArrayList<Flavor> newFlavors = new ArrayList<>();
        newFlavors.add(new Flavor("way to much", 45));
        recipe.setFlavors(newFlavors);
        correct = recipe.checkCorrectPG();
        assertThat(correct, equalTo(false));
    }

    @Test
    public void getBottleSize() {
        assertThat(recipe.getBottleSize(), equalTo(120));
    }

    @Test
    public void setBottleSize() {
        recipe.setBottleSize(60);
        assertThat(recipe.getBottleSize(), equalTo(60));
    }

    @Test
    public void getPG() {
        assertThat(recipe.getPG(), equalTo(30));
    }

    @Test
    public void setPG() {
        recipe.setPG(50);
        assertThat(recipe.getPG(), equalTo(50));
    }

    @Test
    public void getVG() {
        assertThat(recipe.getVG(), equalTo(70));
    }

    @Test
    public void setVG() {
        recipe.setVG(50);
        assertThat(recipe.getVG(), equalTo(50));
    }

    @Test
    public void getNic() {
        assertThat(recipe.getNic(), equalTo(3));
    }

    @Test
    public void setNic() {
        recipe.setNic(12);
        assertThat(recipe.getNic(), equalTo(12));
    }

    @Test
    public void getName() {
        assertThat(recipe.getName(), equalTo(RECIPE_NAME));
    }

    @Test
    public void setName() {
        recipe.setName("Different");
        assertThat(recipe.getName(), equalTo("Different"));
    }
}