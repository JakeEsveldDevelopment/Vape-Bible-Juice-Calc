package com.jakeesveld.vapebiblejuicecalc.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jakeesveld.vapebiblejuicecalc.Adapters.ExampleRecipeAdapter;
import com.jakeesveld.vapebiblejuicecalc.Architecture.SavedRecipesViewModel;
import com.jakeesveld.vapebiblejuicecalc.DAO.StorageDAO;
import com.jakeesveld.vapebiblejuicecalc.Models.Recipe;
import com.jakeesveld.vapebiblejuicecalc.R;

import java.util.ArrayList;

public class ExampleRecipesActivity extends BaseActivity {

    public static final String EXAMPLES_KEY = "Examples";
    SavedRecipesViewModel viewModel;
    ArrayList<Recipe> recipeList;
    ExampleRecipeAdapter listAdapter;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example_recipes);
        progressBar = findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.VISIBLE);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference(EXAMPLES_KEY);
        recipeList = new ArrayList<>();
        listAdapter = new ExampleRecipeAdapter(recipeList);
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setAdapter(listAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext()));

        viewModel = ViewModelProviders.of(this).get(SavedRecipesViewModel.class);

        viewModel.getData().observe(this, new Observer<ArrayList<Recipe>>() {
            @Override
            public void onChanged(ArrayList<Recipe> recipes) {
                if(recipes != null){
                    recipeList.clear();
                    recipeList.addAll(recipes);
                    listAdapter.notifyDataSetChanged();
                }
            }
        });
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<Recipe> networkRecipes = new ArrayList<>();
                for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                    Recipe recipe = snapshot.getValue(Recipe.class);
                    networkRecipes.add(recipe);
                    progressBar.setVisibility(View.GONE);
                }
                viewModel.updateData(networkRecipes);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
