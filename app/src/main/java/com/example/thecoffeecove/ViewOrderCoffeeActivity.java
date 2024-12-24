package com.example.thecoffeecove;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

public class ViewOrderCoffeeActivity extends AppCompatActivity {

    private ListView orderListView;
    private Database_Helper databaseHelper;
    private OrderAdapter orderAdapter;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_order_coffee);

        // Initialize ListView and Button views
        orderListView = findViewById(R.id.orderListView);
        Button buttonBack = findViewById(R.id.btn_back8);

        // Set the OnClickListener for the back button
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();  // Simply finish the activity to return to the previous screen
            }
        });

        // Initialize DatabaseHelper
        databaseHelper = new Database_Helper(this);

        // Get orders from database and set up the adapter
        loadOrders();
    }

    private void loadOrders() {
        // Query to get all orders from the database
        Cursor cursor = databaseHelper.getAllOrders();

        if (cursor != null) {
            // Initialize and set the adapter for the ListView
            orderAdapter = new OrderAdapter(this, cursor, 0);
            orderListView.setAdapter(orderAdapter);
        }
    }
}
