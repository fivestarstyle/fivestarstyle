package com.example.fivestarstyle;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

// adapter to load images into gridView for the closet
public class ImageAdapter extends BaseAdapter {
    private Context mContext;
    public ArrayList<String> mImageUrls;
    // Constructor
    public ImageAdapter(Context c, ArrayList images){
        mContext = c;
        mImageUrls = images;
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
        ImageView imageView = new ImageView(mContext);
        Picasso.get().load(mImageUrls.get(position)).into(imageView);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setLayoutParams(new GridView.LayoutParams(520, 520));
        return imageView;
    }
}
