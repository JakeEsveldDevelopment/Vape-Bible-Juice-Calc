package com.jakeesveld.vapebiblejuicecalc.Activities;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.jakeesveld.vapebiblejuicecalc.Adapters.HowToPagerAdapter;
import com.jakeesveld.vapebiblejuicecalc.R;

public class HowToActivity extends BaseActivity {

    ViewPager pager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_how_to);
        pager = findViewById(R.id.pager);
        int[] resources = new int[]{R.string.how_to_use_this_app, R.string.flavor_mixing_tips, R.string.common_shorthands, R.string.understanding_ratios, R.string.coming_soon};
        HowToPagerAdapter pagerAdapter = new HowToPagerAdapter(this, resources);
        pager.setAdapter(pagerAdapter);
    }
}

