package com.example.clbootstrap.admin;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.clbootstrap.R;
import com.example.clbootstrap.adapters.ClubAdapter;
import com.example.clbootstrap.models.Club;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class CreateClubActivity extends AppCompatActivity {
    private int currentStep = 1;
    private Uri selectedImageUri;
    
    // Store form data
    private String clubName = "";
    private String clubDescription = "";
    private String clubCategory = "";
    private String coordinatorName = "";
    private String coordinatorEmail = "";
    private String coordinatorPhone = "";
    private int availableSpots = 0;
    private String creationDate = "";
    private List<String> selectedFeatures = new ArrayList<>();
    private String clubRules = "";
    private List<SocialMediaHandle> socialMediaHandles = new ArrayList<>();

    private View[] stepViews;

    // Step 1 Views
    private TextInputEditText clubNameInput;
    private TextInputEditText clubDescriptionInput;
    private AutoCompleteTextView clubCategoryInput;
    private ImageView logoPreview;
    private TextView logoFileName;

    // Step 2 Views
    private TextInputEditText spotsInput;
    private TextInputEditText dateInput;
    private TextInputEditText rulesInput;
    private View socialMediaContainer;
    private AutoCompleteTextView socialPlatformInput;
    private TextInputEditText socialHandleInput;

    // Step 3 Views
    private TextInputEditText coordinatorNameInput;
    private TextInputEditText coordinatorEmailInput;
    private TextInputEditText coordinatorPhoneInput;

    // Preview Views
    private ImageView clubLogoPreview;
    private TextView clubNamePreview;
    private TextView clubCategoryPreview;
    private TextView clubDescriptionPreview;
    private TextView coordinatorNamePreview;
    private TextView coordinatorEmailPreview;
    private TextView coordinatorPhonePreview;
    private TextView membersPreview;
    private TextView createdDatePreview;
    private ChipGroup featuresChipGroup;
    private TextView rulesPreview;
    private LinearLayout socialMediaPreview;

    private final ActivityResultLauncher<String> imagePickerLauncher = registerForActivityResult(
            new ActivityResultContracts.GetContent(),
            uri -> {
                if (uri != null) {
                    selectedImageUri = uri;
                    logoPreview.setImageURI(uri);
                    logoFileName.setText("Logo selected");
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_club);
        setupFirstStep();
        setupNavigationButtons();
    }

    private void setupFirstStep() {
        // Initialize step indicators
        stepViews = new View[4];
        stepViews[0] = findViewById(R.id.step1);
        stepViews[1] = findViewById(R.id.step2);
        stepViews[2] = findViewById(R.id.step3);
        stepViews[3] = findViewById(R.id.step4);

        // Initialize form fields
        clubNameInput = findViewById(R.id.clubNameInput);
        clubDescriptionInput = findViewById(R.id.clubDescriptionInput);
        clubCategoryInput = findViewById(R.id.clubCategoryInput);
        logoPreview = findViewById(R.id.logoPreview);
        logoFileName = findViewById(R.id.logoFileName);

        String[] categories = {"Technology", "Sports", "Arts", "Academic", "Social", "Other"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, categories);
        clubCategoryInput.setAdapter(adapter);

        findViewById(R.id.logoUploadArea).setOnClickListener(v -> 
            imagePickerLauncher.launch("image/*")
        );
    }

    private void setupStep2Views() {
        setContentView(R.layout.activity_create_club_step2);
        setupStepIndicators();

        spotsInput = findViewById(R.id.spotsInput);
        dateInput = findViewById(R.id.dateInput);
        rulesInput = findViewById(R.id.rulesInput);
        socialMediaContainer = findViewById(R.id.socialMediaContainer);
        socialPlatformInput = findViewById(R.id.socialPlatformInput);
        socialHandleInput = findViewById(R.id.socialHandleInput);

        String[] platforms = {"Instagram", "Twitter", "Facebook", "LinkedIn"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, platforms);
        socialPlatformInput.setAdapter(adapter);

        dateInput.setOnClickListener(v -> showDatePicker());

        findViewById(R.id.addSocialButton).setOnClickListener(v -> addSocialMediaHandle());
        findViewById(R.id.removeSocialButton).setOnClickListener(v -> removeSocialMediaHandle());

        setupNavigationButtons();
    }

    private void setupStep3Views() {
        setContentView(R.layout.activity_create_club_step3);
        setupStepIndicators();

        coordinatorNameInput = findViewById(R.id.coordinatorNameInput);
        coordinatorEmailInput = findViewById(R.id.coordinatorEmailInput);
        coordinatorPhoneInput = findViewById(R.id.coordinatorPhoneInput);

        setupNavigationButtons();
    }

    private void setupPreviewStep() {
        setContentView(R.layout.activity_create_club_preview);
        setupStepIndicators();

        // Initialize preview views
        clubLogoPreview = findViewById(R.id.clubLogoPreview);
        clubNamePreview = findViewById(R.id.clubNamePreview);
        clubCategoryPreview = findViewById(R.id.clubCategoryPreview);
        clubDescriptionPreview = findViewById(R.id.clubDescriptionPreview);
        coordinatorNamePreview = findViewById(R.id.coordinatorNamePreview);
        coordinatorEmailPreview = findViewById(R.id.coordinatorEmailPreview);
        coordinatorPhonePreview = findViewById(R.id.coordinatorPhonePreview);
        membersPreview = findViewById(R.id.membersPreview);
        createdDatePreview = findViewById(R.id.createdDatePreview);
        rulesPreview = findViewById(R.id.rulesPreview);
        featuresChipGroup = findViewById(R.id.featuresChipGroup);
        socialMediaPreview = findViewById(R.id.socialMediaPreview);

        // Set preview values
        if (selectedImageUri != null) {
            clubLogoPreview.setImageURI(selectedImageUri);
        }
        clubNamePreview.setText(clubName);
        clubCategoryPreview.setText(clubCategory);
        clubDescriptionPreview.setText(clubDescription);
        coordinatorNamePreview.setText(coordinatorName);
        coordinatorEmailPreview.setText(coordinatorEmail);
        coordinatorPhonePreview.setText(coordinatorPhone);
        membersPreview.setText(String.valueOf(availableSpots));
        createdDatePreview.setText(creationDate);
        rulesPreview.setText(clubRules);

        // Add feature chips
        for (String feature : selectedFeatures) {
            Chip chip = new Chip(this);
            chip.setText(feature);
            featuresChipGroup.addView(chip);
        }

        // Add social media handles
        for (SocialMediaHandle handle : socialMediaHandles) {
            TextView socialText = new TextView(this);
            socialText.setText(handle.platform + ": " + handle.handle);
            socialText.setTextColor(getResources().getColor(android.R.color.darker_gray));
            socialText.setTextSize(14);
            socialMediaPreview.addView(socialText);
        }

        // Set up create button
        MaterialButton createButton = findViewById(R.id.createClubButton);
        createButton.setOnClickListener(v -> {
            // Take persistable URI permission
            if (selectedImageUri != null) {
                getContentResolver().takePersistableUriPermission(selectedImageUri,
                    Intent.FLAG_GRANT_READ_URI_PERMISSION);
            }
            
            // Navigate to ClubsActivity with logo and name
            Intent intent = new Intent(this, com.example.clbootstrap.clubs.ClubsActivity.class);
            intent.putExtra("club_name", clubName);
            if (selectedImageUri != null) {
                intent.putExtra("club_logo", selectedImageUri.toString());
            }
            startActivity(intent);
            finish();
        });
    }

    private void setupNavigationButtons() {
        ImageButton backButton = findViewById(R.id.backButton);
        if (backButton != null) {
            backButton.setOnClickListener(v -> onBackPressed());
        }

        MaterialButton nextButton = findViewById(R.id.nextButton);
        if (nextButton != null) {
            nextButton.setOnClickListener(v -> {
                if (validateCurrentStep()) {
                    currentStep++;
                    navigateToStep(currentStep);
                }
            });
        }

        MaterialButton previousButton = findViewById(R.id.previousButton);
        if (previousButton != null) {
            previousButton.setOnClickListener(v -> {
                currentStep--;
                navigateToStep(currentStep);
            });
        }
    }

    private void setupStepIndicators() {
        for (int i = 0; i < stepViews.length; i++) {
            stepViews[i].setBackgroundResource(i < currentStep ? 
                R.drawable.circle_active : R.drawable.circle_inactive);
        }
    }

    private void navigateToStep(int step) {
        switch (step) {
            case 1:
                setContentView(R.layout.activity_create_club);
                setupFirstStep();
                setupNavigationButtons();
                break;
            case 2:
                setContentView(R.layout.activity_create_club_step2);
                setupStep2Views();
                setupNavigationButtons();
                break;
            case 3:
                setContentView(R.layout.activity_create_club_step3);
                setupStep3Views();
                setupNavigationButtons();
                break;
            case 4:
                setContentView(R.layout.activity_create_club_preview);
                setupPreviewStep();
                setupNavigationButtons();
                break;
        }
        updateStepIndicators();
    }

    private void updateStepIndicators() {
        for (int i = 0; i < stepViews.length; i++) {
            stepViews[i].setBackgroundResource(i < currentStep ? 
                R.drawable.circle_active : R.drawable.circle_inactive);
        }
    }

    private boolean validateCurrentStep() {
        switch (currentStep) {
            case 1:
                TextInputEditText nameInput = findViewById(R.id.clubNameInput);
                TextInputEditText descInput = findViewById(R.id.clubDescriptionInput);
                AutoCompleteTextView categoryInput = findViewById(R.id.clubCategoryInput);
                
                if (nameInput != null && descInput != null && categoryInput != null) {
                    clubName = nameInput.getText().toString().trim();
                    clubDescription = descInput.getText().toString().trim();
                    clubCategory = categoryInput.getText().toString().trim();
                    
                    if (clubName.isEmpty()) {
                        nameInput.setError("Club name is required");
                        return false;
                    }
                    if (clubDescription.isEmpty()) {
                        descInput.setError("Description is required");
                        return false;
                    }
                    if (clubCategory.isEmpty()) {
                        categoryInput.setError("Category is required");
                        return false;
                    }
                }
                return true;

            case 2:
                TextInputEditText spotsInput = findViewById(R.id.spotsInput);
                TextInputEditText dateInput = findViewById(R.id.dateInput);
                
                if (spotsInput != null && dateInput != null) {
                    String spots = spotsInput.getText().toString().trim();
                    creationDate = dateInput.getText().toString().trim();
                    
                    if (spots.isEmpty()) {
                        spotsInput.setError("Number of spots is required");
                        return false;
                    }
                    if (creationDate.isEmpty()) {
                        dateInput.setError("Creation date is required");
                        return false;
                    }
                    availableSpots = Integer.parseInt(spots);
                    clubRules = rulesInput.getText().toString().trim();
                }
                return true;

            case 3:
                TextInputEditText nameCoordInput = findViewById(R.id.coordinatorNameInput);
                TextInputEditText emailInput = findViewById(R.id.coordinatorEmailInput);
                TextInputEditText phoneInput = findViewById(R.id.coordinatorPhoneInput);
                
                if (nameCoordInput != null && emailInput != null && phoneInput != null) {
                    coordinatorName = nameCoordInput.getText().toString().trim();
                    coordinatorEmail = emailInput.getText().toString().trim();
                    coordinatorPhone = phoneInput.getText().toString().trim();
                    
                    if (coordinatorName.isEmpty()) {
                        nameCoordInput.setError("Coordinator name is required");
                        return false;
                    }
                    if (coordinatorEmail.isEmpty()) {
                        emailInput.setError("Email is required");
                        return false;
                    }
                    if (coordinatorPhone.isEmpty()) {
                        phoneInput.setError("Phone number is required");
                        return false;
                    }
                }
                return true;

            default:
                return true;
        }
    }

    private void showDatePicker() {
        DatePickerDialog dialog = new DatePickerDialog(this,
                (view, year, month, dayOfMonth) -> {
                    Calendar calendar = Calendar.getInstance();
                    calendar.set(year, month, dayOfMonth);
                    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                    dateInput.setText(sdf.format(calendar.getTime()));
                },
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        dialog.show();
    }

    private void addSocialMediaHandle() {
        String platform = socialPlatformInput.getText().toString();
        String handle = socialHandleInput.getText().toString();
        if (!platform.isEmpty() && !handle.isEmpty()) {
            socialMediaHandles.add(new SocialMediaHandle(platform, handle));
            // Update UI to show new handle
            socialHandleInput.setText("");
        }
    }

    private void removeSocialMediaHandle() {
        if (!socialMediaHandles.isEmpty()) {
            socialMediaHandles.remove(socialMediaHandles.size() - 1);
            // Update UI to reflect removal
        }
    }

    private static class SocialMediaHandle {
        String platform;
        String handle;

        SocialMediaHandle(String platform, String handle) {
            this.platform = platform;
            this.handle = handle;
        }
    }

    @Override
    public void onBackPressed() {
        if (currentStep > 1) {
            currentStep--;
            navigateToStep(currentStep);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}
