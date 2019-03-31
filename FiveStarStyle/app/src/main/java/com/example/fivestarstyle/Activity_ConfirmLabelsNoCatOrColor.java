package com.example.fivestarstyle;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class Activity_ConfirmLabelsNoCatOrColor extends AppCompatActivity {
    private final static String TAG = "Activity_ConfirmLabelsAll";
    private Button right;
    private Button left;
    String category;
    String color;
    LabelsObject labelsObj;
    Dialog myDialog;
    int seasonFlag = 0;
    int eventFlag = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_labels_all);

        Intent i = getIntent();
        labelsObj = (LabelsObject) i.getSerializableExtra("labelsObj");
        category = labelsObj.labelGetCategory();
        color = labelsObj.labelGetColor();
        Log.d("LABELS-CATEGORY", category);
        Log.d("LABELS-COLOR", color);

        right = (Button) findViewById(R.id.right_btn);
        left = (Button) findViewById(R.id.left_btn);
        left.setVisibility(View.GONE);
        right.setOnClickListener(new View.OnClickListener() {
            Fragment fragment = null;
            @Override
            public void onClick(View v) {
                if(seasonFlag >= 1) {
                    fragment = new LabelsTabsEvent();
                    selectTab(1);
                } else {
                    Toast.makeText(Activity_ConfirmLabelsNoCatOrColor.this, "Please select at least one season.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        createTabs();
        myDialog = new Dialog(this);
    }

    public void createTabs() {
        FrameLayout simpleFrameLayout;
        TabLayout tabLayout;

        // get reference of FrameLayout and TabLayout
        simpleFrameLayout = (FrameLayout) findViewById(R.id.simpleFrameLayout);
        tabLayout = (TabLayout) findViewById(R.id.simpleTabLayout);
        // create first tab
        TabLayout.Tab firstTab = tabLayout.newTab();
        firstTab.setText("Season");
        tabLayout.addTab(firstTab);
        // create second tab
        TabLayout.Tab secondTab = tabLayout.newTab();
        secondTab.setText("Event");
        tabLayout.addTab(secondTab);
        // choose tab that is selected
        Fragment fragment = new LabelsTabsSeason();
        firstTab.select();
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.simpleFrameLayout, fragment);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft.commit();

        // perform setOnTabSelectedListener event on TabLayout
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Fragment fragment = new LabelsTabsSeason();
                switch (tab.getPosition()) {
                    case 0:
                        fragment = new LabelsTabsSeason();
                        seasonTab();
                        break;
                    case 1:
                        fragment = new LabelsTabsEvent();
                        eventTab();
                        break;
                }
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

    public void seasonTab() {
        right.setText("Next");
        right.setOnClickListener(new View.OnClickListener() {
            Fragment fragment = null;
            @Override
            public void onClick(View v) {
                if(seasonFlag >= 1) {
                    fragment = new LabelsTabsEvent();
                    selectTab(1);
                } else {
                    Toast.makeText(Activity_ConfirmLabelsNoCatOrColor.this, "Please select at least one season.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        left.setVisibility(View.GONE);
    }

    public void eventTab() {
        right.setText("Add Item");
        right.setOnClickListener(new View.OnClickListener() {
            //            Fragment fragment = null;
            @Override
            public void onClick(View v) {
                if(eventFlag >= 1) {
                    showPopup(v);
                } else {
                    Toast.makeText(Activity_ConfirmLabelsNoCatOrColor.this, "Please select at least one event.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        left.setVisibility(View.VISIBLE);
        left.setOnClickListener(new View.OnClickListener() {
            Fragment fragment = null;
            @Override
            public void onClick(View v) {
                fragment = new LabelsTabsSeason();
                selectTab(0);
            }
        });
    }

    public void onCheckboxClicked(View view) {
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();

        // Check which checkbox was clicked
        switch(view.getId()) {
            // events
            case R.id.category_work:
                if (checked) {
                    // add work event label
                    labelsObj.labelAddEvent("work");
                    eventFlag++;
                } else {
                    // remove work event label
                    labelsObj.labelRemoveEvent("work");
                    eventFlag--;
                }
                break;
            case R.id.category_bar:
                if (checked) {
                    // add bar event label
                    labelsObj.labelAddEvent("bar");
                    eventFlag++;
                } else {
                    // remove bar event label
                    labelsObj.labelRemoveEvent("bar");
                    eventFlag--;
                }
                break;
            case R.id.category_gym:
                if (checked) {
                    // add gym event label
                    labelsObj.labelAddEvent("gym");
                    eventFlag++;
                } else {
                    // remove gym event label
                    labelsObj.labelRemoveEvent("gym");
                    eventFlag--;
                }
                break;
            case R.id.category_casual:
                if (checked) {
                    // add casual event label
                    labelsObj.labelAddEvent("casual");
                    eventFlag++;
                } else {
                    // remove casual event label
                    labelsObj.labelRemoveEvent("casual");
                    eventFlag--;
                }
                break;
            case R.id.category_cocktail:
                if (checked) {
                    // add cocktail event label
                    labelsObj.labelAddEvent("cocktail");
                    eventFlag++;
                } else {
                    // remove cocktail event label
                    labelsObj.labelRemoveEvent("cocktail");
                    eventFlag--;
                }
                break;
            case R.id.category_formal:
                if (checked) {
                    // add formal event label
                    labelsObj.labelAddEvent("formal");
                    eventFlag++;
                } else {
                    // remove formal event label
                    labelsObj.labelRemoveEvent("formal");
                    eventFlag--;
                }
                break;
            // seasons
            case R.id.season_spring:
                if (checked) {
                    // add spring season label
                    labelsObj.labelAddSeason("spring");
                    seasonFlag++;
                } else {
                    labelsObj.labelRemoveSeason("spring");
                    seasonFlag--;
                }
                break;
            case R.id.season_summer:
                if (checked) {
                    // add summer season label
                    labelsObj.labelAddSeason("summer");
                    seasonFlag++;
                } else {
                    // remove summer season label
                    labelsObj.labelRemoveSeason("summer");
                    seasonFlag--;
                }
                break;
            case R.id.season_fall:
                if (checked) {
                    // add fall season label
                    labelsObj.labelAddSeason("fall");
                    seasonFlag++;
                } else {
                    // remove fall season label
                    labelsObj.labelRemoveSeason("fall");
                    seasonFlag--;
                }
                break;
            case R.id.season_winter:
                if (checked) {
                    // add winter season label
                    labelsObj.labelAddSeason("winter");
                    seasonFlag++;
                } else {
                    // remove winter season label
                    labelsObj.labelRemoveSeason("winter");
                    seasonFlag--;
                }
                break;
            default:
                // add to casual?
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_options,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.home__menu_option) {
            Intent homeIntent = new Intent(this, Activity_Main.class);
            this.startActivity(homeIntent);
        }
        else if(item.getItemId() == R.id.closet_menu_option) {
            Intent overviewIntent = new Intent(this, Activity_ViewMyCloset.class);
            this.startActivity(overviewIntent);
        }
        else if(item.getItemId() == R.id.overview_menu_option) {
            Intent overviewIntent = new Intent(this, Activity_Overview.class);
            this.startActivity(overviewIntent);
        }
        else if(item.getItemId() == R.id.choosemyoutfit_menu_option) {
            Intent overviewIntent = new Intent(this, Activity_ChooseMyOutfit.class);
            this.startActivity(overviewIntent);
        }
        else if(item.getItemId() == R.id.settings_menu_option) {
            Intent overviewIntent = new Intent(this, Activity_Settings.class);
            this.startActivity(overviewIntent);
        }
        else if(item.getItemId() == R.id.logout_menu_option) {
            FirebaseAuth.getInstance().signOut();
            Intent overviewIntent = new Intent(this, Activity_Login.class);
            this.startActivity(overviewIntent);
        }
        else {
            return super.onOptionsItemSelected(item);
        }
        return true;
    }

    public void selectTab(Integer index) {
        TabLayout tabLayout = (TabLayout) findViewById(R.id.simpleTabLayout);
        TabLayout.Tab tab = tabLayout.getTabAt(index);
        tab.select();
    }

    public void showPopup(View v) {
        TextView txtCategory;
        TextView txtColor;
        TextView txtSeasons;
        TextView txtEvents;
        Button btnRestart;
        Button btnConfirm;

        myDialog.setContentView(R.layout.labels_review_popup);
        txtCategory = (TextView) myDialog.findViewById(R.id.txtCategory);
        txtColor = (TextView) myDialog.findViewById(R.id.txtColor);
        txtSeasons = (TextView) myDialog.findViewById(R.id.txtSeasons);
        txtEvents = (TextView) myDialog.findViewById(R.id.txtEvents);
        btnRestart = (Button) myDialog.findViewById(R.id.btnRestart);
        btnConfirm = (Button) myDialog.findViewById(R.id.btnConfirm);

        btnRestart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent restartIntent = new Intent(Activity_ConfirmLabelsNoCatOrColor.this, Activity_ConfirmLabelsAll.class);
                LabelsObject emptyObj = new LabelsObject();
                restartIntent.putExtra("labelsObj", emptyObj);
                startActivity(restartIntent);
            }
        });

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean success = DataTransferService.addItem(labelsObj);
                Log.d(TAG, "Add item =>" + labelsObj);
                if (success) {
//                    new Intent with prompt to add more or return to closet
                } else {
//                    "error try again" redirect to add page
                }
            }
        });

        txtCategory.setText("Category: " + labelsObj.labelGetCategory().substring(0,1).toUpperCase() + labelsObj.labelGetCategory().substring(1));
        txtColor.setText("Color: " + labelsObj.labelGetColor().substring(0,1).toUpperCase() + labelsObj.labelGetColor().substring(1));
        StringBuilder seasons = new StringBuilder();
        if(labelsObj.labelGetSeasons().size() > 1) {
            seasons.append("Seasons: ");
        } else {
            seasons.append("Season: ");
        }
        int i;
        String season;
        for(i = 0; i < labelsObj.labelGetSeasons().size(); i++) {
            season = labelsObj.labelGetSeasons().get(i).substring(0,1).toUpperCase() + labelsObj.labelGetSeasons().get(i).substring(1);
            seasons.append(season);
            if(i < labelsObj.labelGetSeasons().size() - 1) {
                seasons.append(", ");
            }
        }
        txtSeasons.setText(String.valueOf(seasons));
        StringBuilder events = new StringBuilder();
        if(labelsObj.labelGetEvents().size() > 1) {
            events.append("Events: ");
        } else {
            events.append("Event: ");
        }
        String event;
        for(i = 0; i < labelsObj.labelGetEvents().size(); i++) {
            event = labelsObj.labelGetEvents().get(i).substring(0,1).toUpperCase() + labelsObj.labelGetEvents().get(i).substring(1);
            events.append(event);
            if(i < labelsObj.labelGetEvents().size() - 1) {
                events.append(", ");
            }
        }
        txtEvents.setText(String.valueOf(events));
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog.show();
    }

}
