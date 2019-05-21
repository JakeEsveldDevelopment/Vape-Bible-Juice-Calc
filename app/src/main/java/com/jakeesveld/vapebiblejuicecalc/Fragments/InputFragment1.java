package com.jakeesveld.vapebiblejuicecalc.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.snackbar.Snackbar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;

import com.jakeesveld.vapebiblejuicecalc.Activities.ResultsActivity;
import com.jakeesveld.vapebiblejuicecalc.Models.Base;
import com.jakeesveld.vapebiblejuicecalc.Models.Recipe;
import com.jakeesveld.vapebiblejuicecalc.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link InputFragment1.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link InputFragment1#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InputFragment1 extends Fragment {

    public static final int FRAGMENT_REQUEST_CODE = 10;
    EditText editBottleSize, editDesiredStrength, editDesiredPG, editDesiredVG, editBaseStrength, editBaseVG, editBasePG;
    SeekBar desiredRatioSeekbar, baseRatioSeekbar;
    Recipe newRecipe;
    Button buttonNextPage;


    private OnFragmentInteractionListener mListener;

    public InputFragment1() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment InputFragment1.
     */
    public static InputFragment1 newInstance(String param1, String param2) {
        InputFragment1 fragment = new InputFragment1();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_input_fragment1, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        editBottleSize = view.findViewById(R.id.edit_bottle_size);
        editDesiredStrength = view.findViewById(R.id.edit_desired_strength);
        editDesiredPG = view.findViewById(R.id.edit_desired_PG);
        editDesiredVG = view.findViewById(R.id.edit_desired_VG);
        editBaseStrength = view.findViewById(R.id.edit_base_strength);
        editBaseVG = view.findViewById(R.id.edit_base_VG);
        editBasePG  = view.findViewById(R.id.edit_base_PG);
        desiredRatioSeekbar = view.findViewById(R.id.seekbar_desired_ratio);
        baseRatioSeekbar = view.findViewById(R.id.seekbar_base_ratio);
        buttonNextPage = view.findViewById(R.id.button_next_page);
        newRecipe = new Recipe();

        desiredRatioSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                editDesiredVG.setText(String.valueOf(progress) + "%");
                editDesiredPG.setText(String.valueOf(100 - progress) + "%");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        baseRatioSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                editBaseVG.setText(String.valueOf(progress) + "%");
                editBasePG.setText(String.valueOf(100 - progress) + "%");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        buttonNextPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editBottleSize.getText().toString().equals("")||
                        editDesiredStrength.getText().toString().equals("") ||
                        editBaseStrength.getText().toString().equals("")){
                    Snackbar.make(view, "Please fill out all fields to continue", Snackbar.LENGTH_LONG).show();
                }else{
                    try{
                        int baseVG = Integer.parseInt(editBaseVG.getText().toString().replace("%", ""));
                        int basePG = Integer.parseInt(editBasePG.getText().toString().replace("%", ""));
                        int baseStrength = Integer.parseInt(editBaseStrength.getText().toString());
                        Base newRecipeBase = new Base(baseVG, basePG, baseStrength);
                        newRecipe.setBaseNic(newRecipeBase);
                        newRecipe.setBottleSize(Integer.parseInt(editBottleSize.getText().toString()));
                        newRecipe.setNic(Integer.parseInt(editDesiredStrength.getText().toString()));
                        newRecipe.setPG(Integer.parseInt(editDesiredPG.getText().toString().replace("%", "")));
                        newRecipe.setVG(Integer.parseInt(editDesiredVG.getText().toString().replace("%", "")));
                        Fragment inputFragment2 = new InputFragment2();
                        Bundle args = new Bundle();
                        args.putSerializable(ResultsActivity.RECIPE_KEY, newRecipe);
                        inputFragment2.setArguments(args);
                        if(getFragmentManager() != null){
                            getFragmentManager().beginTransaction().replace(R.id.container, inputFragment2)
                            .addToBackStack(null).commit();

                        }
                    }catch (Exception e){
                        Snackbar.make(view, "Invalid input(s)", Snackbar.LENGTH_LONG).show();
                    }
                }
            }
        });
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
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
