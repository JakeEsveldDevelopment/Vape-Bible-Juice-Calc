package com.jakeesveld.vapebiblejuicecalc.Fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.jakeesveld.vapebiblejuicecalc.Activities.ResultsActivity;
import com.jakeesveld.vapebiblejuicecalc.Models.Flavor;
import com.jakeesveld.vapebiblejuicecalc.Models.Recipe;
import com.jakeesveld.vapebiblejuicecalc.R;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link InputFragment2.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link InputFragment2#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InputFragment2 extends Fragment {

    LinearLayout layoutFlavorName, layoutFlavorPercentage;
    Button buttonAddFlavor, buttonResults;
    ArrayList<Flavor> flavors;
    Recipe recipe;


    private OnFragmentInteractionListener mListener;

    public InputFragment2() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment InputFragment2.
     */
    public static InputFragment2 newInstance(String param1, String param2) {
        InputFragment2 fragment = new InputFragment2();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            recipe = (Recipe) getArguments().getSerializable(ResultsActivity.RECIPE_KEY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_input_fragment2, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        layoutFlavorName = view.findViewById(R.id.layout_flavor_names);
        layoutFlavorPercentage = view.findViewById(R.id.layout_flavor_percentages);
        buttonAddFlavor = view.findViewById(R.id.button_add_flavor);
        buttonResults = view.findViewById(R.id.button_results);
        flavors = new ArrayList<>();

        buttonAddFlavor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addFlavor();
            }
        });

        buttonResults.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (makeFlavorArray()) {
                    recipe.setFlavors(flavors);
                    if(recipe.checkCorrectPG()) {
                        Intent intent = new Intent(getActivity(), ResultsActivity.class);
                        intent.putExtra(ResultsActivity.RECIPE_KEY, recipe);
                        startActivity(intent);
                    }else{
                        Snackbar.make(getView(),
                                "Flavor percentage cannot be more than total PG",
                                Snackbar.LENGTH_LONG).show();
                    }
                } else {
                    Snackbar.make(getView(),
                            "Please make sure all flavors are filled out correctly before continuing",
                            Snackbar.LENGTH_LONG).show();
                }
            }
        });

    }

    public void addFlavor() {
        EditText flavorName = new EditText(getContext());
        EditText flavorPercentage = new EditText(getContext());
        flavorName.setHint("Flavor Name");
        flavorPercentage.setHint("%");
        flavorPercentage.setInputType(InputType.TYPE_CLASS_NUMBER);
        flavorPercentage.setGravity(Gravity.END);

        layoutFlavorName.addView(flavorName);
        layoutFlavorPercentage.addView(flavorPercentage);
    }

    public boolean makeFlavorArray() {
        try {
            ArrayList<String> flavorNames = new ArrayList<>();
            ArrayList<Float> flavorPercentages = new ArrayList<>();
            for (int i = 0; i < layoutFlavorName.getChildCount(); ++i) {
                View flavorNameView = layoutFlavorName.getChildAt(i);
                if (flavorNameView instanceof EditText) {
                    if (!((EditText) flavorNameView).getText().toString().equals("")) {
                        String flavorName = ((EditText) flavorNameView).getText().toString();
                        flavorNames.add(flavorName);
                    }
                }
                View flavorPercentageView = layoutFlavorPercentage.getChildAt(i);
                if (flavorPercentageView instanceof EditText) {
                    if (!((EditText) flavorPercentageView).getText().toString().equals("")) {
                        Float flavorPercentage = Float.parseFloat(((EditText) flavorPercentageView).getText().toString());
                        flavorPercentages.add(flavorPercentage);
                    }
                }
            }
            for (int i = 0; i < flavorNames.size(); ++i) {
                Flavor flavor = new Flavor(flavorNames.get(i), flavorPercentages.get(i));
                flavors.add(flavor);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
