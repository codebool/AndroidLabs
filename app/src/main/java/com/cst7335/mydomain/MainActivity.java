package com.cst7335.mydomain;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

// MainActivity class extends AppCompatActivity
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Call the super class onCreate to complete the creation of activity like the view hierarchy
        super.onCreate(savedInstanceState);
        // Set the content view to the activity_main layout
        setContentView(R.layout.activity_main);
        // Initialize the button1
        Button button1 = findViewById(R.id.button1);
        // Set the onClickListener for button1
        button1.setOnClickListener(v -> {
            // Create an intent to start the GameActivity
            Intent intent = new Intent(MainActivity.this, GameActivity.class);
            startActivity(intent); // Start the GameActivity
        });

        // Initialize the button2
        Button button2 = findViewById(R.id.button2);
        // Set the onClickListener for button2
        button2.setOnClickListener(v -> {
            // Create an intent to start the EntityActivity
            Intent intent = new Intent(MainActivity.this, EntityActivity.class);
            startActivity(intent); // Start the EntityActivity
        });

        // Initialize the button3
        Button button3 = findViewById(R.id.button3);
        // Set the onClickListener for button3
        button3.setOnClickListener(v -> {
            // Create an intent to start the WeatherActivity
            Intent intent = new Intent(MainActivity.this, WeatherActivity.class);
            startActivity(intent); // Start the WeatherActivity
        });

        // Initialize the button4
        Button button4 = findViewById(R.id.button4);
        // Initialize the formLayout, submitFormButton, and confirmationMessage
        LinearLayout formLayout = findViewById(R.id.formLayout);
        // Set the visibility of formLayout to GONE
        Button submitFormButton = findViewById(R.id.submitFormButton);
        // Set the onClickListener for button4
        TextView confirmationMessage = findViewById(R.id.confirmationMessage);

        // Set the onClickListener for button4
        button4.setOnClickListener(v -> formLayout.setVisibility(View.VISIBLE));

        // Set the onClickListener for submitFormButton
        submitFormButton.setOnClickListener(v -> {
            // Set the visibility of formLayout to GONE
            formLayout.setVisibility(View.GONE);
            // Set the visibility of confirmationMessage to VISIBLE
            confirmationMessage.setVisibility(View.VISIBLE);

            // Use a Handler to hide the confirmationMessage after 3 seconds
            new Handler().postDelayed(() -> {
                // Set the visibility of confirmationMessage to GONE
                confirmationMessage.setVisibility(View.GONE);
            }, 3000); // 3000 milliseconds = 3 seconds
        });
    }
}