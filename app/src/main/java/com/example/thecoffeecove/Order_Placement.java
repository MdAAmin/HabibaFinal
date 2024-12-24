package com.example.thecoffeecove;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Order_Placement extends AppCompatActivity {

    private TextView textViewProductName;
    private TextView textViewProductPrice;
    private TextView textViewProductQuantity;
    private EditText editTextOrderInstructions;
    private Button buttonPlaceOrder;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_placement);

        textViewProductName = findViewById(R.id.text_view_product_name);
        textViewProductPrice = findViewById(R.id.text_view_product_price);
        textViewProductQuantity = findViewById(R.id.text_view_product_quantity);
        editTextOrderInstructions = findViewById(R.id.edit_text_order_instructions);
        buttonPlaceOrder = findViewById(R.id.button_place_order);

        Intent intent = getIntent();
        String productName = intent.getStringExtra("productName");
        double productPrice = intent.getDoubleExtra("productPrice", 0.0);
        int productQuantity = intent.getIntExtra("productQuantity", 0);

        // Set the product details to the TextViews
        textViewProductName.setText(productName);
        textViewProductPrice.setText(String.format("$%.2f", productPrice));
        textViewProductQuantity.setText("Quantity: " + productQuantity);


        buttonPlaceOrder.setOnClickListener(view -> {
            // Check if all fields are filled
            String orderInstructions = editTextOrderInstructions.getText().toString().trim();
            if (!productName.isEmpty() && productPrice != 0.0 && productQuantity != 0) {
                // Display  toast message
                Toast.makeText(Order_Placement.this, "Order successful!", Toast.LENGTH_SHORT).show();
            } else {
                //  all fields must be filled
                Toast.makeText(Order_Placement.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            }
        });
    }
}