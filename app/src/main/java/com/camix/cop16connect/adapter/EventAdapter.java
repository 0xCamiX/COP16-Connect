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

import java.util.ArrayList;
import java.util.List;

public class EventAdapter extends ArrayAdapter<Event> {

    private Context context;
    private List<Event> events;
    private List<Location> locations;

    public EventAdapter(@NonNull Context context, @NonNull List<Event> events, @NonNull List<Location> locations) {
        super(context, 0, events);
        this.context = context;
        this.events = events != null ? events : new ArrayList<>();
        this.locations = locations != null ? locations : new ArrayList<>();
    }

    private static class ViewHolder {
        TextView tvEventTitle;
        TextView tvEventDescription;
        TextView tvEventStartDate;
        TextView tvEventEndDate;
        TextView tvEventLocation;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_event, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.tvEventTitle = convertView.findViewById(R.id.tv_event_title);
            viewHolder.tvEventDescription = convertView.findViewById(R.id.tv_event_description);
            viewHolder.tvEventStartDate = convertView.findViewById(R.id.tv_event_start_date);
            viewHolder.tvEventEndDate = convertView.findViewById(R.id.tv_event_end_date);
            viewHolder.tvEventLocation = convertView.findViewById(R.id.tv_event_location);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Event event = events.get(position);

        viewHolder.tvEventTitle.setText(event.getTitle());
        viewHolder.tvEventDescription.setText(event.getDescription());
        viewHolder.tvEventStartDate.setText(event.getStartDate());
        viewHolder.tvEventEndDate.setText(event.getEndDate());

        // Find the location by ID and set the location name and ID
        for (Location location : locations) {
            if (location.getId() == Integer.parseInt(event.getLocationId())) {
                viewHolder.tvEventLocation.setText(location.getName() + " (" + location.getId() + ")");
                break;
            }
        }

        return convertView;
    }
}