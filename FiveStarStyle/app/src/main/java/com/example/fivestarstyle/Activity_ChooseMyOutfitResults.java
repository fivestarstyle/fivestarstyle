package com.example.fivestarstyle;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Activity_ChooseMyOutfitResults extends AppCompatActivity {

    TextView labelTxt;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__choose_my_outfit_results);
        mContext = this;

        //get imageUrls from old intent
        Intent i = getIntent();
        Bundle bundle = i.getExtras();
        ArrayList<ItemDetailsObject> imagesTops = (ArrayList) bundle.getSerializable("imagesTops");
        ArrayList<ItemDetailsObject> imagesBottoms = (ArrayList) bundle.getSerializable("imagesBottoms");
        ArrayList<ItemDetailsObject> imagesDressesOrSuits = (ArrayList) bundle.getSerializable("imagesDressesOrSuits");
        ArrayList<ItemDetailsObject> imagesOuterwear = (ArrayList) bundle.getSerializable("imagesOuterwear");
        ArrayList<ItemDetailsObject> imagesShoes = (ArrayList) bundle.getSerializable("imagesShoes");
        Log.d("RESULTS PAGE", "imageURLs: " + imagesTops);

        String label = i.getExtras().getString("btnClicked");
        labelTxt = (TextView) findViewById(R.id.txtLabel);
        labelTxt.setText("Choosing a " + label + " outfit:");
//        int rand = (int) (Math.random() * (count));
        // Log.d("COUNT", String.valueOf(count));
        if(GlobalVariables.temperature < 60) {
            ImageView outerwear = new ImageView(mContext);
        }
    }

    // inflate the menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_options,menu);
        return true;
    }

    // setup three dots menu and set intents
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.home__menu_option) {
            // go to home page
            Intent homeIntent = new Intent(this, Activity_Main.class);
            this.startActivity(homeIntent);
        }
        else if(item.getItemId() == R.id.closet_menu_option) {
            // go to view my closet page
            Intent overviewIntent = new Intent(this, Activity_ViewMyCloset.class);
            this.startActivity(overviewIntent);
        }
        else if(item.getItemId() == R.id.overview_menu_option) {
            // go to overview page
            Intent overviewIntent = new Intent(this, Activity_Overview.class);
            this.startActivity(overviewIntent);
        }
        else if(item.getItemId() == R.id.choosemyoutfit_menu_option) {
            // go to choose my outfit page
            Intent overviewIntent = new Intent(this, Activity_ChooseMyOutfit.class);
            this.startActivity(overviewIntent);
        }
        else if(item.getItemId() == R.id.settings_menu_option) {
            // go to settings page
            Intent overviewIntent = new Intent(this, Activity_Settings.class);
            this.startActivity(overviewIntent);
        }
        else if(item.getItemId() == R.id.logout_menu_option) {
            // log current user out and go to login page
            FirebaseAuth.getInstance().signOut();
            Intent overviewIntent = new Intent(this, Activity_Login.class);
            this.startActivity(overviewIntent);
        }
        else {
            return super.onOptionsItemSelected(item);
        }
        return true;
    }
}

