package com.example.fivestarstyle;

import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.ContextThemeWrapper;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.CheckBox;
import android.widget.Toast;


public class ChooseOutfit2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_outfit2);
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
        firstTab.setText("Location");
        tabLayout.addTab(firstTab,true);
        // create second tab
        TabLayout.Tab secondTab = tabLayout.newTab();
        secondTab.setText("Formality");
        tabLayout.addTab(secondTab);
        // create third tab
        TabLayout.Tab thirdTab = tabLayout.newTab();
        thirdTab.setText("Category");
        tabLayout.addTab(thirdTab);
        // set first tab to be selected
        Fragment fragment = new CategoryTab();
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        if(fragment != null) ft.replace(R.id.simpleFrameLayout, fragment);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft.commit();
        createLocationCheckboxes();

        // perform setOnTabSelectedListener event on TabLayout
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                // get the current selected tab's position and replace the fragment accordingly
                Fragment fragment = new CategoryTab();
                switch (tab.getPosition()) {
                    case 0:
                        fragment = new CategoryTab();
                        createLocationCheckboxes();
                        break;
                    case 1:
                        fragment = new SeasonTab();
                        createFormalityCheckboxes();
                        break;
                    case 2:
                        fragment = new ColorTab();
                        createStyleCheckboxes();
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

    public void createLocationCheckboxes() {
        LinearLayout linearLayout = findViewById(R.id.rootContainer);
        linearLayout.removeAllViews();
        final CheckBox checkBox1 = new CheckBox(new ContextThemeWrapper(this, R.style.CheckboxTheme));
        final CheckBox checkBox2 = new CheckBox(new ContextThemeWrapper(this, R.style.CheckboxTheme));
        final CheckBox checkBox3 = new CheckBox(new ContextThemeWrapper(this, R.style.CheckboxTheme));
        checkBox1.setText(R.string.locationTabCheckOne);
        checkBox1.setTextColor(Color.parseColor("#1B4F72"));
        checkBox2.setText(R.string.locationTabCheckTwo);
        checkBox2.setTextColor(Color.parseColor("#1B4F72"));
        checkBox3.setText(R.string.locationTabCheckThree);
        checkBox3.setTextColor(Color.parseColor("#1B4F72"));
        checkBox1.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        checkBox2.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        checkBox3.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        // Add Checkboxes to LinearLayout
        linearLayout.addView(checkBox1);
        linearLayout.addView(checkBox2);
        linearLayout.addView(checkBox3);

        checkBox1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(checkBox1.isChecked()) {
                    checkBox2.setChecked(false);
                    checkBox3.setChecked(false);
                }
            }
        });

        checkBox2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(checkBox2.isChecked()) {
                    checkBox1.setChecked(false);
                    checkBox3.setChecked(false);
                }
            }
        });

        checkBox3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(checkBox3.isChecked()) {
                    checkBox1.setChecked(false);
                    checkBox2.setChecked(false);
                }
            }
        });
    }

    public void createFormalityCheckboxes() {
        LinearLayout linearLayout = findViewById(R.id.rootContainer);
        linearLayout.removeAllViews();
        final CheckBox checkBox1 = new CheckBox(new ContextThemeWrapper(this, R.style.CheckboxTheme));
        final CheckBox checkBox2 = new CheckBox(new ContextThemeWrapper(this, R.style.CheckboxTheme));
        final CheckBox checkBox3 = new CheckBox(new ContextThemeWrapper(this, R.style.CheckboxTheme));
        checkBox1.setText(R.string.formalityTabCheckOne);
        checkBox1.setTextColor(Color.parseColor("#1B4F72"));
        checkBox2.setText(R.string.formalityTabCheckTwo);
        checkBox2.setTextColor(Color.parseColor("#1B4F72"));
        checkBox3.setText(R.string.formalityTabCheckThree);
        checkBox3.setTextColor(Color.parseColor("#1B4F72"));
        checkBox1.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        checkBox2.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        checkBox3.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        // Add Checkboxes to LinearLayout
        linearLayout.addView(checkBox1);
        linearLayout.addView(checkBox2);
        linearLayout.addView(checkBox3);

        checkBox1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(checkBox1.isChecked()) {
                    checkBox2.setChecked(false);
                    checkBox3.setChecked(false);
                }
            }
        });

        checkBox2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(checkBox2.isChecked()) {
                    checkBox1.setChecked(false);
                    checkBox3.setChecked(false);
                }
            }
        });

        checkBox3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(checkBox3.isChecked()) {
                    checkBox1.setChecked(false);
                    checkBox2.setChecked(false);
                }
            }
        });
    }

    public void createStyleCheckboxes() {
        LinearLayout linearLayout = findViewById(R.id.rootContainer);
        linearLayout.removeAllViews();
        final CheckBox checkBox1 = new CheckBox(new ContextThemeWrapper(this, R.style.CheckboxTheme));
        final CheckBox checkBox2 = new CheckBox(new ContextThemeWrapper(this, R.style.CheckboxTheme));
        final CheckBox checkBox3 = new CheckBox(new ContextThemeWrapper(this, R.style.CheckboxTheme));
        checkBox1.setText(R.string.styleTabCheckOne);
        checkBox1.setTextColor(Color.parseColor("#1B4F72"));
        checkBox2.setText(R.string.styleTabCheckTwo);
        checkBox2.setTextColor(Color.parseColor("#1B4F72"));
        checkBox3.setText(R.string.styleTabCheckThree);
        checkBox3.setTextColor(Color.parseColor("#1B4F72"));
        checkBox1.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        checkBox2.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        checkBox3.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        // Add Checkboxes to LinearLayout
        linearLayout.addView(checkBox1);
        linearLayout.addView(checkBox2);
        linearLayout.addView(checkBox3);

        checkBox1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(checkBox1.isChecked()) {
                    checkBox2.setChecked(false);
                    checkBox3.setChecked(false);
                }
            }
        });

        checkBox2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(checkBox2.isChecked()) {
                    checkBox1.setChecked(false);
                    checkBox3.setChecked(false);
                }
            }
        });

        checkBox3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(checkBox3.isChecked()) {
                    checkBox1.setChecked(false);
                    checkBox2.setChecked(false);
                }
            }
        });
    }

}

