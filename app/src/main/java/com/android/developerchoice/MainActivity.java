package com.android.developerchoice;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

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


        // all variable
        ImageButton openNavigation = findViewById(R.id.openNavigation);
        DrawerLayout drawerLayout = findViewById(R.id.drawerLayout);
        NavigationView  navigationView = findViewById(R.id.navigationView);


        // open  navigation drawer
        openNavigation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.open();
            }
        });

        // navigation item selected
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemID =  item.getItemId();

                if (itemID == R.id.drawerAccount) {
                    Toast.makeText(MainActivity.this, "Account", Toast.LENGTH_SHORT).show();
                } else if (itemID == R.id.drawerSQLite) {
                    startActivity(new Intent(MainActivity.this, SQLiteActivity.class));
                } else if (itemID == R.id.drawerWebsite) {
                    startActivity(new Intent(MainActivity.this, WebsiteActivity.class));
                } else if (itemID == R.id.drawerLogout) {
                    Toast.makeText(MainActivity.this, "Logout", Toast.LENGTH_SHORT).show();
                }

                drawerLayout.close();
                return false;
            }
        });
    }
}