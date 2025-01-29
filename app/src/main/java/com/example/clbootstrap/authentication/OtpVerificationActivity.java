package com.example.clbootstrap.authentication;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.example.clbootstrap.R;
import com.example.clbootstrap.campus_choice.CampusChoiceActivity;
import com.example.clbootstrap.views.TimerView;

public class OtpVerificationActivity extends AppCompatActivity {
    private EditText[] otpDigits = new EditText[6];
    private TextView resendOtp;
    private AppCompatButton proceedButton;
    private ImageButton backButton;
    private TimerView timerView;
    private CountDownTimer timer;
    private static final long TIMER_DURATION = 120000; // 2 minutes

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_otp_verification);

        // Initialize views
        otpDigits[0] = findViewById(R.id.otpDigit1);
        otpDigits[1] = findViewById(R.id.otpDigit2);
        otpDigits[2] = findViewById(R.id.otpDigit3);
        otpDigits[3] = findViewById(R.id.otpDigit4);
        otpDigits[4] = findViewById(R.id.otpDigit5);
        otpDigits[5] = findViewById(R.id.otpDigit6);
        resendOtp = findViewById(R.id.resendOtp);
        proceedButton = findViewById(R.id.proceedButton);
        backButton = findViewById(R.id.backButton);
        timerView = findViewById(R.id.timerView);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(android.R.id.content), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Set up OTP input handling
        setupOtpInputs();

        // Set up click listeners
        backButton.setOnClickListener(v -> finish());
        resendOtp.setOnClickListener(v -> handleResendOtp());
        proceedButton.setOnClickListener(v -> handleProceed());

        // Initially disable proceed button and resend
        proceedButton.setEnabled(false);
        proceedButton.setAlpha(0.5f);
        resendOtp.setEnabled(false);
        resendOtp.setAlpha(0.5f);

        // Start timer
        startTimer();
    }

    private void startTimer() {
        if (timer != null) {
            timer.cancel();
        }

        timer = new CountDownTimer(TIMER_DURATION, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                long seconds = millisUntilFinished / 1000;
                String timeText = String.format("%02d:%02d", seconds / 60, seconds % 60);
                timerView.setTimeText(timeText);
                timerView.setProgress(1 - (float) millisUntilFinished / TIMER_DURATION);
            }

            @Override
            public void onFinish() {
                timerView.setTimeText("00:00");
                timerView.setProgress(1f);
                resendOtp.setEnabled(true);
                resendOtp.setAlpha(1f);
            }
        }.start();
    }

    private void setupOtpInputs() {
        for (int i = 0; i < 6; i++) {
            final int currentIndex = i;
            otpDigits[i].addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (s.length() == 1 && currentIndex < 5) {
                        // Move to next digit
                        otpDigits[currentIndex + 1].requestFocus();
                    } else if (s.length() == 0 && currentIndex > 0) {
                        // Move to previous digit
                        otpDigits[currentIndex - 1].requestFocus();
                    }
                    validateOtp();
                }

                @Override
                public void afterTextChanged(Editable s) {}
            });
        }
    }

    private void validateOtp() {
        StringBuilder otp = new StringBuilder();
        for (EditText digit : otpDigits) {
            otp.append(digit.getText().toString());
        }
        boolean isValid = otp.length() == 6;
        proceedButton.setEnabled(isValid);
        proceedButton.setAlpha(isValid ? 1.0f : 0.5f);
    }

    private void handleResendOtp() {
        resendOtp.setEnabled(false);
        resendOtp.setAlpha(0.5f);
        startTimer();
        // TODO: Implement actual OTP resend logic
    }

    private void handleProceed() {
        StringBuilder otp = new StringBuilder();
        for (EditText digit : otpDigits) {
            otp.append(digit.getText().toString());
        }
        
        // TODO: Add actual OTP verification here
        String firstName = getIntent().getStringExtra("first_name");
        startActivity(new Intent(this, CampusChoiceActivity.class)
            .putExtra("first_name", firstName));
        finish(); // Close OTP screen
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (timer != null) {
            timer.cancel();
        }
    }
}
