package com.example.thecoffeecove;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ViewOrderCoffeeActivity extends AppCompatActivity {

    private RecyclerView recyclerViewOrder;
    private Database_Helper databaseHelper;
    private RadioGroup coffeeTypeRadioGroup;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_order_coffee);

        // Initialize views and database helper
        recyclerViewOrder = findViewById(R.id.recyclerViewOrders); // Correct reference to the RecyclerView
        coffeeTypeRadioGroup = findViewById(R.id.radio_group_order_instructions); // Radio group for selecting coffee
        Button btnBack = findViewById(R.id.btn_back);

        databaseHelper = new Database_Helper(this);

        // Set RecyclerView properties
        recyclerViewOrder.setLayoutManager(new LinearLayoutManager(this));

        // Display orders when the activity is created
        displayOrders();

        // Set up the back button
        btnBack.setOnClickListener(v -> {
            Intent intent = new Intent(ViewOrderCoffeeActivity.this, Admin_Home.class);
            startActivity(intent);
        });
    }

    private void displayOrders() {
        // Fetch all orders from the database
        Cursor cursor = databaseHelper.getAllOrders();

        // Check if the cursor is valid and has data
        if (cursor != null && cursor.getCount() > 0) {
            // Create an adapter with the cursor data
            OrderAdapter orderAdapter = new OrderAdapter(cursor);
            recyclerViewOrder.setAdapter(orderAdapter);
        } else {
            Toast.makeText(this, "No orders found", Toast.LENGTH_SHORT).show();
        }
    }
}
