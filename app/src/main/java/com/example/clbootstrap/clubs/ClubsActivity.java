package com.example.clbootstrap.clubs;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.MenuItem;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.card.MaterialCardView;
import com.example.clbootstrap.R;
import com.example.clbootstrap.timeline.TimelineActivity;
import com.example.clbootstrap.admin.AdminGridActivity;

public class ClubsActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigation;
    private ViewPager2 imageSlider;
    private Handler sliderHandler;
    private Runnable sliderRunnable;
    private final int[] images = {
        R.drawable.welcome_image,
        R.drawable.create_club
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clubs);

        bottomNavigation = findViewById(R.id.bottomNavigation);
        imageSlider = findViewById(R.id.imageSlider);
        MaterialCardView adminServicesCard = findViewById(R.id.adminServicesCard);
        
        setupBottomNavigation();
        setupImageSlider();

        // Set click listener for admin services card
        if (adminServicesCard != null) {
            adminServicesCard.setOnClickListener(v -> {
                Intent intent = new Intent(this, AdminGridActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            });
        }
    }

    private void setupImageSlider() {
        ImageSliderAdapter adapter = new ImageSliderAdapter(images);
        imageSlider.setAdapter(adapter);
        
        // Auto slide functionality
        sliderHandler = new Handler(Looper.getMainLooper());
        sliderRunnable = new Runnable() {
            @Override
            public void run() {
                int currentPosition = imageSlider.getCurrentItem();
                int nextPosition = (currentPosition + 1) % images.length;
                imageSlider.setCurrentItem(nextPosition, true);
                sliderHandler.postDelayed(this, 3000);
            }
        };

        // Add page change callback
        imageSlider.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                // Reset the auto-scroll timer when user manually changes page
                sliderHandler.removeCallbacks(sliderRunnable);
                sliderHandler.postDelayed(sliderRunnable, 3000);
            }
        });

        startAutoScroll();
    }

    private void startAutoScroll() {
        sliderHandler.postDelayed(sliderRunnable, 3000);
    }

    private void stopAutoScroll() {
        sliderHandler.removeCallbacks(sliderRunnable);
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopAutoScroll();
    }

    @Override
    protected void onResume() {
        super.onResume();
        startAutoScroll();
    }

    private void setupBottomNavigation() {
        // Show label for Clubs (second item) initially
        MenuItem clubsItem = bottomNavigation.getMenu().getItem(1);
        clubsItem.setChecked(true);
        for (int i = 0; i < bottomNavigation.getMenu().size(); i++) {
            MenuItem item = bottomNavigation.getMenu().getItem(i);
            // Show label only for Clubs initially
            item.setTitleCondensed(i == 1 ? item.getTitle() : null);
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
            if (item.getItemId() == R.id.timeline) {
                startActivity(new Intent(this, TimelineActivity.class));
                finish(); // Close this activity
                return false; // Don't select the item
            }
            return true;
        });
    }
}
