package com.camix.cop16connect.view.admin;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.camix.cop16connect.R;
import com.camix.cop16connect.adapter.LocationAdapter;
import com.camix.cop16connect.controller.LocationController;
import com.camix.cop16connect.model.Location;

public class LocationActivity extends AppCompatActivity {

    private EditText etLocationName, etLocationDescription, etLocationLatitude, etLocationLongitude;
    private Button btnAddLocation, btnUpdateLocation, btnDeleteLocation;
    private ListView lvLocations;
    private LocationController locationController;
    private Location selectedLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        etLocationName = findViewById(R.id.et_location_name);
        etLocationDescription = findViewById(R.id.et_location_description);
        etLocationLatitude = findViewById(R.id.et_location_latitude);
        etLocationLongitude = findViewById(R.id.et_location_longitude);
        btnAddLocation = findViewById(R.id.btn_add_location);
        btnUpdateLocation = findViewById(R.id.btn_update_location);
        btnDeleteLocation = findViewById(R.id.btn_delete_location);
        lvLocations = findViewById(R.id.lv_locations);

        locationController = new LocationController(this);

        btnAddLocation.setOnClickListener(v -> addLocation());
        btnUpdateLocation.setOnClickListener(v -> showUpdateLocationDialog());
        btnDeleteLocation.setOnClickListener(v -> deleteLocation());

        loadLocations();
    }

    private void showUpdateLocationDialog() {
        String locationName = etLocationName.getText().toString();
        locationController.finLocationByName(locationName, location -> {
            if (location != null) {
                runOnUiThread(() -> {
                    LayoutInflater inflater = getLayoutInflater();
                    View dialogView = inflater.inflate(R.layout.dialog_update_location, null);

                    EditText etUpdateLocationName = dialogView.findViewById(R.id.et_update_location_name);
                    EditText etUpdateLocationDescription = dialogView.findViewById(R.id.et_update_location_description);
                    EditText etUpdateLocationLatitude = dialogView.findViewById(R.id.et_update_location_latitude);
                    EditText etUpdateLocationLongitude = dialogView.findViewById(R.id.et_update_location_longitude);
                    Button btnUpdateConfirm = dialogView.findViewById(R.id.btn_update_confirm);
                    Button btnUpdateCancel = dialogView.findViewById(R.id.btn_update_cancel);

                    etUpdateLocationName.setText(location.getName());
                    etUpdateLocationDescription.setText(location.getDescription());
                    etUpdateLocationLatitude.setText(location.getLatitude());
                    etUpdateLocationLongitude.setText(location.getLongitude());

                    AlertDialog.Builder builder = new AlertDialog.Builder(LocationActivity.this);
                    builder.setView(dialogView);
                    AlertDialog dialog = builder.create();

                    btnUpdateConfirm.setOnClickListener(v -> {
                        location.setName(etUpdateLocationName.getText().toString());
                        location.setDescription(etUpdateLocationDescription.getText().toString());
                        location.setLatitude(etUpdateLocationLatitude.getText().toString());
                        location.setLongitude(etUpdateLocationLongitude.getText().toString());
                        locationController.updateLocation(location, this::loadLocations);
                        dialog.dismiss();
                    });

                    btnUpdateCancel.setOnClickListener(v -> dialog.dismiss());

                    dialog.show();
                });
            } else {
                runOnUiThread(() -> {
                    // Handle the case where the location is not found
                    // For example, show a message or log an error
                });
            }
        });
    }

    private void addLocation() {
        Location newLocation = new Location();
        newLocation.setName(etLocationName.getText().toString());
        newLocation.setDescription(etLocationDescription.getText().toString());
        newLocation.setLatitude(etLocationLatitude.getText().toString());
        newLocation.setLongitude(etLocationLongitude.getText().toString());
        locationController.addLocation(newLocation, this::loadLocations);
    }

    private void deleteLocation() {
        String locationName = etLocationName.getText().toString();
        locationController.deleteLocationByName(locationName, this::loadLocations);
    }

    private void loadLocations() {
        locationController.loadLocations(locations -> runOnUiThread(() -> {
            if (locations != null) {
                LocationAdapter adapter = new LocationAdapter(LocationActivity.this, locations);
                lvLocations.setAdapter(adapter);
            }
        }));
    }
}