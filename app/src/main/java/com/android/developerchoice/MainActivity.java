package com.android.developerchoice;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import android.widget.VideoView;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.messaging.FirebaseMessaging;

public class MainActivity extends AppCompatActivity {

    private final String CHANNEL_ID = "example_channel_id";
    private final int NOTIFICATION_ID = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.drawerLayout), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        FirebaseMessaging.getInstance().subscribeToTopic("App Info");

        // Create the notification channel
        createNotificationChannel();


        // all variable
        ImageButton openNavigation = findViewById(R.id.openNavigation);
        DrawerLayout drawerLayout = findViewById(R.id.drawerLayout);
        NavigationView  navigationView = findViewById(R.id.navigationView);
        Button openNotification = findViewById(R.id.openNotification);


        // open  navigation drawer
        openNavigation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.open();
            }
        });

        openNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNotification();
            }
        });


        // navigation item selected
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemID =  item.getItemId();

                if (itemID == R.id.drawerAccount)
                    startActivity(new Intent(MainActivity.this, AccountActivity.class));

                else if (itemID == R.id.drawerSQLite)
                    startActivity(new Intent(MainActivity.this, SQLiteActivity.class));

                else if (itemID == R.id.drawerSensor)
                    startActivity(new Intent(MainActivity.this, SensorActivity.class));

                else if (itemID == R.id.drawerCamera)
                    startActivity(new Intent(MainActivity.this, CameraActivity.class));

                else if (itemID == R.id.drawerButtomNav)
                    startActivity(new Intent(MainActivity.this, ButtomNavActivity.class));

                else if (itemID == R.id.drawerAds)
                    startActivity(new Intent(MainActivity.this, AdsActivity.class));

                else if (itemID == R.id.drawerWebsite)
                    startActivity(new Intent(MainActivity.this, WebsiteActivity.class));

                else if (itemID == R.id.drawerLogout)
                    showConfirmationDialog();

                drawerLayout.close();
                return false;
            }
        });
    }

    @Override
    protected void onStop() {
        // Toast.makeText(MainActivity.this, "Exit", Toast.LENGTH_SHORT).show();
        super.onStop();
    }

    // logout confram dialog
    private void showConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirmation");
        builder.setMessage("Are you sure you want to log out?");

        // Set up the "Yes" button
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // login information delate
                SharedPreferences sharedPreferences = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("isLoggedIn", false);
                editor.apply();

                // navigate the login activity
                Toast.makeText(MainActivity.this, "Logout", Toast.LENGTH_SHORT).show();
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        // Set up the "No" button
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Code to execute when "No" is pressed
                dialog.dismiss();
            }
        });

        // Add a "Dismiss" button
        builder.setNeutralButton("Dismiss", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        // Make the dialog not cancelable by tapping outside
        AlertDialog dialog = builder.create();
        dialog.setCancelable(false);  // Prevent dismissing by tapping outside
        dialog.show();
    }

    // notification channel creat
    private void createNotificationChannel() {
        // Only create a channel on Android 8.0 (API level 26) and higher
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Example Channel";
            String description = "This is a description of the example channel";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);

            // Register the channel with the system
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }
    }

    // show notification
    private void showNotification() {
        // Create an intent to open MainActivity when the notification is tapped
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        // Build the notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.app_logo_circle) // Replace with your app's icon
                .setContentTitle("Hello, World!")
                .setContentText("This is a notification example.")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true); // Dismiss notification when tapped

        // Show the notification
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (notificationManager != null) {
            notificationManager.notify(NOTIFICATION_ID, builder.build());
        }
    }
}