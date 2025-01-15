package com.example.thecoffeecove;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Order_Placement extends AppCompatActivity {

    private TextView textViewProductName;
    private TextView textViewProductPrice;
    private TextView textViewProductQuantity;
    private RadioGroup radioGroupOrderInstructions;
    private Button buttonPlaceOrder;

    private Database_Helper dbHelper; // Corrected name for the object

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_placement);

        textViewProductName = findViewById(R.id.text_view_product_name);
        textViewProductPrice = findViewById(R.id.text_view_product_price);
        textViewProductQuantity = findViewById(R.id.text_view_product_quantity);
        radioGroupOrderInstructions = findViewById(R.id.radio_group_order_instructions);
        buttonPlaceOrder = findViewById(R.id.button_place_order);

        // Initialize the Database_Helper
        dbHelper = new Database_Helper(this); // Correct initialization

        Intent intent = getIntent();
        String productName = intent.getStringExtra("productName");
        double productPrice = intent.getDoubleExtra("productPrice", 0.0);
        int productQuantity = intent.getIntExtra("productQuantity", 0);

        textViewProductName.setText(productName);
        textViewProductPrice.setText(String.format("$%.2f", productPrice));
        textViewProductQuantity.setText("Quantity: " + productQuantity);

        buttonPlaceOrder.setOnClickListener(view -> {
            int selectedOptionId = radioGroupOrderInstructions.getCheckedRadioButtonId();

            if (selectedOptionId != -1) {
                RadioButton selectedRadioButton = findViewById(selectedOptionId);
                String selectedOption = selectedRadioButton.getText().toString();

                if (!productName.isEmpty() && productPrice != 0.0 && productQuantity != 0) {
                    // Call the insertOrder method on the initialized dbHelper object
                    boolean orderInserted = dbHelper.insertOrder(productName, productPrice, productQuantity, selectedOption);

                    if (orderInserted) {
                        Toast.makeText(Order_Placement.this, "Order successful for " + selectedOption + "!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(Order_Placement.this, "Order failed. Please try again.", Toast.LENGTH_SHORT).show();
                    }

                    // Clear the fields after placing the order
                    textViewProductName.setText("");
                    textViewProductPrice.setText("");
                    textViewProductQuantity.setText("");
                    radioGroupOrderInstructions.clearCheck();
                }
            } else {
                Toast.makeText(Order_Placement.this, "Please select an option (Home or Cove)", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

