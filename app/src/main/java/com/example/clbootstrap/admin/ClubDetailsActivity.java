package com.example.clbootstrap.admin;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.card.MaterialCardView;
import com.example.clbootstrap.R;

public class ClubDetailsActivity extends AppCompatActivity {
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_club_details);

        // Get club name from intent
        String clubName = getIntent().getStringExtra("club_name");

        // Initialize views
        ImageView clubBanner = findViewById(R.id.clubBanner);
        TextView clubNameView = findViewById(R.id.clubName);
        MaterialCardView adminServicesCard = findViewById(R.id.adminServicesCard);

        // Set club name and banner
        clubNameView.setText(clubName);
        clubBanner.setImageResource(R.drawable.welcome_image);

        // Set up admin services card click
        adminServicesCard.setOnClickListener(v -> {
            Intent intent = new Intent(this, ClubAdminServicesActivity.class);
            intent.putExtra("club_name", clubName);
            startActivity(intent);
        });
    }
}
