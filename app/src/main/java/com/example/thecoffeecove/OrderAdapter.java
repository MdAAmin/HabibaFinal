package com.example.thecoffeecove;

import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {

    private Cursor cursor;

    public OrderAdapter(Cursor cursor) {
        this.cursor = cursor;
    }

    @Override
    public OrderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_list_item, parent, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(OrderViewHolder holder, int position) {
        if (cursor != null && cursor.moveToPosition(position)) {
            String productName = cursor.getString(cursor.getColumnIndex(Database_Helper.COLUMN_ORDER_PRODUCT_NAME));
            String coffeeType = cursor.getString(cursor.getColumnIndex(Database_Helper.COLUMN_ORDER_COFFEE_TYPE));
            double price = cursor.getDouble(cursor.getColumnIndex(Database_Helper.COLUMN_ORDER_PRICE));
            int quantity = cursor.getInt(cursor.getColumnIndex(Database_Helper.COLUMN_ORDER_QUANTITY));

            holder.productName.setText(productName);
            holder.coffeeType.setText(coffeeType);
            holder.price.setText(String.valueOf(price));
            holder.quantity.setText(String.valueOf(quantity));
        }
    }

    @Override
    public int getItemCount() {
        return cursor != null ? cursor.getCount() : 0;
    }

    public static class OrderViewHolder extends RecyclerView.ViewHolder {

        TextView productName, coffeeType, price, quantity;

        public OrderViewHolder(View itemView) {
            super(itemView);
            productName = itemView.findViewById(R.id.tv_order_name);
            coffeeType = itemView.findViewById(R.id.tv_coffee_type);
            price = itemView.findViewById(R.id.tv_order_price);
            quantity = itemView.findViewById(R.id.tv_order_quantity);
        }
    }
}
