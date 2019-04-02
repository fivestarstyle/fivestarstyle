package com.example.fivestarstyle;

import android.graphics.Bitmap;
import android.view.MenuItem;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mockito;

import java.io.File;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MyHelperTest extends MyHelper {


    @Test (expected = NullPointerException.class)
    public void compressImageTest() {
        //  create mock
        File fileVar = mock(File.class);
        Bitmap bitmapVar = mock(Bitmap.class);
        compressImage(fileVar,bitmapVar);
    }
}