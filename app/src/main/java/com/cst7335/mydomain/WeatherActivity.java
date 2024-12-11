package com.cst7335.mydomain;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

// WeatherActivity class extends AppCompatActivity and implements NavigationView.OnNavigationItemSelectedListener
public class WeatherActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    // Declare DrawerLayout and weatherTextView
    private DrawerLayout drawerLayout;
    private TextView weatherTextView;

    // Override the onCreate method
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Call the super class onCreate to complete the creation of activity like the view hierarchy
        super.onCreate(savedInstanceState);
        // Set the content view to the activity_test_toolbar layout
        setContentView(R.layout.activity_test_toolbar);

        // Initialize the toolbar
        Toolbar toolbar = findViewById(R.id.toolBar);
        // Set the toolbar as the support action bar
        setSupportActionBar(toolbar);

        // Initialize the drawerLayout and navigationView
        drawerLayout = findViewById(R.id.drawer_layout);
        // Set the navigationView setNavigationItemSelectedListener to this
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Set the navigationView setNavigationItemSelectedListener to this
        navigationView.setNavigationItemSelectedListener(this);

        // Initialize the ActionBarDrawerToggle
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle); // Add the ActionBarDrawerToggle to the drawerLayout
        toggle.syncState(); // Sync the state of the ActionBarDrawerToggle

        // Initialize the weather TextView
        weatherTextView = findViewById(R.id.weatherTextView);
        // Execute the FetchWeatherTask with the URL
        new FetchWeatherTask().execute("https://api.openweathermap.org/data/2.5/forecast?id=524901&APPID=080c60be76c8fe36e624dc83b2550bbb");
    }

    // Override the onCreateOptionsMenu method
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    // Override the onOptionsItemSelected method
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Get the item id
        int id = item.getItemId();
        // Check if the item id is the one of action_items
        if (id == R.id.action_item1) {
            Toast.makeText(this, "You clicked on item 1", Toast.LENGTH_SHORT).show();
            return true;
        } else if (id == R.id.action_item2) {
            Toast.makeText(this, "You clicked on item 2", Toast.LENGTH_SHORT).show();
            return true;
        } else if (id == R.id.action_item3) {
            Toast.makeText(this, "You clicked on item 3", Toast.LENGTH_SHORT).show();
            return true;
        } else if (id == R.id.action_settings) {
            // Handle action_settings click
            return true;
        } else {
            return super.onOptionsItemSelected(item); // Return the super class onOptionsItemSelected
        }
    }

    // Override the onNavigationItemSelected method
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId(); // Get the item id
        // Check if the item id is the one of the navigation_items
        if (id == R.id.nav_chat_page) {
            // Start the ChatActivity
            startActivity(new Intent(this, MainActivity.class));
        } else if (id == R.id.nav_weather_forecast) {
            // Start the WeatherActivity
            startActivity(new Intent(this, MainActivity.class));
        } else if (id == R.id.nav_go_back_to_login) {
            finish(); // Finish the activity
        }
        // Close the drawerLayout
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    // Override the onBackPressed method
    @Override
    public void onBackPressed() {
        // Check if the drawerLayout is open
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            // Close the drawerLayout
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed(); // Call the super class onBackPressed
        }
    }

    // Create a private class FetchWeatherTask that extends AsyncTask<String, Void, String>
    private class FetchWeatherTask extends AsyncTask<String, Void, String> {
        @Override
        // Override the doInBackground method
        protected String doInBackground(String... urls) {
            // Create a StringBuilder result
            StringBuilder result = new StringBuilder();
            // Initialize the HttpURLConnection urlConnection
            HttpURLConnection urlConnection = null;
            // Try to fetch the data from the URL
            try {
                URL url = new URL(urls[0]); // Get the URL from the first argument
                urlConnection = (HttpURLConnection) url.openConnection(); // Open the connection
                InputStream in = urlConnection.getInputStream(); // Get the input stream
                InputStreamReader reader = new InputStreamReader(in); // Initialize the InputStreamReader
                int date = reader.read(); // Read the first character
                // Read the data until the end
                while (date != -1) {
                    // Append the character to the result
                    char current = (char) date;
                    // Append the character to the result
                    result.append(current);
                    // Read the next character
                    date = reader.read();
                }
                return result.toString();
            } catch (Exception e) {
                e.printStackTrace();
                return "Failed to fetch data";
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect(); // Disconnect the connection
                }
            }
        }

        // Override the onPostExecute method
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            // Try to parse the JSON data
            try {
                // Create a JSONObject from the result
                JSONObject jsonObject = new JSONObject(result);
                // Get the list JSONArray from the jsonObject
                JSONArray listArray = jsonObject.getJSONArray("list");
                // Create a StringBuilder formattedResult
                StringBuilder formattedResult = new StringBuilder();

                // Iterate over the listArray
                for (int i = 0; i < listArray.length(); i++) {
                    // Get the listObject at index i
                    JSONObject listObject = listArray.getJSONObject(i);
                    // Get the dateTimeInMillis from the listObject
                    long dateTimeInMillis = listObject.getLong("dt") * 1000; // Convert to milliseconds
                    Date date = new Date(dateTimeInMillis); // Create a Date object from the dateTimeInMillis
                    // Create a SimpleDateFormat dateFormatter
                    SimpleDateFormat dateFormatter = new SimpleDateFormat("EEEE, MMMM d, YYYY h:mm a", Locale.getDefault());
                    String dateText = dateFormatter.format(date); // Format the date

                    // Get the main JSONObject from the listObject
                    JSONObject main = listObject.getJSONObject("main");
                    // Get the temperature from the main JSONObject
                    double temp = main.getDouble("temp") - 273.15; // Convert from Kelvin to Celsius
                    String temperature = String.format(Locale.getDefault(), "%.1f°C", temp);

                    // Get the weather JSONArray from the listObject
                    JSONArray weatherArray = listObject.getJSONArray("weather");
                    // Get the weather JSONObject from the weatherArray
                    JSONObject weather = weatherArray.getJSONObject(0);
                    // Get the description from the weather JSONObject
                    String description = weather.getString("description");

                    // Append the dateText, description, and temperature to the formattedResult
                    formattedResult.append(dateText)
                            .append("\nWeather: ").append(description)
                            .append("\nTemperature: ").append(temperature)
                            .append("\n\n");
                }

                // Set the weatherTextView text to the formattedResult
                weatherTextView.setText(formattedResult.toString());
            } catch (Exception e) {
                e.printStackTrace();
                weatherTextView.setText("Failed to parse data");
            }
        }
    }
}