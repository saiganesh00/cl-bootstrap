package com.example.clbootstrap.admin;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.clbootstrap.R;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class ClubDetailsActivity extends AppCompatActivity {
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_club_details);

        // Get club data from intent
        String clubName = getIntent().getStringExtra("club_name");
        String logoUriStr = getIntent().getStringExtra("club_logo");

        // Set club name
        TextView clubNameView = findViewById(R.id.clubName);
        clubNameView.setText(clubName);

        // Set club banner
        if (logoUriStr != null) {
            ImageView clubBanner = findViewById(R.id.clubBanner);
            try {
                Uri logoUri = Uri.parse(logoUriStr);
                InputStream inputStream = getContentResolver().openInputStream(logoUri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                if (bitmap != null) {
                    clubBanner.setImageBitmap(bitmap);
                }
            } catch (FileNotFoundException e) {
                Toast.makeText(this, "Unable to load club image", Toast.LENGTH_SHORT).show();
            } catch (SecurityException e) {
                Toast.makeText(this, "Permission denied to access image", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
