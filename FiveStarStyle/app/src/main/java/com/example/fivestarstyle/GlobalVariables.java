package com.example.fivestarstyle;

import android.app.Application;
import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// this class contains the global variables for the application
public class GlobalVariables extends Application {
    public static String longitude = "";
    public static String latitude = "";
    public static Double temperature = 0.0;
    public static String city = "";
    public static String state = "";
    public static String zipCode = "";
    public static String customCity = "";
    public static String customState = "";
    public static String customZipCode = "";
    public static String firstName = "";
    public static String lastName = "";
    public static String top = "top";
    public static String bottom = "bottom";
    public static String dress_or_suit = "dress_or_suit";
    public static String outerwear = "outerwear";
    public static String shoes = "shoes";
    public static String accessories = "accessories";
    public static List<String> categories = Arrays.asList(
            top, bottom, dress_or_suit, outerwear, shoes, accessories
            );
    public static List<String> seasons = Arrays.asList(
            "fall", "spring", "summer", "winter"
    );
    public static List<String> events = Arrays.asList(
            "bar", "casual", "cocktail", "formal", "gym", "work"
    );
    public static List<String> colors = Arrays.asList(
            "black", "blue", "brown", "gray", "green", "orange", "pink", "purple", "red", "white", "yellow"
    );
    private static Bitmap bitmap;
    public static Bitmap getBitmap() {
        return bitmap;
    }
    public static void setBitmap(Bitmap b) {
        bitmap = b;
    }
}
