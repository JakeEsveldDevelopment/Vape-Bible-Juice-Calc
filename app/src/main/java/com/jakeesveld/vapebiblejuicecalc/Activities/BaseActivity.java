package com.jakeesveld.vapebiblejuicecalc.Activities;

import android.content.Intent;

import androidx.annotation.NonNull;

import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.jakeesveld.vapebiblejuicecalc.DAO.StorageDAO;
import com.jakeesveld.vapebiblejuicecalc.R;

import java.util.Arrays;
import java.util.List;

public class BaseActivity extends AppCompatActivity {


    public static final int AUTH_REQUEST_CODE = 15;

    @Override
    protected void onStart() {
        super.onStart();
        Toolbar toolbar = findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        final DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        final NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch(menuItem.getItemId()){
                    case R.id.nav_main_menu:
                        startActivity(new Intent(getBaseContext(), MainMenu.class));
                        break;
                    case R.id.nav_create_new:
                        startActivity(new Intent(getBaseContext(), ResultsActivity.class));
                        break;
                    case R.id.nav_help:
                        startActivity(new Intent(getBaseContext(), HowToActivity.class));
                        break;
                    case R.id.nav_login:
                        List<AuthUI.IdpConfig> providers = Arrays.asList(
                                new AuthUI.IdpConfig.EmailBuilder().build());
                        startActivityForResult(
                                AuthUI.getInstance()
                                        .createSignInIntentBuilder()
                                        .setAvailableProviders(providers)
                                        .build(), AUTH_REQUEST_CODE);
                        break;
                    case R.id.nav_saved_recipes:
                        if(StorageDAO.user == null){
                            Toast.makeText(getBaseContext(), "Please log in to view saved recipes", Toast.LENGTH_LONG).show();
                        }else{
                            startActivity(new Intent(getBaseContext(), SavedRecipesActivity.class));
                        }
                        break;
                    case R.id.nav_example_recipes:
                        startActivity(new Intent(getBaseContext(), ExampleRecipesActivity.class));
                        break;
                    case R.id.nav_logout:
                        AuthUI.getInstance()
                                .signOut(getBaseContext())
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    public void onComplete(@NonNull Task<Void> task) {
                                        Toast.makeText(getBaseContext(), "Successfully Logged Out", Toast.LENGTH_LONG).show();
                                        StorageDAO.setUser(null);
                                    }
                                });
                        break;
                    case R.id.nav_map:
                        startActivity(new Intent(getBaseContext(), MapsActivity.class));
                        break;
                }
                drawerLayout.closeDrawers();
                return true;
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == AUTH_REQUEST_CODE) {
            IdpResponse response = IdpResponse.fromResultIntent(data);

            if (resultCode == RESULT_OK) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                StorageDAO.setUser(user);
            } else {
                Toast.makeText(getBaseContext(), "Login Failed", Toast.LENGTH_LONG).show();
            }
        }
    }
}
