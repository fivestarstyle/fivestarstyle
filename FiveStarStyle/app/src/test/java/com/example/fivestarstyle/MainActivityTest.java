package com.example.fivestarstyle;

import org.junit.Test;

import static org.junit.Assert.*;

public class MainActivityTest extends MainActivity {

    double latitude = 33.2098;
    double longitude = 87.5692;

    @Test
    public void getCompleteAddressStringTrue() {
        System.out.println(getCompleteAddressString(latitude,longitude));
    }

    @Test
    public void getCompleteAddressStringFalse() {
        assertNotEquals(getCompleteAddressString(latitude,longitude),"Birmingham, Alabama");
    }


}