package com.example.thecoffeecove;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class UpdateCoffeeActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;

    private EditText editTextName;
    private EditText editTextPrice;
    private EditText editTextQuantity;
    private ImageView imageViewProduct;
    private TextView textViewProductId;

    private Database_Helper databaseHelper;
    private byte[] productImageByteArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_coffee);

        editTextName = findViewById(R.id.edit_text_product_name);
        editTextPrice = findViewById(R.id.edit_text_product_price);
        editTextQuantity = findViewById(R.id.edit_text_product_quantity);
        imageViewProduct = findViewById(R.id.image_view_product);
        Button buttonUpdate = findViewById(R.id.button_update);
        Button buttonSelectImage = findViewById(R.id.button_select_image);
        Button buttonSearch = findViewById(R.id.button_search);
        textViewProductId = findViewById(R.id.text_view_product_id);

        databaseHelper = new Database_Helper(this);

        buttonSearch.setOnClickListener(view -> searchProduct());
        buttonSelectImage.setOnClickListener(view -> selectImage());
        buttonUpdate.setOnClickListener(view -> updateProduct());
    }

    @SuppressLint("SetTextI18n")
    private void searchProduct() {
        String productName = editTextName.getText().toString().trim();
        if (productName.isEmpty()) {
            Toast.makeText(this, "Please enter a product name to search", Toast.LENGTH_SHORT).show();
            return;
        }

        Cursor cursor = databaseHelper.getProductByName(productName);
        if (cursor != null && cursor.moveToFirst()) {
            int productId = cursor.getInt(cursor.getColumnIndexOrThrow(Database_Helper.COLUMN_ID));
            double price = cursor.getDouble(cursor.getColumnIndexOrThrow(Database_Helper.COLUMN_PRODUCT_PRICE));
            int quantity = cursor.getInt(cursor.getColumnIndexOrThrow(Database_Helper.COLUMN_PRODUCT_QUANTITY));
            byte[] image = cursor.getBlob(cursor.getColumnIndexOrThrow(Database_Helper.COLUMN_PRODUCT_IMAGE));

            editTextPrice.setText(String.valueOf(price));
            editTextQuantity.setText(String.valueOf(quantity));
            textViewProductId.setText("Product ID: " + productId);

            if (image != null) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);
                imageViewProduct.setImageBitmap(bitmap);
                productImageByteArray = image;
            }
            cursor.close();
        } else {
            Toast.makeText(this, "Product not found", Toast.LENGTH_SHORT).show();
        }
    }

    private void selectImage() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri imageUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                imageViewProduct.setImageBitmap(bitmap);
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream);
                productImageByteArray = byteArrayOutputStream.toByteArray();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void updateProduct() {
        String productName = editTextName.getText().toString().trim();
        String productPrice = editTextPrice.getText().toString().trim();
        String productQuantity = editTextQuantity.getText().toString().trim();

        if (productName.isEmpty() || productPrice.isEmpty() || productQuantity.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        double price = Double.parseDouble(productPrice);
        int quantity = Integer.parseInt(productQuantity);

        String productIdText = textViewProductId.getText().toString();
        int productId = Integer.parseInt(productIdText.replaceAll("\\D+", ""));

        databaseHelper.updateProduct(productId, productName, price, quantity, productImageByteArray);
        Toast.makeText(this, "Product updated successfully", Toast.LENGTH_SHORT).show();

        clearFields();

    }
    private void clearFields() {
        editTextName.setText("");
        editTextPrice.setText("");
        editTextQuantity.setText("");
        textViewProductId.setText("");
        textViewProductId.setVisibility(View.GONE);
        imageViewProduct.setImageResource(0);
    }
}
