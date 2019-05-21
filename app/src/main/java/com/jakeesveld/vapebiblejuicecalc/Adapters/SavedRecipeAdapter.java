package com.jakeesveld.vapebiblejuicecalc.Adapters;


import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.jakeesveld.vapebiblejuicecalc.Activities.ResultsActivity;
import com.jakeesveld.vapebiblejuicecalc.Fragments.DeleteRecipeConfirmationFragment;
import com.jakeesveld.vapebiblejuicecalc.Models.Recipe;
import com.jakeesveld.vapebiblejuicecalc.R;

import java.util.ArrayList;

public class SavedRecipeAdapter extends RecyclerView.Adapter<SavedRecipeAdapter.ViewHolder> {

    private ArrayList<Recipe> dataList;
    private DeleteRecipeConfirmationFragment.onFragmentInteractionListener listener;

    public SavedRecipeAdapter(ArrayList<Recipe> dataList, DeleteRecipeConfirmationFragment.onFragmentInteractionListener listener) {
        this.dataList = dataList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.saved_recipe_list_item, viewGroup, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {
        final Recipe data = dataList.get(i);

        viewHolder.textRecipeName.setText(data.getName());
        viewHolder.textLastSize.setText("Last Bottle Size: " + String.valueOf(data.getBottleSize() + "mL"));
        viewHolder.textLastStrength.setText("Last Nicotine Strength: " + String.valueOf(data.getNic()) + "mg");

        viewHolder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ResultsActivity.class);
                intent.putExtra(ResultsActivity.RECIPE_KEY, data);
                v.getContext().startActivity(intent);
            }
        });

        viewHolder.parent.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if(listener != null){
                    listener.onFragmentInteraction(data);
                }
                return true;
            }
        });

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textRecipeName, textLastSize, textLastStrength;
        View parent;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textRecipeName = itemView.findViewById(R.id.text_recipe_name);
            textLastSize = itemView.findViewById(R.id.text_last_size);
            textLastStrength = itemView.findViewById(R.id.text_last_strength);
            parent = itemView.findViewById(R.id.parent);
        }
    }
}
