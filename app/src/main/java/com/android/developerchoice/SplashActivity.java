package com.android.developerchoice;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        ImageView logoImage = findViewById(R.id.logo_image);
        Animation fadeInAnimation = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        logoImage.startAnimation(fadeInAnimation);

        // Add delay to start main activity after the splash screen
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Intent to open main activity
                startActivity(new Intent(SplashActivity.this, WebsiteActivity.class));
                finish();
            }
        }, 3000); // Delay for 3 seconds
    }
}
