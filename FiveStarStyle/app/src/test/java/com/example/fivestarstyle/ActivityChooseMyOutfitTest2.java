package com.example.fivestarstyle;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class ActivityChooseMyOutfitTest2 extends Activity_ChooseMyOutfit {

    ItemDetailsObject testObject = new ItemDetailsObject();

    ArrayList<ItemDetailsObject> testList = new ArrayList<>();
    ArrayList<ItemDetailsObject> testListTwo = new ArrayList<>();

    public void addToArray(ItemDetailsObject obj) {
        testListTwo.add(obj);
    }

    @Test
    public void notEmptyTestFalse() {
        assertFalse(notEmpty(testList));
    }

    @Test
    public void notEmptyTestTrue() {
        addToArray(testObject);
        assertTrue(notEmpty(testListTwo));
    }


}
