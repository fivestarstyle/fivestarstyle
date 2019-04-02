package com.example.fivestarstyle;
import android.graphics.Bitmap;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class LabelsObject implements Serializable {
    private String category = "";
    private String color = "";
    private List<String> seasons = new ArrayList<>();
    private List<String> events = new ArrayList<>();

    // set category
    public void labelSetCategory(String c) {
        this.category = c;
    }

    // get category
    public String labelGetCategory() {
        return category;
    }

    // set color
    public void labelSetColor(String c) {
        this.color = c;
    }

    // get color
    public String labelGetColor() {
        return color;
    }

    // add season if it does not already exist in seasons list
    public void labelAddSeason(String c) {
        int i;
        for(i = 0; i < seasons.size(); i++) {
            if(c.equals(seasons.get(i))) {
                return;
            }
        }
        this.seasons.add(c);
    }

    // get list of seasons
    public List<String> labelGetSeasons() { return seasons; }

    // remove season from list
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

    // add event if it does not already exist in events list
    public void labelAddEvent(String c) {
        int i;
        for(i = 0; i < events.size(); i++) {
            if(c.equals(events.get(i))) {
                return;
            }
        }
        this.events.add(c);
    }

    // get list of events
    public List<String> labelGetEvents() { return events; }

    // remove event from from list
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
