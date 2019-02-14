package com.example.fivestarstyle;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.button.MaterialButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;


public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        MaterialButton register = (MaterialButton) findViewById(R.id.btnRegister);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

//        register.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
////                registerIntent.putExtra("key", value); //Optional parameters
//                LoginActivity.this.startActivity(registerIntent);
//            }
//        });
    }



}