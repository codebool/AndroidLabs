package com.cst7335.mydomain;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.List;

// EntityAdapter class extends BaseAdapter
public class EntityAdapter extends BaseAdapter {
    // Declare context and entityList
    private Context context;
    private List<Entity> entityList;

    // EntityAdapter constructor
    public EntityAdapter(Context context, List<Entity> entityList) {
        // Initialize context and entityList
        this.context = context;
        this.entityList = entityList;
    }

    // Override the getCount method
    @Override
    public int getCount() {
        return entityList.size();
    }

    // Override the getItem method
    @Override
    public Object getItem(int position) {
        // Return the entity at the specified position
        return entityList.get(position);
    }

    // Override the getItemId method
    @Override
    public long getItemId(int position) {
        // Return the position
        return position;
    }

    // Override the getView method
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Inflate the entity_item layout if convertView is null
        if (convertView == null) {
            // Inflate the entity_item layout
            convertView = LayoutInflater.from(context).inflate(R.layout.entity_item, parent, false);
        }

        // Initialize entityIcon and entityName
        ImageView entityIcon = convertView.findViewById(R.id.entityIcon);
        // Set the entity icon and name
        TextView entityName = convertView.findViewById(R.id.entityName);

        // Get the entity at the specified position
        Entity entity = (Entity) getItem(position);
        // Set the entity name
        entityName.setText(entity.getName());

        // Set the entity icon
        String iconName = "entity_" + entity.getType();
        // Get the resource id for the icon
        int resId = context.getResources().getIdentifier(iconName, "drawable", context.getPackageName());
        // Set the entity icon
        if (resId != 0) {
            // Set the entity icon
            entityIcon.setImageResource(resId);
        } else {
            // Fallback icon
            entityIcon.setImageResource(R.drawable.default_icon); // Fallback icon
            Log.w("EntityAdapter", "Icon not found for entity type: " + entity.getType()); // Log a warning
        }
        // Return the convertView
        return convertView;
    }
}


