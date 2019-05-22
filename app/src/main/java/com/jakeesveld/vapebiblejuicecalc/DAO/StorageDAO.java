package com.jakeesveld.vapebiblejuicecalc.DAO;

import android.content.Context;
import android.database.sqlite.SQLiteConstraintException;

import androidx.room.Room;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.jakeesveld.vapebiblejuicecalc.Models.DBRecipe;
import com.jakeesveld.vapebiblejuicecalc.Models.Recipe;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class StorageDAO {
    public static final String DB_NAME = "recipe-database";
    public static FirebaseUser user;

    public static void setUser(FirebaseUser user) {
        StorageDAO.user = user;
    }


    public static void saveToExamples(Recipe recipe){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("Examples");
        ref.child(recipe.getName()).setValue(recipe);
    }


    public static void saveRecipe(Recipe recipe, Context context){
        try {
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference ref = database.getReference(user.getUid());
            ref.child(recipe.getName()).setValue(recipe);
        }catch (Exception e){
            e.printStackTrace();
        }

        RecipeDatabase db = Room.databaseBuilder(context,
                RecipeDatabase.class, DB_NAME).build();

        try {
            db.recipeDAO().addRecipe(new DBRecipe(recipe));
        } catch (SQLiteConstraintException e){
            db.recipeDAO().updateRecipe(new DBRecipe(recipe));
        }
    }

    public static void deleteRecipe(Recipe recipe, Context context){
        try{
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference ref = database.getReference(user.getUid());
            ref.child(recipe.getName()).getRef().removeValue();
        } catch (Exception e){
            e.printStackTrace();
        }

        RecipeDatabase db = Room.databaseBuilder(context, RecipeDatabase.class, DB_NAME).build();
        db.recipeDAO().deleteRecipe(new DBRecipe(recipe));
    }


    public static ArrayList<Recipe> checkNetworkWithLocal(ArrayList<Recipe> networkRecipeList, Context context){
        RecipeDatabase db = Room.databaseBuilder(context,
                RecipeDatabase.class, DB_NAME).build();
        ArrayList<Recipe> localRecipeList = new ArrayList<>();
        List<DBRecipe> localDBList = db.recipeDAO().getAllbyUserId(user.getUid());
        for(DBRecipe dbRecipe: localDBList){
            Recipe recipe = new Recipe(dbRecipe);
            localRecipeList.add(recipe);
        }
        ArrayList<Recipe> notOnNetworkRecipes = new ArrayList<>();

        for (Recipe recipe: localRecipeList) {
            if(!checkForRecipe(recipe, networkRecipeList)) {
                notOnNetworkRecipes.add(recipe);
            }
        }

        return notOnNetworkRecipes;
    }

    private static boolean checkForRecipe(Recipe recipe, List<Recipe> list){
        for(int i = 0; i < list.size(); ++i){
            if(recipe.getName().equals(list.get(i).getName())){
                return true;
            }
        }
        return false;
    }

}
