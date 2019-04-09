package com.example.fivestarstyle;

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
    public ArrayList<String> mImageUrls;
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
        final int index = position;
        ImageButton imageBtn = new ImageButton(mContext);
        Picasso.get().load(mImageUrls.get(position)).into(imageBtn);
        imageBtn.setScaleType(ImageButton.ScaleType.CENTER_CROP);
        imageBtn.setLayoutParams(new GridView.LayoutParams(520,520));
        imageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onclickImage");
                //get details and send to imageDetailPage
                DataTransferService.getItemDetails(mImageUrls.get(index), mCategory, new OnGetDataListener() {
                    @Override
                    public void onStart() {
                        //on start
                    }

                    @Override
                    public void onSuccess(QuerySnapshot data) {
                        ArrayList<String> itemDetails = new ArrayList<>();
                        for (QueryDocumentSnapshot document : data) {
                            Log.d(TAG, document.getId() + " ImageURL:" + document.get("image").toString() + " => " + document.getData());
                            itemDetails.add(document.get("image").toString());
                        }
                        Intent detailIntent = new Intent(mContext, Activity_ImageDetail.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("details", itemDetails);
                        detailIntent.putExtras(bundle);
                        mContext.startActivity(detailIntent);
                    }

                    @Override
                    public void onFailed(Exception databaseError) {
                        Log.d(TAG, "Error getting documents: ", databaseError);
                    }
                });
            }
        });
        return imageBtn;
    }
}
