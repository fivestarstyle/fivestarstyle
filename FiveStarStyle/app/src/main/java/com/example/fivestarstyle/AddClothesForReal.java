package com.example.fivestarstyle;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class AddClothesForReal extends AppCompatActivity {

    private Button mUploadBtn;
    private ImageView mImageView;

    private static final int CAMERA_REQUEST_CODE = 1;

    private StorageReference mStorage;

    private ProgressDialog mProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_clothes_for_real);

        mStorage = FirebaseStorage.getInstance().getReference();

        mUploadBtn = (Button) findViewById(R.id.takepic);
        mImageView = (ImageView) findViewById(R.id.imageView6);

        mProgress = new ProgressDialog(this);

        mUploadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                startActivityForResult(intent, CAMERA_REQUEST_CODE);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == CAMERA_REQUEST_CODE && resultCode == RESULT_OK) {

            mProgress.setMessage("Uploading Image ...");
            mProgress.show();

            Uri uri = data.getData();

            StorageReference filepath = mStorage.child("Photo").child(uri.getLastPathSegment());

            filepath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    mProgress.dismiss();

                    Task<Uri> urlTask = taskSnapshot.getMetadata().getReference().getDownloadUrl();
                    while (!urlTask.isSuccessful());
                    Uri downloadUrl = urlTask.getResult();

                    Picasso.get().load(downloadUrl).fit().centerCrop().into(mImageView);

                    Toast.makeText(AddClothesForReal.this, "Uploading Finished...", Toast.LENGTH_LONG).show();
                }
            });
        }
    }
}
