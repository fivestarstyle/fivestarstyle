package com.example.fivestarstyle;

import android.content.Intent;
import android.support.design.button.MaterialButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageButton overviewScreen = (ImageButton) findViewById(R.id.overview);

        overviewScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent overviewIntent = new Intent(MainActivity.this, ClosetStatistics.class);
//                myIntent.putExtra("key", value); //Optional parameters
                MainActivity.this.startActivity(overviewIntent);
            }
        });


    }
}
