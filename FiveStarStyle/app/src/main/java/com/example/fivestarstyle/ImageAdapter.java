package com.example.fivestarstyle;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

// adapter to load images into gridView for the closet
public class ImageAdapter extends BaseAdapter {
    public final String TAG = "imageAdapter";
    private Context mContext;
    public ArrayList<ItemDetailsObject> mImageUrls;
    public String mCategory;

    // Constructor
    public ImageAdapter(Context c, ArrayList images, String category){
        mContext = c;
        mImageUrls = images;
        mCategory = category;
    }

    @Override
    public int getCount() {
        return mImageUrls.size();
    }

    @Override
    public Object getItem(int position) {
        return mImageUrls.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
//        ImageView imageView = new ImageView(mContext);
//        Picasso.get().load(mImageUrls.get(position)).into(imageView);
//        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
//        imageView.setLayoutParams(new GridView.LayoutParams(520, 520));
//        return imageView;
        ImageButton imageBtn = new ImageButton(mContext);
        Picasso.get().load(mImageUrls.get(position).getImageUrl()).into(imageBtn);
        imageBtn.setScaleType(ImageButton.ScaleType.CENTER_CROP);
        imageBtn.setLayoutParams(new GridView.LayoutParams(520,520));
        imageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onclickImage");

            }
        });
        return imageBtn;
    }
}
