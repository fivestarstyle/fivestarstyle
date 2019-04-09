package com.example.fivestarstyle;

import com.google.firebase.firestore.QuerySnapshot;

public interface OnGetDataListener {
    void onStart();
    void onSuccess(QuerySnapshot data);
    void onFailed(Exception databaseError);
}
