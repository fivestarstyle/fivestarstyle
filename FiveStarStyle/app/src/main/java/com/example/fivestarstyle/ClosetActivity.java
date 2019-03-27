package com.example.fivestarstyle;

import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.StorageReference;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ClosetActivity extends AppCompatActivity {
    private final static String TAG = "ClosetActivity";
    private static final  int GALLERY_REQUEST =1;
    private static final int CAMERA_REQUEST_CODE=1;
    private StorageReference mStorage;
    private ArrayList<String> imageUrls = new ArrayList<>();

    /*Functionality for the add button, goes to the add clothing page*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_closet);

        ImageButton addClothing = (ImageButton) findViewById(R.id.AddButton);
        //retrieve image buttons
        ImageButton btnCloset = (ImageButton) findViewById(R.id.AllButton);
        ImageButton btnTops = (ImageButton) findViewById(R.id.TopButton);
        ImageButton btnDressesSuits = (ImageButton) findViewById(R.id.DressesSuitsButton);
        ImageButton btnBottoms = (ImageButton) findViewById(R.id.PantsButton);
        ImageButton btnOuterwear = (ImageButton) findViewById(R.id.OuterwearButton);
        ImageButton btnShoes = (ImageButton) findViewById(R.id.ShoesButton);
        ImageButton btnAccessories = (ImageButton) findViewById(R.id.AccButton);

        addClothing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gcapiIntent = new Intent(ClosetActivity.this, GoogleCloudAPI.class);
                startActivity(gcapiIntent);
            }
        });

        btnCloset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getImages("all");
            }
        });

        btnTops.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getImages("tops");
            }
        });

        btnDressesSuits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getImages("dress");
            }
        });
        btnBottoms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getImages("bottoms");
            }
        });
        btnOuterwear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getImages("outerwear");
            }
        });
        btnShoes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getImages("shoes");
            }
        });
        btnAccessories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getImages("accessories");
            }
        });
    }

    private void getImages(String category){
        DataTransferService.retrieveImagesForCloset(category, new OnGetImagesListener() {
            @Override
            public void onStart() {
                //DO SOME THING WHEN START GET DATA HERE
            }

            @Override
            public void onSuccess(QuerySnapshot data) {
                for (QueryDocumentSnapshot document : data) {
                    Log.d(TAG, document.getId() + " ImageURL:" + document.get("image").toString() + " => " + document.getData());
                    imageUrls.add(document.get("image").toString());
                }
                Log.d(TAG, "imageUrls received");
                Intent viewIntent = new Intent(ClosetActivity.this, PopulateViewClosetActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("images", imageUrls);
                viewIntent.putExtras(bundle);
                startActivity(viewIntent);
            }

            @Override
            public void onFailed(Exception databaseError) {
                //DO SOME THING WHEN GET DATA FAILED HERE
            }
        });
    }
//    static final int REQUEST_IMAGE_CAPTURE = 1;
//
//    private void dispatchTakePictureIntent() {
//        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
//            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
//        }
//    }

//    /*Requests for use of camera*/
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        // if(requestCode == GALLERY_REQUEST && resultCode == RESULT_OK){
//        if(requestCode == CAMERA_REQUEST_CODE && resultCode == RESULT_OK){
//            mImageUri = data.getData();
//
////            CropImage.activity(mImageUri)
////                    .setGuidelines(CropImageView.Guidelines.ON)
////                    .setAspectRatio(1,1)
////                    .start(this);
//        }
//    }

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
