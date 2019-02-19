package com.example.fivestarstyle;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.view.Menu;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageButton overviewScreen = (ImageButton) findViewById(R.id.overview);
        ImageButton chooseOutfitScreen = (ImageButton) findViewById(R.id.choose_my_outfit);

        overviewScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent overviewIntent = new Intent(MainActivity.this, ClosetStatistics.class);
//                myIntent.putExtra("key", value); //Optional parameters
                MainActivity.this.startActivity(overviewIntent);
            }
        });

        chooseOutfitScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent chooseOutfitIntent = new Intent(MainActivity.this, ChooseMyOutfit.class);
//                myIntent.putExtra("key", value); //Optional parameters
                MainActivity.this.startActivity(chooseOutfitIntent);
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_options,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.home__menu_option) {
            Toast.makeText(this,"you pressed home!", Toast.LENGTH_LONG).show();
            Intent homeIntent = new Intent(this,MainActivity.class);
            this.startActivity(homeIntent);
        }
        else if(item.getItemId() == R.id.closet_menu_option) {
            Toast.makeText(this,"you pressed closet!", Toast.LENGTH_LONG).show();
        }
        else if(item.getItemId() == R.id.overview_menu_option) {
            Toast.makeText(this,"you pressed overview!", Toast.LENGTH_LONG).show();
            Intent overviewIntent = new Intent(this,ClosetStatistics.class);
            this.startActivity(overviewIntent);
        }
        else {
            return super.onOptionsItemSelected(item);
        }
        return true;
    }
}
