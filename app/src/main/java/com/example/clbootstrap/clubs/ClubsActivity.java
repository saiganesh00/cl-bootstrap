package com.example.clbootstrap.clubs;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.MenuItem;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.example.clbootstrap.R;
import com.example.clbootstrap.timeline.TimelineActivity;

public class ClubsActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigation;
    private ViewPager2 imageSlider;
    private Handler sliderHandler;
    private Runnable sliderRunnable;
    private boolean isSliderMovingRight = true;
    private final int[] images = {
        R.drawable.club_image_1,
        R.drawable.club_image_2,
        R.drawable.club_image_3,
        R.drawable.club_image_4
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clubs);

        bottomNavigation = findViewById(R.id.bottomNavigation);
        imageSlider = findViewById(R.id.imageSlider);
        
        setupBottomNavigation();
        setupImageSlider();
    }

    private void setupImageSlider() {
        ImageSliderAdapter adapter = new ImageSliderAdapter(images);
        imageSlider.setAdapter(adapter);
        imageSlider.setOffscreenPageLimit(3);
        
        // Set page transformer for smooth transition
        float nextItemVisiblePx = getResources().getDimensionPixelSize(R.dimen.viewpager_next_item_visible);
        float currentItemHorizontalMarginPx = getResources().getDimensionPixelSize(R.dimen.viewpager_current_item_horizontal_margin);
        float pageTranslationX = nextItemVisiblePx + currentItemHorizontalMarginPx;
        ViewPager2.PageTransformer pageTransformer = (page, position) -> {
            page.setTranslationX(-pageTranslationX * position);
            page.setScaleY(1 - (0.25f * Math.abs(position)));
            page.setAlpha(0.25f + (1 - Math.abs(position)));
        };
        imageSlider.setPageTransformer(pageTransformer);
        
        // Auto scroll every 2.5 seconds
        sliderHandler = new Handler(Looper.getMainLooper());
        sliderRunnable = new Runnable() {
            @Override
            public void run() {
                int currentPosition = imageSlider.getCurrentItem();
                if (isSliderMovingRight) {
                    if (currentPosition == images.length - 1) {
                        isSliderMovingRight = false;
                        imageSlider.setCurrentItem(currentPosition - 1);
                    } else {
                        imageSlider.setCurrentItem(currentPosition + 1);
                    }
                } else {
                    if (currentPosition == 0) {
                        isSliderMovingRight = true;
                        imageSlider.setCurrentItem(currentPosition + 1);
                    } else {
                        imageSlider.setCurrentItem(currentPosition - 1);
                    }
                }
                sliderHandler.postDelayed(this, 2500); // 2.5 seconds
            }
        };
        startAutoScroll();
    }

    private void startAutoScroll() {
        sliderHandler.postDelayed(sliderRunnable, 2500);
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
