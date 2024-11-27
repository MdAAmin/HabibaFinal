package com.example.thecoffeecove;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.thecoffeecove.activity_insert_coffee;
import com.example.thecoffeecove.activity_view_coffee;

public class activity_admin_home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);

        Button btnInsertCoffee = findViewById(R.id.btn_insert_product);
        Button btnViewCoffee = findViewById(R.id.btn_view_product);

        btnInsertCoffee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentInsert = new Intent(activity_admin_home.this, activity_insert_coffee.class);
                startActivity(intentInsert);
            }
        });

        btnViewCoffee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentView = new Intent(activity_admin_home.this, activity_view_coffee.class);
                startActivity(intentView);
            }
        });
    }
}