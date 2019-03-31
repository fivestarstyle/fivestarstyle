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

public class Activity_ConfirmLabelsAll extends AppCompatActivity {
    private final static String TAG = "Activity_ConfirmLabelsAll";
    private Button right;
    private Button left;
    String category;
    String color;
    LabelsObject labelsObj;
    Dialog myDialog;
    int catFlag = 0;
    int colorFlag = 0;
    int seasonFlag = 0;
    int eventFlag = 0;


//    Bitmap image;
//    byte[] byteArray;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_labels_all);

        Intent i = getIntent();
        labelsObj = (LabelsObject) i.getSerializableExtra("labelsObj");
//        byteArray = i.getByteArrayExtra("image");
//        image = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
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
                if(catFlag == 1) {
                    fragment = new LabelsTabsSeason();
                    selectTab(1);
                } else {
                    Toast.makeText(Activity_ConfirmLabelsAll.this, "Please select one category.", Toast.LENGTH_SHORT).show();
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
        firstTab.setText("Category");
        tabLayout.addTab(firstTab);
        // create second tab
        TabLayout.Tab secondTab = tabLayout.newTab();
        secondTab.setText("Color");
        tabLayout.addTab(secondTab);
        // create third tab
        TabLayout.Tab thirdTab = tabLayout.newTab();
        thirdTab.setText("Season");
        tabLayout.addTab(thirdTab);
        // create fourth tab
        TabLayout.Tab fourthTab = tabLayout.newTab();
        fourthTab.setText("Event");
        tabLayout.addTab(fourthTab);
        // choose tab that is selected
        Fragment fragment = new LabelsTabsCategory();
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
                Fragment fragment = new LabelsTabsCategory();
                switch (tab.getPosition()) {
                    case 0:
                        fragment = new LabelsTabsCategory();
                        categoryTab();
                        break;
                    case 1:
                        fragment = new LabelsTabsColor();
                        colorTab();
                        break;
                    case 2:
                        fragment = new LabelsTabsSeason();
                        seasonTab();
                        break;
                    case 3:
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

    public void categoryTab() {
        right.setText("Next");
        right.setOnClickListener(new View.OnClickListener() {
            Fragment fragment = null;
            @Override
            public void onClick(View v) {
                if(catFlag == 1) {
                    fragment = new LabelsTabsSeason();
                    selectTab(1);
                } else {
                    Toast.makeText(Activity_ConfirmLabelsAll.this, "Please select one category.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        left.setVisibility(View.GONE);
    }

    public void colorTab() {
        right.setText("Next");
        right.setOnClickListener(new View.OnClickListener() {
            Fragment fragment = null;
            @Override
            public void onClick(View v) {
                if(colorFlag == 1) {
                    fragment = new LabelsTabsSeason();
                    selectTab(2);
                } else {
                    Toast.makeText(Activity_ConfirmLabelsAll.this, "Please select one color.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        left.setVisibility(View.VISIBLE);
        left.setOnClickListener(new View.OnClickListener() {
            Fragment fragment = null;
            @Override
            public void onClick(View v) {
                fragment = new LabelsTabsCategory();
                selectTab(0);
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
                    selectTab(3);
                } else {
                    Toast.makeText(Activity_ConfirmLabelsAll.this, "Please select at least one season.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        left.setVisibility(View.VISIBLE);
        left.setOnClickListener(new View.OnClickListener() {
            Fragment fragment = null;
            @Override
            public void onClick(View v) {
                fragment = new LabelsTabsColor();
                selectTab(1);
            }
        });
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
                    Toast.makeText(Activity_ConfirmLabelsAll.this, "Please select at lease one event.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        left.setVisibility(View.VISIBLE);
        left.setOnClickListener(new View.OnClickListener() {
            Fragment fragment = null;
            @Override
            public void onClick(View v) {
                fragment = new LabelsTabsSeason();
                selectTab(2);
            }
        });
    }

    public void onCheckboxClicked(View view) {
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();

        // categories
        View checkboxTops = (CheckBox) findViewById(R.id.category_top);
        View checkboxBottoms = (CheckBox) findViewById(R.id.category_bottom);
        View checkboxDress = (CheckBox) findViewById(R.id.category_dress);
        View checkboxOuterwear = (CheckBox) findViewById(R.id.category_outerwear);
        View checkboxShoes = (CheckBox) findViewById(R.id.category_shoe);
        View checkboxAccessories = (CheckBox) findViewById(R.id.category_accessories);
        // colors
        View checkboxRed = (CheckBox) findViewById(R.id.color_red);
        View checkboxPink = (CheckBox) findViewById(R.id.color_pink);
        View checkboxOrange = (CheckBox) findViewById(R.id.color_orange);
        View checkboxYellow = (CheckBox) findViewById(R.id.color_yellow);
        View checkboxGreen = (CheckBox) findViewById(R.id.color_green);
        View checkboxBlue = (CheckBox) findViewById(R.id.color_blue);
        View checkboxPurple = (CheckBox) findViewById(R.id.color_purple);
        View checkboxBlack = (CheckBox) findViewById(R.id.color_black);
        View checkboxBrown = (CheckBox) findViewById(R.id.color_brown);
        View checkboxWhite = (CheckBox) findViewById(R.id.color_white);
        View checkboxGray = (CheckBox) findViewById(R.id.color_gray);

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
            // categories
            case R.id.category_top:
                if (checked) {
                    // enforce only selecting one
                    ((CheckBox) checkboxBottoms).setChecked(false);
                    ((CheckBox) checkboxDress).setChecked(false);
                    ((CheckBox) checkboxOuterwear).setChecked(false);
                    ((CheckBox) checkboxShoes).setChecked(false);
                    ((CheckBox) checkboxAccessories).setChecked(false);
                    // set top category label
                    labelsObj.labelSetCategory("top");
                    catFlag = 1;
                } else {
                    catFlag = 0;
                }
                break;
            case R.id.category_bottom:
                if (checked) {
                    // enforce only selecting one
                    ((CheckBox) checkboxTops).setChecked(false);
                    ((CheckBox) checkboxDress).setChecked(false);
                    ((CheckBox) checkboxOuterwear).setChecked(false);
                    ((CheckBox) checkboxShoes).setChecked(false);
                    ((CheckBox) checkboxAccessories).setChecked(false);
                    // set bottom category label
                    labelsObj.labelSetCategory("bottom");
                    catFlag = 1;
                } else {
                    catFlag = 0;
                }
                break;
            case R.id.category_dress:
                if (checked) {
                    // enforce only selecting one
                    ((CheckBox) checkboxBottoms).setChecked(false);
                    ((CheckBox) checkboxTops).setChecked(false);
                    ((CheckBox) checkboxOuterwear).setChecked(false);
                    ((CheckBox) checkboxShoes).setChecked(false);
                    ((CheckBox) checkboxAccessories).setChecked(false);
                    // set dress_or_suit category label
                    labelsObj.labelSetCategory("dress_or_suit");
                    catFlag = 1;
                } else {
                    catFlag = 0;
                }
                break;
            case R.id.category_outerwear:
                if (checked) {
                    // enforce only selecting one
                    ((CheckBox) checkboxBottoms).setChecked(false);
                    ((CheckBox) checkboxDress).setChecked(false);
                    ((CheckBox) checkboxTops).setChecked(false);
                    ((CheckBox) checkboxShoes).setChecked(false);
                    ((CheckBox) checkboxAccessories).setChecked(false);
                    // set outwear category label
                    labelsObj.labelSetCategory("outerwear");
                    catFlag = 1;
                } else {
                    catFlag = 0;
                }
                break;
            case R.id.category_shoe:
                if (checked) {
                    // enforce only selecting one
                    ((CheckBox) checkboxBottoms).setChecked(false);
                    ((CheckBox) checkboxDress).setChecked(false);
                    ((CheckBox) checkboxOuterwear).setChecked(false);
                    ((CheckBox) checkboxTops).setChecked(false);
                    ((CheckBox) checkboxAccessories).setChecked(false);
                    // set shoe category label
                    labelsObj.labelSetCategory("shoe");
                    catFlag = 1;
                } else {
                    catFlag = 0;
                }
                break;
            case R.id.category_accessories:
                if (checked) {
                    // enforce only selecting one
                    ((CheckBox) checkboxBottoms).setChecked(false);
                    ((CheckBox) checkboxDress).setChecked(false);
                    ((CheckBox) checkboxOuterwear).setChecked(false);
                    ((CheckBox) checkboxShoes).setChecked(false);
                    ((CheckBox) checkboxTops).setChecked(false);
                    // set accessories category label
                    labelsObj.labelSetCategory("accessories");
                    catFlag = 1;
                } else {
                    catFlag = 0;
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
            // colors
            case R.id.color_red:
                if(checked) {
                    // enforce only selecting one
                    ((CheckBox) checkboxPink).setChecked(false);
                    ((CheckBox) checkboxOrange).setChecked(false);
                    ((CheckBox) checkboxYellow).setChecked(false);
                    ((CheckBox) checkboxGreen).setChecked(false);
                    ((CheckBox) checkboxBlue).setChecked(false);
                    ((CheckBox) checkboxPurple).setChecked(false);
                    ((CheckBox) checkboxBlack).setChecked(false);
                    ((CheckBox) checkboxBrown).setChecked(false);
                    ((CheckBox) checkboxWhite).setChecked(false);
                    ((CheckBox) checkboxGray).setChecked(false);
                    // add red color label
                    labelsObj.labelSetColor("red");
                    colorFlag = 1;
                } else {
                    colorFlag = 0;
                }
                break;
            case R.id.color_pink:
                if(checked) {
                    // enforce only selecting one
                    ((CheckBox) checkboxRed).setChecked(false);
                    ((CheckBox) checkboxOrange).setChecked(false);
                    ((CheckBox) checkboxYellow).setChecked(false);
                    ((CheckBox) checkboxGreen).setChecked(false);
                    ((CheckBox) checkboxBlue).setChecked(false);
                    ((CheckBox) checkboxPurple).setChecked(false);
                    ((CheckBox) checkboxBlack).setChecked(false);
                    ((CheckBox) checkboxBrown).setChecked(false);
                    ((CheckBox) checkboxWhite).setChecked(false);
                    ((CheckBox) checkboxGray).setChecked(false);
                    // add pink color label
                    labelsObj.labelSetColor("pink");
                    colorFlag = 1;
                } else {
                    colorFlag = 0;
                }
                break;
            case R.id.color_orange:
                if(checked) {
                    // enforce only selecting one
                    ((CheckBox) checkboxPink).setChecked(false);
                    ((CheckBox) checkboxRed).setChecked(false);
                    ((CheckBox) checkboxYellow).setChecked(false);
                    ((CheckBox) checkboxGreen).setChecked(false);
                    ((CheckBox) checkboxBlue).setChecked(false);
                    ((CheckBox) checkboxPurple).setChecked(false);
                    ((CheckBox) checkboxBlack).setChecked(false);
                    ((CheckBox) checkboxBrown).setChecked(false);
                    ((CheckBox) checkboxWhite).setChecked(false);
                    ((CheckBox) checkboxGray).setChecked(false);
                    // add orange color label
                    labelsObj.labelSetColor("orange");
                    colorFlag = 1;
                } else {
                    colorFlag = 0;
                }
                break;
            case R.id.color_yellow:
                if(checked) {
                    // enforce only selecting one
                    ((CheckBox) checkboxPink).setChecked(false);
                    ((CheckBox) checkboxOrange).setChecked(false);
                    ((CheckBox) checkboxRed).setChecked(false);
                    ((CheckBox) checkboxGreen).setChecked(false);
                    ((CheckBox) checkboxBlue).setChecked(false);
                    ((CheckBox) checkboxPurple).setChecked(false);
                    ((CheckBox) checkboxBlack).setChecked(false);
                    ((CheckBox) checkboxBrown).setChecked(false);
                    ((CheckBox) checkboxWhite).setChecked(false);
                    ((CheckBox) checkboxGray).setChecked(false);
                    // add yellow color label
                    labelsObj.labelSetColor("yellow");
                    colorFlag = 1;
                } else {
                    colorFlag = 0;
                }
                break;
            case R.id.color_green:
                if(checked) {
                    // enforce only selecting one
                    ((CheckBox) checkboxPink).setChecked(false);
                    ((CheckBox) checkboxOrange).setChecked(false);
                    ((CheckBox) checkboxYellow).setChecked(false);
                    ((CheckBox) checkboxRed).setChecked(false);
                    ((CheckBox) checkboxBlue).setChecked(false);
                    ((CheckBox) checkboxPurple).setChecked(false);
                    ((CheckBox) checkboxBlack).setChecked(false);
                    ((CheckBox) checkboxBrown).setChecked(false);
                    ((CheckBox) checkboxWhite).setChecked(false);
                    ((CheckBox) checkboxGray).setChecked(false);
                    // add green color label
                    labelsObj.labelSetColor("green");
                    colorFlag = 1;
                } else {
                    colorFlag = 0;
                }
                break;
            case R.id.color_blue:
                if(checked) {
                    // enforce only selecting one
                    ((CheckBox) checkboxPink).setChecked(false);
                    ((CheckBox) checkboxOrange).setChecked(false);
                    ((CheckBox) checkboxYellow).setChecked(false);
                    ((CheckBox) checkboxGreen).setChecked(false);
                    ((CheckBox) checkboxRed).setChecked(false);
                    ((CheckBox) checkboxPurple).setChecked(false);
                    ((CheckBox) checkboxBlack).setChecked(false);
                    ((CheckBox) checkboxBrown).setChecked(false);
                    ((CheckBox) checkboxWhite).setChecked(false);
                    ((CheckBox) checkboxGray).setChecked(false);
                    // add blue color label
                    labelsObj.labelSetColor("blue");
                    colorFlag = 1;
                } else {
                    colorFlag = 0;
                }
                break;
            case R.id.color_purple:
                if(checked) {
                    // enforce only selecting one
                    ((CheckBox) checkboxPink).setChecked(false);
                    ((CheckBox) checkboxOrange).setChecked(false);
                    ((CheckBox) checkboxYellow).setChecked(false);
                    ((CheckBox) checkboxGreen).setChecked(false);
                    ((CheckBox) checkboxBlue).setChecked(false);
                    ((CheckBox) checkboxRed).setChecked(false);
                    ((CheckBox) checkboxBlack).setChecked(false);
                    ((CheckBox) checkboxBrown).setChecked(false);
                    ((CheckBox) checkboxWhite).setChecked(false);
                    ((CheckBox) checkboxGray).setChecked(false);
                    // add purple color label
                    labelsObj.labelSetColor("purple");
                    colorFlag = 1;
                } else {
                    colorFlag = 0;
                }
                break;
            case R.id.color_black:
                if(checked) {
                    // enforce only selecting one
                    ((CheckBox) checkboxPink).setChecked(false);
                    ((CheckBox) checkboxOrange).setChecked(false);
                    ((CheckBox) checkboxYellow).setChecked(false);
                    ((CheckBox) checkboxGreen).setChecked(false);
                    ((CheckBox) checkboxBlue).setChecked(false);
                    ((CheckBox) checkboxPurple).setChecked(false);
                    ((CheckBox) checkboxRed).setChecked(false);
                    ((CheckBox) checkboxBrown).setChecked(false);
                    ((CheckBox) checkboxWhite).setChecked(false);
                    ((CheckBox) checkboxGray).setChecked(false);
                    // add black color label
                    labelsObj.labelSetColor("black");
                    colorFlag = 1;
                } else {
                    colorFlag = 0;
                }
                break;
            case R.id.color_brown:
                if(checked) {
                    // enforce only selecting one
                    ((CheckBox) checkboxPink).setChecked(false);
                    ((CheckBox) checkboxOrange).setChecked(false);
                    ((CheckBox) checkboxYellow).setChecked(false);
                    ((CheckBox) checkboxGreen).setChecked(false);
                    ((CheckBox) checkboxBlue).setChecked(false);
                    ((CheckBox) checkboxPurple).setChecked(false);
                    ((CheckBox) checkboxBlack).setChecked(false);
                    ((CheckBox) checkboxRed).setChecked(false);
                    ((CheckBox) checkboxWhite).setChecked(false);
                    ((CheckBox) checkboxGray).setChecked(false);
                    // add brown color label
                    labelsObj.labelSetColor("brown");
                    colorFlag = 1;
                } else {
                    colorFlag = 0;
                }
                break;
            case R.id.color_white:
                if(checked) {
                    // enforce only selecting one
                    ((CheckBox) checkboxPink).setChecked(false);
                    ((CheckBox) checkboxOrange).setChecked(false);
                    ((CheckBox) checkboxYellow).setChecked(false);
                    ((CheckBox) checkboxGreen).setChecked(false);
                    ((CheckBox) checkboxBlue).setChecked(false);
                    ((CheckBox) checkboxPurple).setChecked(false);
                    ((CheckBox) checkboxBlack).setChecked(false);
                    ((CheckBox) checkboxBrown).setChecked(false);
                    ((CheckBox) checkboxRed).setChecked(false);
                    ((CheckBox) checkboxGray).setChecked(false);
                    // add white color label
                    labelsObj.labelSetColor("white");
                    colorFlag = 1;
                } else {
                    colorFlag = 0;
                }
                break;
            case R.id.color_gray:
                if(checked) {
                    // enforce only selecting one
                    ((CheckBox) checkboxPink).setChecked(false);
                    ((CheckBox) checkboxOrange).setChecked(false);
                    ((CheckBox) checkboxYellow).setChecked(false);
                    ((CheckBox) checkboxGreen).setChecked(false);
                    ((CheckBox) checkboxBlue).setChecked(false);
                    ((CheckBox) checkboxPurple).setChecked(false);
                    ((CheckBox) checkboxBlack).setChecked(false);
                    ((CheckBox) checkboxBrown).setChecked(false);
                    ((CheckBox) checkboxWhite).setChecked(false);
                    ((CheckBox) checkboxRed).setChecked(false);
                    // add gray color label
                    labelsObj.labelSetColor("gray");
                    colorFlag = 1;
                } else {
                    colorFlag = 0;
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
                Intent restartIntent = new Intent(Activity_ConfirmLabelsAll.this, Activity_ConfirmLabelsAll.class);
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
                    String msg = "Success! Item added to closet.";
                    Intent confirmIntent = new Intent(Activity_ConfirmLabelsAll.this, Activity_Main.class);
                    confirmIntent.putExtra("msg", msg);
                    startActivity(confirmIntent);
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
