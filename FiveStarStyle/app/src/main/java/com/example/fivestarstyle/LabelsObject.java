package com.example.fivestarstyle;
import java.io.Serializable;

@SuppressWarnings("serial")
public class LabelsObject implements Serializable {
    private String category = "";
    private String color = "";

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
