package com.example.fivestarstyle;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ItemDetailsObject implements Serializable {
    private String imageUrl = "";
    private String cat = "";
    private List<String> events = new ArrayList<>();
    private List<String> seasons = new ArrayList<>();

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getImageUrl(){
        return this.imageUrl;
    }

    public void setCat(String category){
        this.cat = category;
    }

    public String getCat(){
        return this.cat;
    }

    public void setEvents(List<String> events1) {
        this.events = events1;
    }

    public List<String> getEvents(){
        return this.events;
    }

    public void setSeasons(List<String> seasons1){
        this.seasons = seasons1;
    }

    public List<String> getSeasons(){
        return this.seasons;
    }
}
