package com.example.fivestarstyle;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.button.MaterialButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Activity_Login extends AppCompatActivity {
    private static final String TAG = "Login";
    private FirebaseAuth mAuth;
    private EditText mEmailField;
    private EditText mPasswordField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null) {
            Log.d(TAG, "user:" + user.getEmail());
            // User is signed in
            Intent i = new Intent(Activity_Login.this, Activity_Main.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            i.putExtra("user", user); //Optional parameters
            startActivity(i);
        } else {
            // User is signed out
            Log.d(TAG, "onAuthStateChanged:signed_out");
        }

        mEmailField = findViewById(R.id.txtEmail);
        mPasswordField = findViewById(R.id.txtPassword);
        mAuth = FirebaseAuth.getInstance();

        /*creates register, login, and forgot password buttons*/
        MaterialButton btnRegister = (MaterialButton) findViewById(R.id.btnRegister);
        MaterialButton btnLogin = (MaterialButton) findViewById(R.id.btnLogin);
        MaterialButton btnForgotPass = (MaterialButton) findViewById(R.id.btnForgotPassword);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn(mEmailField.getText().toString(), mPasswordField.getText().toString());
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(Activity_Login.this, Activity_Register.class);
//                myIntent.putExtra("key", value); //Optional parameters
                Activity_Login.this.startActivity(myIntent);
            }
        });

        btnForgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().sendPasswordResetEmail(mEmailField.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(Activity_Login.this,"Reset Email Sent", Toast.LENGTH_LONG).show();
                                    Log.d(TAG, "Email sent.");
                                }
                            }
                        });
            }
        });
    }

    /*checks that email and password used are valid and that there is actually something typed in the form*/
    private void signIn(String email, String password) {
        Log.d(TAG, "signIn:" + email);

        if (!validateForm()) {
            Toast.makeText(this, "Please enter a username and password.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!isValidEmailAddress(email)) {
            Toast.makeText(this, "Please enter a valid email address.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!isValidPassword(password)) {
            Toast.makeText(this, "Please enter a valid password.", Toast.LENGTH_SHORT).show();
            return;
        }

//        Toast.makeText(this, "HELLO", Toast.LENGTH_SHORT).show();

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(Activity_Login.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(Activity_Login.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }

                        // ...
                    }
                });
    }

    /*go to home page*/
    private void updateUI(FirebaseUser user) {
        if (user != null) {
            Intent homeIntent = new Intent(Activity_Login.this, Activity_Main.class);
            homeIntent.putExtra("user", user); //Optional parameters
            Activity_Login.this.startActivity(homeIntent);
        } else {

        }
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

    /*checks that there is text in the form username and password fields*/
    public boolean validateForm() {
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

        return valid;
    }

}
