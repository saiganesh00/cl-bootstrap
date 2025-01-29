package com.example.clbootstrap;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.webkit.WebView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.clbootstrap.authentication.LoginScreenActivity;

public class SplashActivity extends AppCompatActivity {
    private static final long SPLASH_DISPLAY_LENGTH = 4000; // 3 seconds

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        WebView webView = findViewById(R.id.splashWebView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("file:///android_asset/splash_screen/index.html");

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent mainIntent = new Intent(SplashActivity.this, LoginScreenActivity.class);
                startActivity(mainIntent);
                finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }
}
