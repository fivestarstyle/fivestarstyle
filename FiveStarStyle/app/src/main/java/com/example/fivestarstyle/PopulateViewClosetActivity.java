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

        //get imageUrls from old intent
        Bundle bundle = getIntent().getExtras();
        ArrayList<String> imageUrls = (ArrayList) bundle.getSerializable("images");
        Log.d(TAG, "imageURLs: " + imageUrls);
        LinearLayout layout = (LinearLayout)findViewById(R.id.imageLayout);

        for(String imageUri : imageUrls){
            Log.d(TAG, "Image =>" + imageUri);
            ImageView image = new ImageView(this);
            image.setLayoutParams(new android.view.ViewGroup.LayoutParams(500,500));
            image.setMaxHeight(500);
            image.setMaxWidth(500);
            Picasso.get().load(imageUri).into(image);
//            Adds the view to the layout
            layout.addView(image);
        }
    }
}
