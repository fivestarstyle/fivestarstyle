package com.example.fivestarstyle;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;

// adapter to load images into gridView for the closet
public class ImageAdapter extends BaseAdapter {
    public final String TAG = "imageAdapter";
    private Context mContext;
    public ArrayList<ItemDetailsObject> mImageUrls;

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
        final int index = position;
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        final FirebaseFirestore db = FirebaseFirestore.getInstance();
        ImageView imageBtn = new ImageView(mContext);
        Picasso.get().load(mImageUrls.get(position).getImageUrl()).into(imageBtn);
        imageBtn.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageBtn.setLayoutParams(new GridView.LayoutParams(520,520));
        imageBtn.setClickable(true);
        imageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d(TAG, "onclickImage");
                Log.d(TAG, "image ID: " + mImageUrls.get(index).getDocTitle() + " cat: " + mImageUrls.get(index).getCat());

                db.collection("userClosets/" + user.getUid() + "/" + mImageUrls.get(index).getCat()).document(mImageUrls.get(index).getDocTitle())
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()) {
                                    DocumentSnapshot document = task.getResult();
                                    if (document.exists()) {
                                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                                        Intent detailIntent = new Intent(mContext, Activity_ImageDetail.class);
                                        Bundle bundle = new Bundle();
                                        ItemDetailsObject obj = new ItemDetailsObject();
                                        obj.setImageUrl(document.getData().get("image").toString());
                                        obj.setDocTitle(mImageUrls.get(index).getDocTitle());
                                        obj.setSeasons(Arrays.asList(document.getData().get("seasons").toString()));
                                        obj.setCat(mImageUrls.get(index).getCat());
                                        obj.setColor(document.getData().get("color").toString());
                                        obj.setEvents(Arrays.asList(document.getData().get("events").toString()));
                                        ArrayList<ItemDetailsObject> details = new ArrayList<>();
                                        details.add(obj);
                                        bundle.putSerializable("detail", details);
                                        detailIntent.putExtras(bundle);
                                        mContext.startActivity(detailIntent);
                                    } else {
                                        Log.d(TAG, "No such document");
                                    }
                                } else {
                                    Log.d(TAG, "get failed with ", task.getException());
                                }
                            }
                        });
            }
        });
        return imageBtn;
    }
}
