package com.jakeesveld.vapebiblejuicecalc.Adapters;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

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

        TextView textView = itemView.findViewById(R.id.text_content);
        textView.setText(resources[position]);
        TextView titleView = itemView.findViewById(R.id.text_title);
        TextView disclaimerView = itemView.findViewById(R.id.text_disclaimer);
        switch (position){
            case 0:
                titleView.setText(R.string.how_to_use_app_title);
                break;
            case 1:
                titleView.setText(R.string.flavor_mixing_tips_title);
                break;
            case 2:
                titleView.setText(R.string.common_shorthands_title);
                break;
            case 3:
                titleView.setText(R.string.understanding_ratios_title);
                disclaimerView.setVisibility(View.GONE);
                break;

        }

        container.addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((ConstraintLayout) object);
    }

}
