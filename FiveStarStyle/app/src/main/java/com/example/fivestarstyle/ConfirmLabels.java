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
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.view.PieChartView;

public class ConfirmLabels extends AppCompatActivity {

    private Button right;
    private Button left;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_labels);
        right = (Button) findViewById(R.id.right_btn);
        left = (Button) findViewById(R.id.left_btn);
        left.setVisibility(View.GONE);
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
        thirdTab.setText("Event");
        tabLayout.addTab(thirdTab);
        // set first tab to be selected
        Fragment fragment = new CategoryTab();
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        if(fragment != null) ft.replace(R.id.simpleFrameLayout, fragment);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft.commit();
//        createCategoryPieGraph();

        // perform setOnTabSelectedListener event on TabLayout
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                // get the current selected tab's position and replace the fragment accordingly
                Fragment fragment = new CategoryTab();
                switch (tab.getPosition()) {
                    case 0:
                        fragment = new CategoryTab();
                        categoryTab();
                        break;
                    case 1:
                        fragment = new SeasonTab();
                        seasonTab();
                        break;
                    case 2:
                        fragment = new ColorTab();
                        eventTab();
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

    public void categoryTab() {
        right.setText("Next");
        left.setVisibility(View.GONE);
    }

    public void seasonTab() {
        right.setText("Next");
        left.setVisibility(View.VISIBLE);
    }

    public void eventTab() {
        right.setText("Add Item");
        left.setVisibility(View.VISIBLE);
    }
}
