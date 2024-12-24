package com.example.thecoffeecove;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Menu_Display extends AppCompatActivity {

    private Database_Helper dbHelper;
    private LinearLayout menuLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_display);

        dbHelper = new Database_Helper(this);
        menuLayout = findViewById(R.id.menuLayout);
        displayMenuItems();
    }

    private void displayMenuItems() {
        Cursor cursor = dbHelper.getAllProducts();
        if (cursor == null || cursor.getCount() == 0) {
            return;
        }

        LayoutInflater inflater = LayoutInflater.from(this);

        while (cursor.moveToNext()) {
            View menuItemView = inflater.inflate(R.layout.activity_coffee_list_item, menuLayout, false);

            TextView nameTextView = menuItemView.findViewById(R.id.text_view_product_name);
            TextView priceTextView = menuItemView.findViewById(R.id.text_view_product_price);
            TextView quantityTextView = menuItemView.findViewById(R.id.text_view_product_quantity);
            ImageView menuItemImageView = menuItemView.findViewById(R.id.image_view_product);

            String name = cursor.getString(cursor.getColumnIndexOrThrow(Database_Helper.COLUMN_PRODUCT_NAME));
            double price = cursor.getDouble(cursor.getColumnIndexOrThrow(Database_Helper.COLUMN_PRODUCT_PRICE));
            int quantity = cursor.getInt(cursor.getColumnIndexOrThrow(Database_Helper.COLUMN_PRODUCT_QUANTITY));
            byte[] imageByteArray = cursor.getBlob(cursor.getColumnIndexOrThrow(Database_Helper.COLUMN_PRODUCT_IMAGE));

            nameTextView.setText(name);
            priceTextView.setText(String.format("$%.2f", price)); // Format price as currency
            quantityTextView.setText("Quantity: " + quantity);

            if (imageByteArray != null && imageByteArray.length > 0) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(imageByteArray, 0, imageByteArray.length);
                menuItemImageView.setImageBitmap(bitmap);
            } else {
                menuItemImageView.setImageResource(R.drawable.img); // Set a default image if no image exists
            }

            // Make the image view clickable
            menuItemImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Menu_Display.this, Order_Placement.class);
                    intent.putExtra("productName", name);
                    intent.putExtra("productPrice", price);
                    intent.putExtra("productQuantity", quantity);
                    startActivity(intent);
                }
            });

            menuLayout.addView(menuItemView);
        }

        cursor.close();
    }
}