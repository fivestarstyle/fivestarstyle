package com.example.fivestarstyle;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.button.MaterialButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SettingsActivity extends AppCompatActivity {

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_options,menu);
        return true;
    }

    /*Adds the menu in the top right corner*/
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.home__menu_option) {
            Intent homeIntent = new Intent(this,MainActivity.class);
            this.startActivity(homeIntent);
        }
        else if(item.getItemId() == R.id.closet_menu_option) {
            Intent overviewIntent = new Intent(this,ClosetActivity.class);
            this.startActivity(overviewIntent);
        }
        else if(item.getItemId() == R.id.overview_menu_option) {
            Intent overviewIntent = new Intent(this,ClosetStatistics.class);
            this.startActivity(overviewIntent);
        }
        else if(item.getItemId() == R.id.choosemyoutfit_menu_option) {
            Intent overviewIntent = new Intent(this,ChooseOutfit.class);
            this.startActivity(overviewIntent);
        }
        else if(item.getItemId() == R.id.settings_menu_option) {
            Intent overviewIntent = new Intent(this,SettingsActivity.class);
            this.startActivity(overviewIntent);
        }
        else if(item.getItemId() == R.id.logout_menu_option) {
            FirebaseAuth.getInstance().signOut();
            Intent overviewIntent = new Intent(this,LoginActivity.class);
            this.startActivity(overviewIntent);
        }
        else {
            return super.onOptionsItemSelected(item);
        }
        return true;
    }

    /*Pulls information from firebase to display on the settings page*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        //pulls information from firebase and initializes other variables
        TextView emailTextView = (TextView) findViewById(R.id.emailFilled);
        TextView nameTextView = (TextView) findViewById(R.id.nameFilled);
        TextView genderTextView = (TextView) findViewById(R.id.genderFilled);
        TextView locationTextView = (TextView) findViewById(R.id.locationFilled);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        final LinearLayout viewCustom = (LinearLayout) findViewById(R.id.customView);

        final Switch current = (Switch) findViewById(R.id.currentLocationSwitch);
        current.setChecked(true);
        final Switch custom = (Switch) findViewById(R.id.customLocationSwitch);

        //makes it so if current location is checked the text box will not appear
        //and makes it so that when current location is checked custom is not
        current.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    custom.setChecked(false);
                    viewCustom.setVisibility(viewCustom.GONE);
                }
            }
        });

        //makes it so if custom location is checked then the text box appears to fill in information
        //and makes it so that when custom location is checked current is not
        custom.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    current.setChecked(false);
                    viewCustom.setVisibility(viewCustom.VISIBLE);
                }
            }
        });


        //fills in information from database on the display
        if (user != null) {
            String email = user.getEmail();
            emailTextView.setText(email);
            nameTextView.setText(MyApplication.firstName + MyApplication.lastName);
            genderTextView.setText(MyApplication.gender);
            locationTextView.setText(MyApplication.city);
        }

        Button btnSubmit = (Button) findViewById(R.id.SubmitButton);
        final EditText mZipField = findViewById(R.id.txtZip);

        //sets custom zipCode when the submit button is clicked
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                custom(mZipField.getText().toString());
            }
        });

    }

    //Toast to say whether or not zip code was filled in when submit button was clicked
    private void custom(String zip) {
        if (zip.length() != 0) {
            MyApplication.customZipCode = zip;
            Toast.makeText(SettingsActivity.this, "Zip Code Set",
                    Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(SettingsActivity.this, "Need to fill in information!",
                    Toast.LENGTH_SHORT).show();
        }
    }
}
