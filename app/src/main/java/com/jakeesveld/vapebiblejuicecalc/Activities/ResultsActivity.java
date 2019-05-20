package com.jakeesveld.vapebiblejuicecalc.Activities;

import android.net.Uri;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.jakeesveld.vapebiblejuicecalc.Fragments.InputFragment1;
import com.jakeesveld.vapebiblejuicecalc.Fragments.InputFragment2;
import com.jakeesveld.vapebiblejuicecalc.Models.Recipe;
import com.jakeesveld.vapebiblejuicecalc.R;

public class ResultsActivity extends BaseActivity implements InputFragment1.OnFragmentInteractionListener, InputFragment2.OnFragmentInteractionListener {

    public static final String RECIPE_KEY = "Recipe";
    LinearLayout layoutColumn1, layoutColumn2;
    Button buttonSave;
    EditText editRecipeName;
    Recipe recipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
        if(getIntent().getExtras() == null) {
            final FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction
                    .replace(R.id.container, new InputFragment1())
                    .addToBackStack(null)
                    .commit();
        }else{
            recipe = (Recipe) getIntent().getSerializableExtra(RECIPE_KEY);

        }
    }

    public void populateUi(){

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
