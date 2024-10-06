package com.cst7335.lab03;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText emailInput;
    private SharedPreferences sharedPreferences;
    private static final String SHARED_PREFS = "sharedPrefs";
    private static final String EMAIL = "email";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        // Initialize the SharedPreferences object
        sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        // Get the email input field
        emailInput = findViewById(R.id.emailInput);
        // login button
        Button loginButton = findViewById(R.id.loginButton);

        // Load the saved email when the activity is created
        loadEmail();

        // Set an OnClickListener on the login button to save the email when clicked
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveEmail();

                // Get the email from the EditText field
                String email = emailInput.getText().toString();

                // Create an Intent to go to ProfileActivity
                Intent goToProfile = new Intent(MainActivity.this, ProfileActivity.class);

                // Pass the email as an extra in the Intent
                goToProfile.putExtra("EMAIL", email);

                // Start ProfileActivity
                startActivity(goToProfile);
            }
        });
    }

    // Method to load the email to the SharedPreferences object
    private void loadEmail() {
        // Load the email from the SharedPreferences object
        String email = sharedPreferences.getString(EMAIL, "");
        // Set the email input field to the loaded email
        emailInput.setText(email);
    }

    // Method to save the email to the SharedPreferences object
    private void saveEmail() {
        // Get the email from the email input field
        String email = emailInput.getText().toString();
        // Create an editor object to edit the SharedPreferences object
        SharedPreferences.Editor editor = sharedPreferences.edit();
        // Save the email to the SharedPreferences object
        editor.putString(EMAIL, email);
        // Apply the changes
        editor.apply();
    }

    // Override the onPause method to ensure the email is saved when the activity pauses
    @Override
    protected void onPause() {
        super.onPause();
        saveEmail();
    }
}