package com.example.fivestarstyle;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class PopulateViewClosetActivity extends AppCompatActivity {
    private final static String TAG = "PopulateView";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_populate_view_closet);
        //TEST
//        String imageUri = "https://i.imgur.com/tGbaZCY.jpg";
//        ImageView ivBasicImage = (ImageView) findViewById(R.id.ivBasicImage);
//        Picasso.get().load(imageUri).into(ivBasicImage);
        //TEST

        //get imageUrls from old intent
        Bundle bundle = getIntent().getExtras();
        ArrayList<String> imageUrls = (ArrayList) bundle.getSerializable("images");
        LinearLayout layout = (LinearLayout)findViewById(R.id.imageLayout);
        for(String imageUri : imageUrls){
            Log.d(TAG, "Image =>" + imageUri);
            ImageView image = new ImageView(this);
            image.setLayoutParams(new android.view.ViewGroup.LayoutParams(80,60));
            image.setMaxHeight(20);
            image.setMaxWidth(20);
            // Adds the view to the layout
            layout.addView(image);
            Picasso.get().load(imageUri).into(image);
        }
    }
}
