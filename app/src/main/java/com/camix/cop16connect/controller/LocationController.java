package com.camix.cop16connect.controller;

import android.content.Context;

import com.camix.cop16connect.database.AppDatabase;
import com.camix.cop16connect.model.Location;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LocationController {
    private AppDatabase db;
    private ExecutorService executorService;

    public LocationController(Context context) {
        db = AppDatabase.getInstance(context);
        executorService = Executors.newSingleThreadExecutor();
    }

    public void addLocation(Location location, Runnable callback) {
        executorService.execute(() -> {
            db.locationDao().insert(location);
            callback.run();
        });
    }

    public void updateLocation(Location location, Runnable callback) {
        executorService.execute(() -> {
            db.locationDao().update(location);
            callback.run();
        });
    }

    public void deleteLocation(int id, Runnable callback) {
        executorService.execute(() -> {
            Location location = db.locationDao().getById(id);
            if (location != null) {
                db.locationDao().delete(location);
            }
            callback.run();
        });
    }

    public void findLocationById(int id, LocationCallback callback) {
        executorService.execute(() -> {
            Location location = db.locationDao().getById(id);
            callback.onLocationFound(location);
        });
    }

    public void finLocationByName(String name, LocationCallback callback) {
        executorService.execute(() -> {
            Location location = db.locationDao().getByName(name);
            callback.onLocationFound(location);
        });
    }

    public void findLocationByPosition(String latitude, String longitude, LocationCallback callback) {
        executorService.execute(() -> {
            Location location = db.locationDao().getByPosition(latitude, longitude);
            callback.onLocationFound(location);
        });
    }

    public void loadLocations(LocationListCallback callback) {
        executorService.execute(() -> {
            List<Location> locations = db.locationDao().getAll();
            callback.onLocationsLoaded(locations);
        });
    }

    public void deleteLocationByName(String name, Runnable callback) {
        executorService.execute(() -> {
            Location location = db.locationDao().getByName(name);
            if (location != null) {
                db.locationDao().delete(location);
            }
            callback.run();
        });
    }

    public interface LocationCallback {
        void onLocationFound(Location location);
    }

    public interface LocationListCallback {
        void onLocationsLoaded(List<Location> locations);
    }
}
