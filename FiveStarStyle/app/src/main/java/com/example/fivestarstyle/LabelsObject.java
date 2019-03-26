package com.example.fivestarstyle;
import android.graphics.Bitmap;

import java.io.Serializable;

@SuppressWarnings("serial")
public class LabelsObject implements Serializable {
    private Bitmap image;
    private String category = "";
    private String color = "";

    //added to test
    public void labelSetImage(Bitmap i) {
        this.image = i;
    }
    public Bitmap labelGetImage() {
        return image;
    }

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
}
