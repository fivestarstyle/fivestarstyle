package com.example.fivestarstyle;
import android.graphics.Bitmap;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class LabelsObject implements Serializable {
//    private Bitmap image;
    private String category = "";
    private String color = "";
    private List<String> seasons = new ArrayList<>();
    private List<String> events = new ArrayList<>();

    //added to test -c
//    public void labelSetImage(Bitmap i) {
//        this.image = i;
//    }
//    public Bitmap labelGetImage() {
//        return image;
//    }

    public void labelSetCategory(String c) {
        this.category = c;
    }

    public String labelGetCategory() {
        return category;
    }

    public void labelSetColor(String c) {
        this.color = c;
    }

    public String labelGetColor() {
        return color;
    }

    public void labelAddSeason(String c) { this.seasons.add(c); }

    public List<String> labelGetSeasons(String c) { return seasons; }

    public void labelAddEvent(String c) { this.events.add(c); }

    public List<String> labelGetEvents(String c) { return events; }
}
