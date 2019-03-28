package com.example.fivestarstyle;

import org.junit.Test;

import static org.junit.Assert.*;

public class SettingsActivityTest extends SettingsActivity {

    String goodZip = "35205";
    String nullZip = "";

//    @Test
//    public void customTrue() {
//        assertTrue(custom(goodZip));
//    }

    @Test
    public void customFalse() {
        assertFalse(custom(nullZip));
    }
}