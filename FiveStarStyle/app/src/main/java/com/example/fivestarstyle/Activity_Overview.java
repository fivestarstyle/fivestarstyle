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
import android.widget.Toast;

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
    private ArrayList<Integer> colors = new ArrayList<>();
    private Button right;
    private Button left;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overview);
        left = (Button) findViewById(R.id.left_btn2);
        left.setVisibility(View.GONE);
        right = (Button) findViewById(R.id.right_btn2);
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
        //set up buttons
        left.setVisibility(View.GONE);
        right.setVisibility(View.VISIBLE);
        right.setOnClickListener(new View.OnClickListener() {
            Fragment fragment = null;
            @Override
            public void onClick(View v) {
                // go to the next tab
                fragment = new OverviewTabsSeason();
                selectTab(1);
            }
        });
        // reference pie chart view
        Log.d(TAG, "createPieGraph");
        PieChartView pieChartView = findViewById(R.id.chart);
        // initialize data for the pie chart
        List<SliceValue> pieData = new ArrayList<>();
        // assign values to pie slices
        if (notEmpty(catCounts)) {
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
        stylePieChart(pieData, pieChartView, true);
    }

    public boolean notEmpty(ArrayList<Integer> list) {
        if(list.size() > 0) {
            return true;
        }
        return false;
    }

    public void createSeasonPieGraph(ArrayList<Integer> counts) {
        //set up buttons
        left.setVisibility(View.VISIBLE);
        left.setOnClickListener(new View.OnClickListener() {
            Fragment fragment = null;
            @Override
            public void onClick(View v) {
                // go to the previous tab
                fragment = new OverviewTabsCategory();
                selectTab(0);
            }
        });
        right.setVisibility(View.VISIBLE);
        right.setOnClickListener(new View.OnClickListener() {
            Fragment fragment = null;
            @Override
            public void onClick(View v) {
                // go to the next tab
                fragment = new OverviewTabsColor();
                selectTab(2);
            }
        });
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
        stylePieChart(pieData, pieChartView, true);
    }

    public void createColorPieGraph(ArrayList<Integer> counts) {
        //set up buttons
        left.setVisibility(View.VISIBLE);
        left.setOnClickListener(new View.OnClickListener() {
            Fragment fragment = null;
            @Override
            public void onClick(View v) {
                // go to the previous tab
                fragment = new OverviewTabsSeason();
                selectTab(1);
            }
        });
        right.setVisibility(View.VISIBLE);
        right.setOnClickListener(new View.OnClickListener() {
            Fragment fragment = null;
            @Override
            public void onClick(View v) {
                // go to the next tab
                fragment = new OverviewTabsEvent();
                selectTab(3);
            }
        });
        // reference pie chart view+
        PieChartView pieChartView = findViewById(R.id.chart);
        // initialize data for the pie chart
        List<SliceValue> pieData = new ArrayList<>();
        if (colors.size() == 0) {
            colors = counts;
        }
        // assign values to pie slices
        if (counts.size() != 0) {
            pieData.add(new SliceValue(colors.get(0), Color.BLACK));
            pieData.add(new SliceValue(colors.get(1), Color.parseColor("#1B4F72"))); //blue
            pieData.add(new SliceValue(colors.get(2), Color.parseColor("#824709"))); //brown
            pieData.add(new SliceValue(colors.get(3), Color.GRAY));
            pieData.add(new SliceValue(colors.get(4), Color.parseColor("#178409"))); //green
            pieData.add(new SliceValue(colors.get(5), Color.parseColor("#ef912d"))); //orange
            pieData.add(new SliceValue(colors.get(6), Color.parseColor("#ef53c0"))); //pink
            pieData.add(new SliceValue(colors.get(7), Color.parseColor("#a160af"))); //purple
            pieData.add(new SliceValue(colors.get(8), Color.parseColor("#d8130d"))); //red
            pieData.add(new SliceValue(colors.get(9), Color.parseColor("#fffcdd"))); //white
            pieData.add(new SliceValue(colors.get(10), Color.parseColor("#f4e618"))); //yellow
        } else {
            pieData.add(new SliceValue(counts.get(0), Color.BLACK)); //black
            pieData.add(new SliceValue(counts.get(1), Color.parseColor("#1B4F72"))); //blue
            pieData.add(new SliceValue(counts.get(2), Color.parseColor("#824709"))); //brown
            pieData.add(new SliceValue(counts.get(3), Color.GRAY)); //gray
            pieData.add(new SliceValue(counts.get(4), Color.parseColor("#178409"))); //green
            pieData.add(new SliceValue(counts.get(5), Color.parseColor("#001E3B"))); //orange
            pieData.add(new SliceValue(counts.get(6), Color.parseColor("#ef53c0"))); //pink
            pieData.add(new SliceValue(counts.get(7), Color.parseColor("#a160af"))); //purple
            pieData.add(new SliceValue(counts.get(8), Color.parseColor("#d8130d"))); //red
            pieData.add(new SliceValue(counts.get(9), Color.parseColor("#fffcdd"))); //white
            pieData.add(new SliceValue(counts.get(10), Color.parseColor("#f4e618"))); //yellow
        }
        // call function to style pie chart
        stylePieChart(pieData, pieChartView, false);
    }

    public void createEventPieGraph(ArrayList<Integer> counts) {
        //set up buttons
        left.setVisibility(View.VISIBLE);
        left.setOnClickListener(new View.OnClickListener() {
            Fragment fragment = null;
            @Override
            public void onClick(View v) {
                // go to the previous tab
                fragment = new OverviewTabsColor();
                selectTab(2);
            }
        });
        right.setVisibility(View.GONE);
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
        stylePieChart(pieData, pieChartView, true);
    }

    // styles the pie charts
    public void stylePieChart(List<SliceValue> pieData, PieChartView pView, boolean displayLabels) {
        PieChartData pieChartData = new PieChartData(pieData);
        if(displayLabels) {
            pieChartData.setHasLabels(true).setValueLabelTextSize(18);
        }
        pieChartData.setValueLabelBackgroundEnabled(false);
        pieChartData.setHasCenterCircle(true).setCenterCircleScale((float) .35);
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
                    Toast.makeText(Activity_Overview.this, "Gathering info...", Toast.LENGTH_SHORT).show();
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
        for (final String season : GlobalVariables.seasons) {
            final ArrayList<Integer> tempTotal = new ArrayList<>();
            for (final String cat : GlobalVariables.categories) {
                DataTransferService.getItemsBySeason(cat, season, new OnGetDataListener() {
                    @Override
                    public void onStart() {
                        Toast.makeText(Activity_Overview.this, "Gathering info...", Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onSuccess(QuerySnapshot data) {
                        int count = 0;
                        for (DocumentSnapshot document : data) {
                            count++;
                        }
                        tempTotal.add(count);
                        if (tempTotal.size() == 6) {
                            int sum = 0;
                            for (int i : tempTotal) {
                                sum += i;
                            }
                            totals.add(sum);
                        }
                        if (totals.size() == 4) {
                            Log.d(TAG, "Seasons Totals: " + totals);
                            createSeasonPieGraph(totals);
                        }
                    }
                    @Override
                    public void onFailed(Exception databaseError) {
                        Log.w(TAG, databaseError);
                    }
                });
            }
        }
    }

    public void getEventData(){
        final ArrayList<Integer> totals = new ArrayList<>();
        for (final String event : GlobalVariables.events) {
            final ArrayList<Integer> tempTotal = new ArrayList<>();
            for (final String cat : GlobalVariables.categories) {
                DataTransferService.getItemsByEvent(cat, event, new OnGetDataListener() {
                    @Override
                    public void onStart() {
                        Toast.makeText(Activity_Overview.this, "Gathering info...", Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onSuccess(QuerySnapshot data) {
                        int count = 0;
                        for (DocumentSnapshot document : data) {
                            count++;
                        }
                        tempTotal.add(count);
                        if (tempTotal.size() == 6) {
                            int sum = 0;
                            for (int i : tempTotal) {
                                sum += i;
                            }
                            totals.add(sum);
                        }
                        if (totals.size() == 6) {
                            Log.d(TAG, "Events Totals: " + totals);
                            createEventPieGraph(totals);
                        }
                    }
                    @Override
                    public void onFailed(Exception databaseError) {
                        Log.w(TAG, databaseError);
                    }
                });
            }
        }
    }

    public void getColorData(){
        final ArrayList<Integer> totals = new ArrayList<>();
        for (final String color : GlobalVariables.colors) {
            final ArrayList<Integer> tempTotal = new ArrayList<>();
            for (final String cat : GlobalVariables.categories) {
                DataTransferService.getItemsByColor(cat, color, new OnGetDataListener() {
                    @Override
                    public void onStart() {
                        Toast.makeText(Activity_Overview.this, "Gathering info...", Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onSuccess(QuerySnapshot data) {
                        int count = 0;
                        for (DocumentSnapshot document : data) {
                            count++;
                        }
                        tempTotal.add(count);
                        if (tempTotal.size() == 6) {
                            try {
                                Thread.sleep(100);
                            } catch(InterruptedException e) {
                                Log.d("ERROR", "Got interrupted!");
                            }
                            int sum = 0;
                            for (int i : tempTotal) {
                                sum += i;
                            }
                            totals.add(sum);
                            Log.d(TAG, color + " ==> " + sum);
                        }

                        if (totals.size() == 11) {
                            Log.d(TAG, "Color Totals: " + totals);
                            createColorPieGraph(totals);
                        }
                    }
                    @Override
                    public void onFailed(Exception databaseError) {
                        Log.w(TAG, databaseError);
                    }
                });
            }
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

    // selects the tab with the index that is passed to the function
    public void selectTab(Integer index) {
        TabLayout tabLayout = (TabLayout) findViewById(R.id.simpleTabLayout);
        TabLayout.Tab tab = tabLayout.getTabAt(index);
        tab.select();
    }

}
