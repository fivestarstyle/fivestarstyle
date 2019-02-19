package com.example.fivestarstyle;

public class Model {
    private boolean isSelected;
    private String category;

    Model(String category, boolean isSelected) {
        this.category = category;
        this.isSelected = isSelected;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
