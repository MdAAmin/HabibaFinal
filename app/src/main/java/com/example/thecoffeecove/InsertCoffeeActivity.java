package com.example.thecoffeecove;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class InsertCoffeeActivity extends AppCompatActivity {

    private EditText productNameEditText;
    private EditText productPriceEditText;
    private EditText productQuantityEditText;
    private ImageView selectedImageView;
    private Database_Helper databaseHelper;
    private byte[] imageByteArray;

    private ActivityResultLauncher<Intent> imagePickerLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_coffee);

        productNameEditText = findViewById(R.id.et_product_name);
        productPriceEditText = findViewById(R.id.et_product_price);
        productQuantityEditText = findViewById(R.id.et_product_quantity);
        selectedImageView = findViewById(R.id.iv_selected_image);
        Button selectImageButton = findViewById(R.id.btn_select_image);
        Button insertProductButton = findViewById(R.id.btn_insert_product);
        Button goBackButton = findViewById(R.id.back_button); // Ensure goBackButton is initialized

        databaseHelper = new Database_Helper(this);

        // Registering
        imagePickerLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                Uri imageUri = result.getData().getData();
                try {
                    Bitmap imageBitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                    selectedImageView.setImageBitmap(imageBitmap);
                    imageByteArray = bitmapToByteArray(imageBitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        //  button listeners
        selectImageButton.setOnClickListener(view -> showImageSelectionDialog());
        insertProductButton.setOnClickListener(view -> insertProduct());

        //  back to AdminHome
        goBackButton.setOnClickListener(v -> {
            Intent intent = new Intent(InsertCoffeeActivity.this, Admin_Home.class);
            startActivity(intent);
        });
    }

    @SuppressLint("IntentReset")
    private void showImageSelectionDialog() {
        @SuppressLint("IntentReset") Intent pickIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickIntent.setType("image/*");
        imagePickerLauncher.launch(pickIntent);
    }

    private byte[] bitmapToByteArray(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    private void insertProduct() {
        String name = productNameEditText.getText().toString();
        String priceText = productPriceEditText.getText().toString();
        String quantityText = productQuantityEditText.getText().toString();

        if (name.isEmpty() || priceText.isEmpty() || quantityText.isEmpty() || imageByteArray == null) {
            Toast.makeText(this, "Please fill all fields and select an image", Toast.LENGTH_SHORT).show();
            return;
        }

        double price = Double.parseDouble(priceText);
        int quantity = Integer.parseInt(quantityText);

        databaseHelper.insertProduct(name, price, quantity, imageByteArray);
        Toast.makeText(this, "Product inserted successfully", Toast.LENGTH_SHORT).show();

        clearFields();
    }

    private void clearFields() {
        productNameEditText.setText("");
        productPriceEditText.setText("");
        productQuantityEditText.setText("");
        selectedImageView.setImageResource(0); // Clear
        imageByteArray = null; // Clear
    }
}
