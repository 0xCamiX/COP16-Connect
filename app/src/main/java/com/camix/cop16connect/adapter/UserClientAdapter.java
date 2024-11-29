package com.camix.cop16connect.adapter;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.camix.cop16connect.controller.UserClientController;
import com.camix.cop16connect.model.Event;
import com.camix.cop16connect.model.Habitat;
import com.camix.cop16connect.model.Location;
import com.camix.cop16connect.model.Species;

import java.util.ArrayList;
import java.util.List;

public class UserClientAdapter {
    private final String TAG = "UserClientAdapter";
    private final UserClientController controller;
    private final Handler mainHandler = new Handler(Looper.getMainLooper());

    public UserClientAdapter(Context context) {
        controller = new UserClientController(context);
    }

    public void loadSpecies(OnDataLoadedListener<List<Species>> listener) {
        new Thread(() -> {
            List<Species> speciesList = controller.getSpecies();
            mainHandler.post(() -> listener.onDataLoaded(speciesList));
        }).start();
    }

    public void loadEvents(OnDataLoadedListener<List<Event>> listener) {
        new Thread(() -> {
            List<Event> events = controller.getEvents();
            mainHandler.post(() -> listener.onDataLoaded(events));
        }).start();
    }


    public void getLatLng(String locationId, OnDataLoadedListener<List<String>> listener) {
        new Thread(() -> {
            Location location = controller.getLocationById(locationId);
            if (location != null) {
                List<String> latLng = new ArrayList<>();
                latLng.add(location.getLatitude());
                latLng.add(location.getLongitude());
                listener.onDataLoaded(latLng);
            } else {
                Log.e(TAG, "Location not found for ID: " + locationId);
                listener.onDataLoaded(null);
            }
        }).start();
    }

    public void getHabitatByName(String habitatName, OnDataLoadedListener<Habitat> listener) {
        new Thread(() -> {
            Habitat habitat = controller.getHabitatByName(habitatName);
            mainHandler.post(() -> listener.onDataLoaded(habitat));
        }).start();
    }

    public void getLatLngById(String locationId, OnDataLoadedListener<List<String>> listener) {
        new Thread(() -> {
            Location location = controller.getLocationById(locationId);
            if (location != null) {
                List<String> latLng = new ArrayList<>();
                latLng.add(location.getLatitude());
                latLng.add(location.getLongitude());
                listener.onDataLoaded(latLng);
            } else {
                Log.e(TAG, "Location not found for ID: " + locationId);
                listener.onDataLoaded(null);
            }
        }).start();
    }

    public void getLatLngByName(String locationName, OnDataLoadedListener<List<String>> listener) {
        new Thread(() -> {
            Location location = controller.getLocationByName(locationName);
            if (location != null) {
                List<String> latLng = new ArrayList<>();
                latLng.add(location.getLatitude());
                latLng.add(location.getLongitude());
                listener.onDataLoaded(latLng);
            } else {
                Log.e(TAG, "Location not found for name: " + locationName);
                listener.onDataLoaded(null);
            }
        }).start();
    }

    public void getHabitatById(int habitatId, OnDataLoadedListener<Habitat> listener) {
        new Thread(() -> {
            Habitat habitat = controller.getHabitatById(habitatId);
            mainHandler.post(() -> listener.onDataLoaded(habitat));
        }).start();
    }

    public interface OnDataLoadedListener<T> {
        void onDataLoaded(T data);
    }
}