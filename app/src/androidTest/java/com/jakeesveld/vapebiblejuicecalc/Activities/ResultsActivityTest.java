package com.jakeesveld.vapebiblejuicecalc.Activities;

import android.content.Intent;

import androidx.test.rule.ActivityTestRule;

import com.jakeesveld.vapebiblejuicecalc.Models.Base;
import com.jakeesveld.vapebiblejuicecalc.Models.Flavor;
import com.jakeesveld.vapebiblejuicecalc.Models.Recipe;
import com.jakeesveld.vapebiblejuicecalc.R;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.ArrayList;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isClickable;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.*;

public class ResultsActivityTest {

    public static final String RECIPE_NAME = "Test Recipe";
    public static final String RECIPE_PROFILE = "A zesty test";

    @Rule
    public ActivityTestRule<ResultsActivity> activityTestRule = new ActivityTestRule<>(ResultsActivity.class, false, false);

    @Before
    public void setUp(){
        Intent intent = new Intent();
        Recipe recipe = new Recipe();
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
        intent.putExtra(ResultsActivity.RECIPE_KEY, recipe);
        activityTestRule.launchActivity(intent);
    }

    @Test
    public void resultsActivity_CheckCorrectNicotine(){
        onView(withId(R.id.text_nic_result)).check(matches(withText("3.6 mL")));
    }

    @Test
    public void resultsActivity_CheckCorrectVG(){
        onView(withId(R.id.text_vg_result)).check(matches(withText("80.4 mL")));
    }

    @Test
    public void resultsActivity_CheckCorrectPG(){
        onView(withId(R.id.text_pg_result)).check(matches(withText("27.6 mL")));
    }

    @Test
    public void resultsActivity_CheckSaveClickable(){
        onView(withId(R.id.button_save)).check(matches(isClickable()));
    }




}