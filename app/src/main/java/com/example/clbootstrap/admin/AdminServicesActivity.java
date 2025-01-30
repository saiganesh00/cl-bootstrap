package com.example.clbootstrap.admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import com.example.clbootstrap.R;
import com.google.android.material.card.MaterialCardView;

public class AdminServicesActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_services);

        MaterialCardView adminServicesCard = findViewById(R.id.adminServicesCard);
        adminServicesCard.setOnClickListener(v -> {
            Intent intent = new Intent(this, AdminGridActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        });
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}
