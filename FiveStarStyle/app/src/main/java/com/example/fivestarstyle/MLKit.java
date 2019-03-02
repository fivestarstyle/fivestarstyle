package com.example.fivestarstyle;

import android.graphics.BitmapFactory;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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

import java.io.File;
import java.util.List;


public class MLKit extends BaseActivity implements View.OnClickListener {
    private Bitmap mBitmap;
    private ImageView mImageView;
    private TextView mTextView;
    private Button takePicture;
    private Button choosePicture;
    private Button confirmLabels;

    static final int REQUEST_IMAGE_CAPTURE = 1;
    private File output;
    private static final int CONTENT_REQUEST=1337;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mlkit);

        mTextView = findViewById(R.id.textView);
        mImageView = findViewById(R.id.imageView);
        takePicture = findViewById(R.id.btn_take_picture);
        choosePicture = findViewById(R.id.btn_choose_picture);
        confirmLabels = findViewById(R.id.btn_confirm_label);
        confirmLabels.setVisibility(View.GONE);

        findViewById(R.id.btn_choose_picture).setOnClickListener(this);
        findViewById(R.id.btn_take_picture).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_take_picture:
//                dispatchTakePictureIntent();
                takePictureIntent();
                break;
            case R.id.btn_choose_picture:
                checkStoragePermission(RC_STORAGE_PERMS1);
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

            final FirebaseVisionImage image = FirebaseVisionImage.fromBitmap(mBitmap);

//Create an instance of FirebaseVisionLabelDetector//

            FirebaseVisionLabelDetector detector =
                    FirebaseVision.getInstance().getVisionLabelDetector(options);

//Register an OnSuccessListener//

            detector.detectInImage(image).addOnSuccessListener(new OnSuccessListener<List<FirebaseVisionLabel>>() {
                        @Override

//Implement the onSuccess callback//

                        public void onSuccess(final List<FirebaseVisionLabel> labels) {
                            for (FirebaseVisionLabel label : labels) {

//Display the label and confidence score in our TextView//

                                mTextView.append(label.getLabel() + "\n");
                                mTextView.append(label.getConfidence() + "\n\n");
                            }
                            confirmLabels.setVisibility(View.VISIBLE);
                            confirmLabels.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    DataTransferService.addItem(image, labels);
                                }
                            });
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
                    choosePicture.setText("Choose Another Picture");
                    takePicture.setText("Take Picture");
                    break;
                case REQUEST_IMAGE_CAPTURE:
                    mBitmap = (Bitmap) data.getExtras().get("data");
                    mImageView.setImageBitmap(mBitmap);
                    mTextView.setText(null);
                    analyzePicture();
                    takePicture.setText("Retake Picture");
                    choosePicture.setText("Choose From Library");
            }
        }
    }


    private void takePictureIntent() {
        Intent pictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(pictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(pictureIntent, REQUEST_IMAGE_CAPTURE);
        }

    }
}
