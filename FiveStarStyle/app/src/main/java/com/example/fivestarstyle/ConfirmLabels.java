package com.example.fivestarstyle;

import android.content.Intent;
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

public class ConfirmLabels extends AppCompatActivity {
    private final static String TAG = "ConfirmLabels";
    private Button right;
    private Button left;
    String category;
    String color;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_labels);
        Intent i = getIntent();
        LabelsObject labelsObj = (LabelsObject) i.getSerializableExtra("labelsObj");
        category = labelsObj.labelGetCategory();
        color = labelsObj.labelGetColor();
        Log.d("LABELS-CATEGORY", category);
        Log.d("LABELS-COLOR", color);

        right = (Button) findViewById(R.id.right_btn);
        left = (Button) findViewById(R.id.left_btn);

        if( (!category.equals("none") && category.length() != 0) ||
                (!color.equals("none") && color.length() != 0) ) {
            right.setOnClickListener(new View.OnClickListener() {
                Fragment fragment = null;
                @Override
                public void onClick(View v) {
                    fragment = new LabelsTabsColor();
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
        else {

            left.setVisibility(View.GONE);
            right.setOnClickListener(new View.OnClickListener() {
                Fragment fragment = null;
                @Override
                public void onClick(View v) {
                    fragment = new LabelsTabsSeason();
                    selectTab(1);
                }
            });
        }
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
        Fragment fragment = null;
        if( (!category.equals("none") && category.length() != 0) ||
                (!color.equals("none") && color.length() != 0) ) {
            fragment = new LabelsTabsSeason();
            thirdTab.select();
        }
        else {
            fragment = new LabelsTabsCategory();
            firstTab.select();
        }
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.simpleFrameLayout, fragment);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft.commit();

        // perform setOnTabSelectedListener event on TabLayout
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Fragment fragment;
                // get the current selected tab's position and replace the fragment accordingly
                if( (!category.equals("none") && category.length() != 0) ||
                        (!color.equals("none") && color.length() != 0) ) {
                            fragment = new LabelsTabsSeason();
                }
                else {
                    fragment = new LabelsTabsCategory();
                }
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
                fragment = new LabelsTabsColor();
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
                //temporary to test before passing data between fragments
//                Bitmap image = ;
                Object item = new Object();
                Log.d(TAG, "Add item =>" + item);
//              Boolean success = DataTransferService.addItem(image, item);
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
                break;
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
                break;
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
            case R.id.category_top:
                if (checked) {
                    // add to bar stuff
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "You Chose Top",
                            Toast.LENGTH_SHORT);
                    toast.show();
                };
                break;
            case R.id.category_bottom:
                if (checked) {
                    // add to bar stuff
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "You Chose Bottom",
                            Toast.LENGTH_SHORT);
                    toast.show();
                };
                break;
            case R.id.category_dress:
                if (checked) {
                    // add to bar stuff
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "You Chose Dresses/Suits",
                            Toast.LENGTH_SHORT);
                    toast.show();
                };
                break;
            case R.id.category_outerwear:
                if (checked) {
                    // add to bar stuff
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "You Chose Outerwear",
                            Toast.LENGTH_SHORT);
                    toast.show();
                };
                break;
            case R.id.category_shoe:
                if (checked) {
                    // add to bar stuff
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "You Chose Shoes",
                            Toast.LENGTH_SHORT);
                    toast.show();
                };
                break;
            case R.id.category_accessories:
                if (checked) {
                    // add to bar stuff
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "You Chose Accessories",
                            Toast.LENGTH_SHORT);
                    toast.show();
                };
                break;
            case R.id.season_spring:
                if (checked) {
                    // add to bar stuff
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "You Chose Spring",
                            Toast.LENGTH_SHORT);
                    toast.show();
                };
                break;
            case R.id.season_summer:
                if (checked) {
                    // add to bar stuff
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "You Chose Summer",
                            Toast.LENGTH_SHORT);
                    toast.show();
                };
                break;
            case R.id.season_fall:
                if (checked) {
                    // add to bar stuff
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "You Chose Fall",
                            Toast.LENGTH_SHORT);
                    toast.show();
                };
                break;
            case R.id.season_winter:
                if (checked) {
                    // add to bar stuff
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "You Chose Winter",
                            Toast.LENGTH_SHORT);
                    toast.show();
                };
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
