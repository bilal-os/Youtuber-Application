package com.example.youtuberapplication;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.text.Html;
import android.widget.TextView;

public class SignupActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        TextView termsText = findViewById(R.id.termsText);
        String styledText = "By tapping Sign up you accept all <font color='#FFB10B'>terms</font> and <font color='#FFB10B'>conditions</font>";
        termsText.setText(Html.fromHtml(styledText, Html.FROM_HTML_MODE_LEGACY));

    }
}
