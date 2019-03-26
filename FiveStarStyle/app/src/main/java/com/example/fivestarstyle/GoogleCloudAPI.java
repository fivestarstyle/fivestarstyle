package com.example.fivestarstyle;

import android.Manifest;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.common.AccountPicker;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.vision.v1.Vision;
import com.google.api.services.vision.v1.VisionRequestInitializer;
import com.google.api.services.vision.v1.model.AnnotateImageRequest;
import com.google.api.services.vision.v1.model.BatchAnnotateImagesRequest;
import com.google.api.services.vision.v1.model.BatchAnnotateImagesResponse;
import com.google.api.services.vision.v1.model.EntityAnnotation;
import com.google.api.services.vision.v1.model.Feature;
import com.google.api.services.vision.v1.model.Image;
import com.google.api.services.vision.v1.model.ImageProperties;
import com.google.firebase.auth.FirebaseAuth;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class GoogleCloudAPI extends BaseActivity {
    private static String accessToken;
    static final int REQUEST_GALLERY_IMAGE = 10;
    static final int REQUEST_CODE_PICK_ACCOUNT = 11;
    static final int REQUEST_ACCOUNT_AUTHORIZATION = 12;
    static final int REQUEST_PERMISSIONS = 13;
    static final int REQUEST_CODE_CAMERA = 14;
    private final String LOG_TAG = "GoogleCloudAPI";
    private ImageView selectedImage;
//    private TextView resultTextView;
    private Account mAccount;
    private ProgressDialog mProgressDialog;
    private Button selectImage;
    private Button takePhoto;
    private Button confirmLabels;
    private Integer chooseImageFlag = 0;
    private Integer takePictureFlag = 0;
    private Uri uri;
    private Integer counter = 0;
    Dialog myDialog;
    LabelsObject newLabelsObject;
    Bitmap image;


    static final int REQUEST_IMAGE_CAPTURE = 1;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_options,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.home__menu_option) {
            Intent homeIntent = new Intent(this,MainActivity.class);
            this.startActivity(homeIntent);
        }
        else if(item.getItemId() == R.id.closet_menu_option) {
            Intent overviewIntent = new Intent(this,ClosetActivity.class);
            this.startActivity(overviewIntent);
        }
        else if(item.getItemId() == R.id.overview_menu_option) {
            Intent overviewIntent = new Intent(this,ClosetStatistics.class);
            this.startActivity(overviewIntent);
        }
        else if(item.getItemId() == R.id.choosemyoutfit_menu_option) {
            Intent overviewIntent = new Intent(this,ChooseOutfit.class);
            this.startActivity(overviewIntent);
        }
        else if(item.getItemId() == R.id.settings_menu_option) {
            Intent overviewIntent = new Intent(this,SettingsActivity.class);
            this.startActivity(overviewIntent);
        }
        else if(item.getItemId() == R.id.logout_menu_option) {
            FirebaseAuth.getInstance().signOut();
            Intent overviewIntent = new Intent(this,LoginActivity.class);
            this.startActivity(overviewIntent);
        }
        else {
            return super.onOptionsItemSelected(item);
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_cloud_api);
        mProgressDialog = new ProgressDialog(this);
        selectedImage = (ImageView) findViewById(R.id.selected_image);
//        resultTextView = (TextView) findViewById(R.id.result);
        selectImage = (Button) findViewById(R.id.btn_choose_picture);
        takePhoto = (Button) findViewById(R.id.btn_take_picture);
        confirmLabels = (Button) findViewById(R.id.btn_confirm_label);
        confirmLabels.setVisibility(View.GONE);

        newLabelsObject = new LabelsObject();

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        selectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityCompat.requestPermissions(GoogleCloudAPI.this,
                        new String[]{Manifest.permission.GET_ACCOUNTS},
                        REQUEST_PERMISSIONS);
                chooseImageFlag = 1;
            }
        });

        takePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityCompat.requestPermissions(GoogleCloudAPI.this,
                        new String[]{Manifest.permission.GET_ACCOUNTS},
                        REQUEST_PERMISSIONS);
                takePictureFlag = 1;
            }
        });

        myDialog = new Dialog(this);
                //
//        checkStoragePermission(RC_STORAGE_PERMS1);
//        findViewById(R.id.select_image_button).setOnClickListener(this);
    }

    public void ShowPopup(View v) {
        TextView txtclose;
        Button btnFollow;
        final CheckBox chkCategory;
        final CheckBox chkColor;

        View view = findViewById(R.id.linearLayout1);
        myDialog.setContentView(R.layout.labels_popup);
        txtclose =(TextView) myDialog.findViewById(R.id.txtclose);
        btnFollow = (Button) myDialog.findViewById(R.id.btnfollow);
        chkCategory = (CheckBox) myDialog.findViewById(R.id.chkCategory);
        chkColor = (CheckBox) myDialog.findViewById(R.id.chkColor);

//        newLabelsObject.labelSetCategory("Top");
//        newLabelsObject.labelSetColor("Red");

        // check if category/color labels were found/updated
        String category = newLabelsObject.labelGetCategory();
        String color = newLabelsObject.labelGetColor();

        if(category.equals("none") || category.length() == 0) {
            if(color.equals("none") || color.length() == 0) {
                // no labels found
                Log.d("LABELS", "dismissed");
                chkCategory.setChecked(false);
                chkCategory.setChecked(false);
                newLabelsObject.labelSetCategory("none");
                newLabelsObject.labelSetColor("none");
                Intent confirmLabelIntent = new Intent(GoogleCloudAPI.this, ConfirmLabels.class);
                confirmLabelIntent.putExtra("labelsObj", newLabelsObject);
                startActivity(confirmLabelIntent);
            }
            // just color label found
            else {
                Log.d("LABELS", "Just Color");
                txtclose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        myDialog.dismiss();
                    }
                });
                btnFollow.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(!chkColor.isChecked()) {
                            newLabelsObject.labelSetColor("none");
                        }
                        newLabelsObject.labelSetCategory("none");
                        Intent confirmLabelIntent = new Intent(GoogleCloudAPI.this, ConfirmLabels.class);
                        confirmLabelIntent.putExtra("labelsObj", newLabelsObject);
                        startActivity(confirmLabelIntent);
                    }
                });
                chkColor.setText("Color: " + color);
                chkCategory.setChecked(false);
                chkCategory.setVisibility(View.GONE);
                myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                myDialog.show();
            }
        }
        // just category label found
        else if(color.equals("none") || color.length() == 0) {
            Log.d("LABELS", "Just Category");
            txtclose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    myDialog.dismiss();
                }
            });
            btnFollow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!chkCategory.isChecked()) {
                        newLabelsObject.labelSetCategory("none");
                    }
                    newLabelsObject.labelSetColor("none");
                    Intent confirmLabelIntent = new Intent(GoogleCloudAPI.this, ConfirmLabels.class);
                    confirmLabelIntent.putExtra("labelsObj", newLabelsObject);
                    startActivity(confirmLabelIntent);
                }
            });
            chkCategory.setText("Category: " + category);
            chkColor.setChecked(false);
            chkColor.setVisibility(View.GONE);
            myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            myDialog.show();
        }
        // both labels found
        else {
            txtclose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    myDialog.dismiss();
                }
            });
            btnFollow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!chkCategory.isChecked()) {
                        newLabelsObject.labelSetCategory("none");
                    }
                    if(!chkColor.isChecked()) {
                        newLabelsObject.labelSetColor("none");
                    }
                    Intent confirmLabelIntent = new Intent(GoogleCloudAPI.this, ConfirmLabels.class);
                    confirmLabelIntent.putExtra("labelsObj", newLabelsObject);
                    startActivity(confirmLabelIntent);
                }
            });
            chkCategory.setText("Category: " + category);
            chkColor.setText("Color: " + color);
            myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            myDialog.show();
        }
    }

    private void launchImagePicker() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select an image"),
                REQUEST_GALLERY_IMAGE);
    }

    private void dispatchTakePictureIntent() throws IOException {
//        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
//            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
//        }
        counter++; //this is an int
        String imageFileName = "JPEG_" + counter; //make a better file name
        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(imageFileName,
                ".jpg",
                storageDir
        );

        uri = Uri.fromFile(image);
        Intent takePhotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        takePhotoIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        takePhotoIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivityForResult(takePhotoIntent, REQUEST_IMAGE_CAPTURE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_PERMISSIONS:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getAuthToken();
                } else {
                    Toast.makeText(GoogleCloudAPI.this, "Permission Denied!", Toast.LENGTH_SHORT).show();
                }
                break;
            case REQUEST_CODE_CAMERA:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

//                    Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show();
                    try {
                        Log.d(LOG_TAG, "Camera Permission Granted!");
                        dispatchTakePictureIntent();
                    } catch (IOException e) {
                        Log.d(LOG_TAG, "error taking photo");
                    }

                } else {

                    Toast.makeText(this, "Camera Permission Denied", Toast.LENGTH_LONG).show();

                }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_GALLERY_IMAGE && resultCode == RESULT_OK && data != null) {
            uploadImage(data.getData());
        }
        else if (requestCode == REQUEST_CODE_PICK_ACCOUNT) {
            if (resultCode == RESULT_OK) {
                String email = data.getStringExtra(AccountManager.KEY_ACCOUNT_NAME);
                AccountManager am = AccountManager.get(this);
                Account[] accounts = am.getAccountsByType(GoogleAuthUtil.GOOGLE_ACCOUNT_TYPE);
                for (Account account : accounts) {
                    if (account.name.equals(email)) {
                        mAccount = account;
                        break;
                    }
                }
                getAuthToken();
//                checkStoragePermission(RC_STORAGE_PERMS1);
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "No Account Selected", Toast.LENGTH_SHORT)
                        .show();
            }
        } else if (requestCode == REQUEST_ACCOUNT_AUTHORIZATION) {
            if (resultCode == RESULT_OK) {
                Bundle extra = data.getExtras();
                onTokenReceived(extra.getString("authtoken"));
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "Authorization Failed", Toast.LENGTH_SHORT)
                        .show();
            }
        }
        if(resultCode == RESULT_OK) {
            switch(requestCode) {
                case RC_STORAGE_PERMS1:
                    checkStoragePermission(requestCode);
                    break;
                case RC_SELECT_PICTURE:
//                    resultTextView.setText("Woohoo!");
                    Uri dataUri = data.getData();
                    uploadImage(dataUri);
                    break;
                case REQUEST_IMAGE_CAPTURE:
//                    Bitmap photo = (Bitmap) data.getExtras().get("data");
//                    Uri u = data.getData();
                    uploadImage(uri);
            }
        }
    }

    public void uploadImage(Uri uri) {
        if (uri != null) {
            try {
                Bitmap bitmap = resizeBitmap(
                        MediaStore.Images.Media.getBitmap(getContentResolver(), uri));
                callCloudVision(bitmap);
                selectedImage.setImageBitmap(bitmap);
            } catch (IOException e) {
                Log.e(LOG_TAG, e.getMessage());
            }
        } else {
            Log.e(LOG_TAG, "Null image was returned.");
        }
    }

    private void callCloudVision(final Bitmap bitmap) throws IOException {
//        resultTextView.setText("Retrieving results from cloud");

        new AsyncTask<Object, Void, String>() {
            @Override
            protected String doInBackground(Object... params) {
                try {
                    GoogleCredential credential = new GoogleCredential().setAccessToken(accessToken);
                    HttpTransport httpTransport = AndroidHttp.newCompatibleTransport();
                    JsonFactory jsonFactory = GsonFactory.getDefaultInstance();

                    Vision.Builder builder = new Vision.Builder
                            (httpTransport, jsonFactory, credential);
                    Vision vision = builder.build();

                    List<Feature> featureList = new ArrayList<>();
                    Feature labelDetection = new Feature();
                    labelDetection.setType("LABEL_DETECTION");
                    labelDetection.setMaxResults(10);
                    featureList.add(labelDetection);

//                    Feature imageProperties = new Feature();
//                    imageProperties.setType("IMAGE_PROPERTIES");
//                    imageProperties.setMaxResults(5);
//                    Log.d("IMAGE", String.valueOf(imageProperties));
//                    featureList.add(imageProperties);

                    List<AnnotateImageRequest> imageList = new ArrayList<>();
                    AnnotateImageRequest annotateImageRequest = new AnnotateImageRequest();
                    Image base64EncodedImage = getBase64EncodedJpeg(bitmap);
                    annotateImageRequest.setImage(base64EncodedImage);
                    annotateImageRequest.setFeatures(featureList);
                    imageList.add(annotateImageRequest);

                    BatchAnnotateImagesRequest batchAnnotateImagesRequest =
                            new BatchAnnotateImagesRequest();
                    batchAnnotateImagesRequest.setRequests(imageList);

                    Vision.Images.Annotate annotateRequest =
                            vision.images().annotate(batchAnnotateImagesRequest);
                    // Due to a bug: requests to Vision API containing large images fail when GZipped.
                    annotateRequest.setDisableGZipContent(true);
                    Log.d(LOG_TAG, "sending request");

                    BatchAnnotateImagesResponse response = annotateRequest.execute();
                    Log.d("Response", String.valueOf(response));
                    //add image to object being passed
                    image = bitmap;
//                    newLabelsObject.labelSetImage(bitmap);
                    return convertResponseToString(response);

                } catch (GoogleJsonResponseException e) {
                    Log.e(LOG_TAG, "Request failed: " + e.getContent());
                } catch (IOException e) {
                    Log.d(LOG_TAG, "Request failed: " + e.getMessage());
                }
                return "Cloud Vision API request failed.";
            }

            protected void onPostExecute(String result) {
                Log.d(LOG_TAG, "post");
//                resultTextView.setText(result);
                takePhoto.setText(R.string.try_again);
                takePhoto.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent startOver = new Intent(GoogleCloudAPI.this, GoogleCloudAPI.class);
                        GoogleCloudAPI.this.startActivity(startOver);
                    }
                });
                selectImage.setVisibility(View.GONE);
                confirmLabels.setVisibility(View.VISIBLE);
            }
        }.execute();
    }

    private String convertResponseToString(BatchAnnotateImagesResponse response) {
        Log.d("RESPONSE-LABELS", String.valueOf(response));
        StringBuilder message = new StringBuilder("Results:\n\n");
        List<String> filteredMessage = new ArrayList<>();
        message.append("Labels:\n");
        List<EntityAnnotation> labels = response.getResponses().get(0).getLabelAnnotations();
//        ImageProperties colors = response.getResponses().get(0).getImagePropertiesAnnotation();
//        labels.add(response.getResponses().get(0).getImagePropertiesAnnotation());
        Log.d("LABELS", String.valueOf(labels));
//        Log.d("COLORS", String.valueOf(colors));
        if (labels != null) {
            for (EntityAnnotation label : labels) {
                filteredMessage.add(String.format(Locale.getDefault(), "%s", label.getDescription()));
                message.append(String.format(Locale.getDefault(), "%s", label.getDescription()));
                message.append("\n");
            }
//            message.append(String.format(Locale.getDefault(), "%s", colors.getDominantColors().getColors().get(0).getColor()));

        } else {
            message.append("nothing\n");
        }
        Log.d("LIST", String.valueOf(filterLabels(filteredMessage)));
//        return message.toString();
        List<String> filtered = filterLabels(filteredMessage);
        updateLabels(filtered);
        return String.valueOf(filtered);
    }

    private List<String> filterLabels(List labels) {
        List<String> newList = new ArrayList<>();
        int i = 0;
        newList.add("category");
        for(i = 0; i < labels.size(); i++) {
            // check for category
            switch (String.valueOf(labels.get(i)).toLowerCase()) {
                // tops
                case "top":
                case "shirt":
                case "blouse":
                case "sleeve":
                case "sleeveless":
                    newList.add("top");
                    break;
                // bottoms
                case "bottom":
                case "pant":
                case "pants":
                case "shorts":
                case "slacks":
                    newList.add("bottom");
                    break;
                case "dress":
                case "jumper":
                    newList.add("dress");
                    break;
                // outerwear
                case "jacket":
                case "sweatshirt":
                case "blazer":
                case "coat":
                    newList.add("outerwear");
                    break;
                // accessories
                case "jewelry":
                case "earrings":
                case "necklace":
                case "bracelet":
                case "ring":
                case "scarf":
                case "sunglasses":
                case "glasses":
                case "swim":
                case "swimsuit":
                case "bikini":
                case "headband":
                    newList.add("accessories");
                    break;
                //shoes
                case "shoes":
                case "sneakers":
                case "wedges":
                case "heels":
                case "loafers":
                case "sandals":
                    newList.add("shoes");
                    break;
            }
        }
        if (newList.size() == 1) {
            newList.add("none");
        }
        newList.add("color");
        for(i = 0; i < labels.size(); i++) {
            // check for color
            switch(String.valueOf(labels.get(i)).toLowerCase()) {
                // red
                case "red":
                case "crimson":
                case "scarlet":
                case "wine":
                case "rust":
                    newList.add("red");
                    break;
                // orange
                case "orange":
                case "pumpkin":
                case "melon":
                case "amber":
                case "carrot":
                    newList.add("orange");
                    break;
                // yellow
                case "yellow":
                case "gold":
                case "mustard":
                case "lemon":
                    newList.add("yellow");
                    break;
                // green
                case "green":
                case "emerald":
                case "olive":
                case "seafoam":
                    newList.add("green");
                    break;
                // blue
                case "blue":
                case "teal":
                case "navy":
                case "royal":
                case "turquoise":
                case "denim":
                    newList.add("blue");
                    break;
                // purple
                case "purple":
                case "violet":
                case "lilac":
                    newList.add("purple");
                    break;
                // black
                case "black":
                case "charcoal":
                case "ebony":
                    newList.add("black");
                    break;
                // white
                case "white":
                case "ivory":
                case "cream":
                    newList.add("white");
                    break;
                // brown
                case "brown":
                case "mocha":
                case "tan":
                case "khaki":
                    newList.add("brown");
                    break;
                // gray
                case "gray":
                    newList.add("gray");
                    break;
                case "pink":
                case "peach":
                case "salmon":
                    newList.add("pink");
            }
        }
        if (newList.size() == 3) {
            newList.add("none");
        }
        return newList;
    }

    public void updateLabels(List labels) {
//        newLabelsObject = new LabelsObject();
        newLabelsObject.labelSetCategory(String.valueOf(labels.get(1)));
        newLabelsObject.labelSetColor(String.valueOf(labels.get(3)));
        Log.d("LABELS-CATEGORY", newLabelsObject.labelGetCategory());
        Log.d("LABELS-COLOR", newLabelsObject.labelGetColor());
    }

    public Bitmap resizeBitmap(Bitmap bitmap) {

        int maxDimension = 1024;
        int originalWidth = bitmap.getWidth();
        int originalHeight = bitmap.getHeight();
        int resizedWidth = maxDimension;
        int resizedHeight = maxDimension;

        if (originalHeight > originalWidth) {
            resizedHeight = maxDimension;
            resizedWidth = (int) (resizedHeight * (float) originalWidth / (float) originalHeight);
        } else if (originalWidth > originalHeight) {
            resizedWidth = maxDimension;
            resizedHeight = (int) (resizedWidth * (float) originalHeight / (float) originalWidth);
        } else if (originalHeight == originalWidth) {
            resizedHeight = maxDimension;
            resizedWidth = maxDimension;
        }
        return Bitmap.createScaledBitmap(bitmap, resizedWidth, resizedHeight, false);
    }

    public Image getBase64EncodedJpeg(Bitmap bitmap) {
        Image image = new Image();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 90, byteArrayOutputStream);
        byte[] imageBytes = byteArrayOutputStream.toByteArray();
        image.encodeContent(imageBytes);
        return image;
    }

    private void pickUserAccount() {
        String[] accountTypes = new String[]{GoogleAuthUtil.GOOGLE_ACCOUNT_TYPE};
        Intent intent = AccountPicker.newChooseAccountIntent(null, null,
                accountTypes, false, null, null, null, null);
        startActivityForResult(intent, REQUEST_CODE_PICK_ACCOUNT);
    }

    private void getAuthToken() {
        String SCOPE = "oauth2:https://www.googleapis.com/auth/cloud-platform";
        if (mAccount == null) {
            pickUserAccount();
//            checkStoragePermission(RC_STORAGE_PERMS1);
            Log.d(LOG_TAG, "pick account");
        } else {
//            Log.d(LOG_TAG, "getTokenTask");
            new GetTokenTask(GoogleCloudAPI.this, mAccount, SCOPE, REQUEST_ACCOUNT_AUTHORIZATION)
                    .execute();
//            checkStoragePermission(RC_STORAGE_PERMS1);
            Log.d(LOG_TAG, "getTokenTask");
        }
    }

    public void onTokenReceived(String token){
        accessToken = token;
//        dispatchTakePictureIntent();
//        launchImagePicker();
        if(chooseImageFlag == 1) {
            checkStoragePermission(RC_STORAGE_PERMS1);
            chooseImageFlag = 0;
        }
        else if(takePictureFlag == 1) {
            Log.d(LOG_TAG, "Camera Permission Granted!");
            try {
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                        == PackageManager.PERMISSION_DENIED) {
                    ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.CAMERA}, REQUEST_CODE_CAMERA);
                    Log.d(LOG_TAG, "Camera Permission Granted!");
                }
                else {
                    Log.d(LOG_TAG, "Camera Permission Granted!");
                    dispatchTakePictureIntent();
                }
            } catch (IOException e) {
                Log.d(LOG_TAG, "error taking photo");
            }
            takePictureFlag = 0;

        }
    }
}
