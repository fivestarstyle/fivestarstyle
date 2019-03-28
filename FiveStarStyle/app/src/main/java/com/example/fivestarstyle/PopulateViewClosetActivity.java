package com.example.fivestarstyle;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class PopulateViewClosetActivity extends AppCompatActivity {
    private final static String TAG = "PopulateView";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_populate_view_closet);

        //get imageUrls from old intent
        Bundle bundle = getIntent().getExtras();
        ArrayList<String> imageUrls = (ArrayList) bundle.getSerializable("images");
        Log.d(TAG, "imageURLs: " + imageUrls);

        //dynamically load images through GridView
        GridView gridView = (GridView) findViewById(R.id.gridView);
        gridView.setAdapter(new ImageAdapter(this, imageUrls));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_options,menu);
        return true;
    }

    /*Functionality to the dropdown menu in the top right corner*/
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.home__menu_option) {
            Intent homeIntent = new Intent(this,MainActivity.class);
            this.startActivity(homeIntent);
        }
        else if(item.getItemId() == R.id.closet_menu_option) {
            Intent overviewIntent = new Intent(this,ClosetActivity.class);
            this.startActivity(overviewIntent);
        }
        else if(item.getItemId() == R.id.overview_menu_option) {
            Intent overviewIntent = new Intent(this,ClosetStatistics.class);
            this.startActivity(overviewIntent);
        }
        else if(item.getItemId() == R.id.choosemyoutfit_menu_option) {
            Intent overviewIntent = new Intent(this,ChooseOutfit.class);
            this.startActivity(overviewIntent);
        }
        else if(item.getItemId() == R.id.settings_menu_option) {
            Intent overviewIntent = new Intent(this,SettingsActivity.class);
            this.startActivity(overviewIntent);
        }
        else if(item.getItemId() == R.id.logout_menu_option) {
            FirebaseAuth.getInstance().signOut();
            Intent overviewIntent = new Intent(this,LoginActivity.class);
            this.startActivity(overviewIntent);
        }
        else {
            return super.onOptionsItemSelected(item);
        }
        return true;
    }
}
