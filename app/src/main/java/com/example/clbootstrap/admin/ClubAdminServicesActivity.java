package com.example.clbootstrap.admin;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.clbootstrap.R;
import com.google.android.material.card.MaterialCardView;
import com.example.clbootstrap.events.CreateEventActivity;

public class ClubAdminServicesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_club_admin_services);

        // Get club name from intent
        String clubName = getIntent().getStringExtra("club_name");

        // Set up back button
        findViewById(R.id.backButton).setOnClickListener(v -> finish());

        // Set title
        TextView titleText = findViewById(R.id.titleText);
        titleText.setText("Admin Services");

        // Set up click listeners for all cards
        setupCardClickListeners();
    }

    private void setupCardClickListeners() {
        // Invite card click
        findViewById(R.id.inviteCard).setOnClickListener(v -> {
            // TODO: Implement invite functionality
        });

        // Edit club card click
        findViewById(R.id.editClubCard).setOnClickListener(v -> {
            // TODO: Implement edit club functionality
        });

        // Manage members card click
        findViewById(R.id.manageMembersCard).setOnClickListener(v -> {
            // TODO: Implement manage members functionality
        });

        // Event QR codes card click
        findViewById(R.id.eventQRCodesCard).setOnClickListener(v -> {
            // TODO: Implement QR codes functionality
        });

        // Announcements card click
        findViewById(R.id.announcementsCard).setOnClickListener(v -> {
            // TODO: Implement announcements functionality
        });

        // Reports card click
        findViewById(R.id.reportsCard).setOnClickListener(v -> {
            // TODO: Implement reports functionality
        });

        // Nominate admin card click
        findViewById(R.id.nominateAdminCard).setOnClickListener(v -> {
            // TODO: Implement nominate admin functionality
        });

        // Create Event card click listener
        MaterialCardView createEventCard = findViewById(R.id.createEventCard);
        createEventCard.setOnClickListener(v -> {
            Intent intent = new Intent(this, CreateEventActivity.class);
            startActivity(intent);
        });
    }
}
