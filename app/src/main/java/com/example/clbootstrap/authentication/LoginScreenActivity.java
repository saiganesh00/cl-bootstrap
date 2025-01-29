package com.example.clbootstrap.authentication;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.example.clbootstrap.R;
import com.example.clbootstrap.adapters.CountryAdapter;
import com.example.clbootstrap.helpers.CountryHelper;
import com.example.clbootstrap.models.Country;
import java.util.ArrayList;
import java.util.List;

public class LoginScreenActivity extends AppCompatActivity {
    private EditText phoneNumberInput;
    private Button loginButton;
    private TextView selectedCountryText;
    private String selectedCountryCode = "91"; // Default to India
    private List<Country> allCountries;
    private CountryAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login_screen);
        
        // Initialize views
        phoneNumberInput = findViewById(R.id.phoneNumberInput);
        loginButton = findViewById(R.id.loginButton);
        selectedCountryText = findViewById(R.id.selectedCountryText);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Set up click listeners and validation
        selectedCountryText.setOnClickListener(v -> showCountryPicker());
        setupPhoneNumberValidation();

        // Load countries
        loadCountries();
        selectedCountryText.setText("+91"); // Default to India
    }

    private void setupPhoneNumberValidation() {
        phoneNumberInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String phoneNumber = s.toString();
                if (phoneNumber.length() > 10) {
                    phoneNumberInput.setText(phoneNumber.substring(0, 10));
                    phoneNumberInput.setSelection(10);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        // Set up login button click listener
        loginButton.setOnClickListener(v -> {
            String phoneNumber = phoneNumberInput.getText().toString().trim();
            if (isValidPhoneNumber(phoneNumber)) {
                navigateToRegister(phoneNumber);
            } else {
                phoneNumberInput.setError("Please enter a valid 10-digit mobile number");
            }
        });
    }

    private void loadCountries() {
        // Initialize country data
        allCountries = CountryHelper.getCountries(this);
    }

    private void showCountryPicker() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_country_picker);
        
        Window window = dialog.getWindow();
        if (window != null) {
            window.setBackgroundDrawable(new android.graphics.drawable.ColorDrawable(getResources().getColor(android.R.color.transparent)));
            window.setLayout(
                (int) (getResources().getDisplayMetrics().widthPixels * 0.9),
                (int) (getResources().getDisplayMetrics().heightPixels * 0.8)
            );
        }

        EditText searchInput = dialog.findViewById(R.id.searchInput);
        ListView listView = dialog.findViewById(R.id.countryList);
        
        // Initialize adapter with all countries
        adapter = new CountryAdapter(this, new ArrayList<>(allCountries));
        listView.setAdapter(adapter);

        // Set up search functionality
        searchInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterCountries(s.toString().toLowerCase());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        listView.setOnItemClickListener((parent, view, position, id) -> {
            Country country = adapter.getItem(position);
            if (country != null) {
                selectedCountryCode = country.getDialCode().replace("+", "");
                selectedCountryText.setText(country.getDialCode());
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void filterCountries(String query) {
        List<Country> filteredList = new ArrayList<>();
        for (Country country : allCountries) {
            if (country.getName().toLowerCase().contains(query) ||
                country.getDialCode().contains(query) ||
                country.getCode().toLowerCase().contains(query)) {
                filteredList.add(country);
            }
        }
        adapter.clear();
        adapter.addAll(filteredList);
        adapter.notifyDataSetChanged();
    }

    private void navigateToRegister(String phoneNumber) {
        Intent intent = new Intent(this, RegisterScreenActivity.class);
        intent.putExtra("phone_number", phoneNumber);
        intent.putExtra("country_code", selectedCountryCode);
        startActivity(intent);
    }

    private boolean isValidPhoneNumber(String phoneNumber) {
        return !TextUtils.isEmpty(phoneNumber) && phoneNumber.length() == 10 && TextUtils.isDigitsOnly(phoneNumber);
    }
}