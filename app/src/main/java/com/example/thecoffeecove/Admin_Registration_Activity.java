package com.example.thecoffeecove;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Admin_Registration_Activity extends AppCompatActivity {

    private EditText usernameEditText, passwordEditText;
    private Database_Helper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_registration);

        databaseHelper = new Database_Helper(this);

        usernameEditText = findViewById(R.id.usernameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        Button submitBtn = findViewById(R.id.submitBtn);
        Button backBtn = findViewById(R.id.backBtn);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Admin_Registration_Activity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();

                if (validateInputs(username, password)) {
                    if (databaseHelper.checkAdmin(username, password)) {
                        Toast.makeText(Admin_Registration_Activity.this, "Admin Login Successful", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Admin_Registration_Activity.this, Admin_Home.class);
                        startActivity(intent);
                    } else {
                        passwordEditText.setError("Invalid Username or Password");
                        passwordEditText.requestFocus();
                    }
                }
            }
        });
    }

    private boolean validateInputs(String username, String password) {
        String usernameRegex = "^[a-zA-Z0-9._-]{3,15}$";
        String passwordRegex = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@#$%^&+=])[A-Za-z\\d@#$%^&+=]{8}$";

        if (username.isEmpty()) {
            usernameEditText.setError("Admin name is required");
            usernameEditText.requestFocus();
            return false;
        }
        if (!username.matches(usernameRegex)) {
            usernameEditText.setError("Username must be 3-15 characters, alphanumeric with '.', '_', or '-'");
            usernameEditText.requestFocus();
            return false;
        }
        if (password.isEmpty()) {
            passwordEditText.setError("Password is required");
            passwordEditText.requestFocus();
            return false;
        }
        if (!password.matches(passwordRegex)) {
            passwordEditText.setError("Password must be exactly 8 characters, include uppercase, lowercase, digit, and special character");
            passwordEditText.requestFocus();
            return false;
        }
        return true;
    }
}
