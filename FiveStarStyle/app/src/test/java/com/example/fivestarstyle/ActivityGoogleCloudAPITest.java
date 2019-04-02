package com.example.fivestarstyle;

import com.google.api.services.vision.v1.model.BatchAnnotateImagesResponse;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

public class ActivityGoogleCloudAPITest extends Activity_GoogleCloudAPI {


    @Test
    public void filterLabelsTest() {
        List<String> stringList = new ArrayList<>();
        assertEquals("category",filterLabels(stringList).get(0));
    }
}