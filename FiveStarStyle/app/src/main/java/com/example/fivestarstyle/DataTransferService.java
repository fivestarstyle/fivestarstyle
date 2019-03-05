package com.example.fivestarstyle;

import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.label.FirebaseVisionLabel;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.protobuf.Any;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class DataTransferService {
    private static FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private static FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static StorageReference storageRef = FirebaseStorage.getInstance().getReference();
    private final static String TAG = "DataTransferService";

    public static void addItem(Bitmap bitmap, final List<FirebaseVisionLabel> labels) {
        //final string type, final string season){
        if (user != null) {
            //upload picture to storage
            final String id = UUID.randomUUID().toString();
            final StorageReference userStorage = storageRef.child(user.getUid() + "/" + id);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] data = baos.toByteArray();

            UploadTask uploadTask = userStorage.putBytes(data);
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle unsuccessful uploads
                    Log.e(TAG, "Image failed to upload");
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                    Log.d(TAG, "imageURL : " + userStorage.getDownloadUrl().toString());
                    Task<Uri> urlTask = taskSnapshot.getMetadata().getReference().getDownloadUrl();
                    while (!urlTask.isSuccessful());
                    Uri downloadUrl = urlTask.getResult();
                    Log.d(TAG, "imageURL : " + downloadUrl.toString());
                    uploadImage(downloadUrl.toString(), labels);
                }
            });
        }
    }



    private static List<String> extractTags(List<FirebaseVisionLabel> labels){
        List<String> tags = new ArrayList<>();
        for (FirebaseVisionLabel label : labels) {
            tags.add(label.getLabel());
        }
        return tags;
    }

    private static void uploadImage(String image, List<FirebaseVisionLabel> labels) {
        //String type, String season) {
        Map<String, Object> newItem = new HashMap<>();
        newItem.put("image", image);
        newItem.put("labels", extractTags(labels));


        // Add a new document with a generated ID
        db.collection("userClosets/" + user.getUid() + "/Items")
                .add(newItem)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });
    }

}
