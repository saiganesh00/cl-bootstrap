package com.example.clbootstrap.payment;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.clbootstrap.R;
import com.example.clbootstrap.timeline.TimelineActivity;

public class PaymentCheckoutActivity extends AppCompatActivity {
    private RadioGroup paymentMethodsGroup;
    private Button payButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_checkout);

        // Initialize views
        paymentMethodsGroup = findViewById(R.id.paymentMethodsGroup);
        payButton = findViewById(R.id.payButton);
        ImageButton backButton = findViewById(R.id.backButton);

        // Set up back button
        backButton.setOnClickListener(v -> finish());

        // Set up pay button
        payButton.setOnClickListener(v -> {
            showProcessingDialog();
        });
    }

    private void showProcessingDialog() {
        Dialog processingDialog = new Dialog(this);
        processingDialog.setContentView(R.layout.dialog_payment_processing);
        processingDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        processingDialog.setCancelable(false);
        processingDialog.show();

        // Simulate payment processing
        new Handler().postDelayed(() -> {
            processingDialog.dismiss();
            showSuccessDialog();
        }, 2000);
    }

    private void showSuccessDialog() {
        Dialog successDialog = new Dialog(this);
        successDialog.setContentView(R.layout.dialog_payment_success);
        successDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        successDialog.setCancelable(false);

        Button doneButton = successDialog.findViewById(R.id.doneButton);
        doneButton.setOnClickListener(v -> {
            successDialog.dismiss();
            // Launch TimelineActivity
            Intent intent = new Intent(this, TimelineActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });

        successDialog.show();
    }
}
