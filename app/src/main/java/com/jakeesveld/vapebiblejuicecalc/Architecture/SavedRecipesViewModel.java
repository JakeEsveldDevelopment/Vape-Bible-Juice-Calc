package com.jakeesveld.vapebiblejuicecalc.Architecture;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.jakeesveld.vapebiblejuicecalc.Models.Recipe;

import java.util.ArrayList;

public class SavedRecipesViewModel extends ViewModel {
    private MutableLiveData<ArrayList<Recipe>> data;
    private SavedRecipesRepository repo;

    public LiveData<ArrayList<Recipe>> getData(){
        if(data == null){
            loadData();
        }
        return data;
    }

    private void loadData(){
        repo = new SavedRecipesRepository();
        data = repo.getData();
    }

    public void updateData(ArrayList<Recipe> recipeList){
        data.setValue(repo.UpdateData(recipeList));
    }
}
