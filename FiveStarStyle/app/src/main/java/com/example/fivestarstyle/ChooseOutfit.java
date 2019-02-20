package com.example.fivestarstyle;

import android.app.Dialog;
import android.arch.core.util.Function;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
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

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ChooseOutfit extends AppCompatActivity {
    private ListView lstView;
    Dialog myDialog;
    JSONObject data = null;

    TextView currentTemp, humidity;
    TextView weatherIcon;
    Typeface weatherFont;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_outfit);
        getJSON("Tuscaloosa");
        currentTemp = (TextView) findViewById(R.id.currentTemp);
        humidity = (TextView) findViewById(R.id.humidity);
        weatherIcon = (TextView) findViewById(R.id.weatherIcon);
    }

    public void getJSON(final String city) {

        new AsyncTask<Void, Void, Void>() {


            @Override
            protected void onPreExecute() {
                super.onPreExecute();

            }

            @Override
            protected Void doInBackground(Void... params) {
                try {
                    URL url = new URL("https://api.openweathermap.org/data/2.5/weather?q="+city+"&units=imperial&appid=bba8e629ce93f0a063c0a46c47dc5960");

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
                        currentTemp.setText(String.format("%.0f",main.getDouble("temp")) + "°F");
                        humidity.setText(String.format("%.0f",main.getDouble("temp_min")) + "%");
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

}