package com.example.youtuberapplication;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SigninActivity extends AppCompatActivity {

    private EditText emailEditText, passwordEditText;
    private ImageButton backButton, closeButton, togglePasswordButton;
    private Button signinButton;
    private TextView forgotPasswordText;
    private boolean isPasswordVisible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        // === View Binding ===
        emailEditText = findViewById(R.id.email);
        passwordEditText = findViewById(R.id.password);
        backButton = findViewById(R.id.backButton);
        closeButton = findViewById(R.id.closeButton);
        togglePasswordButton = findViewById(R.id.togglePasswordVisibility);
        signinButton = findViewById(R.id.signupBtn); // ID is still 'signupBtn' in XML
        forgotPasswordText = findViewById(R.id.forgotPassword);

        // === Navigation Buttons (working as expected) ===
        backButton.setOnClickListener(v -> finish());
        closeButton.setOnClickListener(v -> finish());

        // === Password Toggle Button ===
        togglePasswordButton.setOnClickListener(v -> {
            if (isPasswordVisible) {
                passwordEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                togglePasswordButton.setImageResource(R.drawable.ic_visibility_off);
            } else {
                passwordEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                togglePasswordButton.setImageResource(R.drawable.ic_visibility);
            }
            passwordEditText.setSelection(passwordEditText.getText().length());
            isPasswordVisible = !isPasswordVisible;
        });

        // === Sign-In Logic (TEMPORARILY DISABLED for design phase) ===
        /*
        signinButton.setOnClickListener(v -> {
            String email = emailEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString();

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(SigninActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            // TODO: Replace with real auth later
            if (email.equals("user@example.com") && password.equals("password123")) {
                Toast.makeText(SigninActivity.this, "Sign-in successful", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(SigninActivity.this, DashboardActivity.class));
                finish();
            } else {
                Toast.makeText(SigninActivity.this, "Invalid credentials", Toast.LENGTH_SHORT).show();
            }
        });
        */

        // === Forgot Password Click (TEMPORARILY DISABLED) ===
        /*
        forgotPasswordText.setOnClickListener(v -> {
            Intent intent = new Intent(SigninActivity.this, ForgotPasswordActivity.class);
            startActivity(intent);
        });
        */
    }
}
