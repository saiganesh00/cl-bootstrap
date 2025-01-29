package com.example.clbootstrap.campus_choice;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
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

        // Get data from intent
        String firstName = getIntent().getStringExtra("first_name");
        String countryCode = getIntent().getStringExtra("country_code");

        // Set welcome text
        TextView welcomeText = findViewById(R.id.welcomeText);
        welcomeText.setText(String.format("Welcome %s!", firstName));

        // Set up click listeners
        View createCampusCard = findViewById(R.id.createCampusCard);
        View joinCampusCard = findViewById(R.id.joinCampusCard);

        createCampusCard.setOnClickListener(v -> {
            Intent intent = new Intent(this, CreateCampusActivity.class);
            intent.putExtra("country_code", countryCode);
            startActivity(intent);
        });

        joinCampusCard.setOnClickListener(v -> {
            Intent intent = new Intent(this, JoinCampusActivity.class);
            intent.putExtra("country_code", countryCode);
            startActivity(intent);
        });
    }
}
