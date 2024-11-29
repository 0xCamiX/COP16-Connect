package com.camix.cop16connect.controller;

import android.content.Context;
import android.util.Log;

import com.camix.cop16connect.database.AppDatabase;
import com.camix.cop16connect.model.Event;
import com.camix.cop16connect.model.Habitat;
import com.camix.cop16connect.model.Location;
import com.camix.cop16connect.model.Species;

import java.util.List;
import java.util.function.Consumer;

public class UserClientController {
    private final AppDatabase db;
    private static final String TAG = "UserClientController";

    public UserClientController(Context context) {
        db = AppDatabase.getInstance(context);
    }

    public List<Species> getSpecies() {
        return db.speciesDao().getAll();
    }

    public List<Event> getEvents() {
        return db.eventDao().getAll();
    }

    public Location getLocationById(String locationId) {
        return db.locationDao().getById(Integer.parseInt(locationId));
    }

    public List<String> getLatLng(String locationId) {
        Location location = getLocationById(locationId);
        if (location != null) {
            return List.of(location.getLatitude(), location.getLongitude());
        } else {
            Log.e(TAG, "Location not found for ID: " + locationId);
            return null;
        }
    }

    public Habitat getHabitatByName(String habitatName) {
        return db.habitatDao().getByName(habitatName);
    }

    public Location getLocationById(int locationId) {
        return db.locationDao().getById(locationId);
    }

    public Location getLocationByName(String name) {
        return db.locationDao().getByName(name);
    }

    public Habitat getHabitatById(int habitatId) {
        return db.habitatDao().get(habitatId);
    }
}