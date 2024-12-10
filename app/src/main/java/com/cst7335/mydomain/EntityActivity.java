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

public class EntityActivity extends AppCompatActivity {

    private ListView entityListView;
    private List<Entity> entityList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entity);

        entityListView = findViewById(R.id.entityListView);

        loadJSONFromAsset();
        EntityAdapter adapter = new EntityAdapter(this, entityList);
        entityListView.setAdapter((ListAdapter) adapter);
    }

    private void loadJSONFromAsset() {
        try {
            InputStream is = getAssets().open("entities.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();

            String jsonContent = new String(buffer, "UTF-8");
            JSONArray jsonArray = new JSONArray(jsonContent);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Entity entity = new Entity(
                        jsonObject.getInt("type"),
                        jsonObject.getString("name"),
                        jsonObject.getString("text_type")
                );
                entityList.add(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}



