package com.example.fivestarstyle;

import android.app.Dialog;
import android.arch.core.util.Function;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.media.Image;
import android.nfc.Tag;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

public class ChooseOutfit extends AppCompatActivity {
    private ListView lstView;
    Dialog myDialog;
    JSONObject data = null;

    private final static String TAG = "Time";

    TextView currentTemp, humidity, humidityText, temperatureText, txtGreeting;
    TextView weatherIcon;
    Typeface weatherFont;
    Calendar cal;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_options,menu);
        return true;
    }

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_outfit);
        getCompleteAddressString(Double.valueOf(MyApplication.latitude), Double.valueOf(MyApplication.longitude));
        getJSON(MyApplication.longitude, MyApplication.latitude, MyApplication.customZipCode, MyApplication.customCity, MyApplication.customState, "bba8e629ce93f0a063c0a46c47dc5960");
        currentTemp = (TextView) findViewById(R.id.currentTemp);
        humidity = (TextView) findViewById(R.id.humidity);
        weatherIcon = (TextView) findViewById(R.id.weatherIcon);
        temperatureText = (TextView) findViewById(R.id.currentTemp_text);
        humidityText = (TextView) findViewById(R.id.humidity_text);
        txtGreeting = (TextView) findViewById(R.id.txtGreeting);
        cal = Calendar.getInstance();
    }

    public void getJSON(final String longitude, final String latitude, final String zipCode, final String city, final String state, final String api_key) {

        // error handling
        if(longitude.length() == 0 && latitude.length() == 0) {
            if(city.length() == 0 && state.length() == 0) {
                if(zipCode.length() == 0) {
                    return;
                }
            }
        }


        new AsyncTask<Void, Void, Void>() {


            @Override
            protected void onPreExecute() {
                super.onPreExecute();

            }

            @Override
            protected Void doInBackground(Void... params) {
                try {
                    URL url;
                    if(city.length() != 0 && state.length() != 0) {
                        url = new URL("https://api.openweathermap.org/data/2.5/weather?q=" + city + ",us&units=imperial&appid=" + api_key);
                    }
                    else if(zipCode.length() != 0) {
                        url = new URL("https://api.openweathermap.org/data/2.5/weather?zip=" + zipCode + ",us&units=imperial&appid=" + api_key);
                    }
                    else {
                        url = new URL("https://api.openweathermap.org/data/2.5/weather?lat=" + latitude + "&lon=" + longitude + "&units=imperial&appid=" + api_key);
                    }

//                    URL url = new URL("https://api.openweathermap.org/data/2.5/weather?lat=" + latitude + "&lon=" + longitude + "&units=imperial&appid=" + api_key);

                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                    BufferedReader reader =
                            new BufferedReader(new InputStreamReader(connection.getInputStream()));

                    StringBuffer json = new StringBuffer(1024);
                    String tmp = "";

                    while((tmp = reader.readLine()) != null)
                        json.append(tmp).append("\n");
                    reader.close();

                    data = new JSONObject(json.toString());

                    if(data.getInt("cod") != 200) {
                        System.out.println("Cancelled");
                        return null;
                    }


                } catch (Exception e) {

                    System.out.println("Exception "+ e.getMessage());
                    return null;
                }

                return null;
            }

            @Override
            protected void onPostExecute(Void Void) {
                if(data!=null){
                    Log.d("my weather received",data.toString());
                    try {
                        JSONObject main = data.getJSONObject("main");
                        JSONObject weather = data.getJSONArray("weather").getJSONObject(0);
                        Log.d("weather returned",weather.toString());
//                        String icon = weather.getString("icon");
//                        String iconUrl = "http://openweathermap.org/img/w/" + icon + ".png";
//                        Picasso.get().load(iconUrl).into(weatherIcon);

                        String times = String.valueOf(cal.getTime());
                        String hour = times.substring(11,13);
                        int time = Integer.valueOf(hour);

                        if(time >= 4 && time < 12) {
                            txtGreeting.setText("Good Morning");
                            Log.d(TAG, String.valueOf(time));
                        }
                        else if(time >= 12 && time <= 16) {
                            txtGreeting.setText("Good Afternoon");
                            Log.d(TAG, String.valueOf(time));
                        }
                        else {
                            txtGreeting.setText("Good Night");
                            Log.d(TAG, String.valueOf(time));
                        }
                        currentTemp.setText(String.format("%.0f",main.getDouble("temp")) + "°F");
                        temperatureText.setText("Temperature");
                        humidity.setText(String.format("%.0f",main.getDouble("humidity")) + "%");
                        humidityText.setText("Humidity");
                        weatherIcon.setText(Html.fromHtml(setWeatherIcon(weather.getInt("id"),data.getJSONObject("sys").getLong("sunrise") * 1000,
                                data.getJSONObject("sys").getLong("sunset") * 1000)));

                    } catch (JSONException e) {
                        Toast.makeText(getApplicationContext(), "Error, Check City", Toast.LENGTH_SHORT).show();
                    }


                }

            }
        }.execute();

    }

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

    private String getCompleteAddressString(double LATITUDE, double LONGITUDE) {
        String strAdd = "";
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(LATITUDE, LONGITUDE, 1);
            if (addresses != null) {
                Address returnedAddress = addresses.get(0);
                StringBuilder strReturnedAddress = new StringBuilder("");

                for (int i = 0; i <= returnedAddress.getMaxAddressLineIndex(); i++) {
                    strReturnedAddress.append(returnedAddress.getAddressLine(i)).append("\n");
                }
                strAdd = strReturnedAddress.toString();
                String[] strAddArray = strAdd.split("\\s*,\\s*");
                String[] strAddArray2 = strAddArray[2].split("\\s+");
                MyApplication.city = strAddArray[1];
                MyApplication.state = strAddArray2[0];
                MyApplication.zipCode = strAddArray2[1];
                Log.w("My Current location address", MyApplication.city);
                Log.w("My Current location address", MyApplication.state);
                Log.w("My Current location address", MyApplication.zipCode);

            } else {
                Log.w("My Current location address", "No Address returned!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.w("My Current location address", "Cannot get Address!");
        }
        return strAdd;
    }

}
