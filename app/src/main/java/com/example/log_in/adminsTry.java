package com.example.log_in;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class adminsTry extends AppCompatActivity {

    Button button1, button2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admins_try);

        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);


        button2.setOnClickListener(view -> {
            Intent intent = new Intent(adminsTry.this, Receptionist.class);
            startActivity(intent);
        });
    }
}