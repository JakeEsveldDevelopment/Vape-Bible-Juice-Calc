package com.jakeesveld.vapebiblejuicecalc.Activities;

import android.annotation.TargetApi;
import android.content.Intent;
import android.net.Uri;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Build;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.internal.Storage;
import com.jakeesveld.vapebiblejuicecalc.DAO.StorageDAO;
import com.jakeesveld.vapebiblejuicecalc.Fragments.InputFragment1;
import com.jakeesveld.vapebiblejuicecalc.Fragments.InputFragment2;
import com.jakeesveld.vapebiblejuicecalc.Models.Recipe;
import com.jakeesveld.vapebiblejuicecalc.Models.RecipeResult;
import com.jakeesveld.vapebiblejuicecalc.R;

public class ResultsActivity extends BaseActivity implements InputFragment1.OnFragmentInteractionListener, InputFragment2.OnFragmentInteractionListener {

    public static final String RECIPE_KEY = "Recipe";
    LinearLayout layoutColumn1, layoutColumn2;
    Button buttonSave, buttonEdit;
    EditText editRecipeName, editRecipeProfile;
    TextView textVGResult, textPGResult, textNicResult;
    RecipeResult recipeResult;
    Recipe recipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
        layoutColumn1 = findViewById(R.id.layout_column_1);
        layoutColumn2 = findViewById(R.id.layout_column_2);
        textNicResult = findViewById(R.id.text_nic_result);
        textVGResult = findViewById(R.id.text_vg_result);
        textPGResult = findViewById(R.id.text_pg_result);
        buttonSave = findViewById(R.id.button_save);
        buttonEdit = findViewById(R.id.button_edit_recipe);
        editRecipeName = findViewById(R.id.edit_recipe_name);
        editRecipeProfile = findViewById(R.id.edit_recipe_profile);
        if (getIntent().getExtras() == null) {
            final FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction
                    .add(R.id.container, new InputFragment1())
                    .addToBackStack(null)
                    .commit();
        } else {
            recipe = (Recipe) getIntent().getSerializableExtra(RECIPE_KEY);
            recipeResult = recipe.calculateResults();
            populateUi();
            try {
                editRecipeName.setText(recipe.getName());
            } catch (NullPointerException e) {
                e.printStackTrace();
            }

        }

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!editRecipeName.getText().toString().equals("") && StorageDAO.user != null) {
                    recipe.setName(editRecipeName.getText().toString());
                    if (!editRecipeProfile.getText().toString().equals("")) {
                        recipe.setProfile(editRecipeProfile.getText().toString());
                    }

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            StorageDAO.saveRecipe(recipe, getBaseContext());
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getBaseContext(), "Recipe Saved Successfully", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(getBaseContext(), SavedRecipesActivity.class));
                                }
                            });
                        }
                    }).start();

                } else if (StorageDAO.user == null) {
                    Toast.makeText(getBaseContext(), "Please log in to save recipes", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getBaseContext(), "Please name your recipe to save", Toast.LENGTH_SHORT).show();
                }
            }
        });

        buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                Fragment fragment = new InputFragment1();
                Bundle arguments = new Bundle();
                arguments.putSerializable(RECIPE_KEY, recipe);
                fragment.setArguments(arguments);
                fragmentTransaction
                        .replace(R.id.container, fragment)
                        .addToBackStack(null)
                        .commit();
            }
        });
    }

    @TargetApi(Build.VERSION_CODES.O)
    public void populateUi() {

        textPGResult.setText(String.format("%.1f mL", recipeResult.getPG()));
        textVGResult.setText(String.format("%.1f mL", recipeResult.getVG()));
        textNicResult.setText(String.format("%.1f mL", recipeResult.getNic()));
        for (int i = 0; i < recipe.getFlavors().size(); ++i) {
            String flavorName = recipe.getFlavors().get(i).getName();
            float flavorAmount = recipeResult.getFlavorAmounts().get(i);
            TextView flavorNameView = new TextView(getBaseContext());
            TextView flavorAmountView = new TextView(getBaseContext());
            flavorNameView.setText(flavorName);
            flavorNameView.setTextSize(24);
            flavorNameView.setLines(1);
            flavorNameView.setMaxLines(1);
            flavorNameView.setAutoSizeTextTypeUniformWithConfiguration(12, 24, 2, TypedValue.COMPLEX_UNIT_SP);
            layoutColumn1.addView(flavorNameView);
            flavorAmountView.setText(String.format("%.1f mL", flavorAmount));
            flavorAmountView.setTextSize(24);
            flavorAmountView.setGravity(Gravity.END);
            layoutColumn2.addView(flavorAmountView);
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
