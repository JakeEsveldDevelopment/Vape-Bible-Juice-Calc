package com.jakeesveld.vapebiblejuicecalc.DAO;

import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jakeesveld.vapebiblejuicecalc.Models.Recipe;

import org.json.JSONObject;

import java.util.ArrayList;

public class FirebaseDAO {
    public static FirebaseUser user;

    public static void setUser(FirebaseUser user) {
        FirebaseDAO.user = user;
    }

    public static void saveRecipe(Recipe recipe){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference(user.getUid());
        ref.child(recipe.getName()).setValue(recipe);
    }

    public static ArrayList<Recipe> getRecipes(){
        final ArrayList<Recipe> recipeList = new ArrayList<>();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference(user.getUid());
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                    Recipe recipe = snapshot.getValue(Recipe.class);
                    recipeList.add(recipe);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return recipeList;
    }
}
