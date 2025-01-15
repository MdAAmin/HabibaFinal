package com.example.thecoffeecove;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.AuthResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class Old_User extends AppCompatActivity {

    private EditText etUsername, etPassword;
    private Button btnLogin, btnBack;
    private ProgressBar progressBar;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_old_user);

        auth = FirebaseAuth.getInstance();

        etUsername = findViewById(R.id.usernameEditText);
        etPassword = findViewById(R.id.passwordEditText);
        btnLogin = findViewById(R.id.submitBtn);
        btnBack = findViewById(R.id.backBtn);
        progressBar = findViewById(R.id.progressBar); // Assuming the ProgressBar exists in the layout

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = etUsername.getText().toString().trim();
                String password = etPassword.getText().toString().trim();

                if (validateInputs(username, password)) {
                    progressBar.setVisibility(View.VISIBLE); // Show progress bar

                    auth.signInWithEmailAndPassword(username, password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(Task<AuthResult> task) {
                                    progressBar.setVisibility(View.GONE); // Hide progress bar
                                    if (task.isSuccessful()) {
                                        FirebaseUser user = auth.getCurrentUser();
                                        if (user != null) {
                                            if (user.isEmailVerified()) {
                                                // User is verified, proceed to next activity
                                                Toast.makeText(Old_User.this, "Login successful", Toast.LENGTH_SHORT).show();
                                                Intent intent = new Intent(Old_User.this, Facilities.class);
                                                startActivity(intent);
                                                finish(); // Close current activity
                                            } else {
                                                Toast.makeText(Old_User.this, "Please verify your email first", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    } else {
                                        Toast.makeText(Old_User.this, "Authentication failed. Try again.", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });

        btnBack.setOnClickListener(v -> {
            Intent intent = new Intent(Old_User.this, Showing.class); // Replace with your previous activity
            startActivity(intent);
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
