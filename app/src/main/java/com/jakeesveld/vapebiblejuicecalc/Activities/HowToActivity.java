package com.jakeesveld.vapebiblejuicecalc.Activities;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.jakeesveld.vapebiblejuicecalc.R;

public class HowToActivity extends AppCompatActivity {

    ViewPager pager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_how_to);
        pager = findViewById(R.id.pager);
/*        int[] resources = new int[]{R.drawable.carousel_background};
        HowToPagerAdapter pagerAdapter = new HowToPagerAdapter(this, resources);
        pager.setAdapter(pagerAdapter);*/
    }
}

