package com.example.fivestarstyle;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.button.MaterialButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        MaterialButton homeScreen = (MaterialButton) findViewById(R.id.btnRegister);

        homeScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent homeIntent = new Intent(RegisterActivity.this, MainActivity.class);
//                myIntent.putExtra("key", value); //Optional parameters
                RegisterActivity.this.startActivity(homeIntent);
            }
        });
    }

    }

