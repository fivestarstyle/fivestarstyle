package com.example.fivestarstyle;

import android.Manifest;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.view.Menu;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;
import pub.devrel.easypermissions.PermissionRequest;


public class MainActivity extends AppCompatActivity {
    private final static String TAG = "MainActivity";
    private final static String TAG2 = "Location Permission";

    private final int REQUEST_LOCATION_PERMISSION = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        requestLocationPermission();

        ImageButton overviewScreen = (ImageButton) findViewById(R.id.overview);
        ImageButton chooseOutfitScreen = (ImageButton) findViewById(R.id.choose_my_outfit);
        ImageButton viewMyClosetScreen = (ImageButton) findViewById(R.id.view_my_closet);

        overviewScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent overviewIntent = new Intent(MainActivity.this, ClosetStatistics.class);
//                myIntent.putExtra("key", value); //Optional parameters
                MainActivity.this.startActivity(overviewIntent);
            }
        });

        chooseOutfitScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent chooseOutfitIntent = new Intent(MainActivity.this, ChooseOutfit.class);
//                myIntent.putExtra("key", value); //Optional parameters
                MainActivity.this.startActivity(chooseOutfitIntent);
            }
        });

        viewMyClosetScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent viewClosetIntent = new Intent(MainActivity.this, ClosetActivity.class);
//                myIntent.putExtra("key", value); //Optional parameters
                MainActivity.this.startActivity(viewClosetIntent);
            }
        });


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @AfterPermissionGranted(REQUEST_LOCATION_PERMISSION)
    public void requestLocationPermission() {
        String[] perms = {Manifest.permission.ACCESS_FINE_LOCATION};
        if(EasyPermissions.hasPermissions(this, perms)) {
            Log.d(TAG2,"Location permission has been granted.");
        }
        else {
//            EasyPermissions.requestPermissions(this, "Please grant the location permission", REQUEST_LOCATION_PERMISSION, perms);
            EasyPermissions.requestPermissions(
                    new PermissionRequest.Builder(this, REQUEST_LOCATION_PERMISSION, perms)
                            .setRationale("Please allow FiveStarStyle to access your location in order for the outfit predictions to " +
                                    "match the weather in your area.")
//                            .setPositiveButtonText(R.string.rationale_ask_ok)
//                            .setNegativeButtonText(R.string.rationale_ask_cancel)
                            .setTheme(R.style.ButtonTheme)
                            .build());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_options,menu);
        return true;
    }

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
        else if(item.getItemId() == R.id.logout_menu_option) {
            try {
                FirebaseAuth.getInstance().signOut();
                Log.d(TAG, "User signed out");
                Intent overviewIntent = new Intent(this,LoginActivity.class);
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
