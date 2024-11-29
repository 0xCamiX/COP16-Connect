package com.camix.cop16connect.view;

import android.os.Bundle;
import android.util.Log;
import android.widget.DatePicker;
import android.widget.Switch;

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
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

public class UserClientActivity extends AppCompatActivity implements OnMapReadyCallback {

    private static final String TAG = "UserClientActivity";
    private GoogleMap mMap;
    private DatePicker datePicker;
    private Switch dateFilterSwitch;
    private UserClientAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_client);

        adapter = new UserClientAdapter(this);

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
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(cali, 11.0f)); // Use newLatLngZoom to set zoom level
        loadEvents();
    }

    private void loadEvents() {
        adapter.loadEvents(events -> {
            for (Event event : events) {
                adapter.getLatLng(event.getLocationId(), latLng -> {
                    LatLng location = new LatLng(Double.parseDouble(latLng.get(0)), Double.parseDouble(latLng.get(1)));
                    mMap.addMarker(new MarkerOptions().position(location).title(event.getTitle()));
                });
            }
        });
    }
}