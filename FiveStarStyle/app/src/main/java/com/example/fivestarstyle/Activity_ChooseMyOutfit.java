package com.example.fivestarstyle;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

// description: this class sets up the "Choose My Outfit" Activity

public class Activity_ChooseMyOutfit extends AppCompatActivity {

    // initialize necessary class global variables
    JSONObject data = null;
    private final static String TAG = "WEATHER-API CALL";
    TextView currentTemp, humidity, humidityText, temperatureText, txtGreeting, weatherIcon;
    Button btn_casual, btn_cocktail, btn_formal, btn_work, btn_gym, btn_bar, btn_tops_and_bottoms, btn_dresses_or_suits;
    Calendar cal;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_options,menu);
        return true;
    }

    // setup three dots menu and set intents
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.home__menu_option) {
            // go to the home page
            Intent homeIntent = new Intent(this, Activity_Main.class);
            this.startActivity(homeIntent);
        }
        else if(item.getItemId() == R.id.closet_menu_option) {
            // go to the view closet page
            Intent overviewIntent = new Intent(this, Activity_ViewMyCloset.class);
            this.startActivity(overviewIntent);
        }
        else if(item.getItemId() == R.id.overview_menu_option) {
            // go to the closet overview page
            Intent overviewIntent = new Intent(this, Activity_Overview.class);
            this.startActivity(overviewIntent);
        }
        else if(item.getItemId() == R.id.choosemyoutfit_menu_option) {
            // go to the choose outfit page
            Intent overviewIntent = new Intent(this, Activity_ChooseMyOutfit.class);
            this.startActivity(overviewIntent);
        }
        else if(item.getItemId() == R.id.settings_menu_option) {
            // go to the settings page
            Intent overviewIntent = new Intent(this, Activity_Settings.class);
            this.startActivity(overviewIntent);
        }
        else if(item.getItemId() == R.id.logout_menu_option) {
            // log the current user out and go to the login page
            FirebaseAuth.getInstance().signOut();
            Intent overviewIntent = new Intent(this, Activity_Login.class);
            this.startActivity(overviewIntent);
        }
        else {
            return super.onOptionsItemSelected(item);
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_my_outfit);

        // call function to get current weather data from open weather api based on given location
        if(GlobalVariables.customZipCode.equals("")) {
            getJSON(GlobalVariables.zipCode, "bba8e629ce93f0a063c0a46c47dc5960");
        } else {
            getJSON(GlobalVariables.customZipCode, "bba8e629ce93f0a063c0a46c47dc5960");
        }

        // assign global variables
        currentTemp = (TextView) findViewById(R.id.currentTemp);
        humidity = (TextView) findViewById(R.id.humidity);
        weatherIcon = (TextView) findViewById(R.id.weatherIcon);
        temperatureText = (TextView) findViewById(R.id.currentTemp_text);
        humidityText = (TextView) findViewById(R.id.humidity_text);
        txtGreeting = (TextView) findViewById(R.id.txtGreeting);
        cal = Calendar.getInstance();

        // assign buttons
        btn_casual = (Button) findViewById(R.id.btn_casual);
        btn_cocktail = (Button) findViewById(R.id.btn_cocktail);
        btn_formal = (Button) findViewById(R.id.btn_formal);
        btn_work = (Button) findViewById(R.id.btn_work);
        btn_gym = (Button) findViewById(R.id.btn_gym);
        btn_bar = (Button) findViewById(R.id.btn_bar);
//        btn_tops_and_bottoms = (Button) findViewById(R.id.btn_tops_and_bottoms);
//        btn_dresses_or_suits = (Button) findViewById(R.id.btn_dresses_or_suits);

        // add click listeners to buttons
        clickListen(btn_casual, "casual");
        clickListen(btn_cocktail, "cocktail");
        clickListen(btn_formal, "formal");
        clickListen(btn_work, "work");
        clickListen(btn_gym, "gym");
        clickListen(btn_bar, "bar");
//        clickListen(btn_tops_and_bottoms, "tops and bottoms");
//        clickListen(btn_dresses_or_suits, "dresses or suits");
    }

    public void clickListen(Button btn, final String str) {
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String> categories = new ArrayList<>();
                categories.add("top");
                categories.add("bottom");
                categories.add("dress_or_suit");
                categories.add("shoes");
                categories.add("outerwear");
                final ArrayList<ItemDetailsObject> imagesTops = new ArrayList<>();
                final ArrayList<ItemDetailsObject> imagesBottoms = new ArrayList<>();
                final ArrayList<ItemDetailsObject> imagesDressesOrSuits = new ArrayList<>();
                final ArrayList<ItemDetailsObject> imagesShoes = new ArrayList<>();
                final ArrayList<ItemDetailsObject> imagesOuterwear = new ArrayList<>();
                for(final String cat : categories) {
                    DataTransferService.getItemsByEvent(cat, str, new OnGetDataListener() {
                        @Override
                        public void onStart() {
                            Toast.makeText(Activity_ChooseMyOutfit.this, "Choosing your outfit...", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onSuccess(QuerySnapshot data) {
                            // get all tops for that event
                            for (DocumentSnapshot document : data) {
                                ItemDetailsObject obj = new ItemDetailsObject();
                                obj.setImageUrl(document.get("image").toString());
                                obj.setCat(cat);
                                obj.setDocTitle(document.getId());
                                Log.d(TAG, document.getData() + "=> " + obj.getDocTitle());
                                switch(cat) {
                                    case "top":
                                        imagesTops.add(obj);
                                        break;
                                    case "bottom":
                                        imagesBottoms.add(obj);
                                        break;
                                    case "dress_or_suit":
                                        imagesDressesOrSuits.add(obj);
                                        break;
                                    case "shoes":
                                        imagesShoes.add(obj);
                                        break;
                                    case "outerwear":
                                        imagesOuterwear.add(obj);
                                        break;
                                }
                            }
                            Log.d(TAG, "imageUrls received");
                            if (cat.equals("outerwear")) {
                                try {
                                    Thread.sleep(2000);
                                } catch(InterruptedException e) {
                                    Log.d("ERROR", "Got interrupted!");
                                }
                                Intent results = new Intent(Activity_ChooseMyOutfit.this, Activity_ChooseMyOutfitResults.class);
                                Bundle bundle = new Bundle();
                                if(imagesTops.size() > 0) bundle.putSerializable("imagesTops", imagesTops);
                                if(imagesBottoms.size() > 0) bundle.putSerializable("imagesBottoms", imagesBottoms);
                                if(imagesDressesOrSuits.size() > 0) bundle.putSerializable("imagesDressesOrSuits", imagesDressesOrSuits);
                                if(imagesShoes.size() > 0) bundle.putSerializable("imagesShoes", imagesShoes);
                                if(imagesOuterwear.size() > 0) bundle.putSerializable("imagesOuterwear", imagesOuterwear);
                                results.putExtras(bundle);
                                results.putExtra("btnClicked", str);
//                                try {
//                                    Thread.sleep(1000);
//                                } catch(InterruptedException e) {
//                                    Log.d("ERROR", "Got interrupted!");
//                                }
                                if(imagesTops.size() == 0) {
                                    if(imagesDressesOrSuits.size() == 0) {
                                        Log.d("ERROR", "top/dress");
                                        Toast.makeText(Activity_ChooseMyOutfit.this, "You do not have a full outfit for this event! Please choose another event.", Toast.LENGTH_SHORT).show();
                                        return;
                                    }
                                }
                                if(imagesBottoms.size() == 0) {
                                    if(imagesDressesOrSuits.size() == 0) {
                                        Log.d("ERROR", "bottom/dress");
                                        Toast.makeText(Activity_ChooseMyOutfit.this, "You do not have a full outfit for this event! Please choose another event.", Toast.LENGTH_SHORT).show();
                                        return;
                                    }
                                }
                                startActivity(results);
                            }
                        }
                        @Override
                        public void onFailed(Exception databaseError) {
                            Toast.makeText(Activity_ChooseMyOutfit.this, "Whoops! Something went wrong. Please try again.", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            }
        });
    }

    // use zip code and api key to get current weather data from open weather api
    public void getJSON(final String zipCode, final String api_key) {
        // error handling: zip code was never set, return without displaying weather
        if(zipCode.length() == 0) {
            return;
        }

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected Void doInBackground(Void... params) {
                try {
                    // build url to call api based on zip code and api key
                    URL  url = new URL("https://api.openweathermap.org/data/2.5/weather?zip=" + zipCode + ",us&units=imperial&appid=" + api_key);
                    // open connection to the api call
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    // read the output from the api call
                    BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    // initialize the variable to hold the string of json
                    StringBuffer json = new StringBuffer(1024);
                    // initialize variable to parse the input
                    String tmp;

                    // add each part of the output to the json string buffer
                    while((tmp = reader.readLine()) != null)
                        json.append(tmp).append("\n");
                    // finish reading the output
                    reader.close();

                    // set the JSONObject to the output
                    data = new JSONObject(json.toString());

                    // error handling: api call to given url was unsuccessful, log error and return without displaying weather
                    if(data.getInt("cod") != 200) {
                        Log.d(TAG, "Cancelled");
                        return null;
                    }
                } catch (Exception e) {
                    // error handling: an error occurred while executing the function, log error and return without displaying weather
                    Log.d(TAG, e.getMessage());
                    return null;
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void Void) {
                if(data!=null){
                    Log.d(TAG,data.toString());
                    try {
                        // parse the json object
                        JSONObject main = data.getJSONObject("main");
                        JSONObject weather = data.getJSONArray("weather").getJSONObject(0);

                        // call function to set text greeting based on time of day
                        txtGreeting.setText(setGreeting());

                        // set temp values/weather icon based on data in json object
                        currentTemp.setText(String.format("%.0f",main.getDouble("temp")) + "°F");
                        GlobalVariables.temperature = main.getDouble("temp");
                        temperatureText.setText("Temperature");
                        humidity.setText(String.format("%.0f",main.getDouble("humidity")) + "%");
                        humidityText.setText("Humidity");
                        weatherIcon.setText(Html.fromHtml(setWeatherIcon(weather.getInt("id"),data.getJSONObject("sys").getLong("sunrise") * 1000,
                                data.getJSONObject("sys").getLong("sunset") * 1000)));
                    } catch (JSONException e) {
                        // error handling: an error occurred while parsing the json object, log error and return without displaying weather
                        Log.d(TAG, e.getMessage());
                    }
                }
            }
        }.execute();

    }

    // sets text greeting based on current time
    public String setGreeting() {
        // get the current hour
        int time = Integer.valueOf(String.valueOf(cal.getTime()).substring(11,13));
        // use current hour to set greeting based on time of day
        if(time >= 4 && time < 12) {
            return "Good Morning";
        }
        else if(time >= 12 && time <= 16) {
            return "Good Afternoon";
        }
        else {
            return "Good Night";
        }
    }

    // set the image to represent the current weather
    public static String setWeatherIcon(int actualId, long sunrise, long sunset){
        int id = actualId / 100;
        String icon = "";
        if(actualId == 800){
            long currentTime = new Date().getTime();
            if(currentTime>=sunrise && currentTime<sunset) {
                icon = "&#xf00d;";
            } else {
                icon = "&#xf02e;";
            }
        } else {
            switch(id) {
                case 2 : icon = "&#xf01e;";
                    break;
                case 3 : icon = "&#xf01c;";
                    break;
                case 7 : icon = "&#xf014;";
                    break;
                case 8 : icon = "&#xf013;";
                    break;
                case 6 : icon = "&#xf01b;";
                    break;
                case 5 : icon = "&#xf019;";
                    break;
            }
        }
        return icon;
    }
}
