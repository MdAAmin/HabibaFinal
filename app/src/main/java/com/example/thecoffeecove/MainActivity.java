package com.example.thecoffeecove;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LayoutInflater inflater = getLayoutInflater();
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) View layout = inflater.inflate(R.layout.backgroundtoast, findViewById(R.id.go));

        Toast customToast = new Toast(getApplicationContext());
        customToast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        customToast.setDuration(Toast.LENGTH_SHORT);
        customToast.setView(layout);

        // Find  "Get Started" button
        Button buttonGetStarted,AdminRegistration;
        buttonGetStarted = findViewById(R.id.buttonGetStarted);
        AdminRegistration= findViewById(R.id.button_Admin);

        //  for the button
        buttonGetStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // intent for Showing activity
                customToast.show();
                Intent intent = new Intent(MainActivity.this, Showing.class);
                startActivity(intent);
            }
        });
        AdminRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customToast.show();
                Intent intent = new Intent(MainActivity.this, Admin_Registration_Activity.class);
                startActivity(intent);
            }
        });
    }
}
