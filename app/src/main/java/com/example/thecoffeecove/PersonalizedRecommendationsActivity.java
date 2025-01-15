package com.example.thecoffeecove;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class PersonalizedRecommendationsActivity extends AppCompatActivity {

    private TextView recommendationsTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_personalized_recommendations);

        recommendationsTextView = findViewById(R.id.recommendationsTextView);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"})
        Button backButton = findViewById(R.id.btn_back10);

        // Load and display recommendations
        loadRecommendations();

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate back to UserConnectionActivity
                Intent intent1 = new Intent(PersonalizedRecommendationsActivity.this, Facilities.class);
                startActivity(intent1);
            }
        });
    }

    private void loadRecommendations() {
        // Sample recommendations
        String[] recommendations = {
                "Try our signature Hazelnut Latte!",
                "How about a refreshing Iced Caramel Macchiato?",
                "Have you tasted our rich Mocha Frappuccino yet?",
                "Our Vanilla Cold Brew is a customer favorite!",
                "Enjoy a classic Espresso shot for a quick pick-me-up!",
                "Don't miss out on our exclusive Signature NUt Coffee!"
        };

        StringBuilder recommendationsBuilder = new StringBuilder();
        for (String recommendation : recommendations) {
            recommendationsBuilder.append(recommendation).append("\n\n");
        }

        recommendationsTextView.setText(recommendationsBuilder.toString());
    }
}

