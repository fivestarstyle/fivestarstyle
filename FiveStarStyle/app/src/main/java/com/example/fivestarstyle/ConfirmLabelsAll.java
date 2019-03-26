package com.example.fivestarstyle;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class ConfirmLabelsAll extends AppCompatActivity {
    private final static String TAG = "ConfirmLabelsAll";
    private Button right;
    private Button left;
    String category;
    String color;
    LabelsObject labelsObj;
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
                fragment = new LabelsTabsSeason();
                selectTab(1);
            }
        });
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
                fragment = new LabelsTabsSeason();
                selectTab(1);
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
                fragment = new LabelsTabsSeason();
                selectTab(2);
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
                fragment = new LabelsTabsEvent();
                selectTab(3);
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
                DataTransferService.addItem(labelsObj);
                Log.d(TAG, "Add item =>" + labelsObj);
                //add method to
                // if (success) new Intent with prompt to add more or return to closet
                // else "error try again" redirect to add page
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
                } else {
                    // remove work event label
                    labelsObj.labelRemoveEvent("work");
                }
                break;
            case R.id.category_bar:
                if (checked) {
                    // add bar event label
                    labelsObj.labelAddEvent("bar");
                } else {
                    // remove bar event label
                    labelsObj.labelRemoveEvent("bar");
                }
                break;
            case R.id.category_gym:
                if (checked) {
                    // add gym event label
                    labelsObj.labelAddEvent("gym");
                } else {
                    // remove gym event label
                    labelsObj.labelRemoveEvent("gym");
                }
                break;
            case R.id.category_casual:
                if (checked) {
                    // add casual event label
                    labelsObj.labelAddEvent("casual");
                } else {
                    // remove casual event label
                    labelsObj.labelRemoveEvent("casual");
                }
                break;
            case R.id.category_cocktail:
                if (checked) {
                    // add cocktail event label
                    labelsObj.labelAddEvent("cocktail");
                } else {
                    // remove cocktail event label
                    labelsObj.labelRemoveEvent("cocktail");
                }
                break;
            case R.id.category_formal:
                if (checked) {
                    // add formal event label
                    labelsObj.labelAddEvent("formal");
                } else {
                    // remove formal event label
                    labelsObj.labelRemoveEvent("formal");
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
                };
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
                };
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
                };
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
                };
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
                };
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
                };
                break;
            // seasons
            case R.id.season_spring:
                if (checked) {
                    // add spring season label
                    labelsObj.labelAddSeason("spring");
                } else {
                    labelsObj.labelRemoveSeason("spring");
                }
                break;
            case R.id.season_summer:
                if (checked) {
                    // add summer season label
                    labelsObj.labelAddSeason("summer");
                } else {
                    // remove summer season label
                    labelsObj.labelRemoveSeason("summer");
                }
                break;
            case R.id.season_fall:
                if (checked) {
                    // add fall season label
                    labelsObj.labelAddSeason("fall");
                } else {
                    // remove fall season label
                    labelsObj.labelRemoveSeason("fall");
                }
                break;
            case R.id.season_winter:
                if (checked) {
                    // add winter season label
                    labelsObj.labelAddSeason("winter");
                } else {
                    // remove winter season label
                    labelsObj.labelRemoveSeason("winter");
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

    public void selectTab(Integer index) {
        TabLayout tabLayout = (TabLayout) findViewById(R.id.simpleTabLayout);
        TabLayout.Tab tab = tabLayout.getTabAt(index);
        tab.select();
    }

}
