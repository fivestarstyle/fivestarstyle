package com.example.fivestarstyle;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ActivityChooseMyOutfitResultsTest2 extends Activity_ChooseMyOutfitResults {

    ArrayList<ItemDetailsObject> testList = null;
    ArrayList<ItemDetailsObject> testListTwo = new ArrayList<>();

    @Test
    public void isNullTestFalse() {
        assertFalse(isNull(testListTwo));
    }

    @Test
    public void isNullTestTrue() {
        assertTrue(isNull(testList));
    }

}
