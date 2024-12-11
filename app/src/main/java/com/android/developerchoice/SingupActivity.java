package com.android.developerchoice;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
//import java.util.regex.Pattern;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class SingupActivity extends AppCompatActivity {

    private AlertDialog progressDialog;
//    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_singup);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.activity_singup), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        EditText nameField = findViewById(R.id.nameField);
        EditText emailField = findViewById(R.id.emailField);
        EditText passwordField = findViewById(R.id.passwordField);
        Button singupButton = findViewById(R.id.singupButton);
        TextView loginText = findViewById(R.id.loginText);


        singupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = String.valueOf(nameField.getText());
                String userEmail = String.valueOf(emailField.getText());
                String userPassword = String.valueOf(passwordField.getText());
                FirebaseAuth mAuth = FirebaseAuth.getInstance();

                if (userEmail.isEmpty()) {
                    emailField.setError("Email is required");
                    emailField.requestFocus();
                    return;
                }

                if (userPassword.isEmpty()) {
                    passwordField.setError("Password is required");
                    passwordField.requestFocus();
                    return;
                }

                if (userName.isEmpty()) {
                    nameField.setError("Password is required");
                    nameField.requestFocus();
                    return;
                }

//                if (!isValidEmail(userEmail)) {
//                    emailField.setError("Invalid email address!");
//                    emailField.requestFocus();
//                    return;
//                }

                showProgressDialog();

                // keyboard dismiss
                InputMethodManager imm = (InputMethodManager) getSystemService(LoginActivity.INPUT_METHOD_SERVICE);
                View currentFocus = getCurrentFocus();
                if (currentFocus != null) imm.hideSoftInputFromWindow(currentFocus.getWindowToken(), 0);

                mAuth.createUserWithEmailAndPassword(userEmail, userPassword)
                        .addOnCompleteListener(SingupActivity.this, task -> {
                            if (task.isSuccessful()) {
                                // User creation success
                                FirebaseUser user = mAuth.getCurrentUser();
                                Toast.makeText(SingupActivity.this, "User created successfully!", Toast.LENGTH_SHORT).show();

                                // save the login data
                                SharedPreferences sharedPreferences = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putBoolean("isLoggedIn", true);
                                editor.putString("userEmail", userEmail);
                                editor.apply();

                                dismissProgressDialog();

                                // navigate to main activity
                                Intent intent = new Intent(SingupActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            } else {
                                // If sign up fails, display a message to the user.
                                Toast.makeText(SingupActivity.this, "Authentication failed: " + task.getException().getMessage(),
                                        Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

        loginText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SingupActivity.this, LoginActivity.class));
                finish();
            }
        });
    }

//    public static boolean isValidEmail(String email) {
//        return Pattern.matches(EMAIL_REGEX, email);
//    }

    private void showProgressDialog() {
        // Inflate the custom layout
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.progress_dialog, null);

        // Build the dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogView);
        builder.setCancelable(false); // Prevent the dialog from being dismissed by the back button

        // Create and show the dialog
        progressDialog = builder.create();
        progressDialog.show();
    }

    private void dismissProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }
}