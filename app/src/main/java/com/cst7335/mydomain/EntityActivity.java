package com.cst7335.mydomain;

import android.os.Bundle;
import android.widget.ListAdapter;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

// EntityActivity class extends AppCompatActivity
public class EntityActivity extends AppCompatActivity {
    // Declare entityListView and entityList
    private ListView entityListView;
    private List<Entity> entityList = new ArrayList<>();

    // Override the onCreate method
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Call the super class onCreate to complete the creation of activity like the view hierarchy
        super.onCreate(savedInstanceState);
        // Set the content view to the activity_entity layout
        setContentView(R.layout.activity_entity);
        // Initialize the entityListView
        entityListView = findViewById(R.id.entityListView);
        // Load JSON data from the assets folder
        loadJSONFromAsset();
        // Create an EntityAdapter and set it to the entityListView
        EntityAdapter adapter = new EntityAdapter(this, entityList);
        // Set the adapter to the entityListView
        entityListView.setAdapter((ListAdapter) adapter);
    }

    // Method to load JSON data from the assets folder
    private void loadJSONFromAsset() {
        // Try to load JSON data from the assets folder
        try {
            // Get the input stream from the assets folder
            InputStream is = getAssets().open("entities.json");
            // Get the size of the input stream
            int size = is.available();
            // Create a buffer to read the input stream
            byte[] buffer = new byte[size];
            // Read the input
            is.read(buffer);
            // Close the input stream
            is.close();

            // Convert the buffer to a string
            String jsonContent = new String(buffer, "UTF-8");
            // Create a JSON array from the JSON content
            JSONArray jsonArray = new JSONArray(jsonContent);
            // Loop through the JSON array
            for (int i = 0; i < jsonArray.length(); i++) {
                // Get the JSON object at the specified index
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                // Create an Entity object from the JSON object
                Entity entity = new Entity(
                        jsonObject.getInt("type"),
                        jsonObject.getString("name"),
                        jsonObject.getString("text_type")
                );
                entityList.add(entity); // Add the entity to the entityList
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}



