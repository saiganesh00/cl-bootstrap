package com.example.clbootstrap.events;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import com.example.clbootstrap.R;
import com.google.android.material.textfield.TextInputEditText;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class CreateEventActivity extends AppCompatActivity {
    private int currentStep = 1;
    private Uri selectedImageUri;
    private Calendar startDate = Calendar.getInstance();
    private Calendar endDate = Calendar.getInstance();
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
    private SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
    private ActivityResultLauncher<String> imagePickerLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);

        setupImagePicker();
        setupSpinner();
        setupDateTimePickers();
        setupNavigationButtons();
        updateStepIndicators(currentStep);

        // Set up back button
        findViewById(R.id.backButton).setOnClickListener(v -> finish());
    }

    private void setupImagePicker() {
        imagePickerLauncher = registerForActivityResult(
            new ActivityResultContracts.GetContent(),
            uri -> {
                if (uri != null) {
                    selectedImageUri = uri;
                    TextView fileNameText = findViewById(R.id.selectedFileName);
                    fileNameText.setText(uri.getLastPathSegment());
                }
            }
        );

        findViewById(R.id.browseButton).setOnClickListener(v -> 
            imagePickerLauncher.launch("image/*")
        );
    }

    private void setupSpinner() {
        Spinner categorySpinner = findViewById(R.id.eventCategorySpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
            this,
            R.array.event_categories,
            android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(adapter);
    }

    private void setupDateTimePickers() {
        TextInputEditText startDateEdit = findViewById(R.id.startDateEdit);
        TextInputEditText startTimeEdit = findViewById(R.id.startTimeEdit);
        TextInputEditText endDateEdit = findViewById(R.id.endDateEdit);
        TextInputEditText endTimeEdit = findViewById(R.id.endTimeEdit);

        startDateEdit.setOnClickListener(v -> showDatePicker(startDate, startDateEdit));
        startTimeEdit.setOnClickListener(v -> showTimePicker(startDate, startTimeEdit));
        endDateEdit.setOnClickListener(v -> showDatePicker(endDate, endDateEdit));
        endTimeEdit.setOnClickListener(v -> showTimePicker(endDate, endTimeEdit));
    }

    private void showDatePicker(Calendar calendar, TextInputEditText editText) {
        new DatePickerDialog(this, (view, year, month, day) -> {
            calendar.set(year, month, day);
            editText.setText(dateFormat.format(calendar.getTime()));
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), 
           calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    private void showTimePicker(Calendar calendar, TextInputEditText editText) {
        new TimePickerDialog(this, (view, hour, minute) -> {
            calendar.set(Calendar.HOUR_OF_DAY, hour);
            calendar.set(Calendar.MINUTE, minute);
            editText.setText(timeFormat.format(calendar.getTime()));
        }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), 
           true).show();
    }

    private void setupNavigationButtons() {
        View previousButton = findViewById(R.id.previousButton);
        View nextButton = findViewById(R.id.nextButton);

        previousButton.setOnClickListener(v -> {
            if (currentStep > 1) {
                currentStep--;
                updateStepIndicators(currentStep);
                updateFormVisibility();
            }
        });

        nextButton.setOnClickListener(v -> {
            if (currentStep < 3) {
                currentStep++;
                updateStepIndicators(currentStep);
                updateFormVisibility();
            } else {
                // Submit the form
                submitEvent();
            }
        });
    }

    private void updateStepIndicators(int currentStep) {
        TextView step1Text = findViewById(R.id.step1Text);
        TextView step2Text = findViewById(R.id.step2Text);
        TextView step3Text = findViewById(R.id.step3Text);

        step1Text.setTextColor(getResources().getColor(currentStep >= 1 ? R.color.purple_500 : R.color.gray_600));
        step2Text.setTextColor(getResources().getColor(currentStep >= 2 ? R.color.purple_500 : R.color.gray_600));
        step3Text.setTextColor(getResources().getColor(currentStep >= 3 ? R.color.purple_500 : R.color.gray_600));

        View previousButton = findViewById(R.id.previousButton);
        View nextButton = findViewById(R.id.nextButton);

        previousButton.setVisibility(currentStep == 1 ? View.INVISIBLE : View.VISIBLE);
        ((TextView) nextButton).setText(currentStep == 3 ? "Submit" : "Next");
    }

    private void updateFormVisibility() {
        View step1Layout = findViewById(R.id.step1Layout);
        View step2Layout = findViewById(R.id.step2Layout);
        View step3Layout = findViewById(R.id.step3Layout);

        step1Layout.setVisibility(currentStep == 1 ? View.VISIBLE : View.GONE);
        step2Layout.setVisibility(currentStep == 2 ? View.VISIBLE : View.GONE);
        step3Layout.setVisibility(currentStep == 3 ? View.VISIBLE : View.GONE);
    }

    private void submitEvent() {
        // TODO: Implement event submission
        finish();
    }
}
