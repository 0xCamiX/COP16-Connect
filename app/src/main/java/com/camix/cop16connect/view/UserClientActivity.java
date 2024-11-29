package com.camix.cop16connect.view;

import android.os.Bundle;
import android.util.Log;
import android.widget.DatePicker;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import com.camix.cop16connect.R;
import com.camix.cop16connect.adapter.UserClientAdapter;
import com.camix.cop16connect.model.Event;
import com.camix.cop16connect.model.Species;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserClientActivity extends AppCompatActivity implements OnMapReadyCallback {

    private static final String TAG = "UserClientActivity";
    private GoogleMap mMap;
    private DatePicker datePicker;
    private Switch dateFilterSwitch;
    private UserClientAdapter adapter;
    private TextView speciesInfoTextView;
    private Map<Marker, Object> markerMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_client);

        adapter = new UserClientAdapter(this);
        speciesInfoTextView = findViewById(R.id.speciesInfoTextView);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapView);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        Log.d(TAG, "onMapReady: map is ready");

        LatLng cali = new LatLng(3.4516, -76.5320); // Cali, Colombia
        mMap.addMarker(new MarkerOptions().position(cali).title("Marker in Cali, Colombia"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(cali, 11.0f));
        loadEvents();
        loadSpecies();

        mMap.setOnMarkerClickListener(marker -> {
            Object data = markerMap.get(marker);
            if (data instanceof Species) {
                Species species = (Species) data;
                speciesInfoTextView.setText("Species: " + species.getScientificName() + "\nHabitat: " + species.getHabit() + "\nDescription: " +
                        species.getDescription() + "\nConservation Status: " + species.getConservationStatus());
            } else if (data instanceof Event) {
                Event event = (Event) data;
                speciesInfoTextView.setText("Event: " + event.getTitle() + "\nDate: " + event.getStartDate() + " - " + event.getEndDate());
            }
            return false;
        });
    }

    private void loadSpecies() {
        new Thread(() -> {
            adapter.loadSpecies(speciesList -> runOnUiThread(() -> {
                for (Species species : speciesList) {
                    String habitatName = species.getHabit();
                    adapter.getHabitatByName(habitatName, habitat -> {
                        if (habitat != null) {
                            String locationId = habitat.getLocationId();
                            adapter.getLatLngById(locationId, latLng -> runOnUiThread(() -> {
                                if (latLng != null) {
                                    LatLng location = new LatLng(Double.parseDouble(latLng.get(0)), Double.parseDouble(latLng.get(1)));
                                    Marker marker = mMap.addMarker(new MarkerOptions().position(location).title(species.getScientificName()));
                                    markerMap.put(marker, species);
                                }
                            }));
                        }
                    });
                }
            }));
        }).start();
    }

    private void loadEvents() {
        new Thread(() -> {
            adapter.loadEvents(events -> runOnUiThread(() -> {
                for (Event event : events) {
                    adapter.getLatLng(event.getLocationId(), latLng -> runOnUiThread(() -> {
                        if (latLng != null) {
                            LatLng location = new LatLng(Double.parseDouble(latLng.get(0)), Double.parseDouble(latLng.get(1)));
                            Marker marker = mMap.addMarker(new MarkerOptions().position(location).title(event.getTitle()));
                            markerMap.put(marker, event);
                        }
                    }));
                }
            }));
        }).start();
    }
}