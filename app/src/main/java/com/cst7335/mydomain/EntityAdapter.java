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

public class EntityAdapter extends BaseAdapter {

    private Context context;
    private List<Entity> entityList;

    public EntityAdapter(Context context, List<Entity> entityList) {
        this.context = context;
        this.entityList = entityList;
    }

    @Override
    public int getCount() {
        return entityList.size();
    }

    @Override
    public Object getItem(int position) {
        return entityList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.entity_item, parent, false);
        }

        ImageView entityIcon = convertView.findViewById(R.id.entityIcon);
        TextView entityName = convertView.findViewById(R.id.entityName);

        Entity entity = (Entity) getItem(position);
        entityName.setText(entity.getName());

        String iconName = "entity_" + entity.getType();
        int resId = context.getResources().getIdentifier(iconName, "drawable", context.getPackageName());
        if (resId != 0) {
            entityIcon.setImageResource(resId);
        } else {
            entityIcon.setImageResource(R.drawable.default_icon); // Fallback icon
            Log.w("EntityAdapter", "Icon not found for entity type: " + entity.getType());
        }

        return convertView;
    }
}


