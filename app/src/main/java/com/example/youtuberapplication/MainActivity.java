package com.example.youtuberapplication;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    TextView textView ;
    String text ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        splashTextEffect();
        Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(()->{
            Intent intent = new Intent(this,HomeActivity.class);
            startActivity(intent);
            finish();
        },5000);
    }

    private void splashTextEffect()
    {
        textView=findViewById(R.id.tvSplash);
        text = getString(R.string.an_app_for_youtubers_and_watchers);

        SpannableString spannable = new SpannableString(text);

        // Set color for "Youtubers"
        spannable.setSpan(
                new ForegroundColorSpan(Color.parseColor("#FFF14A")),
                text.indexOf("Youtubers"),
                text.indexOf("Youtubers") + "Youtubers".length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        );

        // Set color for "Watchers"
        spannable.setSpan(
                new ForegroundColorSpan(Color.parseColor("#FFF14A")),
                text.indexOf("Watchers"),
                text.indexOf("Watchers") + "Watchers".length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        );

        textView.setText(spannable);
    }
}