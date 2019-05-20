package com.jakeesveld.vapebiblejuicecalc.Activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.jakeesveld.vapebiblejuicecalc.Activities.BaseActivity;
import com.jakeesveld.vapebiblejuicecalc.R;

import javax.xml.transform.Result;

public class MainMenu extends BaseActivity {
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        context = this;

        findViewById(R.id.button_create_new).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, ResultsActivity.class));
            }
        });
    }
}
