package com.example.youtuberapplication;

import android.os.Bundle;
import android.text.Html;
import android.text.InputType;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SignupActivity extends AppCompatActivity {

    private boolean isPasswordVisible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        TextView termsText = findViewById(R.id.termsText);
        String styledText = "By tapping Sign up you accept all <font color='#FFB10B'>terms</font> and <font color='#FFB10B'>conditions</font>";
        termsText.setText(Html.fromHtml(styledText, Html.FROM_HTML_MODE_LEGACY));

        EditText passwordField = findViewById(R.id.password);
        ImageButton toggleButton = findViewById(R.id.togglePasswordVisibility);

        toggleButton.setOnClickListener(v -> {
            if (isPasswordVisible) {
                // Hide password
                passwordField.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                toggleButton.setImageResource(R.drawable.ic_visibility_off);
            } else {
                // Show password
                passwordField.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                toggleButton.setImageResource(R.drawable.ic_visibility);
            }
            passwordField.setSelection(passwordField.getText().length()); // keep cursor at end
            isPasswordVisible = !isPasswordVisible;
        });
    }
}
