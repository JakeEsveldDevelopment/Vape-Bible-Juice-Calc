package com.jakeesveld.vapebiblejuicecalc.Activities;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jakeesveld.vapebiblejuicecalc.Adapters.SavedRecipeAdapter;
import com.jakeesveld.vapebiblejuicecalc.DAO.FirebaseDAO;
import com.jakeesveld.vapebiblejuicecalc.Models.Recipe;
import com.jakeesveld.vapebiblejuicecalc.R;

import java.util.ArrayList;

public class SavedRecipesActivity extends AppCompatActivity {

    ArrayList<Recipe> recipeList;
    SavedRecipeAdapter listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_recipes);
        recipeList = new ArrayList<>();
        listAdapter = new SavedRecipeAdapter(recipeList);
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setAdapter(listAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference(FirebaseDAO.user.getUid());
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                    Recipe recipe = snapshot.getValue(Recipe.class);
                    recipeList.add(recipe);
                }
                listAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
