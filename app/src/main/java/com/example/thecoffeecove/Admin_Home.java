package com.example.thecoffeecove;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Admin_Home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);

        // Initialize buttons
        Button btnInsertCoffee = findViewById(R.id.btn_insert_product);
        Button btnViewCoffee = findViewById(R.id.btn_view_product);
        Button btnUpdateCoffee = findViewById(R.id.btn_update_product);
        Button btnDeleteCoffee = findViewById(R.id.btn_delete_product);
        Button btnViewOrderCoffee = findViewById(R.id.btn_view_order_product); // Add this line for the new button
        Button btnBack = findViewById(R.id.btn_back4);

        // Set up onClickListeners for buttons
        btnInsertCoffee.setOnClickListener(v -> {
            Intent intent = new Intent(Admin_Home.this, InsertCoffeeActivity.class);
            startActivity(intent);
        });

        btnViewCoffee.setOnClickListener(v -> {
            Intent intent = new Intent(Admin_Home.this, ViewCoffeeActivity.class);
            startActivity(intent);
        });

        btnUpdateCoffee.setOnClickListener(v -> {
            Intent intent = new Intent(Admin_Home.this, UpdateCoffeeActivity.class);
            startActivity(intent);
        });

        btnDeleteCoffee.setOnClickListener(v -> {
            Intent intent = new Intent(Admin_Home.this, DeleteCoffeeActivity.class);
            startActivity(intent);
        });

        // New View Orders button logic
        btnViewOrderCoffee.setOnClickListener(v -> {
            Intent intent = new Intent(Admin_Home.this, ViewOrderCoffeeActivity.class); // Add the new activity
            startActivity(intent);
        });

        // Back button logic
        btnBack.setOnClickListener(v -> {
            Intent intent = new Intent(Admin_Home.this, MainActivity.class);
            startActivity(intent);
        });
    }
}
