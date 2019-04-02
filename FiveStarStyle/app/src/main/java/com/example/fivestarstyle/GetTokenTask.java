package com.example.fivestarstyle;

import android.accounts.Account;
import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.auth.GoogleAuthException;
import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.auth.UserRecoverableAuthException;

import java.io.IOException;

// this class gets the GCAPI token and calls the API
public class GetTokenTask extends AsyncTask<Void, Void, Void> {
    Activity mActivity;
    String mScope;
    Account mAccount;
    int mRequestCode;

    GetTokenTask(Activity activity, Account account, String scope, int requestCode) {
        // initializes variables with parameters
        this.mActivity = activity;
        this.mScope = scope;
        this.mAccount = account;
        this.mRequestCode = requestCode;
    }

    @Override
    protected Void doInBackground(Void... params) {
        try {
            String token = fetchToken();
            if (token != null) {
                // call GCAPI with token
                ((Activity_GoogleCloudAPI)mActivity).onTokenReceived(token);
            }
        } catch (IOException e) {
            // catch exception if fails
            e.printStackTrace();
        }
        return null;
    }

    // gets an authentication token from Google and handles any GoogleAuthException that may occur.
    protected String fetchToken() throws IOException {
        String accessToken;
        try {
            // set access token to retrieved token
            accessToken = GoogleAuthUtil.getToken(mActivity, mAccount, mScope);
            // used to remove stale tokens.
            GoogleAuthUtil.clearToken (mActivity, accessToken);
            // get token again after stale token has been removed
            accessToken = GoogleAuthUtil.getToken(mActivity, mAccount, mScope);
            return accessToken;
        } catch (UserRecoverableAuthException userRecoverableException) {
            // catch authentication exception
            mActivity.startActivityForResult(userRecoverableException.getIntent(), mRequestCode);
        } catch (GoogleAuthException fatalException) {
            // catch fatal exception
            fatalException.printStackTrace();
        }
        return null;
    }
}