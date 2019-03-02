package com.example.fivestarstyle;

import android.graphics.BitmapFactory;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.label.FirebaseVisionLabel;
import com.google.firebase.ml.vision.label.FirebaseVisionLabelDetector;
import com.google.firebase.ml.vision.label.FirebaseVisionLabelDetectorOptions;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;


public class MLKit extends BaseActivity implements View.OnClickListener {
    private Bitmap mBitmap;
    private ImageView mImageView;
    private TextView mTextView;

    static final int REQUEST_IMAGE_CAPTURE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mlkit);

        mTextView = findViewById(R.id.textView);
        mImageView = findViewById(R.id.imageView);
        findViewById(R.id.btn_choose_picture).setOnClickListener(this);
        findViewById(R.id.btn_take_picture).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_take_picture:
                dispatchTakePictureIntent();
                break;
            case R.id.btn_choose_picture:
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, 0);
        }
    }


    private void analyzePicture() {
        if (mBitmap != null) {

//Configure the detector//

            FirebaseVisionLabelDetectorOptions options = new FirebaseVisionLabelDetectorOptions.Builder()

//Set the confidence threshold//

                    .setConfidenceThreshold(0.7f)
                    .build();

//Create a FirebaseVisionImage object//

            FirebaseVisionImage image = FirebaseVisionImage.fromBitmap(mBitmap);

//Create an instance of FirebaseVisionLabelDetector//

            FirebaseVisionLabelDetector detector =
                    FirebaseVision.getInstance().getVisionLabelDetector(options);

//Register an OnSuccessListener//

            detector.detectInImage(image).addOnSuccessListener(new OnSuccessListener<List<FirebaseVisionLabel>>() {
                        @Override

//Implement the onSuccess callback//

                        public void onSuccess(List<FirebaseVisionLabel> labels) {
                            for (FirebaseVisionLabel label : labels) {

//Display the label and confidence score in our TextView//

                                mTextView.append(label.getLabel() + "\n");
                                mTextView.append(label.getConfidence() + "\n\n");
                            }
                        }

//Register an OnFailureListener//

                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            mTextView.setText(e.getMessage());
                        }
                    });
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("requestCode", String.valueOf(requestCode));
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case RC_STORAGE_PERMS1:
                    checkStoragePermission(requestCode);
                    break;
                case RC_SELECT_PICTURE:
                    Uri dataUri = data.getData();
                    String path = MyHelper.getPath(this, dataUri);
                    if (path == null) {
                        mBitmap = MyHelper.resizeImage(imageFile, this, dataUri, mImageView);
                    } else {
                        mBitmap = MyHelper.resizeImage(imageFile, path, mImageView);
                    }
                    if (mBitmap != null) {
                        mTextView.setText(null);
                        mImageView.setImageBitmap(mBitmap);
                        analyzePicture();
                    }
                    break;
                case REQUEST_IMAGE_CAPTURE:
                    Uri targetUri = data.getData();
                    mBitmap = MyHelper.resizeImage(imageFile, this, targetUri, mImageView);
//                    mBitmap = (Bitmap) data.getExtras().get("data");
                    mImageView.setImageBitmap(mBitmap);
                    analyzePicture();
                    break;
                case 0:
                    try {
                        final Uri imageUri = data.getData();
                        final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                        mBitmap = BitmapFactory.decodeStream(imageStream);
                        mImageView.setImageBitmap(mBitmap);
                        mTextView.setText(null);
                        analyzePicture();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                        Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG).show();
                    }
                    break;
            }
        }
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }
}
