package com.example.fivestarstyle;

import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.api.services.vision.v1.model.BatchAnnotateImagesResponse;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
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

    public static void addItem(Bitmap bitmap, final String category, final List<String> seasons,
                               final List<String> events, final BatchAnnotateImagesResponse response) {
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
                    Task<Uri> urlTask = taskSnapshot.getMetadata().getReference().getDownloadUrl();
                    while (!urlTask.isSuccessful());
                    Uri downloadUrl = urlTask.getResult();
                    Log.d(TAG, "imageURL : " + downloadUrl.toString());
                    //add image and tags to database
                    uploadItem(downloadUrl.toString(), category, seasons, events, response);
                }
            });
        }
    }


    private static void uploadItem(String image, String category, List<String> seasons, List<String> events, BatchAnnotateImagesResponse response) {

        Map<String, Object> newItem = new HashMap<>();
        newItem.put("image", image);
        newItem.put("seasons", seasons);
        newItem.put("events", events);
        newItem.put("labels", MyHelper.extractLabels(response));


        // Add a new document with a generated ID
        db.collection("userClosets/" + user.getUid() + "/" + category)
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

    public static void retrieveImagesForCloset(String category){
        if (category == "all") {
            db.collection("userClosets/" + user.getUid() + "/Items")
                .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    Log.d(TAG, document.getId() + " => " + document.getData());
                                }
                            } else {
                                Log.d(TAG, "Error getting documents: ", task.getException());
                            }
                        }
                    });
        }
        else {
        db.collection("userClosets/" + user.getUid() + category)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
        }
    }

}
