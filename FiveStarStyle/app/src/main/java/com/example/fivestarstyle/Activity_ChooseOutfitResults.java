package com.example.fivestarstyle;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class Activity_ChooseOutfitResults extends AppCompatActivity {

    String passedLabel;
    TextView txtLabel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__choose_outfit_results);
        txtLabel = findViewById(R.id.btnClicked);

        Intent i = getIntent();
        passedLabel = i.getExtras().getString("btnClicked");
        Log.d("LABEL", passedLabel);
        txtLabel.setText("Choosing a " + passedLabel + " outfit:");
    }
}
