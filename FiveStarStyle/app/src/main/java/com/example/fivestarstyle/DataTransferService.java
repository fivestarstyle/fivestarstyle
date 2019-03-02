package com.example.fivestarstyle;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.label.FirebaseVisionLabel;
import com.google.protobuf.Any;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataTransferService {
    private static FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private static FirebaseUser user = mAuth.getCurrentUser();
    private static FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final static String TAG = "DataTransferService";

    public static void addItem(FirebaseVisionImage image, List<FirebaseVisionLabel> labels){
        if (user != null) {
            Map<String, Object> newItem = new HashMap<>();
//            newItem.put("image", image);

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

    private static List<String> extractTags(List<FirebaseVisionLabel> labels){
        List<String> tags = new ArrayList<>();
        for (FirebaseVisionLabel label : labels) {
            tags.add(label.getLabel());
        }
        return tags;
    }
}
