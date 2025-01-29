package com.example.clbootstrap.campus_choice;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.example.clbootstrap.R;
import com.example.clbootstrap.helpers.CountryHelper;
import com.example.clbootstrap.models.Country;
import com.google.android.material.textfield.TextInputEditText;
import java.util.List;

public class JoinCampusActivity extends AppCompatActivity {
    private AutoCompleteTextView countryInput;
    private AutoCompleteTextView stateInput;
    private TextInputEditText locationInput;
    private AutoCompleteTextView campusInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_join_campus);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(android.R.id.content), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize views
        countryInput = findViewById(R.id.countryInput);
        stateInput = findViewById(R.id.stateInput);
        locationInput = findViewById(R.id.locationInput);
        campusInput = findViewById(R.id.campusInput);
        View backButton = findViewById(R.id.backButton);
        View joinButton = findViewById(R.id.joinButton);

        // Set up country spinner
        List<Country> countries = CountryHelper.getCountries(this);
        ArrayAdapter<Country> countryAdapter = new ArrayAdapter<>(
            this,
            android.R.layout.simple_dropdown_item_1line,
            countries
        );
        countryInput.setAdapter(countryAdapter);

        // Handle country selection to update states
        countryInput.setOnItemClickListener((parent, view, position, id) -> {
            Country selectedCountry = (Country) parent.getItemAtPosition(position);
            updateStates(selectedCountry);
        });

        // Handle state selection to update campuses
        stateInput.setOnItemClickListener((parent, view, position, id) -> {
            String selectedState = (String) parent.getItemAtPosition(position);
            updateCampuses(selectedState);
        });

        // Set click listeners
        backButton.setOnClickListener(v -> finish());
        joinButton.setOnClickListener(v -> handleJoinCampus());
    }

    private void updateStates(Country country) {
        // TODO: Update with actual states for the selected country
        String[] states = {"State 1", "State 2", "State 3"};
        ArrayAdapter<String> stateAdapter = new ArrayAdapter<>(
            this,
            android.R.layout.simple_dropdown_item_1line,
            states
        );
        stateInput.setAdapter(stateAdapter);
    }

    private void updateCampuses(String state) {
        // TODO: Update with actual campuses for the selected state
        String[] campuses = {"Campus 1", "Campus 2", "Campus 3"};
        ArrayAdapter<String> campusAdapter = new ArrayAdapter<>(
            this,
            android.R.layout.simple_dropdown_item_1line,
            campuses
        );
        campusInput.setAdapter(campusAdapter);
    }

    private void handleJoinCampus() {
        String country = countryInput.getText().toString().trim();
        String state = stateInput.getText().toString().trim();
        String location = locationInput.getText().toString().trim();
        String campus = campusInput.getText().toString().trim();

        // TODO: Validate inputs and join campus
        // For now, just finish the activity
        finish();
    }
}
