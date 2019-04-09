package com.example.fivestarstyle;

import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.view.PieChartView;

// description: This class sets up the "Overview" Activity.
//              in this activity, the user can see the percentages of the contents of their closet,
//              based on four different factors: category, season, color, and event

public class Activity_Overview extends AppCompatActivity {
    private final static String TAG = "ClosetOverview";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overview);
        // set up the tab layout
        createTabs();
    }

    public void createTabs() {
        TabLayout tabLayout;
        // get reference of FrameLayout and TabLayout
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
        // create fourth tab
        TabLayout.Tab fourthTab = tabLayout.newTab();
        fourthTab.setText("Event");
        tabLayout.addTab(fourthTab);
        // set first tab to be selected on start
        Fragment fragment = new OverviewTabsCategory();
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.simpleFrameLayout, fragment);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft.commit();
        getCategoryData();

        // perform setOnTabSelectedListener event on TabLayout
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                // get the current selected tab's position and replace the fragment accordingly
                Fragment fragment = new OverviewTabsCategory();
                switch (tab.getPosition()) {
                    case 0:
                        // switch to first tab
                        fragment = new OverviewTabsCategory();
                        getCategoryData();
//                        createCategoryPieGraph();
                        break;
                    case 1:
                        // switch to second tab
                        fragment = new OverviewTabsSeason();
                        getSeasonData();
                        createSeasonPieGraph();
                        break;
                    case 2:
                        // switch to third tab
                        fragment = new OverviewTabsColor();
                        createColorPieGraph();
                        break;
                    case 3:
                        // switch to fourth tab
                        fragment = new OverviewTabsEvent();
                        createEventPieGraph();
                        break;
                }
                // commit fragment change
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.simpleFrameLayout, fragment);
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

    public void createCategoryPieGraph(ArrayList<Integer> catCounts) {
        // reference pie chart view
        Log.d(TAG, "createPieGraph");
        PieChartView pieChartView = findViewById(R.id.chart);
        // initialize data for the pie chart
        List<SliceValue> pieData = new ArrayList<>();
//        ArrayList<Integer> catCounts = getCategoryData();
        // assign values to pie slices
        if (catCounts.size() != 0) {
            pieData.add(new SliceValue(catCounts.get(0), Color.parseColor("#99B5C3")).setLabel("Tops"));
            pieData.add(new SliceValue(catCounts.get(1), Color.parseColor("#567d9c")).setLabel("Bottoms"));
            pieData.add(new SliceValue(catCounts.get(2), Color.parseColor("#1B4F72")).setLabel("Dresses/Suits"));
            pieData.add(new SliceValue(catCounts.get(3), Color.parseColor("#567d9c")).setLabel("Outerwear"));
            pieData.add(new SliceValue(catCounts.get(4), Color.parseColor("#0d3653")).setLabel("Shoes"));
            pieData.add(new SliceValue(catCounts.get(5), Color.parseColor("#001E3B")).setLabel("Accessories"));
        } else {
            pieData.add(new SliceValue(1, Color.parseColor("#99B5C3")).setLabel("Tops"));
            pieData.add(new SliceValue(1, Color.parseColor("#567d9c")).setLabel("Bottoms"));
            pieData.add(new SliceValue(1, Color.parseColor("#1B4F72")).setLabel("Dresses/Suits"));
            pieData.add(new SliceValue(1, Color.parseColor("#567d9c")).setLabel("Outerwear"));
            pieData.add(new SliceValue(1, Color.parseColor("#0d3653")).setLabel("Shoes"));
            pieData.add(new SliceValue(1, Color.parseColor("#001E3B")).setLabel("Accessories"));
        }


        // call function to style pie chart data
        stylePieChart(pieData, pieChartView);
    }

    public void createSeasonPieGraph() {
        // reference pie chart view
        PieChartView pieChartView = findViewById(R.id.chart);
        // initialize data for the pie chart
        List<SliceValue> pieData = new ArrayList<>();

        // assign values to pie slices
        pieData.add(new SliceValue(35, Color.parseColor("#1B4F72")).setLabel("Fall"));
        pieData.add(new SliceValue(15, Color.parseColor("#0d3653")).setLabel("Winter"));
        pieData.add(new SliceValue(20, Color.parseColor("#001E3B")).setLabel("Spring"));
        pieData.add(new SliceValue(30, Color.parseColor("#99B5C3")).setLabel("Summer"));

        // call function to style pie chart
        stylePieChart(pieData, pieChartView);
    }

    public void createColorPieGraph() {
        // reference pie chart view+
        PieChartView pieChartView = findViewById(R.id.chart);
        // initialize data for the pie chart
        List<SliceValue> pieData = new ArrayList<>();

        // assign values to pie slices
        pieData.add(new SliceValue(15, Color.parseColor("#1B4F72")).setLabel("Red"));
        pieData.add(new SliceValue(29, Color.parseColor("#0d3653")).setLabel("Black"));
        pieData.add(new SliceValue(18, Color.parseColor("#001E3B")).setLabel("White"));
        pieData.add(new SliceValue(18, Color.parseColor("#99B5C3")).setLabel("Gray"));
        pieData.add(new SliceValue(20, Color.parseColor("#567d9c")).setLabel("Blue"));

        // call function to style pie chart
        stylePieChart(pieData, pieChartView);
    }

    public void createEventPieGraph() {
        // reference pie chart view
        PieChartView pieChartView = findViewById(R.id.chart);
        // initialize data for the pie chart
        List<SliceValue> pieData = new ArrayList<>();

        // assign values to pie slices
        pieData.add(new SliceValue(21, Color.parseColor("#1B4F72")).setLabel("Gym"));
        pieData.add(new SliceValue(30, Color.parseColor("#0d3653")).setLabel("Work"));
        pieData.add(new SliceValue(27, Color.parseColor("#001E3B")).setLabel("Casual"));
        pieData.add(new SliceValue(12, Color.parseColor("#99B5C3")).setLabel("Cocktail"));
        pieData.add(new SliceValue(10, Color.parseColor("#567d9c")).setLabel("Formal"));

        // call function to style pie chart
        stylePieChart(pieData, pieChartView);
    }

    // styles the pie charts
    public void stylePieChart(List<SliceValue> pieData, PieChartView pView) {
        PieChartData pieChartData = new PieChartData(pieData);
        pieChartData.setHasLabels(true).setValueLabelTextSize(18);
        pieChartData.setValueLabelBackgroundEnabled(false);
        pView.setPieChartData(pieChartData);
        pieChartData.setSlicesSpacing(3);
    }

    //data retrieval listeners
    public void getCategoryData(){
        final ArrayList<Integer> totals = new ArrayList<>();
        for (String cat : GlobalVariables.categories) {
            DataTransferService.getCategoryCount(cat, new OnGetDataListener() {
                @Override
                public void onStart() {
                    //on start method
                }

                @Override
                public void onSuccess(QuerySnapshot data) {
                    Log.d(TAG, "on success");
                    int count = 0;
                    for (DocumentSnapshot document : data) {
                        count++;
                    }
                    totals.add(count);
                    Log.d(TAG, "count =>" + count + " totals =>" + totals);
                    if(totals.size() == 6) {
                        createCategoryPieGraph(totals);
                    }
                }

                @Override
                public void onFailed(Exception databaseError) {
                    Log.w(TAG, "Error adding document", databaseError);
                }
            });
        }
    }

    public void getSeasonData(){
        final ArrayList<Integer> totals = new ArrayList<>();
        final List<String> seasons = Arrays.asList("spring", "summer", "winter", "fall");
        for (String cat : GlobalVariables.categories) {
//            for (String season : seasons) {
                DataTransferService.getSeasonCount(cat, "spring", new OnGetDataListener() {
                    @Override
                    public void onStart() {
                        //on start method
                    }

                    @Override
                    public void onSuccess(QuerySnapshot data) {
                        Log.d(TAG, "on success");
                        int count = 0;
                        for (DocumentSnapshot document : data) {
                            count++;
                        }
                        totals.add(count);
                        Log.d(TAG, "count =>" + count + " totals =>" + totals);
                    }

                    @Override
                    public void onFailed(Exception databaseError) {
                        Log.w(TAG, "Error adding document", databaseError);
                    }
                });
//            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_options,menu);
        return true;
    }

    // set up three dot menu and set intents
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.home__menu_option) {
            // go to home page
            Intent homeIntent = new Intent(this, Activity_Main.class);
            this.startActivity(homeIntent);
        }
        else if(item.getItemId() == R.id.closet_menu_option) {
            // go to view my closet page
            Intent overviewIntent = new Intent(this, Activity_ViewMyCloset.class);
            this.startActivity(overviewIntent);
        }
        else if(item.getItemId() == R.id.overview_menu_option) {
            // go to overview page
            Intent overviewIntent = new Intent(this, Activity_Overview.class);
            this.startActivity(overviewIntent);
        }
        else if(item.getItemId() == R.id.choosemyoutfit_menu_option) {
            // go to choose my outfit page
            Intent overviewIntent = new Intent(this, Activity_ChooseMyOutfit.class);
            this.startActivity(overviewIntent);
        }
        else if(item.getItemId() == R.id.settings_menu_option) {
            // go to settings page
            Intent overviewIntent = new Intent(this, Activity_Settings.class);
            this.startActivity(overviewIntent);
        }
        else if(item.getItemId() == R.id.logout_menu_option) {
            // logout current user and go to login page
            FirebaseAuth.getInstance().signOut();
            Intent overviewIntent = new Intent(this, Activity_Login.class);
            this.startActivity(overviewIntent);
        }
        else {
            return super.onOptionsItemSelected(item);
        }
        return true;
    }

}
