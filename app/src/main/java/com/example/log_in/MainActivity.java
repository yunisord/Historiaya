package com.example.log_in;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
//FOR UPDATE ONLY
public class MainActivity extends AppCompatActivity {
    private Button GetStarted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        GetStarted = findViewById(R.id.GetStarted);
        GetStarted.setOnClickListener(v -> {
            Intent intent = new Intent(this, LogIn.class);
            startActivity(intent);
        });
    }
}
