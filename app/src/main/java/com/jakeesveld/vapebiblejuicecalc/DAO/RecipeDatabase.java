package com.jakeesveld.vapebiblejuicecalc.DAO;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.jakeesveld.vapebiblejuicecalc.Models.DBRecipe;

@Database(entities = {DBRecipe.class}, version = 1)
public abstract class RecipeDatabase extends RoomDatabase {
    public abstract RecipeDAO recipeDAO();
}
