package com.example.fivestarstyle;

import org.junit.Test;

import static org.junit.Assert.*;

public class ActivityRegisterTest extends Activity_Register {


    String goodEmail = "mjwilliams1@gmail.com";
    String noAtSignEmail = "mjwilliamsgmail.com";
    String noPeriodEmail = "mjwilliams@gmailcom";
    String goodPassword = "MeganWasserman";
    String noUpperCasePass = "meganwass";
    String shortPass = "meg";
    String noLowerCasePass = "MEGANWASS";

    @Test
    public void isValidEmailAddressTrue() {
        assertTrue(isValidEmailAddress(goodEmail));
    }

    @Test
    public void isValidEmailAddressFalse1() { assertFalse(isValidEmailAddress(noAtSignEmail)); }

    @Test
    public void isValidEmailAddressFalse2() { assertFalse(isValidEmailAddress(noPeriodEmail)); }

    @Test
    public void isValidPasswordTrue() { assertTrue(isValidPassword(goodPassword)); }

    @Test
    public void isValidPasswordFalse1() { assertFalse(isValidPassword(noUpperCasePass)); }

    @Test
    public void isValidPasswordFalse2() { assertFalse(isValidPassword(shortPass));}

    @Test
    public void isValidPasswordFalse3() { assertFalse(isValidPassword(noLowerCasePass));}

}
