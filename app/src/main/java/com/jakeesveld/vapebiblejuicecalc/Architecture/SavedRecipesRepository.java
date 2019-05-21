package com.jakeesveld.vapebiblejuicecalc.Architecture;

import androidx.lifecycle.MutableLiveData;

import com.jakeesveld.vapebiblejuicecalc.Activities.SavedRecipesActivity;
import com.jakeesveld.vapebiblejuicecalc.Models.Recipe;

import java.util.ArrayList;

public class SavedRecipesRepository {
    private ArrayList<Recipe> data;

    public SavedRecipesRepository(){ data = new ArrayList<>(); }

    public MutableLiveData<ArrayList<Recipe>> getData(){
        MutableLiveData<ArrayList<Recipe>> liveData = new MutableLiveData<>();
        liveData.setValue(data);
        return liveData;
    }

    public ArrayList<Recipe> UpdateData(ArrayList<Recipe> updatedData){
        data.clear();
        data.addAll(updatedData);
        return data;
    }
}
