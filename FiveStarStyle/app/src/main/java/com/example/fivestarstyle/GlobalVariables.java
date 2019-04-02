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
    public static String city = "";
    public static String state = "";
    public static String zipCode = "";
    public static String customCity = "";
    public static String customState = "";
    public static String customZipCode = "";
    public static String gender = "";
    public static String firstName = "";
    public static String lastName = "";
    public static List<String> categories = Arrays.asList("Tops", "Bottoms", "Dresses_Suits", "Outerwear", "Shoes", "Accessories");
    private static Bitmap bitmap;
    public static Bitmap getBitmap() {
        return bitmap;
    }
    public static void setBitmap(Bitmap b) {
        bitmap = b;
    }
}
