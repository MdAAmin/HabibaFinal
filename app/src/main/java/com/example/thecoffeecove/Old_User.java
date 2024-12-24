package com.example.thecoffeecove;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Old_User extends AppCompatActivity {

    Database_Helper dbHelper;

    EditText etUsername, etPassword;
    Button btnLogin, btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_old_user);

        dbHelper = new Database_Helper(this);

        etUsername = findViewById(R.id.usernameEditText);
        etPassword = findViewById(R.id.passwordEditText);

        btnLogin = findViewById(R.id.submitBtn);
        btnBack = findViewById(R.id.backBtn);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = etUsername.getText().toString().trim();
                String password = etPassword.getText().toString().trim();

                if (validateInputs(username, password)) {
                    boolean userExists = dbHelper.checkUser(username, password);
                    if (userExists) {
                        Toast.makeText(Old_User.this, "Login successful", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Old_User.this, Facilities.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(Old_User.this, "Invalid username or password", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Old_User.this, Showing.class); // Replace with your previous activity
                startActivity(intent);
            }
        });
    }

    private boolean validateInputs(String username, String password) {
        if (username.isEmpty()) {
            etUsername.setError("Username is required");
            etUsername.requestFocus();
            return false;
        }
        if (password.isEmpty()) {
            etPassword.setError("Password is required");
            etPassword.requestFocus();
            return false;
        }
        return true;
    }
}