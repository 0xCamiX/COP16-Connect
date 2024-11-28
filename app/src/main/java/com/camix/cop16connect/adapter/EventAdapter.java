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
import com.camix.cop16connect.model.Event;
import com.camix.cop16connect.model.Location;

import java.util.List;

public class EventAdapter extends ArrayAdapter<Event> {

    private Context context;
    private List<Event> events;
    private List<Location> locations;

    public EventAdapter(@NonNull Context context, @NonNull List<Event> events, @NonNull List<Location> locations) {
        super(context, 0, events);
        this.context = context;
        this.events = events;
        this.locations = locations;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_event, parent, false);
        }

        Event event = events.get(position);

        TextView tvEventTitle = convertView.findViewById(R.id.tv_event_title);
        TextView tvEventDescription = convertView.findViewById(R.id.tv_event_description);
        TextView tvEventStartDate = convertView.findViewById(R.id.tv_event_start_date);
        TextView tvEventEndDate = convertView.findViewById(R.id.tv_event_end_date);
        TextView tvEventLocation = convertView.findViewById(R.id.tv_event_location);

        tvEventTitle.setText(event.getTitle());
        tvEventDescription.setText(event.getDescription());
        tvEventStartDate.setText(event.getStartDate());
        tvEventEndDate.setText(event.getEndDate());

        // Find the location by ID and set the location name and ID
        for (Location location : locations) {
            if (location.getId() == Integer.parseInt(event.getLocationId())) {
                tvEventLocation.setText(location.getName() + " (" + location.getId() + ")");
                break;
            }
        }

        return convertView;
    }
}