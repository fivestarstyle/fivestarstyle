package com.example.fivestarstyle;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ActivityChooseMyOutfitTest3 extends Activity_ChooseMyOutfit {

    ItemDetailsObject testObject = new ItemDetailsObject();

    ArrayList<ItemDetailsObject> testList = new ArrayList<>();
    ArrayList<ItemDetailsObject> testListTwo = new ArrayList<>();

    public void addToArray(ItemDetailsObject obj) {
        testListTwo.add(obj);
    }

    @Test
    public void isEmptyTestTrue() {
        assertTrue(isEmpty(testList));
    }

    @Test
    public void isEmptyTestFalse() {
        addToArray(testObject);
        assertFalse(isEmpty(testListTwo));
    }

}
