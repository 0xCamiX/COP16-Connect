package com.camix.cop16connect.controller;

import android.content.Context;
import com.camix.cop16connect.database.AppDatabase;
import com.camix.cop16connect.model.Event;
import com.camix.cop16connect.model.Location;
import com.camix.cop16connect.model.Species;

import java.util.List;

public class UserClientController {
    private final AppDatabase db;

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
        return List.of(location.getLatitude(), location.getLongitude());
    }
}