package com.example.clbootstrap.timeline;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.clbootstrap.clubs.ClubsActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;
import com.example.clbootstrap.R;


public class TimelineActivity extends AppCompatActivity {
    private TabLayout tabLayout;
    private RecyclerView timelineRecyclerView;
    private TimelineAdapter timelineAdapter;
    private BottomNavigationView bottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);

        // Initialize views
        tabLayout = findViewById(R.id.tabLayout);
        timelineRecyclerView = findViewById(R.id.timelineRecyclerView);
        bottomNavigation = findViewById(R.id.bottomNavigation);

        // Set up tabs
        setupTabs();

        // Set up RecyclerView
        setupRecyclerView();

        // Set up bottom navigation
        setupBottomNavigation();
    }

    private void setupTabs() {
        // Remove any existing tabs
        tabLayout.removeAllTabs();

        // Add tabs
        tabLayout.addTab(tabLayout.newTab().setText("Following"));
        tabLayout.addTab(tabLayout.newTab().setText("My Activities"));
        tabLayout.addTab(tabLayout.newTab().setText("Featured"));

        // Select the first tab
        TabLayout.Tab firstTab = tabLayout.getTabAt(0);
        if (firstTab != null) {
            firstTab.select();
        }
    }

    private void setupRecyclerView() {
        timelineAdapter = new TimelineAdapter();
        timelineAdapter.setOnItemClickListener(post -> {
            // Navigate to ClubsActivity when post is clicked
            startActivity(new Intent(this, ClubsActivity.class));
        });
        timelineRecyclerView.setAdapter(timelineAdapter);
        timelineRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        timelineRecyclerView.setHasFixedSize(true);
    }

    private void setupBottomNavigation() {
        // Show label for Timeline (first item) initially
        MenuItem timelineItem = bottomNavigation.getMenu().getItem(0);
        timelineItem.setChecked(true);
        for (int i = 0; i < bottomNavigation.getMenu().size(); i++) {
            MenuItem item = bottomNavigation.getMenu().getItem(i);
            // Show label only for Timeline initially
            item.setTitleCondensed(i == 0 ? item.getTitle() : null);
        }

        bottomNavigation.setOnItemSelectedListener(item -> {
            // Hide all labels first
            for (int i = 0; i < bottomNavigation.getMenu().size(); i++) {
                MenuItem menuItem = bottomNavigation.getMenu().getItem(i);
                menuItem.setTitleCondensed(null);
            }
            // Show label for selected item
            item.setTitleCondensed(item.getTitle());

            // Handle navigation
            if (item.getItemId() == R.id.clubs) {
                startActivity(new Intent(this, ClubsActivity.class));
                return false; // Don't select the item
            }
            return true;
        });
    }
}
