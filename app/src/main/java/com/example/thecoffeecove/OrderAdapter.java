package com.example.thecoffeecove;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.Locale;

public class OrderAdapter extends CursorAdapter {

    public OrderAdapter(Context context, Cursor cursor, int flags) {
        super(context, cursor, flags);
    }

    private static class ViewHolder {
        TextView productNameTextView;
        TextView priceTextView;
        TextView quantityTextView;
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.order_list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder();
        viewHolder.productNameTextView = view.findViewById(R.id.orderProductNameTextView);
        viewHolder.priceTextView = view.findViewById(R.id.orderPriceTextView);
        viewHolder.quantityTextView = view.findViewById(R.id.orderQuantityTextView);
        view.setTag(viewHolder);
        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ViewHolder viewHolder = (ViewHolder) view.getTag();

        @SuppressLint("Range") String productName = cursor.getString(cursor.getColumnIndex(Database_Helper.COLUMN_PRODUCT_NAME));
        @SuppressLint("Range") double price = cursor.getDouble(cursor.getColumnIndex(Database_Helper.COLUMN_PRODUCT_PRICE));
        @SuppressLint("Range") int quantity = cursor.getInt(cursor.getColumnIndex(Database_Helper.COLUMN_PRODUCT_QUANTITY));

        // Format the price as currency
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(Locale.getDefault());
        String formattedPrice = currencyFormat.format(price);

        // Set the values to the corresponding TextViews
        viewHolder.productNameTextView.setText(productName);
        viewHolder.priceTextView.setText(formattedPrice);
        viewHolder.quantityTextView.setText(String.valueOf(quantity));
    }
}
