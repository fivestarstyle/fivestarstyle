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

    public void labelAddSeason(String c) {
        int i;
        for(i = 0; i < seasons.size(); i++) {
            if(c.equals(seasons.get(i))) {
                return;
            }
        }
        this.seasons.add(c);
    }

    public List<String> labelGetSeasons() { return seasons; }

    public void labelRemoveSeason(String c) {
        int i = 0;
        List<String> newSeasons = new ArrayList<>();
        for(i = 0; i < seasons.size(); i++) {
            if(seasons.get(i) != c) {
                newSeasons.add(seasons.get(i));
            }
        }
        seasons = newSeasons;
    }

    public void labelAddEvent(String c) {
        int i;
        for(i = 0; i < events.size(); i++) {
            if(c.equals(events.get(i))) {
                return;
            }
        }
        this.events.add(c);
    }

    public List<String> labelGetEvents() { return events; }

    public void labelRemoveEvent(String c) {
        int i = 0;
        List<String> newEvents = new ArrayList<>();
        for(i = 0; i < events.size(); i++) {
            if(events.get(i) != c) {
                newEvents.add(events.get(i));
            }
        }
        events = newEvents;
    }

}
