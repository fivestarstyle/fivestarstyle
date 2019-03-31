package com.example.fivestarstyle;

import android.view.MenuItem;

import org.junit.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.*;

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

    @Test
    public void menuTestAssertEquals()  {
        //  create mock
        MenuItem home = mock(MenuItem.class);

        // define return value for method getUniqueId()
        when(home.getItemId()).thenReturn(R.id.home__menu_option);

        // use mock in test....
        assertEquals(home.getItemId(), R.id.home__menu_option);
    }


    @Test
    public void menuTestVerify()  {
        // create and configure mock
        MenuItem item = Mockito.mock(MenuItem.class);
        when(item.getItemId()).thenReturn(R.id.overview_menu_option);

        // call method testing on the mock with parameter 12
        //overview.testing(12);
        item.getItemId();
        item.getItemId();

        // was the method called twice?
        verify(item, times(2)).getItemId();

    }


}