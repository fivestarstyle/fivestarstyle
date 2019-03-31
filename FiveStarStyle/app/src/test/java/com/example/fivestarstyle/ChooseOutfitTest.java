package com.example.fivestarstyle;

import org.junit.Test;

import static org.junit.Assert.*;

public class ChooseOutfitTest extends ChooseOutfit {

    int testIdOne = 200;
    int testIdTwo = 300;
    int testIdThree = 400;

    String idOneString = "&#xf01e;";
    String idTwoString = "&#xf01c;";
    String idThreeString =  "&#xf014;"; //this string should be true only for an ID of 700

    @Test
    public void setWeatherIconTestTrue1() {
        assertEquals(idOneString,setWeatherIcon(testIdOne,0,0));

    }

    @Test
    public void setWeatherIconTestTrue2() {
        assertEquals(idTwoString,setWeatherIcon(testIdTwo,0,0));
    }

    @Test
    public void setWeatherIconTestFalse() {
        assertNotEquals(idThreeString,setWeatherIcon(testIdThree,0,0));
    }
}