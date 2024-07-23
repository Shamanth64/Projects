package com.nkm90.HearMeWhenYouCanNotSeeMe;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class welcome_screen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_screen);

        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) Button btnLearn = findViewById(R.id.btnLearn);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) Button btnSignText = findViewById(R.id.btnSignText);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) Button btnTextSign = findViewById(R.id.btnTextSign);

        btnLearn.setOnClickListener(view -> {
            Intent intent = new Intent(this, display_all_alphabets.class);
            startActivity(intent);
        });

        btnSignText.setOnClickListener(view -> {
            Intent intent = new Intent(this, MenuActivity.class);
            startActivity(intent);
        });

        btnTextSign.setOnClickListener(view -> {
            Intent intent = new Intent(this, VideoPlayer.class);
            startActivity(intent);
        });
    }
}