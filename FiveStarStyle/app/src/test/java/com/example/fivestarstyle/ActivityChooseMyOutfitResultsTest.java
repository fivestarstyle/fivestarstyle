package com.example.fivestarstyle;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ActivityChooseMyOutfitResultsTest extends Activity_ChooseMyOutfitResults {

    ArrayList<ItemDetailsObject> testList = null;
    ArrayList<ItemDetailsObject> testListTwo = new ArrayList<>();

    @Test
    public void notNullTestFalse() {
        assertFalse(notNull(testList));
    }

    @Test
    public void notNullTestTrue() {
        assertTrue(notNull(testListTwo));
    }

}
