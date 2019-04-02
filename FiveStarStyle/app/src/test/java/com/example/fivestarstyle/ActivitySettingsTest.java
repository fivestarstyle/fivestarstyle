package com.example.fivestarstyle;

import org.junit.Test;

import static org.junit.Assert.*;

public class ActivitySettingsTest extends Activity_Settings {

    String goodZip = "35205";
    String nullZip = "";


    @Test (expected = NullPointerException.class)
    public void customTrue() {
        custom(goodZip);
    }

    @Test
    public void customFalse() {
        assertFalse(custom(nullZip));
    }
}