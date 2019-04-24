package com.example.fivestarstyle;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ActivityOverviewTest extends Activity_Overview {

    Integer testObject = 5;

    ArrayList<Integer> testList = new ArrayList<>();
    ArrayList<Integer> testListTwo = new ArrayList<>();

    public void addToArray(Integer num) {
        testListTwo.add(num);
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
