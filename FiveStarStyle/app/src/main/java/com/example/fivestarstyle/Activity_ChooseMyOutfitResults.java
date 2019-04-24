package com.example.fivestarstyle;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import lecho.lib.hellocharts.view.PieChartView;

public class Activity_ChooseMyOutfitResults extends AppCompatActivity {

    TextView labelTxt;
    private Context mContext;
    ArrayList<ItemDetailsObject> imagesTops = null;
    ArrayList<ItemDetailsObject> imagesBottoms = null;
    ArrayList<ItemDetailsObject> imagesDressesOrSuits = null;
    ArrayList<ItemDetailsObject> imagesOuterwear = null;
    ArrayList<ItemDetailsObject> imagesShoes = null;
    Integer rand;
    Integer size;
    Integer chosen;
    Button btnChooseNewOutfit;
    Button btnChangeTheEvent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__choose_my_outfit_results);
        mContext = this;
        btnChooseNewOutfit = (Button) findViewById(R.id.btn_choose_new_outfit);
        btnChangeTheEvent = (Button) findViewById(R.id.btn_change_the_event);

        //get imageUrls from old intent
        Intent i = getIntent();
        final Bundle bundle = i.getExtras();
        if(bundle.getSerializable("imagesTops") != null) {
            imagesTops = (ArrayList) bundle.getSerializable("imagesTops");
        }
        if(bundle.getSerializable("imagesBottoms") != null) {
            imagesBottoms = (ArrayList) bundle.getSerializable("imagesBottoms");
        }
        if(bundle.getSerializable("imagesDressesOrSuits") != null) {
            imagesDressesOrSuits = (ArrayList) bundle.getSerializable("imagesDressesOrSuits");
        }
        if(bundle.getSerializable("imagesOuterwear") != null) {
            imagesOuterwear = (ArrayList) bundle.getSerializable("imagesOuterwear");
        }
        if(bundle.getSerializable("imagesShoes") != null) {
            imagesShoes = (ArrayList) bundle.getSerializable("imagesShoes");
        }

        // get event clicked from old intent
        final String label = i.getExtras().getString("btnClicked");
        labelTxt = (TextView) findViewById(R.id.txtLabel);
        labelTxt.setText("Choosing a " + label + " outfit:");

        btnChooseNewOutfit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent restart = new Intent(Activity_ChooseMyOutfitResults.this, Activity_ChooseMyOutfitResults.class);
                restart.putExtras(bundle);
                restart.putExtra("btnClicked", label);
                startActivity(restart);
            }
        });

        btnChangeTheEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent back = new Intent(Activity_ChooseMyOutfitResults.this, Activity_ChooseMyOutfit.class);
                startActivity(back);
            }
        });

        // determine type of outfit to choose
        if(isNull(imagesTops) || isNull(imagesBottoms)) {
            // no top and bottom outfit, use dress or suit
            rand = 1;
        } else if(isNull(imagesDressesOrSuits)) {
            // no dress or suit, use top and bottom
            rand = 0;
        } else {
            // both top and bottom and dress or suit exist, randomly pick
            rand = (int) (Math.random() * 2);
        }

        // create list to pass to image adapter
        ArrayList<ItemDetailsObject> images = new ArrayList<>();

        // find random top and bottom OR dress or suit and add to list
        if(rand == 0) {
            // top and bottom outfit
            // get random top from list
            size = imagesTops.size();
            chosen = (int) (Math.random() * size);
            // add random top to new list
            images.add(imagesTops.get(chosen));
            // get random bottom from list
            size = imagesBottoms.size();
            chosen = (int) (Math.random() * size);
            // add random bottom to new list
            images.add(imagesBottoms.get(chosen));
        } else if(rand == 1) {
            // dress or suit outfit
            // get random dress or suit from list
            size = imagesDressesOrSuits.size();
            chosen = (int) (Math.random() * size);
            // add random dress or suit to new list
            images.add(imagesDressesOrSuits.get(chosen));
        }

        // if outerwear exists and ...
        if(notNull(imagesOuterwear)) {
            // current temp is less than 60 deg F ...
            if(GlobalVariables.temperature < 60) {
                // get random outerwear from list
                size = imagesOuterwear.size();
                chosen = (int) (Math.random() * size);
                // add random outwear to new list
                images.add(imagesOuterwear.get(chosen));
            }
        }

        // if shoes exist ...
        if(notNull(imagesShoes)) {
            // get random shoes from list
            size = imagesShoes.size();
            chosen = (int) (Math.random() * size);
            // add random shoes to new list
            images.add(imagesShoes.get(chosen));
        }

        //dynamically load images through GridView
        GridView gridView = (GridView) findViewById(R.id.gridViewResults);
        gridView.setAdapter(new ImageAdapterForRandomOutfit(this, images));
    }

    public boolean notNull(ArrayList<ItemDetailsObject> list) {
        if(list != null) {
            return true;
        }
        return false;
    }

    public boolean isNull(ArrayList<ItemDetailsObject> list) {
        if(list == null) {
            return true;
        }
        return false;
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

