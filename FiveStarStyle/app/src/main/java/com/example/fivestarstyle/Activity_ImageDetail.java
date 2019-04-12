package com.example.fivestarstyle;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;

public class Activity_ImageDetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__image_detail);

        ItemDetailsObject itemDeets;

        Intent i = getIntent();
        itemDeets = (ItemDetailsObject) i.getSerializableExtra("itemDeets");

        TextView categoryText = (TextView) findViewById(R.id.categoryPulledText);
        TextView colorText = (TextView) findViewById(R.id.colorPulledText);
        TextView seasonText = (TextView) findViewById(R.id.seasonPulledText);
        TextView eventText = (TextView) findViewById(R.id.eventPulledText);

        categoryText.setText(itemDeets.getCat());
        colorText.setText(itemDeets.getColor());
        seasonText.setText(itemDeets.getSeasons().get(0));
        eventText.setText(itemDeets.getEvents().get(0));


        Bundle bundle = getIntent().getExtras();
        ArrayList<ItemDetailsObject> obj = (ArrayList) bundle.getSerializable("detail");
        Log.d("ImageDetail", "events: " + obj.get(0).getEvents());
    }
}
