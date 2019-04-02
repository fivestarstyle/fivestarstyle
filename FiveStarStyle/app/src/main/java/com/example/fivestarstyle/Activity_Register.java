package com.example.fivestarstyle;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.button.MaterialButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.text.TextUtils;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Activity_Register extends AppCompatActivity {
    private static final String TAG = "Register";
    private FirebaseAuth mAuth;
    private EditText mEmailField;
    private EditText mPasswordField;
    private EditText mFirstNameField;
    private EditText mLastNameField;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        mAuth = FirebaseAuth.getInstance();

        mEmailField = findViewById(R.id.txtEmail);
        mPasswordField = findViewById(R.id.txtPassword);
        mFirstNameField = findViewById(R.id.txtFirstName);
        mLastNameField = findViewById(R.id.txtLastName);

        if (!isValidEmailAddress(mEmailField.getText().toString())) {
            return;
        }
        if (!isValidPassword(mPasswordField.getText().toString())) {
            return;
        }

        MaterialButton btnRegister = findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createAccount(mEmailField.getText().toString(), mPasswordField.getText().toString());
            }
        });

    }

    /*checks that email address has an @ sign and a period*/
    public boolean isValidEmailAddress(String email) {
        boolean hasAtSign = email.contains("@");
        boolean hasDot = email.contains(".");
        boolean returnValue = (hasAtSign && hasDot);
        return returnValue;
    }

    /*checks that password is longer than 4 chars, has one uppercase letter, and one lowercase letter*/
    public boolean isValidPassword(String password) {
        boolean hasUppercase = !password.equals(password.toLowerCase());
        boolean hasLowercase = !password.equals(password.toUpperCase());
        boolean isLongEnough = password.length() > 4;
        boolean returnValue = (hasUppercase && hasLowercase && isLongEnough);
        return returnValue;
    }

    private void createAccount(String email, String password) {
        Log.d(TAG, "createAccount:" + email);
        if (!validateForm()) {
            Toast.makeText(Activity_Register.this, "Registration Failed: Please Fill Required Fields.",
                    Toast.LENGTH_SHORT).show();
            return;
        }


        // [START create_user_with_email]
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(Activity_Register.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            addUserCloset(user);
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(Activity_Register.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }

                    }
                });

    }

    private void addUserCloset(FirebaseUser user){
        if (user != null) {
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            Map<String, Object> test = new HashMap<>();
            test.put("first", mFirstNameField.getText().toString());
            test.put("last", mLastNameField.getText().toString());

            GlobalVariables.firstName = mFirstNameField.getText().toString();
            GlobalVariables.lastName = mLastNameField.getText().toString();

            // Add a new document with a generated ID
            db.collection("userClosets" ).document(user.getUid())
                    .set(test)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d(TAG, "DocumentSnapshot successfully written!");
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w(TAG, "Error writing document", e);
                        }
                    });
        }
    }

    private void updateUI(FirebaseUser user) {
        if (user != null) {
            Intent newIntent = new Intent(Activity_Register.this, Activity_Login.class);
            Activity_Register.this.startActivity(newIntent);
        } else {
            Log.w(TAG, "Error changing screen");
        }
    }


    private boolean validateForm() {
        boolean valid = true;

        String email = mEmailField.getText().toString();
        if (TextUtils.isEmpty(email)) {
            mEmailField.setError("Required.");
            valid = false;
        } else {
            mEmailField.setError(null);
        }

        String password = mPasswordField.getText().toString();
        if (TextUtils.isEmpty(password)) {
            mPasswordField.setError("Required.");
            valid = false;
        } else {
            mPasswordField.setError(null);
        }

        String firstName = mFirstNameField.getText().toString();
        if (TextUtils.isEmpty(firstName)) {
            mFirstNameField.setError("Required.");
            valid = false;
        } else {
            mFirstNameField.setError(null);
        }

        String lastName = mLastNameField.getText().toString();
        if (TextUtils.isEmpty(lastName)) {
            mLastNameField.setError("Required.");
            valid = false;
        } else {
            mLastNameField.setError(null);
        }

        return valid;
    }

}
