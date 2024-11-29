package com.camix.cop16connect.adapter;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import com.camix.cop16connect.controller.UserClientController;
import com.camix.cop16connect.model.Event;
import com.camix.cop16connect.model.Species;

import java.util.List;

public class UserClientAdapter {
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
            List<String> latLng = controller.getLatLng(locationId);
            mainHandler.post(() -> listener.onDataLoaded(latLng));
        }).start();
    }

    public interface OnDataLoadedListener<T> {
        void onDataLoaded(T data);
    }
}