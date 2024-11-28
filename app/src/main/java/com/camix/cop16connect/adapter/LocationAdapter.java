package com.camix.cop16connect.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.camix.cop16connect.R;
import com.camix.cop16connect.model.Location;
import java.util.List;

public class LocationAdapter extends ArrayAdapter<Location> {

    private Context context;
    private List<Location> locations;

    public LocationAdapter(@NonNull Context context, @NonNull List<Location> locations) {
        super(context, 0, locations);
        this.context = context;
        this.locations = locations;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_location, parent, false);
        }

        Location location = locations.get(position);

        TextView tvLocationName = convertView.findViewById(R.id.tv_location_name);
        TextView tvLocationDescription = convertView.findViewById(R.id.tv_location_description);
        TextView tvLocationLatitude = convertView.findViewById(R.id.tv_location_latitude);
        TextView tvLocationLongitude = convertView.findViewById(R.id.tv_location_longitude);

        tvLocationName.setText(location.getName());
        tvLocationDescription.setText(location.getDescription());
        tvLocationLatitude.setText(location.getLatitude());
        tvLocationLongitude.setText(location.getLongitude());

        return convertView;
    }
}