package com.example.fivestarstyle;

import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class DataTransferService {
    private static FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private static FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static StorageReference storageRef = FirebaseStorage.getInstance().getReference();
    private final static String TAG = "DataTransferService";

    //method to upload image to storage then add image and data to the database
    public static Boolean addItem(final LabelsObject item) {
        final Boolean success;
        if (user != null) {
            //upload picture to storage
            Log.d("DATA-CATEGORY", item.labelGetCategory());
            Log.d("DATA-COLOR", item.labelGetColor());
            Log.d("DATA_SEASONS", item.labelGetSeasons().toString());
            Log.d("DATA_EVENTS", item.labelGetEvents().toString());
            final String id = UUID.randomUUID().toString();
            final StorageReference userStorage = storageRef.child(user.getUid() + "/" + id);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            Bitmap bitmap = GlobalVariables.getBitmap();
//            Log.d(TAG, String.valueOf(bitmap));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] data = baos.toByteArray();
//            Log.d(TAG, String.valueOf(data));
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
                    uploadItem(downloadUrl.toString(), item);
                }
            });
            return true;
        }
        return false;
    }

    //method to upload item to database by category
    private static void uploadItem(String image, LabelsObject item){
        //de
        Map<String, Object> newItem = new HashMap<>();
        newItem.put("image", image);
        newItem.put("seasons", item.labelGetSeasons());
        newItem.put("events", item.labelGetEvents());
        newItem.put("color", item.labelGetColor());

        String category = item.labelGetCategory();
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

    //method to retrieve imageUrls from database
    public static void retrieveImagesForCloset(String category, final OnGetDataListener listener){
        listener.onStart();
        final ArrayList<String> imageUrls = new ArrayList<>();
        if (category == "all") {
            // loop to iterate through all categories
            for (String cat : GlobalVariables.categories){
                db.collection("userClosets/" + user.getUid() + "/" + cat)
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        Log.d(TAG, document.getId() + " ImageURL:" + document.get("image").toString() + " => " + document.getData());
                                        imageUrls.add(document.get("image").toString());
                                    }
                                } else {
                                    Log.d(TAG, "Error getting documents: ", task.getException());
                                }
                            }
                        });
                }
        }
        else {
            db.collection("userClosets/" + user.getUid() + "/" + category)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            listener.onSuccess(task.getResult());
                        } else {
                            listener.onFailed(task.getException());
                        }
                    }
                });
        }
    }

    public static void getCategoryCount(String cat, final OnGetDataListener listener){
        listener.onStart();
        db.collection("userClosets/" + user.getUid() + "/" + cat)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            listener.onSuccess(task.getResult());
                        } else {
                            listener.onFailed(task.getException());
                        }
                    }
                });
    }

    public static void getSeasonCount(final OnGetDataListener listener){
        listener.onStart();

        for (String cat : GlobalVariables.categories){
            db.collection("userClosets/" + user.getUid() + "/" + cat)
                    .whereEqualTo("capital", true)
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


    //Item Details
    public static void getItemDetails(final String imageUrl, final String cat, final OnGetDataListener listener){
        listener.onStart();
        db.collection("userClosets/" + user.getUid() + "/" + cat)
                .whereEqualTo("image", imageUrl)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, task.getResult().toString());
                            listener.onSuccess(task.getResult());
                        } else {
                            listener.onFailed(task.getException());
                        }
                    }
                });
    }



}
