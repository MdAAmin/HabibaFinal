package com.example.thecoffeecove;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Reservations extends AppCompatActivity {

    private EditText editTextName;
    private EditText editTextPhone;
    private EditText editTextGuests;
    private EditText editTextDate;
    private EditText editTextTime;
    private EditText editTextRequests;
    private Button buttonReserve;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservations);

        editTextName = findViewById(R.id.editTextName);
        editTextPhone = findViewById(R.id.editTextPhone);
        editTextGuests = findViewById(R.id.editTextGuests);
        editTextDate = findViewById(R.id.editTextDate);
        editTextTime = findViewById(R.id.editTextTime);
        editTextRequests = findViewById(R.id.editTextRequests);
        buttonReserve = findViewById(R.id.buttonReserve);

        buttonReserve.setOnClickListener(v -> {
            if (areFieldsFilled()) {
                Toast.makeText(Reservations.this, "Reservation Successful", Toast.LENGTH_SHORT).show();
                // Handle successful reservation logic
            } else {
                Toast.makeText(Reservations.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean areFieldsFilled() {
        return !editTextName.getText().toString().isEmpty() &&
                !editTextPhone.getText().toString().isEmpty() &&
                !editTextGuests.getText().toString().isEmpty() &&
                !editTextDate.getText().toString().isEmpty() &&
                !editTextTime.getText().toString().isEmpty() &&
                !editTextRequests.getText().toString().isEmpty();
    }
}