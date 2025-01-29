package com.example.clbootstrap.campus_choice;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.Toast;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import com.example.clbootstrap.R;
import com.example.clbootstrap.helpers.CountryHelper;
import com.example.clbootstrap.models.Country;
import com.example.clbootstrap.payment.PaymentCheckoutActivity;
import com.google.android.material.textfield.TextInputEditText;
import java.util.List;

public class CreateCampusActivity extends AppCompatActivity {
    private TextInputEditText campusNameInput;
    private AutoCompleteTextView stateInput;
    private TextInputEditText locationInput;
    private TextInputEditText descriptionInput;
    private CheckBox termsCheckbox;
    private Button createButton;
    private String countryCode;
    private ActivityResultLauncher<Intent> paymentLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_campus_new);

        // Get country code from intent
        countryCode = getIntent().getStringExtra("country_code");
        if (countryCode == null) {
            Toast.makeText(this, "Country code not found", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Initialize views
        campusNameInput = findViewById(R.id.campusNameInput);
        stateInput = findViewById(R.id.stateInput);
        locationInput = findViewById(R.id.locationInput);
        descriptionInput = findViewById(R.id.descriptionInput);
        termsCheckbox = findViewById(R.id.termsCheckbox);
        createButton = findViewById(R.id.createButton);
        ImageButton backButton = findViewById(R.id.backButton);

        // Set up back button
        backButton.setOnClickListener(v -> finish());

        // Set up states based on country code
        Country country = CountryHelper.getCountryByCode(this, countryCode);
        if (country != null) {
            updateStates(country);
        }

        // Set up terms checkbox click listener
        termsCheckbox.setOnClickListener(v -> {
            if (termsCheckbox.isChecked()) {
                showTermsDialog();
            }
        });

        // Initialize payment launcher
        paymentLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK) {
                    // Payment successful, finish activity
                    finish();
                }
            }
        );

        // Set up create button
        createButton.setOnClickListener(v -> {
            if (validateInputs()) {
                // Launch payment screen
                Intent intent = new Intent(this, PaymentCheckoutActivity.class);
                paymentLauncher.launch(intent);
            }
        });
    }

    private void updateStates(Country country) {
        List<String> states = country.getStates();
        ArrayAdapter<String> stateAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, states);
        stateInput.setAdapter(stateAdapter);
    }

    private void showTermsDialog() {
        Dialog termsDialog = new Dialog(this);
        termsDialog.setContentView(R.layout.dialog_campus_terms);
        termsDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        
        Button closeButton = termsDialog.findViewById(R.id.closeButton);
        closeButton.setOnClickListener(v -> termsDialog.dismiss());
        
        termsDialog.show();
    }

    private boolean validateInputs() {
        String campusName = campusNameInput.getText().toString().trim();
        String state = stateInput.getText().toString().trim();
        String location = locationInput.getText().toString().trim();
        String description = descriptionInput.getText().toString().trim();

        if (campusName.isEmpty()) {
            campusNameInput.setError("Please enter campus name");
            return false;
        }

        if (state.isEmpty()) {
            stateInput.setError("Please select state");
            return false;
        }

        if (location.isEmpty()) {
            locationInput.setError("Please enter location");
            return false;
        }

        if (description.isEmpty()) {
            descriptionInput.setError("Please enter campus description");
            return false;
        }

        if (!termsCheckbox.isChecked()) {
            termsCheckbox.setError("You must accept the terms and conditions");
            return false;
        }

        return true;
    }
}
