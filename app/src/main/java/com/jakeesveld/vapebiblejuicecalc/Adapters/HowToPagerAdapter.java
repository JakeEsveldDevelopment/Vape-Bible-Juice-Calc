package com.jakeesveld.vapebiblejuicecalc.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.jakeesveld.vapebiblejuicecalc.R;

public class HowToPagerAdapter extends PagerAdapter {

    private LayoutInflater layoutInflater;
    private int[] resources;

    public HowToPagerAdapter(Context context, int[] resources) {
        this.resources = resources;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return resources.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }


    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View itemView = layoutInflater.inflate(R.layout.pager_item, container, false);

        ImageView imageView = itemView.findViewById(R.id.image_background);
        imageView.setImageResource(resources[position]);

        container.addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((ConstraintLayout) object);
    }

}
