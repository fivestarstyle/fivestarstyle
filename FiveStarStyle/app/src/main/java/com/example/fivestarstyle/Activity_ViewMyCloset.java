package com.example.fivestarstyle;

import android.content.Intent;
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

import java.util.ArrayList;
import java.util.Arrays;

public class Activity_ViewMyCloset extends AppCompatActivity {
    private final static String TAG = "Activity_ViewMyCloset";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_my_closet);

        /*Functionality for the add button, goes to the add clothing page*/
        ImageButton addClothing = (ImageButton) findViewById(R.id.AddButton);
        addClothing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gcapiIntent = new Intent(Activity_ViewMyCloset.this, Activity_GoogleCloudAPI.class);
                startActivity(gcapiIntent);
            }
        });

        //retrieve image buttons
        ImageButton btnTops = (ImageButton) findViewById(R.id.TopButton);
        ImageButton btnDressesSuits = (ImageButton) findViewById(R.id.DressesSuitsButton);
        ImageButton btnBottoms = (ImageButton) findViewById(R.id.PantsButton);
        ImageButton btnOuterwear = (ImageButton) findViewById(R.id.OuterwearButton);
        ImageButton btnShoes = (ImageButton) findViewById(R.id.ShoesButton);
        ImageButton btnAccessories = (ImageButton) findViewById(R.id.AccButton);

        //onClickListeners for retrievals
        btnTops.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getImages("top");
            }
        });
        btnDressesSuits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getImages("dress_or_suit");
            }
        });
        btnBottoms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getImages("bottom");
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

    private void getImages(final String category){
        DataTransferService.retrieveImagesForCloset(category, new OnGetDataListener() {
            @Override
            public void onStart() {
                Log.d(TAG, "start data retrieval");
            }
            @Override
            public void onSuccess(QuerySnapshot data) {
                ArrayList<ItemDetailsObject> images = new ArrayList<>();
                for (QueryDocumentSnapshot document : data) {
                    ItemDetailsObject obj = new ItemDetailsObject();
                    obj.setImageUrl(document.get("image").toString());
                    obj.setCat(category);
                    obj.setDocTitle(document.getId());
//                    obj.setEvents(Arrays.asList(document.get("events").toString()));
//                    obj.setSeasons(Arrays.asList(document.get("seasons").toString()));
                    Log.d(TAG,  document.getData() + "=> " + obj.getDocTitle());
                    images.add(obj);
                }
                Log.d(TAG, "imageUrls received");
                Intent viewIntent = new Intent(Activity_ViewMyCloset.this, Activity_PopulateViewCloset.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("images", images);
                viewIntent.putExtras(bundle);
                startActivity(viewIntent);
            }
            @Override
            public void onFailed(Exception databaseError) {
                Log.d(TAG, "Error getting documents: ", databaseError);
            }
        });
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
            Intent homeIntent = new Intent(this, Activity_Main.class);
            this.startActivity(homeIntent);
        }
        else if(item.getItemId() == R.id.closet_menu_option) {
            Intent overviewIntent = new Intent(this, Activity_ViewMyCloset.class);
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
