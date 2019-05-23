package com.jakeesveld.vapebiblejuicecalc.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jakeesveld.vapebiblejuicecalc.Adapters.SavedRecipeAdapter;
import com.jakeesveld.vapebiblejuicecalc.Architecture.SavedRecipesViewModel;
import com.jakeesveld.vapebiblejuicecalc.DAO.StorageDAO;
import com.jakeesveld.vapebiblejuicecalc.Fragments.DeleteRecipeConfirmationFragment;
import com.jakeesveld.vapebiblejuicecalc.Models.Base;
import com.jakeesveld.vapebiblejuicecalc.Models.Recipe;
import com.jakeesveld.vapebiblejuicecalc.R;

import java.util.ArrayList;

public class SavedRecipesActivity extends BaseActivity implements DeleteRecipeConfirmationFragment.onFragmentInteractionListener, DeleteRecipeConfirmationFragment.onDeleteRequestListener {

    public static final int DELETE_REQUEST_CODE = 178;
    public static final int DELETE_CONFIRMED = 19933;
    public static final String RECIPE_KEY = "recipe";
    public static final String DELETE_TAG = "deletion";

    ArrayList<Recipe> recipeList;
    SavedRecipeAdapter listAdapter;
    SavedRecipesViewModel viewModel;
    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_recipes);
        progressBar = findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.VISIBLE);
        recipeList = new ArrayList<>();
        listAdapter = new SavedRecipeAdapter(recipeList, this);
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setAdapter(listAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference(StorageDAO.user.getUid());

        viewModel = ViewModelProviders.of(this).get(SavedRecipesViewModel.class);

        viewModel.getData().observe(this, new Observer<ArrayList<Recipe>>() {
            @Override
            public void onChanged(ArrayList<Recipe> recipes) {
                if(recipes != null){
                    recipeList.clear();
                    recipeList.addAll(recipes);
                    listAdapter.notifyDataSetChanged();
                    checkNetworkWithLocal();
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

    @Override
    public void onDeleteRequestConfirmed(boolean confirmed, final Recipe recipe) {
        recipeList.remove(recipe);
        listAdapter.notifyDataSetChanged();
        new Thread(new Runnable() {
            @Override
            public void run() {
                StorageDAO.deleteRecipe(recipe, getBaseContext());
            }
        }).start();
    }

    @Override
    public void onFragmentInteraction(Recipe recipe) {
        DialogFragment fragment = new DeleteRecipeConfirmationFragment(SavedRecipesActivity.this, this);
        Bundle args = new Bundle();
        args.putSerializable(RECIPE_KEY, recipe);
        fragment.setArguments(args);
        fragment.show(getSupportFragmentManager(), DELETE_TAG);
    }

    public void checkNetworkWithLocal(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                final ArrayList<Recipe> notOnNetworkRecipes = new ArrayList<>(StorageDAO.checkNetworkWithLocal(recipeList, getBaseContext()));

                if(notOnNetworkRecipes.size() > 0){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            recipeList.addAll(notOnNetworkRecipes);
                            listAdapter.notifyDataSetChanged();
                        }
                    });
                    for(Recipe recipe: notOnNetworkRecipes){
                        StorageDAO.saveRecipe(recipe, getBaseContext());
                    }
                }
            }
        }).start();

    }


}
