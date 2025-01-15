package com.example.thecoffeecove;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class New_User extends AppCompatActivity {

    private EditText usernameEditText, emailEditText, passEditText, confirmPassEditText, phoneEditText;
    private Button submitButton, backButton;
    private ProgressBar progressBar;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;

    private static final String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
    private static final String PHONE_REGEX = "^01[3-9]\\d{8}$";
    private static final String PASSWORD_REGEX = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
    private static final String NAME_REGEX = "^[a-zA-Z\\s]+$"; // Regex for name validation

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user);

        // Initialize Firebase instances
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        // Initialize UI elements
        usernameEditText = findViewById(R.id.et_register_username);
        emailEditText = findViewById(R.id.et_register_email);
        passEditText = findViewById(R.id.et_register_password);
        confirmPassEditText = findViewById(R.id.et_register_confirm_password);
        phoneEditText = findViewById(R.id.et_register_mobile);
        submitButton = findViewById(R.id.btn_submit);
        backButton = findViewById(R.id.btn_Back);
        progressBar = findViewById(R.id.progressBar);

        // Submit button click event
        submitButton.setOnClickListener(v -> {
            String username = usernameEditText.getText().toString();
            String email = emailEditText.getText().toString();
            String pass = passEditText.getText().toString();
            String confirmPass = confirmPassEditText.getText().toString();
            String phone = phoneEditText.getText().toString();

            progressBar.setVisibility(View.VISIBLE);

            // Input validation
            if (!validateInputs(username, email, pass, confirmPass, phone)) {
                progressBar.setVisibility(View.GONE);
                return;
            }

            // Create a new user with Firebase Authentication
            firebaseAuth.createUserWithEmailAndPassword(email, pass)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            progressBar.setVisibility(View.GONE);

                            if (task.isSuccessful()) {
                                FirebaseUser user = firebaseAuth.getCurrentUser();
                                if (user != null) {
                                    // Send email verification
                                    user.sendEmailVerification()
                                            .addOnCompleteListener(task1 -> {
                                                if (task1.isSuccessful()) {
                                                    Toast.makeText(New_User.this, "Verification email sent!", Toast.LENGTH_SHORT).show();
                                                }
                                            });

                                    // Store user details in Firestore
                                    storeUserDetailsInFirestore(user, username, email, phone);
                                }

                                Toast.makeText(New_User.this, "User registration successful! Please verify your email.", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(New_User.this, Old_User.class));
                                finish();
                            } else {
                                Toast.makeText(New_User.this, "Registration failed! Try again.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        });

        // Back button click event
        backButton.setOnClickListener(v -> finish()); // Close the activity
    }

    private boolean validateInputs(String username, String email, String pass, String confirmPass, String phone) {
        if (username.isEmpty()) {
            setError(usernameEditText, "Username cannot be empty!");
        } else if (!username.matches(NAME_REGEX)) {
            setError(usernameEditText, "Invalid name format! Only letters and spaces are allowed.");
        } else if (email.isEmpty()) {
            setError(emailEditText, "Email cannot be empty!");
        } else if (!email.matches(EMAIL_REGEX)) {
            setError(emailEditText, "Invalid email format!");
        } else if (pass.isEmpty()) {
            setError(passEditText, "Password cannot be empty!");
        } else if (!pass.matches(PASSWORD_REGEX)) {
            setError(passEditText, "Password must contain at least one uppercase letter, one lowercase letter, one digit, and one special character, and be at least 8 characters long!");
        } else if (!pass.equals(confirmPass)) {
            setError(confirmPassEditText, "Passwords do not match!");
        } else if (phone.isEmpty()) {
            setError(phoneEditText, "Phone number cannot be empty!");
        } else if (!phone.matches(PHONE_REGEX)) {
            setError(phoneEditText, "Invalid phone number!");
        } else {
            return true;
        }
        return false;
    }

    private void setError(EditText editText, String message) {
        editText.setError(message);
        editText.requestFocus();
    }

    // Store user details in Firestore
    private void storeUserDetailsInFirestore(FirebaseUser user, String username, String email, String phone) {
        String userId = user.getUid();
        Map<String, Object> userDetails = new HashMap<>();
        userDetails.put("username", username);
        userDetails.put("email", email);
        userDetails.put("phone", phone.isEmpty() ? "No phone number" : phone);

        DocumentReference userDocRef = firebaseFirestore.collection("users").document(userId);
        userDocRef.set(userDetails)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(getApplicationContext(), "User details saved to Firestore", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(getApplicationContext(), "Failed to save user details to Firestore", Toast.LENGTH_SHORT).show();
                });
    }
}
