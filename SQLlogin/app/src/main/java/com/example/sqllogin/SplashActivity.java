package com.example.sqllogin;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    private static final int SPLASH_TIME_OUT = 2000; // Waktu tampilan splash screen (dalam milidetik)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // Mengatur tindakan setelah selesai tampilan splash screen
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Menjalankan tindakan setelah SPLASH_TIME_OUT
                Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        }, SPLASH_TIME_OUT);
    }
}