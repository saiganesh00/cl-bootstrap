package com.example.clbootstrap.authentication;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.clbootstrap.R;
import com.example.clbootstrap.campus_choice.CampusChoiceActivity;

public class RegisterScreenActivity extends AppCompatActivity {
    private EditText firstNameInput;
    private EditText lastNameInput;
    private EditText phoneInput;
    private EditText emailInput;
    private TextView countryCodeText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_screen);

        // Initialize views
        firstNameInput = findViewById(R.id.firstNameInput);
        lastNameInput = findViewById(R.id.lastNameInput);
        phoneInput = findViewById(R.id.phoneInput);
        emailInput = findViewById(R.id.emailInput);
        countryCodeText = findViewById(R.id.countryCodeText);
        ImageButton backButton = findViewById(R.id.backButton);

        // Get phone number and country code from intent
        String phoneNumber = getIntent().getStringExtra("phone_number");
        String countryCode = getIntent().getStringExtra("country_code");

        if (phoneNumber != null) {
            phoneInput.setText(phoneNumber);
            phoneInput.setEnabled(false); // Disable editing since it's pre-filled
        }

        if (countryCode != null) {
            countryCodeText.setText("+" + countryCode);
        }

        // Set up back button
        backButton.setOnClickListener(v -> finish());

        // Set up register button
        findViewById(R.id.signUpButton).setOnClickListener(v -> {
            if (validateInputs()) {
                // Create intent and pass data
                Intent intent = new Intent(this, OtpVerificationActivity.class);
                intent.putExtra("first_name", firstNameInput.getText().toString().trim());
                intent.putExtra("last_name", lastNameInput.getText().toString().trim());
                intent.putExtra("mobile", phoneInput.getText().toString().trim());
                intent.putExtra("country_code", countryCodeText.getText().toString().replace("+", ""));
                intent.putExtra("email", emailInput.getText().toString().trim());
                startActivity(intent);
                finish();
            }
        });
    }

    private boolean validateInputs() {
        String firstName = firstNameInput.getText().toString().trim();
        String lastName = lastNameInput.getText().toString().trim();
        String phone = phoneInput.getText().toString().trim();
        String email = emailInput.getText().toString().trim();

        if (firstName.isEmpty()) {
            firstNameInput.setError("Please enter your first name");
            return false;
        }

        if (lastName.isEmpty()) {
            lastNameInput.setError("Please enter your last name");
            return false;
        }

        if (phone.isEmpty()) {
            phoneInput.setError("Please enter your phone number");
            return false;
        }

        if (email.isEmpty()) {
            emailInput.setError("Please enter your email");
            return false;
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailInput.setError("Please enter a valid email");
            return false;
        }

        return true;
    }
}