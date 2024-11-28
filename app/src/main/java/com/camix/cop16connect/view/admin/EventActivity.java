package com.camix.cop16connect.view.admin;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.camix.cop16connect.R;
import com.camix.cop16connect.adapter.EventAdapter;
import com.camix.cop16connect.controller.EventController;
import com.camix.cop16connect.controller.LocationController;
import com.camix.cop16connect.model.Event;
import com.camix.cop16connect.model.Location;

import java.util.List;

public class EventActivity extends AppCompatActivity {

    private EditText etEventTitle, etEventDescription, etEventStartDate, etEventEndDate;
    private Spinner spinnerEventLocation;
    private Button btnAddEvent, btnUpdateEvent, btnDeleteEvent;
    private ListView lvEvents;
    private EventController eventController;
    private LocationController locationController;
    private List<Location> locations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        etEventTitle = findViewById(R.id.et_event_title);
        etEventDescription = findViewById(R.id.et_event_description);
        etEventStartDate = findViewById(R.id.et_event_start_date);
        etEventEndDate = findViewById(R.id.et_event_end_date);
        spinnerEventLocation = findViewById(R.id.spinner_event_location);
        btnAddEvent = findViewById(R.id.btn_add_event);
        btnUpdateEvent = findViewById(R.id.btn_update_event);
        btnDeleteEvent = findViewById(R.id.btn_delete_event);
        lvEvents = findViewById(R.id.lv_events);

        eventController = new EventController(this);
        locationController = new LocationController(this);

        btnAddEvent.setOnClickListener(v -> addEvent());
        btnUpdateEvent.setOnClickListener(v -> showUpdateEventDialog());
        btnDeleteEvent.setOnClickListener(v -> deleteEvent());

        loadLocations();
        loadEvents();
    }

    private void loadLocations() {
        locationController.loadLocations(locations -> runOnUiThread(() -> {
            this.locations = locations;
            ArrayAdapter<Location> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, locations);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerEventLocation.setAdapter(adapter);
        }));
    }

    private void showUpdateEventDialog() {
        String eventName = etEventTitle.getText().toString();
        eventController.findEventByName(eventName, event -> {
            if (event != null) {
                runOnUiThread(() -> {
                    LayoutInflater inflater = getLayoutInflater();
                    View dialogView = inflater.inflate(R.layout.dialog_update_event, null);

                    EditText etUpdateEventTitle = dialogView.findViewById(R.id.et_update_event_title);
                    EditText etUpdateEventDescription = dialogView.findViewById(R.id.et_update_event_description);
                    EditText etUpdateEventStartDate = dialogView.findViewById(R.id.et_update_event_start_date);
                    EditText etUpdateEventEndDate = dialogView.findViewById(R.id.et_update_event_end_date);
                    Spinner spinnerUpdateEventLocation = dialogView.findViewById(R.id.spinner_update_event_location);
                    Button btnUpdateConfirm = dialogView.findViewById(R.id.btn_update_confirm);
                    Button btnUpdateCancel = dialogView.findViewById(R.id.btn_update_cancel);

                    etUpdateEventTitle.setText(event.getTitle());
                    etUpdateEventDescription.setText(event.getDescription());
                    etUpdateEventStartDate.setText(event.getStartDate());
                    etUpdateEventEndDate.setText(event.getEndDate());

                    ArrayAdapter<Location> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, locations);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerUpdateEventLocation.setAdapter(adapter);

                    for (int i = 0; i < locations.size(); i++) {
                        if (locations.get(i).getName().equals(event.getLocationId())) {
                            spinnerUpdateEventLocation.setSelection(i);
                            break;
                        }
                    }

                    AlertDialog.Builder builder = new AlertDialog.Builder(EventActivity.this);
                    builder.setView(dialogView);
                    AlertDialog dialog = builder.create();

                    btnUpdateConfirm.setOnClickListener(v -> {
                        Event updatedEvent = new Event();
                        updatedEvent.setTitle(etUpdateEventTitle.getText().toString());
                        updatedEvent.setDescription(etUpdateEventDescription.getText().toString());
                        updatedEvent.setStartDate(etUpdateEventStartDate.getText().toString());
                        updatedEvent.setEndDate(etUpdateEventEndDate.getText().toString());
                        Location selectedLocation = (Location) spinnerUpdateEventLocation.getSelectedItem();

                        updatedEvent.setLocationId(String.valueOf(selectedLocation.getId()));

                        eventController.updateEventByName(eventName, updatedEvent, this::loadEvents);
                        dialog.dismiss();
                    });

                    btnUpdateCancel.setOnClickListener(v -> dialog.dismiss());

                    dialog.show();
                });
            } else {
                runOnUiThread(() -> {
                    // Handle the case where the event is not found
                    // For example, show a message or log an error
                });
            }
        });
    }

    private void addEvent() {
        Event newEvent = new Event();
        newEvent.setTitle(etEventTitle.getText().toString());
        newEvent.setDescription(etEventDescription.getText().toString());
        newEvent.setStartDate(etEventStartDate.getText().toString());
        newEvent.setEndDate(etEventEndDate.getText().toString());
        Location selectedLocation = (Location) spinnerEventLocation.getSelectedItem();
        newEvent.setLocationId(String.valueOf(selectedLocation.getId()));
        eventController.addEvent(newEvent, this::loadEvents);
    }

    private void deleteEvent() {
        String eventTitle = etEventTitle.getText().toString();
        eventController.deleteEventByName(eventTitle, this::loadEvents);
    }

    private void loadEvents() {
        eventController.loadEvents(events -> runOnUiThread(() -> {
            if (events != null) {
                EventAdapter adapter = new EventAdapter(EventActivity.this, events, locations);
                lvEvents.setAdapter(adapter);
            }
        }));
    }
}