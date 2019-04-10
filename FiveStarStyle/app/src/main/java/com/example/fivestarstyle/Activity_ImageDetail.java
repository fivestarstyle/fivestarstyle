package com.example.fivestarstyle;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;

public class Activity_ImageDetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__image_detail);

        Bundle bundle = getIntent().getExtras();
        ArrayList<ItemDetailsObject> obj = (ArrayList) bundle.getSerializable("details");
        Log.d("ImageDetail", "events: " + obj.get(0).getEvents());
    }
}
