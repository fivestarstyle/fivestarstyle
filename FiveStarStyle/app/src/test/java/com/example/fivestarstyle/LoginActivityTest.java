package com.example.fivestarstyle;

import org.junit.Test;

import static org.junit.Assert.*;

public class LoginActivityTest extends LoginActivity{

    String goodEmail = "test@test.com";
    String badEmail = "testtestcom";

    @Test
    public void isValidEmailAddressTestTrue() {
        assertTrue(isValidEmailAddress(goodEmail));
    }

    @Test
    public void isValidEmailAddressTestFalse() {
        assertFalse(isValidEmailAddress(badEmail));
    }
}