package com.example.thecoffeecove;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;


public class Showing extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showing);

        // Initialize buttons
        Button signUpButton = findViewById(R.id.button_SignUp);
        Button loginButton = findViewById(R.id.button_Login);
        Button backButton = findViewById(R.id.button_Back);


        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signUpIntent = new Intent(Showing .this, New_User.class);
                startActivity(signUpIntent);
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loginIntent = new Intent(Showing .this, Old_User.class);
                startActivity(loginIntent);
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backIntent = new Intent(Showing .this, MainActivity.class);
                startActivity(backIntent);
            }
        });
    }
}
