package com.jakeesveld.vapebiblejuicecalc.Activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.jakeesveld.vapebiblejuicecalc.R;

public class BaseActivity extends AppCompatActivity {


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
                    case R.id.nav_create_new:
                        break;
                    case R.id.nav_help:
                        break;
                    case R.id.nav_login:
                        break;
                    case R.id.nav_saved_recipes:
                        break;
                    case R.id.nav_example_recipes:
                        break;
                }
                drawerLayout.closeDrawers();
                return true;
            }
        });
    }
}
