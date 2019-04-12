package com.example.fivestarstyle;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class Activity_ImageDetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__image_detail);


        ImageView clothingImage = (ImageView) findViewById(R.id.imageFromDB);

        TextView categoryText = (TextView) findViewById(R.id.categoryPulledText);
        TextView colorText = (TextView) findViewById(R.id.colorPulledText);
        TextView seasonText = (TextView) findViewById(R.id.seasonPulledText);
        TextView eventText = (TextView) findViewById(R.id.eventPulledText);




        Bundle bundle = getIntent().getExtras();
        ArrayList<ItemDetailsObject> obj = (ArrayList) bundle.getSerializable("detail");
        Log.d("ImageDetail", "events: " + obj.get(0).getEvents());




        //set category text
        String category = obj.get(0).getCat();
        if (category.equals("dress_or_suit")) {
            category = "Dress or Suit";
        }
        else {
            category = obj.get(0).getCat().substring(0,1).toUpperCase() + obj.get(0).getCat().substring(1);
        }
        categoryText.setText(category);



        //set color text
        colorText.setText(obj.get(0).getColor().substring(0,1).toUpperCase() + obj.get(0).getColor().substring(1));



        //set seasons text
        int i;
        StringBuilder seasons = new StringBuilder();
        String season;
        for(i = 0; i < obj.get(0).getSeasons().size(); i++) {
            season = obj.get(0).getSeasons().get(i).substring(0,1).toUpperCase() + obj.get(0).getSeasons().get(i).substring(1);
            seasons.append(season);
            if(i < obj.get(0).getSeasons().size() - 1) {
                seasons.append(", ");
            }
        }
        seasonText.setText(String.valueOf(seasons));



        //set events text
        //eventText.setText(obj.get(0).getEvents().get(0));
        StringBuilder events = new StringBuilder();
        String event;
        for(i = 0; i < obj.get(0).getEvents().size(); i++) {
            event = obj.get(0).getEvents().get(i).substring(0,1).toUpperCase() + obj.get(0).getEvents().get(i).substring(1);
            events.append(event);
            if(i < obj.get(0).getEvents().size() - 1) {
                events.append(", ");
            }
        }
        eventText.setText(String.valueOf(events));
    }
}
