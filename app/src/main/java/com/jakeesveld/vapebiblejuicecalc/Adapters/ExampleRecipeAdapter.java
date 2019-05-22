package com.jakeesveld.vapebiblejuicecalc.Adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jakeesveld.vapebiblejuicecalc.Activities.ResultsActivity;
import com.jakeesveld.vapebiblejuicecalc.Models.Recipe;
import com.jakeesveld.vapebiblejuicecalc.R;

import java.util.ArrayList;

public class ExampleRecipeAdapter extends RecyclerView.Adapter<ExampleRecipeAdapter.ViewHolder> {

    private ArrayList<Recipe> dataList;

    public ExampleRecipeAdapter(ArrayList<Recipe> dataList) {
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.example_recipe_list_item, viewGroup, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {
        final Recipe data = dataList.get(i);

        viewHolder.textRecipeName.setText(data.getName());
        viewHolder.textProfile.setText(data.getProfile());


        viewHolder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ResultsActivity.class);
                intent.putExtra(ResultsActivity.RECIPE_KEY, data);
                v.getContext().startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textRecipeName, textProfile;
        View parent;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textRecipeName = itemView.findViewById(R.id.text_recipe_name);
            textProfile = itemView.findViewById(R.id.text_profile);
            parent = itemView.findViewById(R.id.parent);
        }
    }
}
