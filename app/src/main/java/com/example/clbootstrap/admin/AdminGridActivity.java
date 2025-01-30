package com.example.clbootstrap.admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import com.example.clbootstrap.R;

public class AdminGridActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageButton backButton;
    private ImageButton menuButton;
    private CardView createGroupCard;
    private CardView instructionsCard;
    private CardView approveRejectCard;
    private CardView campusMembersCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_grid);

        // Initialize views
        backButton = findViewById(R.id.backButton);
        menuButton = findViewById(R.id.menuButton);
        createGroupCard = findViewById(R.id.createGroupCard);
        instructionsCard = findViewById(R.id.instructionsCard);
        approveRejectCard = findViewById(R.id.approveRejectCard);
        campusMembersCard = findViewById(R.id.campusMembersCard);

        // Set click listeners
        backButton.setOnClickListener(v -> finish());
        menuButton.setOnClickListener(this);
        createGroupCard.setOnClickListener(this);
        instructionsCard.setOnClickListener(this);
        approveRejectCard.setOnClickListener(this);
        campusMembersCard.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        
        if (id == R.id.createGroupCard) {
            Intent intent = new Intent(this, CreateClubActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        } else if (id == R.id.instructionsCard) {
            startActivity(new Intent(this, InstructionsActivity.class));
        } else if (id == R.id.approveRejectCard) {
            startActivity(new Intent(this, ApproveRejectActivity.class));
        } else if (id == R.id.campusMembersCard) {
            startActivity(new Intent(this, CampusMembersActivity.class));
        } else if (id == R.id.menuButton) {
            showMenuOptions();
        }
    }

    private void showMenuOptions() {
        androidx.appcompat.widget.PopupMenu popup = new androidx.appcompat.widget.PopupMenu(this, menuButton);
        popup.getMenuInflater().inflate(R.menu.admin_menu, popup.getMenu());
        popup.setOnMenuItemClickListener(item -> {
            int id = item.getItemId();
            if (id == R.id.menu_settings) {
                Toast.makeText(this, "Settings clicked", Toast.LENGTH_SHORT).show();
                return true;
            } else if (id == R.id.menu_help) {
                Toast.makeText(this, "Help clicked", Toast.LENGTH_SHORT).show();
                return true;
            }
            return false;
        });
        popup.show();
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}
