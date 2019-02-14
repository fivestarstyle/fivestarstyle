package com.example.fivestarstyle;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.view.PieChartView;

public class ClosetStatistics<pieChartData> extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_closet_statistics);
        createPieGraph();
    }

    public void createPieGraph() {
        // reference pie chart view
        PieChartView pieChartView = findViewById(R.id.chart);
        // initialize data for the pie chart
        List<SliceValue> pieData = new ArrayList<>();

        pieData.add(new SliceValue(15, Color.parseColor("#1B4F72")).setLabel("Dresses"));
        pieData.add(new SliceValue(29, Color.parseColor("#0d3653")).setLabel("Shoes"));
        pieData.add(new SliceValue(18, Color.parseColor("#001E3B")).setLabel("Accessories"));
        pieData.add(new SliceValue(18, Color.parseColor("#99B5C3")).setLabel("Tops"));
        pieData.add(new SliceValue(20, Color.parseColor("#567d9c")).setLabel("Bottoms"));


        // create pie chart data
        PieChartData pieChartData = new PieChartData(pieData);
        pieChartData.setHasLabels(true).setValueLabelTextSize(18);
        pieChartData.setValueLabelBackgroundEnabled(false);
        pieChartView.setPieChartData(pieChartData);

        pieChartData.setSlicesSpacing(3);
    }
}
