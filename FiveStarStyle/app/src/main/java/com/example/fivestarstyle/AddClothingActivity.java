package com.example.fivestarstyle;

import android.content.Intent;
import android.provider.MediaStore;
import android.support.design.button.MaterialButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class AddClothingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_clothing);
        Button btnTakePicture = (Button) findViewById(R.id.btnCamera);
        btnTakePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();
            }
        });
    }
    static final int REQUEST_IMAGE_CAPTURE = 1;

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
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
            Intent overviewIntent = new Intent(this,ClosetActivity.class);
            this.startActivity(overviewIntent);
        }
        else if(item.getItemId() == R.id.overview_menu_option) {
            Toast.makeText(this,"you pressed overview!", Toast.LENGTH_LONG).show();
            Intent overviewIntent = new Intent(this,ClosetStatistics.class);
            this.startActivity(overviewIntent);
        }
        else if(item.getItemId() == R.id.choosemyoutfit_menu_option) {
            Toast.makeText(this,"you pressed choose my outfit!", Toast.LENGTH_LONG).show();
            Intent overviewIntent = new Intent(this,ChooseOutfit.class);
            this.startActivity(overviewIntent);
        }
        else {
            return super.onOptionsItemSelected(item);
        }
        return true;
    }
}
