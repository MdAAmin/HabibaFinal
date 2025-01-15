package com.example.thecoffeecove;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CoffeeAdapter extends CursorAdapter {

    public CoffeeAdapter(Context context, Cursor cursor, int flags) {
        super(context, cursor, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        // Inflate the row layout for each item
        LayoutInflater inflater = LayoutInflater.from(context);
        return inflater.inflate(R.layout.activity_coffee_list_item, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        // Find views in the layout
        TextView nameTextView = view.findViewById(R.id.text_view_product_name);
        TextView priceTextView = view.findViewById(R.id.text_view_product_price);
        TextView quantityTextView = view.findViewById(R.id.text_view_product_quantity);
        ImageView productImageView = view.findViewById(R.id.image_view_product);

        // Extract data from the cursor
        String name = cursor.getString(cursor.getColumnIndexOrThrow(Database_Helper.COLUMN_PRODUCT_NAME));
        double price = cursor.getDouble(cursor.getColumnIndexOrThrow(Database_Helper.COLUMN_PRODUCT_PRICE));
        int quantity = cursor.getInt(cursor.getColumnIndexOrThrow(Database_Helper.COLUMN_PRODUCT_QUANTITY));
        byte[] imageBytes = cursor.getBlob(cursor.getColumnIndexOrThrow(Database_Helper.COLUMN_PRODUCT_IMAGE));

        // Set data to the views
        nameTextView.setText("Name: " + name);
        priceTextView.setText(String.format("Price: $%.2f", price)); // Corrected formatting
        quantityTextView.setText("Quantity: " + quantity);

        // Decode and set the product image, handle null image case
        if (imageBytes != null && imageBytes.length > 0) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
            productImageView.setImageBitmap(bitmap);
        } else {
            // Set a default image or placeholder if imageBytes is null
            productImageView.setImageResource(R.drawable.img);
        }
    }
}