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
        LayoutInflater inflater = LayoutInflater.from(context);
        return inflater.inflate(R.layout.activity_coffee_list_item, parent, false);
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView nameTextView = view.findViewById(R.id.text_view_product_name);
        TextView priceTextView = view.findViewById(R.id.text_view_product_price);
        ImageView productImageView = view.findViewById(R.id.image_view_product);

        String name = cursor.getString(cursor.getColumnIndexOrThrow(Database_Helper.COLUMN_PRODUCT_NAME));
        double price = cursor.getDouble(cursor.getColumnIndexOrThrow(Database_Helper.COLUMN_PRODUCT_PRICE));
        byte[] imageBytes = cursor.getBlob(cursor.getColumnIndexOrThrow(Database_Helper.COLUMN_PRODUCT_IMAGE));

        // Set text and image
        nameTextView.setText(name);
        priceTextView.setText(String.format("$%.2f", price));

        if (imageBytes != null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
            productImageView.setImageBitmap(bitmap);
        } else {
            productImageView.setImageResource(R.drawable.img); // Set default image
        }
    }
}