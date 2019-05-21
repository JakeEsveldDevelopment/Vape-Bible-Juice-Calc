package com.jakeesveld.vapebiblejuicecalc.Activities;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.jakeesveld.vapebiblejuicecalc.Adapters.HowToPagerAdapter;
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

