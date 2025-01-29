package com.example.clbootstrap.authentication;

import android.os.Bundle;
import android.view.View;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.example.clbootstrap.R;

public class CampusChoiceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_campus_choice);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(android.R.id.content), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Set up click listeners
        View createCampusCard = findViewById(R.id.createCampusCard);
        View joinCampusCard = findViewById(R.id.joinCampusCard);

        createCampusCard.setOnClickListener(v -> {
            // TODO: Navigate to create campus flow
        });

        joinCampusCard.setOnClickListener(v -> {
            // TODO: Navigate to join campus flow
        });
    }
}
