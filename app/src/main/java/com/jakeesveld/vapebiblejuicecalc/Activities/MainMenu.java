package com.jakeesveld.vapebiblejuicecalc.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.jakeesveld.vapebiblejuicecalc.DAO.StorageDAO;
import com.jakeesveld.vapebiblejuicecalc.R;

import java.util.Arrays;
import java.util.List;

public class MainMenu extends BaseActivity {
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        context = this;

        findViewById(R.id.button_create_new).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, ResultsActivity.class));
            }
        });
        findViewById(R.id.button_how_to).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, HowToActivity.class));
            }
        });
        findViewById(R.id.button_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<AuthUI.IdpConfig> providers = Arrays.asList(
                        new AuthUI.IdpConfig.EmailBuilder().build());
                startActivityForResult(
                        AuthUI.getInstance()
                                .createSignInIntentBuilder()
                                .setAvailableProviders(providers)
                                .build(), AUTH_REQUEST_CODE);
            }
        });
        findViewById(R.id.button_saved_recipes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(StorageDAO.user == null){
                    Toast.makeText(getBaseContext(), "Please log in to view saved recipes", Toast.LENGTH_LONG).show();
                }else{
                    startActivity(new Intent(getBaseContext(), SavedRecipesActivity.class));
                }
            }
        });

        findViewById(R.id.button_example_recipes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getBaseContext(), ExampleRecipesActivity.class));
            }
        });
    }
}
