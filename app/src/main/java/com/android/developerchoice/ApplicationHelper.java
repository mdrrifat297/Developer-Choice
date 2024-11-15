package com.android.developerchoice;

import android.content.DialogInterface;
import android.content.Intent;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.google.firebase.auth.FirebaseAuth;

public class ApplicationHelper {
//    private void showConfirmationDialog() {
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setTitle("Confirmation");
//        builder.setMessage("Are you sure you want to log out?");
//
//        // Set up the "Yes" button
//        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                // Code to execute when "Yes" is pressed
//                Toast.makeText(MainActivity.this, "Logout", Toast.LENGTH_SHORT).show();
//                FirebaseAuth.getInstance().signOut();
//                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
//                startActivity(intent);
//                finish();
//            }
//        });
//
//        // Set up the "No" button
//        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                // Code to execute when "No" is pressed
//                dialog.dismiss();
//            }
//        });
//
//        // Add a "Dismiss" button
//        builder.setNeutralButton("Dismiss", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                dialog.dismiss(); // Close the dialog when clicked
//            }
//        });
//
//        // Make the dialog not cancelable by tapping outside
//        AlertDialog dialog = builder.create();
//        dialog.setCancelable(false);  // Prevent dismissing by tapping outside
//        dialog.show();
//    }
}
