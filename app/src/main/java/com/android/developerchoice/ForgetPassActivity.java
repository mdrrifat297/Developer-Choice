package com.android.developerchoice;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;

public class ForgetPassActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private AlertDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_forget_pass);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // variable
        EditText emailField = findViewById(R.id.emailField);
        Button sentEmail = findViewById(R.id.sentEmail);
        TextView continueLogin = findViewById(R.id.continueLogin);

        // Initialize FirebaseAuth
        auth = FirebaseAuth.getInstance();

        sentEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userEmail = String.valueOf(emailField.getText());

                if (userEmail.isEmpty()) {
                    emailField.setError("Email is required");
                    emailField.requestFocus();
                    return;
                }

                showProgressDialog();

                // keyboard dismiss
                InputMethodManager imm = (InputMethodManager) getSystemService(LoginActivity.INPUT_METHOD_SERVICE);
                View currentFocus = getCurrentFocus();
                if (currentFocus != null) imm.hideSoftInputFromWindow(currentFocus.getWindowToken(), 0);

                // Send reset password email
                auth.sendPasswordResetEmail(userEmail)
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                dismissProgressDialog();
                                Toast.makeText(ForgetPassActivity.this, "Reset email sent successfully", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(ForgetPassActivity.this, LoginActivity.class);
                                startActivity(intent);
                            } else {
                                String error = task.getException() != null ? task.getException().getMessage() : "Error occurred";
                                Toast.makeText(ForgetPassActivity.this, "Error: " + error, Toast.LENGTH_LONG).show();
                            }
                        });
            }
        });


        continueLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ForgetPassActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }

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