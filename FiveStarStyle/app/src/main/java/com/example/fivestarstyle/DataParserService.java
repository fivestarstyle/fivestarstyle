package com.example.fivestarstyle;

import android.os.Bundle;
import android.util.Log;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

class DataParserService {
    private final static String TAG = "DataParserService";

    public static ArrayList<Integer> getCategoryData(){
        final ArrayList<Integer> totals = new ArrayList<>();
        for (String cat : GlobalVariables.categories) {
            DataTransferService.getCategoryCount(cat, new OnGetDataListener() {
                @Override
                public void onStart() {
                    //on start method
                }

                @Override
                public void onSuccess(QuerySnapshot data) {
                    Log.d(TAG, "on success");
                    int count = 0;
                    for (DocumentSnapshot document : data) {
                        count++;
                    }
                    totals.add(count);
                }

                @Override
                public void onFailed(Exception databaseError) {
                    Log.w(TAG, "Error adding document", databaseError);
                }
            });
        }

        return totals;
    }

    public static ArrayList<Integer> getEventData(){
        final ArrayList<Integer> totals = new ArrayList<>();
        for (String cat : GlobalVariables.categories) {
            DataTransferService.getCategoryCount(cat, new OnGetDataListener() {
                @Override
                public void onStart() {
                    //on start method
                }

                @Override
                public void onSuccess(QuerySnapshot data) {
                    Log.d(TAG, "on success");
                    int count = 0;
                    for (DocumentSnapshot document : data) {
                        count++;
                    }
                    totals.add(count);
                }

                @Override
                public void onFailed(Exception databaseError) {
                    Log.w(TAG, "Error adding document", databaseError);
                }
            });
        }

        return totals;
    }
}
