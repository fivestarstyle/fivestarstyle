package com.example.fivestarstyle;

import android.os.Bundle;
import android.app.Activity;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirestoreRegistrar;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.firestore.EventListener;


public class PullImages extends Activity {
private TextView url;
private ImageView img;
private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
private FirebaseFirestore firebaseDatabase = FirebaseFirestore.getInstance();
//private FirebaseFirestore reference = FirebaseFirestore.getInstance().getReference();
//private StorageReference childreference = reference.child("url");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pull_images);
        url = findViewById(R.id.text);
        img = findViewById(R.id.image);
        firebaseDatabase.collection("userClosets" + user.getUid() + "/clothes").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Log.d("dataPull", document.getId() + " : " + document.getData());
                    }
                }
                else {
                    Log.d("dataPull", "Error getting documents : ", task.getException());
                }
            }
        });
    }

//    @Override
//    protected void onStart() {
//        super.onStart();
//        childreference.get(new EventListener() {
//            @Override
//            public void onDataChange(DataSnapShot )
//        })
//    }
}
