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

public class WeatherActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    private TextView weatherTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_toolbar);

        Toolbar toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        // Initialize the weather TextView
        weatherTextView = findViewById(R.id.weatherTextView);
        new FetchWeatherTask().execute("https://api.openweathermap.org/data/2.5/forecast?id=524901&APPID=080c60be76c8fe36e624dc83b2550bbb");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
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
            return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_chat_page) {
            startActivity(new Intent(this, MainActivity.class));
        } else if (id == R.id.nav_weather_forecast) {
            startActivity(new Intent(this, MainActivity.class));
        } else if (id == R.id.nav_go_back_to_login) {
            finish();
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private class FetchWeatherTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            StringBuilder result = new StringBuilder();
            HttpURLConnection urlConnection = null;
            try {
                URL url = new URL(urls[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                InputStream in = urlConnection.getInputStream();
                InputStreamReader reader = new InputStreamReader(in);
                int date = reader.read();
                while (date != -1) {
                    char current = (char) date;
                    result.append(current);
                    date = reader.read();
                }
                return result.toString();
            } catch (Exception e) {
                e.printStackTrace();
                return "Failed to fetch data";
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
            }
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            try {
                JSONObject jsonObject = new JSONObject(result);
                JSONArray listArray = jsonObject.getJSONArray("list");
                StringBuilder formattedResult = new StringBuilder();

                for (int i = 0; i < listArray.length(); i++) {
                    JSONObject listObject = listArray.getJSONObject(i);
                    long dateTimeInMillis = listObject.getLong("dt") * 1000; // Convert to milliseconds
                    Date date = new Date(dateTimeInMillis);
                    SimpleDateFormat dateFormatter = new SimpleDateFormat("EEEE, MMMM d, YYYY h:mm a", Locale.getDefault());
                    String dateText = dateFormatter.format(date);

                    JSONObject main = listObject.getJSONObject("main");
                    double temp = main.getDouble("temp") - 273.15; // Convert from Kelvin to Celsius
                    String temperature = String.format(Locale.getDefault(), "%.1f°C", temp);

                    JSONArray weatherArray = listObject.getJSONArray("weather");
                    JSONObject weather = weatherArray.getJSONObject(0);
                    String description = weather.getString("description");

                    formattedResult.append(dateText)
                            .append("\nWeather: ").append(description)
                            .append("\nTemperature: ").append(temperature)
                            .append("\n\n");
                }

                weatherTextView.setText(formattedResult.toString());
            } catch (Exception e) {
                e.printStackTrace();
                weatherTextView.setText("Failed to parse data");
            }
        }

    }
}