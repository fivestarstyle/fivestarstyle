package com.example.fivestarstyle;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Activity_ImageDetail extends AppCompatActivity {

    private final static String TAG = "Activity_ImageDetails";

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


        String url = obj.get(0).getImageUrl();
        Picasso.get().load(url).into(clothingImage);

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

        StringBuilder seasons = new StringBuilder();
        String season;

            season = obj.get(0).getSeasons().get(0).substring(1,2).toUpperCase() + obj.get(0).getSeasons().get(0).substring(2);
            seasons.append(season);


        seasons.setLength(seasons.length()-1);

        String [] s = seasons.toString().split(",");
        StringBuilder seasons2 = new StringBuilder();
        String t;
        seasons2.append(s[0].substring(0,1).toUpperCase()+s[0].substring(1));
        if (s.length > 1) {
            seasons2.append(", ");
        }

        for (int j = 1; j < s.length; j ++) {
            t = s[j].substring(1,2).toUpperCase()+s[j].substring(2);
            seasons2.append(t);
            if ((j+1) < s.length) {
                seasons2.append(", ");
            }
        }

        seasonText.setText(String.valueOf(seasons2));



        //set events text
        eventText.setText(obj.get(0).getEvents().get(0));
        StringBuilder events = new StringBuilder();
        String event;

            event = obj.get(0).getEvents().get(0).substring(1,2).toUpperCase() + obj.get(0).getEvents().get(0).substring(2);
            events.append(event);


        events.setLength(events.length()-1);
        String [] x = events.toString().split(",");
        StringBuilder events2 = new StringBuilder();
        String y;
        events2.append(x[0].substring(0,1).toUpperCase()+x[0].substring(1));
        if (x.length > 1) {
            events2.append(", ");
        }

        for (int i = 1; i < x.length; i ++) {
            y = x[i].substring(1,2).toUpperCase()+x[i].substring(2);
            events2.append(y);
            if ((i+1) < x.length) {
                events2.append(", ");
            }
        }
        eventText.setText(String.valueOf(events2));
    }


    /*create options menu in upper right corner*/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_options,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.home__menu_option) {
            Intent homeIntent = new Intent(this, Activity_Main.class);
            this.startActivity(homeIntent);
        }
        else if(item.getItemId() == R.id.closet_menu_option) {
            Intent overviewIntent = new Intent(this, Activity_ViewMyCloset.class);
            this.startActivity(overviewIntent);
        }
        else if(item.getItemId() == R.id.add_menu_option) {
            Intent overviewIntent = new Intent(this, Activity_GoogleCloudAPI.class);
            this.startActivity(overviewIntent);
        }
        else if(item.getItemId() == R.id.overview_menu_option) {
            Intent overviewIntent = new Intent(this, Activity_Overview.class);
            this.startActivity(overviewIntent);
        }
        else if(item.getItemId() == R.id.choosemyoutfit_menu_option) {
            Intent overviewIntent = new Intent(this, Activity_ChooseMyOutfit.class);
            this.startActivity(overviewIntent);
        }
        else if(item.getItemId() == R.id.settings_menu_option) {
            Intent overviewIntent = new Intent(this, Activity_Settings.class);
            this.startActivity(overviewIntent);
        }
        else if(item.getItemId() == R.id.logout_menu_option) {
            try {
                FirebaseAuth.getInstance().signOut();
                Log.d(TAG, "User signed out");
                Intent overviewIntent = new Intent(this, Activity_Login.class);
                this.startActivity(overviewIntent);
            }catch (Exception e) {
                Log.e(TAG, "onClick: Exception "+e.getMessage(),e );
            }
        }
        else {
            return super.onOptionsItemSelected(item);
        }
        return true;
    }
}
