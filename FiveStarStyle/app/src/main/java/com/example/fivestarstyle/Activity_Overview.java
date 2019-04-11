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
import android.view.View;
import android.widget.Button;

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

        //TEST CODE
        Button test = (Button) findViewById(R.id.btnLeastFrequentlyWorn);
        test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ArrayList<Integer> totals = new ArrayList<>();
                DataTransferService.getItemsByColor("shoes", "blue", new OnGetDataListener() {
                    @Override
                    public void onStart() {
                        //on start
                    }

                    @Override
                    public void onSuccess(QuerySnapshot data) {
                        Log.d(TAG, "TESTER on success");
                        int count = 0;
                        for (DocumentSnapshot document : data) {
                            count++;
                        }
                        totals.add(count);
                        Log.d(TAG, "TESTER count =>" + count + " totals =>" + totals);
                    }

                    @Override
                    public void onFailed(Exception databaseError) {
                        Log.w(TAG, databaseError);
                    }
                });
            }
        });
        ///END TEST CODE
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
                        break;
                    case 1:
                        // switch to second tab
                        fragment = new OverviewTabsSeason();
                        getSeasonData();
                        break;
                    case 2:
                        // switch to third tab
                        fragment = new OverviewTabsColor();
                        getColorData();
                        break;
                    case 3:
                        // switch to fourth tab
                        fragment = new OverviewTabsEvent();
                        getEventData();
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

    public void createSeasonPieGraph(ArrayList<Long> counts) {
        // reference pie chart view
        PieChartView pieChartView = findViewById(R.id.chart);
        // initialize data for the pie chart
        List<SliceValue> pieData = new ArrayList<>();
        // assign values to pie slices
        if (counts.size() != 0) {
            pieData.add(new SliceValue(counts.get(0), Color.parseColor("#1B4F72")).setLabel("Fall"));
            pieData.add(new SliceValue(counts.get(1), Color.parseColor("#001E3B")).setLabel("Spring"));
            pieData.add(new SliceValue(counts.get(2), Color.parseColor("#99B5C3")).setLabel("Summer"));
            pieData.add(new SliceValue(counts.get(3), Color.parseColor("#0d3653")).setLabel("Winter"));
        } else {
            pieData.add(new SliceValue(35, Color.parseColor("#1B4F72")).setLabel("Fall"));
            pieData.add(new SliceValue(20, Color.parseColor("#001E3B")).setLabel("Spring"));
            pieData.add(new SliceValue(30, Color.parseColor("#99B5C3")).setLabel("Summer"));
            pieData.add(new SliceValue(15, Color.parseColor("#0d3653")).setLabel("Winter"));
        }

        // call function to style pie chart
        stylePieChart(pieData, pieChartView);
    }

    public void createColorPieGraph(ArrayList<Long> counts) {
        // reference pie chart view+
        PieChartView pieChartView = findViewById(R.id.chart);
        // initialize data for the pie chart
        List<SliceValue> pieData = new ArrayList<>();

        // assign values to pie slices
        if (counts.size() != 0) {
            pieData.add(new SliceValue(counts.get(0), Color.parseColor("#99B5C3")).setLabel("Black"));
            pieData.add(new SliceValue(counts.get(1), Color.parseColor("#567d9c")).setLabel("Blue"));
            pieData.add(new SliceValue(counts.get(2), Color.parseColor("#0d3653")).setLabel("Brown"));
            pieData.add(new SliceValue(counts.get(3), Color.parseColor("#1B4F72")).setLabel("Gray"));
            pieData.add(new SliceValue(counts.get(4), Color.parseColor("#567d9c")).setLabel("Green"));
            pieData.add(new SliceValue(counts.get(5), Color.parseColor("#001E3B")).setLabel("Orange"));
            pieData.add(new SliceValue(counts.get(6), Color.parseColor("#99B5C3")).setLabel("Pink"));
            pieData.add(new SliceValue(counts.get(7), Color.parseColor("#567d9c")).setLabel("Purple"));
            pieData.add(new SliceValue(counts.get(8), Color.parseColor("#0d3653")).setLabel("Red"));
            pieData.add(new SliceValue(counts.get(9), Color.parseColor("#1B4F72")).setLabel("White"));
            pieData.add(new SliceValue(counts.get(10), Color.parseColor("#567d9c")).setLabel("Yellow"));
        } else {
            pieData.add(new SliceValue(counts.get(0), Color.parseColor("#99B5C3")).setLabel("Black"));
            pieData.add(new SliceValue(counts.get(1), Color.parseColor("#567d9c")).setLabel("Blue"));
            pieData.add(new SliceValue(counts.get(2), Color.parseColor("#0d3653")).setLabel("Brown"));
            pieData.add(new SliceValue(counts.get(3), Color.parseColor("#1B4F72")).setLabel("Gray"));
            pieData.add(new SliceValue(counts.get(4), Color.parseColor("#567d9c")).setLabel("Green"));
            pieData.add(new SliceValue(counts.get(5), Color.parseColor("#001E3B")).setLabel("Orange"));
            pieData.add(new SliceValue(counts.get(6), Color.parseColor("#99B5C3")).setLabel("Pink"));
            pieData.add(new SliceValue(counts.get(7), Color.parseColor("#567d9c")).setLabel("Purple"));
            pieData.add(new SliceValue(counts.get(8), Color.parseColor("#0d3653")).setLabel("Red"));
            pieData.add(new SliceValue(counts.get(9), Color.parseColor("#1B4F72")).setLabel("White"));
            pieData.add(new SliceValue(counts.get(10), Color.parseColor("#567d9c")).setLabel("Yellow"));
        }
        // call function to style pie chart
        stylePieChart(pieData, pieChartView);
    }

    public void createEventPieGraph(ArrayList<Long> counts) {
        // reference pie chart view
        PieChartView pieChartView = findViewById(R.id.chart);
        // initialize data for the pie chart
        List<SliceValue> pieData = new ArrayList<>();

        // assign values to pie slices
        if (counts.size() != 0) {
            pieData.add(new SliceValue(counts.get(0), Color.parseColor("#99B5C3")).setLabel("Bar"));
            pieData.add(new SliceValue(counts.get(1), Color.parseColor("#567d9c")).setLabel("Casual"));
            pieData.add(new SliceValue(counts.get(2), Color.parseColor("#0d3653")).setLabel("Cocktail"));
            pieData.add(new SliceValue(counts.get(3), Color.parseColor("#1B4F72")).setLabel("Formal"));
            pieData.add(new SliceValue(counts.get(4), Color.parseColor("#567d9c")).setLabel("Gym"));
            pieData.add(new SliceValue(counts.get(5), Color.parseColor("#001E3B")).setLabel("Work"));
        } else {
            pieData.add(new SliceValue(1, Color.parseColor("#99B5C3")).setLabel("Bar"));
            pieData.add(new SliceValue(1, Color.parseColor("#567d9c")).setLabel("Casual"));
            pieData.add(new SliceValue(1, Color.parseColor("#0d3653")).setLabel("Cocktail"));
            pieData.add(new SliceValue(1, Color.parseColor("#1B4F72")).setLabel("Formal"));
            pieData.add(new SliceValue(1, Color.parseColor("#567d9c")).setLabel("Gym"));
            pieData.add(new SliceValue(1, Color.parseColor("#001E3B")).setLabel("Work"));
        }

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
        final ArrayList<Long> totals = new ArrayList<>();
        DataTransferService.getSeasonCount( new OnGetDataListener() {
            @Override
            public void onStart() {
                //on start method
            }

            @Override
            public void onSuccess(QuerySnapshot data) {
                Log.d(TAG, "on success");
                for (DocumentSnapshot document : data) {
                    totals.add((long) document.get("total"));
                }
                if (totals.size() == 4) {
                    createSeasonPieGraph(totals);
                }
                Log.d(TAG, "season totals =>" + totals);
            }

            @Override
            public void onFailed(Exception databaseError) {
                Log.w(TAG, "Error getting totals", databaseError);
            }
        });
    }

    public void getEventData(){
        final ArrayList<Long> totals = new ArrayList<>();
        DataTransferService.getEventCount( new OnGetDataListener() {
            @Override
            public void onStart() {
                //on start method
            }

            @Override
            public void onSuccess(QuerySnapshot data) {
                Log.d(TAG, "on success");
                for (DocumentSnapshot document : data) {
                    totals.add((long) document.get("total"));
                }
                if (totals.size() == 6) {
                    createEventPieGraph(totals);
                }
                Log.d(TAG, "event totals =>" + totals);
            }

            @Override
            public void onFailed(Exception databaseError) {
                Log.w(TAG, "Error getting totals", databaseError);
            }
        });
    }

    public void getColorData(){
        final ArrayList<Long> totals = new ArrayList<>();
        DataTransferService.getColorCount( new OnGetDataListener() {
            @Override
            public void onStart() {
                //on start method
            }

            @Override
            public void onSuccess(QuerySnapshot data) {
                Log.d(TAG, "on success");
                for (DocumentSnapshot document : data) {
                    totals.add((long) document.get("total"));
                }
                if (totals.size() == 11) {
                    createColorPieGraph(totals);
                }
                Log.d(TAG, "color totals =>" + totals);
            }

            @Override
            public void onFailed(Exception databaseError) {
                Log.w(TAG, "Error getting totals", databaseError);
            }
        });
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
