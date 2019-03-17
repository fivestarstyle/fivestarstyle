package com.example.fivestarstyle;

import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

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
        createTabs();

    }

    public void createTabs() {
        FrameLayout simpleFrameLayout;
        TabLayout tabLayout;

        // get reference of FrameLayout and TabLayout
        simpleFrameLayout = (FrameLayout) findViewById(R.id.simpleFrameLayout);
        tabLayout = (TabLayout) findViewById(R.id.simpleTabLayout);
        // create first tab
        TabLayout.Tab firstTab = tabLayout.newTab();
        firstTab.setText("Category");
        tabLayout.addTab(firstTab,true);
        // create second tab
        TabLayout.Tab secondTab = tabLayout.newTab();
        secondTab.setText("Season");
        tabLayout.addTab(secondTab);
        // create third tab
        TabLayout.Tab thirdTab = tabLayout.newTab();
        thirdTab.setText("Color");
        tabLayout.addTab(thirdTab);
        // set first tab to be selected
        Fragment fragment = new CategoryTab();
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        if(fragment != null) ft.replace(R.id.simpleFrameLayout, fragment);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft.commit();
        createCategoryPieGraph();

        // perform setOnTabSelectedListener event on TabLayout
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                // get the current selected tab's position and replace the fragment accordingly
                Fragment fragment = new CategoryTab();
                switch (tab.getPosition()) {
                    case 0:
                        fragment = new CategoryTab();
                        createCategoryPieGraph();
                        break;
                    case 1:
                        fragment = new SeasonTab();
                        createSeasonPieGraph();
                        break;
                    case 2:
                        fragment = new ColorTab();
                        createColorPieGraph();
                        break;
                }
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                if(fragment != null) ft.replace(R.id.simpleFrameLayout, fragment);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                ft.commit();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    public void createCategoryPieGraph() {
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

    public void createSeasonPieGraph() {
        // reference pie chart view
        PieChartView pieChartView = findViewById(R.id.chart);
        // initialize data for the pie chart
        List<SliceValue> pieData = new ArrayList<>();

        pieData.add(new SliceValue(35, Color.parseColor("#1B4F72")).setLabel("Fall"));
        pieData.add(new SliceValue(15, Color.parseColor("#0d3653")).setLabel("Winter"));
        pieData.add(new SliceValue(20, Color.parseColor("#001E3B")).setLabel("Spring"));
        pieData.add(new SliceValue(30, Color.parseColor("#99B5C3")).setLabel("Summer"));


        // create pie chart data
        PieChartData pieChartData = new PieChartData(pieData);
        pieChartData.setHasLabels(true).setValueLabelTextSize(18);
        pieChartData.setValueLabelBackgroundEnabled(false);
        pieChartView.setPieChartData(pieChartData);

        pieChartData.setSlicesSpacing(3);
    }

    public void createColorPieGraph() {
        // reference pie chart view
        PieChartView pieChartView = findViewById(R.id.chart);
        // initialize data for the pie chart
        List<SliceValue> pieData = new ArrayList<>();

        pieData.add(new SliceValue(15, Color.parseColor("#1B4F72")).setLabel("Red"));
        pieData.add(new SliceValue(29, Color.parseColor("#0d3653")).setLabel("Black"));
        pieData.add(new SliceValue(18, Color.parseColor("#001E3B")).setLabel("White"));
        pieData.add(new SliceValue(18, Color.parseColor("#99B5C3")).setLabel("Gray"));
        pieData.add(new SliceValue(20, Color.parseColor("#567d9c")).setLabel("Blue"));


        // create pie chart data
        PieChartData pieChartData = new PieChartData(pieData);
        pieChartData.setHasLabels(true).setValueLabelTextSize(18);
        pieChartData.setValueLabelBackgroundEnabled(false);
        pieChartView.setPieChartData(pieChartData);

        pieChartData.setSlicesSpacing(3);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_options,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.home__menu_option) {
            Intent homeIntent = new Intent(this,MainActivity.class);
            this.startActivity(homeIntent);
        }
        else if(item.getItemId() == R.id.closet_menu_option) {
            Intent overviewIntent = new Intent(this,ClosetActivity.class);
            this.startActivity(overviewIntent);
        }
        else if(item.getItemId() == R.id.overview_menu_option) {
            Intent overviewIntent = new Intent(this,ClosetStatistics.class);
            this.startActivity(overviewIntent);
        }
        else if(item.getItemId() == R.id.choosemyoutfit_menu_option) {
            Intent overviewIntent = new Intent(this,ChooseOutfit.class);
            this.startActivity(overviewIntent);
        }
        else if(item.getItemId() == R.id.settings_menu_option) {
            Intent overviewIntent = new Intent(this,SettingsActivity.class);
            this.startActivity(overviewIntent);
        }
        else if(item.getItemId() == R.id.logout_menu_option) {
            FirebaseAuth.getInstance().signOut();
            Intent overviewIntent = new Intent(this,LoginActivity.class);
            this.startActivity(overviewIntent);
        }
        else {
            return super.onOptionsItemSelected(item);
        }
        return true;
    }

    public void onCheckboxClicked(View view) {
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();

        // Check which checkbox was clicked
        switch(view.getId()) {
            case R.id.category_work:
                if (checked) {
                    // add to work stuff
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "You Chose Work",
                            Toast.LENGTH_SHORT);
                    toast.show();
                };
                break;
            case R.id.category_bar:
                if (checked) {
                    // add to bar stuff
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "You Chose Bar",
                            Toast.LENGTH_SHORT);
                    toast.show();
                }
            case R.id.category_gym:
                if (checked) {
                    // add to gym stuff
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "You Chose Gym",
                            Toast.LENGTH_SHORT);
                    toast.show();
                };
                break;
            case R.id.category_casual:
                if (checked) {
                    // add to casual stuff
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "You Chose Casual",
                            Toast.LENGTH_SHORT);
                    toast.show();
                };
            case R.id.category_cocktail:
                if (checked) {
                    // add to cocktail stuff
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "You Chose Cocktail",
                            Toast.LENGTH_SHORT);
                    toast.show();
                };
                break;
            case R.id.category_formal:
                if (checked) {
                    // add to bar stuff
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "You Chose Formal",
                            Toast.LENGTH_SHORT);
                    toast.show();
                };
                break;
            default:
                // add to casual?
                break;
        }
    }
}
