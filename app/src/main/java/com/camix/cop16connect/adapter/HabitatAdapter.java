package com.camix.cop16connect.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.camix.cop16connect.R;
import com.camix.cop16connect.model.Habitat;

import java.util.List;

public class HabitatAdapter extends BaseAdapter {
    private Context context;
    private List<Habitat> habitats;

    public HabitatAdapter(Context context, List<Habitat> habitats) {
        this.context = context;
        this.habitats = habitats;
    }

    @Override
    public int getCount() {
        return habitats.size();
    }

    @Override
    public Object getItem(int position) {
        return habitats.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_habitat, parent, false);
        }

        Habitat habitat = habitats.get(position);

        TextView tvName = convertView.findViewById(R.id.tv_habitat_name);
        TextView tvDescription = convertView.findViewById(R.id.tv_habitat_description);

        tvName.setText(habitat.getName());
        tvDescription.setText(habitat.getDescription());

        return convertView;
    }

    public void updateHabitats(List<Habitat> habitats) {
        this.habitats = habitats;
        notifyDataSetChanged();
    }
}