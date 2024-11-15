package com.android.developerchoice;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class SplashActivity extends AppCompatActivity {

    boolean flag = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        ImageView logoImage = findViewById(R.id.logo_image);
        Animation fadeInAnimation = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        logoImage.startAnimation(fadeInAnimation);

        if (NetworkUtil.isInternetAvailable(this)) {
            System.out.println("Internet is available");
        } else {
            flag = false;
        }

        // Add delay to start main activity after the splash screen
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (flag) {
                    // Intent to open main activity
                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                    finish();
                } else {
                    showConfirmationDialog();
                }
            }
        }, 3000);
    }

    private void showConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Warning");
        builder.setMessage("No Internet connection!");

        // Set up the "Ok" button
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Code to execute when "Yes" is pressed
                finishAffinity();
                System.exit(0);
            }
        });

        // Make the dialog not cancelable by tapping outside
        AlertDialog dialog = builder.create();
        dialog.setCancelable(false);  // Prevent dismissing by tapping outside
        dialog.show();
    }
}
