package com.example.fivestarstyle;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Switch;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Activity_Settings extends AppCompatActivity {

    /*Adds the menu in the top right corner*/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_options,menu);
        return true;
    }

    /*Menu Options*/
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.home__menu_option) {
            Intent homeIntent = new Intent(this, Activity_Main.class);
            this.startActivity(homeIntent);
        }
        else if(item.getItemId() == R.id.closet_menu_option) {
            Intent overviewIntent = new Intent(this, Activity_ViewMyCloset.class);
            this.startActivity(overviewIntent);
        }
        else if(item.getItemId() == R.id.overview_menu_option) {
            Intent overviewIntent = new Intent(this, Activity_Overview.class);
            this.startActivity(overviewIntent);
        }
        else if(item.getItemId() == R.id.choosemyoutfit_menu_option) {
            Intent overviewIntent = new Intent(this, Activity_ChooseMyOutfit.class);
            this.startActivity(overviewIntent);
        }
        else if(item.getItemId() == R.id.settings_menu_option) {
            Intent overviewIntent = new Intent(this, Activity_Settings.class);
            this.startActivity(overviewIntent);
        }
        else if(item.getItemId() == R.id.logout_menu_option) {
            FirebaseAuth.getInstance().signOut();
            Intent overviewIntent = new Intent(this, Activity_Login.class);
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
        //TextView genderTextView = (TextView) findViewById(R.id.genderFilled);
        final TextView locationTextView = (TextView) findViewById(R.id.locationFilled);

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
                    locationTextView.setText(GlobalVariables.zipCode);
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


//        FirebaseFirestore db = FirebaseFirestore.getInstance();
//        final String TAG = "Settings";
//        DocumentReference docRef = db.collection("userClosets").document(user.getUid());
//        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                if (task.isSuccessful()) {
//                    DocumentSnapshot document = task.getResult();
//                    if (document.exists()) {
//                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
//                        GlobalVariables.firstName = document.get("first").toString();
//                        GlobalVariables.lastName = document.get("last").toString();
//                    } else {
//                        Log.d(TAG, "No such document");
//                    }
//                } else {
//                    Log.d(TAG, "get failed with ", task.getException());
//                }
//            }
//        });
//
//        Log.d(TAG, "First Name: " + GlobalVariables.firstName);
//        Log.d(TAG, "Last Name: " + GlobalVariables.lastName);
        //Log.d(TAG, "Latitude: " + GlobalVariables.latitude);
        //Log.d(TAG, "Longitude: " + GlobalVariables.longitude);
        //Log.d(TAG, "Zip Code: " + GlobalVariables.zipCode);

        //fills in information from database on the display
        if (user != null) {
            String email = user.getEmail();
            emailTextView.setText(email);
            nameTextView.setText(GlobalVariables.firstName + " " + GlobalVariables.lastName);
            //genderTextView.setText(GlobalVariables.gender);
            locationTextView.setText(GlobalVariables.zipCode);
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

    //Return true/false to say whether or not zip code was filled in when submit button was clicked
    public boolean custom(String zip) {
        if (zip.isEmpty()) {
            return false;
        }
        //checkZip(zip);
        TextView locationTextView = (TextView) findViewById(R.id.locationFilled);
        if (zip.length() != 0) {
            GlobalVariables.customZipCode = zip;
            locationTextView.setText(GlobalVariables.customZipCode);
            //Toast.makeText(Activity_Settings.this, "Zip Code Set",
                    //Toast.LENGTH_SHORT).show();
            return true;
        }
        else {
//            Toast.makeText(Activity_Settings.this, "Need to fill in information!",
//                   Toast.LENGTH_SHORT).show();
            return false;
        }
    }


}
