package com.jakeesveld.vapebiblejuicecalc.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.jakeesveld.vapebiblejuicecalc.Models.DBRecipe;

import java.util.List;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface RecipeDAO {
    @Query("SELECT * FROM DBRECIPE")
    List<DBRecipe> getAllRecipes();

    @Query("SELECT * FROM DBRecipe WHERE userId = :userId")
    List<DBRecipe> getAllbyUserId(String userId);

    @Insert
    void addRecipe(DBRecipe recipe);

    @Delete
    void deleteRecipe(DBRecipe recipe);

    @Update
    void updateRecipe(DBRecipe recipe);
}
