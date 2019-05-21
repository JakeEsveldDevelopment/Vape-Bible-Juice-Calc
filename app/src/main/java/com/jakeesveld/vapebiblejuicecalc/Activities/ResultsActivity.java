package com.jakeesveld.vapebiblejuicecalc.Activities;

import android.net.Uri;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jakeesveld.vapebiblejuicecalc.DAO.FirebaseDAO;
import com.jakeesveld.vapebiblejuicecalc.Fragments.InputFragment1;
import com.jakeesveld.vapebiblejuicecalc.Fragments.InputFragment2;
import com.jakeesveld.vapebiblejuicecalc.Models.Recipe;
import com.jakeesveld.vapebiblejuicecalc.Models.RecipeResult;
import com.jakeesveld.vapebiblejuicecalc.R;

import org.w3c.dom.Text;

public class ResultsActivity extends BaseActivity implements InputFragment1.OnFragmentInteractionListener, InputFragment2.OnFragmentInteractionListener {

    public static final String RECIPE_KEY = "Recipe";
    LinearLayout layoutColumn1, layoutColumn2;
    Button buttonSave;
    EditText editRecipeName;
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
        editRecipeName = findViewById(R.id.edit_recipe_name);
        if(getIntent().getExtras() == null) {
            final FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction
                    .replace(R.id.container, new InputFragment1())
                    .addToBackStack(null)
                    .commit();
        }else{
            recipe = (Recipe) getIntent().getSerializableExtra(RECIPE_KEY);
            recipeResult = recipe.calculateResults();
            populateUi();
            if(!recipe.getName().equals("")){
                editRecipeName.setText(recipe.getName());
            }

        }

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!editRecipeName.getText().toString().equals("") && FirebaseDAO.user != null) {
                    recipe.setName(editRecipeName.getText().toString());
                    FirebaseDAO.saveRecipe(recipe);
                    Toast.makeText(getBaseContext(), "Recipe Saved Successfully", Toast.LENGTH_SHORT).show();
                }else if(FirebaseDAO.user == null){
                    Toast.makeText(getBaseContext(), "Please log in to save recipes", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getBaseContext(), "Please name your recipe to save", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void populateUi(){

        textPGResult.setText(String.format("%.1f mL", recipeResult.getPG()));
        textVGResult.setText(String.format("%.1f mL", recipeResult.getVG()));
        textNicResult.setText(String.format("%.1f mL", recipeResult.getNic()));
        for(int i = 0; i < recipe.getFlavors().size(); ++i){
            String flavorName = recipe.getFlavors().get(i).getName();
            float flavorAmount = recipeResult.getFlavorAmounts().get(i);
            TextView flavorNameView = new TextView(getBaseContext());
            TextView flavorAmountView = new TextView(getBaseContext());
            flavorNameView.setText(flavorName);
            flavorNameView.setTextSize(24);
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
