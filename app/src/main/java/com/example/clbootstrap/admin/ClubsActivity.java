package com.example.clbootstrap.admin;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.clbootstrap.R;
import com.example.clbootstrap.adapters.ClubAdapter;
import com.example.clbootstrap.models.Club;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ClubsActivity extends AppCompatActivity {
    private ClubAdapter clubAdapter;
    private RecyclerView clubsRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clubs);

        // Initialize RecyclerView
        clubsRecyclerView = findViewById(R.id.clubsRecyclerView);
        clubsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        clubAdapter = new ClubAdapter();
        clubsRecyclerView.setAdapter(clubAdapter);

        // Set up FAB
        FloatingActionButton createClubFab = findViewById(R.id.createClubFab);
        createClubFab.setVisibility(View.VISIBLE);
        createClubFab.setOnClickListener(v -> {
            Intent intent = new Intent(this, CreateClubActivity.class);
            startActivity(intent);
        });

        // Check for club logo from CreateClubActivity
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("club_logo")) {
            String logoUriStr = intent.getStringExtra("club_logo");
            String clubName = intent.getStringExtra("club_name");
            Uri logoUri = Uri.parse(logoUriStr);
            
            // Create new club card with logo and name
            Club newClub = new Club(clubName, "", "", logoUri);
            clubAdapter.addClub(newClub);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Check for new club data
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("logo_uri")) {
            String logoUriStr = intent.getStringExtra("logo_uri");
            String clubName = intent.getStringExtra("club_name");
            
            Uri logoUri = Uri.parse(logoUriStr);
            Club newClub = new Club(clubName, "", "", logoUri);
            clubAdapter.addClub(newClub);
            
            // Clear the intent data so we don't add the club again
            intent.removeExtra("logo_uri");
            intent.removeExtra("club_name");
        }
    }
}
