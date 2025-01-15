package com.example.thecoffeecove;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class ViewCoffeeActivity extends AppCompatActivity {
    private ListView listViewProducts;
    private Database_Helper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_coffee);

        listViewProducts = findViewById(R.id.list_view_products);
        Button buttonUpdate = findViewById(R.id.button_update);
        Button buttonDelete = findViewById(R.id.button_delete);
        databaseHelper = new Database_Helper(this);

        displayProducts();

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleUpdate();
            }
        });

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleDelete();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        displayProducts();
    }


    private void displayProducts() {
        Cursor cursor = databaseHelper.getAllProducts();
        CoffeeAdapter adapter = new CoffeeAdapter(this, cursor, 0);
        listViewProducts.setAdapter(adapter);
    }

    private void handleUpdate() {
        // Logic for updating
        Intent intent = new Intent(ViewCoffeeActivity.this,UpdateCoffeeActivity.class); // Assuming HomeActivity is the activity after login
        startActivity(intent);
    }
    //
    private void handleDelete() {
        Intent intent = new Intent(ViewCoffeeActivity.this, DeleteCoffeeActivity.class); // Assuming HomeActivity is the activity after login
        startActivity(intent);
        Toast.makeText(this, "Delete button clicked", Toast.LENGTH_SHORT).show();
    }
}