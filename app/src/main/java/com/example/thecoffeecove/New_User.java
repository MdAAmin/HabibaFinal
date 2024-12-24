package com.example.thecoffeecove;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class New_User extends AppCompatActivity {

    private final String USERNAME_REGEX = "^[a-zA-Z0-9._-]{3,15}$"; // Alphanumeric, 3-15 characters
    private final String EMAIL_REGEX = "^[\\w-\\.]+@[\\w-]+\\.[a-z]{2,4}$"; // Standard email pattern
    private final String PASSWORD_REGEX = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@#$%^&+=])[A-Za-z\\d@#$%^&+=]{8}$"; // 8 chars, includes uppercase, lowercase, digit, and special char
    private final String PHONE_REGEX = "^01[3-9][0-9]{8}$"; // Valid phone number pattern for Bangladesh

    Database_Helper dbHelper;

    EditText etUsername, etEmail, etPassword, etConfirmPassword, etMobile;
    Button btnSubmit, btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user);

        dbHelper = new Database_Helper(this);

        etUsername = findViewById(R.id.et_register_username);
        etEmail = findViewById(R.id.et_register_email);
        etPassword = findViewById(R.id.et_register_password);
        etConfirmPassword = findViewById(R.id.et_register_confirm_password);
        etMobile = findViewById(R.id.et_register_mobile);

        btnSubmit = findViewById(R.id.btn_submit);
        btnBack = findViewById(R.id.btn_Back);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = etUsername.getText().toString().trim();
                String email = etEmail.getText().toString().trim();
                String password = etPassword.getText().toString();
                String confirmPassword = etConfirmPassword.getText().toString();
                String mobile = etMobile.getText().toString().trim();

                if (validateInputs(username, email, password, confirmPassword, mobile)) {
                    boolean success = dbHelper.addUser(username, password, email, mobile);
                    if (success) {
                        Toast.makeText(New_User.this, "User registered successfully", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(New_User.this, Facilities.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(New_User.this, "Failed to register user", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(New_User.this, Showing.class);
                startActivity(intent);
            }
        });
    }

    private boolean validateInputs(String username, String email, String password, String confirmPassword, String mobile) {
        if (username.isEmpty()) {
            etUsername.setError("Username is required");
            etUsername.requestFocus();
            return false;
        }
        if (!username.matches(USERNAME_REGEX)) {
            etUsername.setError("Username must be 3-15 characters, alphanumeric with '.', '_', or '-'");
            etUsername.requestFocus();
            return false;
        }
        if (email.isEmpty()) {
            etEmail.setError("Email is required");
            etEmail.requestFocus();
            return false;
        }
        if (!email.matches(EMAIL_REGEX)) {
            etEmail.setError("Enter a valid email address");
            etEmail.requestFocus();
            return false;
        }
        if (password.isEmpty()) {
            etPassword.setError("Password is required");
            etPassword.requestFocus();
            return false;
        }
        if (!password.matches(PASSWORD_REGEX)) {
            etPassword.setError("Password must be exactly 8 characters, include uppercase, lowercase, digit, and special character");
            etPassword.requestFocus();
            return false;
        }
        if (confirmPassword.isEmpty()) {
            etConfirmPassword.setError("Confirm Password is required");
            etConfirmPassword.requestFocus();
            return false;
        }
        if (!password.equals(confirmPassword)) {
            etConfirmPassword.setError("Passwords do not match");
            etConfirmPassword.requestFocus();
            return false;
        }
        if (mobile.isEmpty()) {
            etMobile.setError("Mobile number is required");
            etMobile.requestFocus();
            return false;
        }
        if (!mobile.matches(PHONE_REGEX)) {
            etMobile.setError("Enter a valid Bangladeshi mobile number starting with 013-019");
            etMobile.requestFocus();
            return false;
        }
        return true;
    }
}
