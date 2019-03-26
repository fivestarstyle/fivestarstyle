package com.example.fivestarstyle;

import org.junit.Test;

import static org.junit.Assert.*;

public class LoginActivityTest extends LoginActivity {

    String goodEmail = "test@test.com";
    String badEmail = "testtestcom";
    String shortPass = "Mn23";
    String allLowerPass = "asdfghjk";
    String allUpperPass = "ASDFGHJK";
    String goodPass = "Megan123";

    @Test
    public void isValidEmailAddressTestTrue() {
        assertTrue(isValidEmailAddress(goodEmail));
    }

    @Test
    public void isValidEmailAddressTestFalse() {
        assertFalse(isValidEmailAddress(badEmail));
    }

    @Test
    public void isValidPasswordTestShort() {
        assertFalse(isValidPassword(shortPass));
    }

    @Test
    public void isValidPasswordTestLower() {
        assertFalse(isValidEmailAddress(allLowerPass));
    }

    @Test
    public void isValidPasswordTestUpper() {
        assertFalse(isValidPassword(allUpperPass));
    }

    @Test
    public void isValidPasswordTestTrue() {
        assertTrue(isValidPassword(goodPass));
    }
}