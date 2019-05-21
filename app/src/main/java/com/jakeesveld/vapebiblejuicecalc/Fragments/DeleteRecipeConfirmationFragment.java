package com.jakeesveld.vapebiblejuicecalc.Fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;

import com.jakeesveld.vapebiblejuicecalc.Activities.SavedRecipesActivity;
import com.jakeesveld.vapebiblejuicecalc.Models.Recipe;

public class DeleteRecipeConfirmationFragment extends DialogFragment{
    Context context;
    onDeleteRequestListener listener;
    public DeleteRecipeConfirmationFragment(Context context, onDeleteRequestListener listener) {
        this.context = context;
        this.listener = listener;
    }
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setTitle("Delete Recipe?");
        alertDialogBuilder.setMessage("Are you sure you want to delete this recipe?");
        //null should be your on click listener
        alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                listener.onDeleteRequestConfirmed(true, (Recipe) getArguments().getSerializable(SavedRecipesActivity.RECIPE_KEY));
                dialog.dismiss();
            }
        });
        alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });


        return alertDialogBuilder.create();
    }

    public interface onDeleteRequestListener{
        void onDeleteRequestConfirmed(boolean confirmed, Recipe recipe);
    }

    public interface onFragmentInteractionListener{
        void onFragmentInteraction(Recipe recipe);
    }
}
