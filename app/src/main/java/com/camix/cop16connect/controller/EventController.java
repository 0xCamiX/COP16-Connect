package com.camix.cop16connect.controller;

import android.content.Context;

import com.camix.cop16connect.database.AppDatabase;
import com.camix.cop16connect.model.Event;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EventController {
    private AppDatabase db;
    private ExecutorService executorService;

    public EventController(Context context) {
        db = AppDatabase.getInstance(context);
        executorService = Executors.newSingleThreadExecutor();
    }

    public void addEvent(Event event, Runnable callback) {
        executorService.execute(() -> {
            db.eventDao().insert(event);
            callback.run();
        });
    }

    public void updateEventByName(String name, Event updatedEvent, Runnable callback) {
        executorService.execute(() -> {
            Event event = db.eventDao().getByTitle(name);
            if (event != null) {
                updatedEvent.setId(event.getId());
                db.eventDao().update(updatedEvent);
            }
            callback.run();
        });
    }
    public void deleteEventById(int id, Runnable callback) {
        executorService.execute(() -> {
            Event event = db.eventDao().getById(id);
            if (event != null) {
                db.eventDao().delete(event);
            }
            callback.run();
        });
    }

    public void deleteEventByName(String name, Runnable callback) {
        executorService.execute(() -> {
            Event event = db.eventDao().getByTitle(name);
            if (event != null) {
                db.eventDao().delete(event);
            }
            callback.run();
        });
    }

    public void findEventById(int id, EventCallback callback) {
        executorService.execute(() -> {
            Event event = db.eventDao().getById(id);
            callback.onEventFound(event);
        });
    }

    public void findEventByName(String name, EventCallback callback) {
        executorService.execute(() -> {
            Event event = db.eventDao().getByTitle(name);
            callback.onEventFound(event);
        });
    }

    public void loadEvents(EventListCallback callback) {
        executorService.execute(() -> {
            List<Event> events = db.eventDao().getAll();
            callback.onEventsLoaded(events);
        });
    }

    public interface EventCallback {
        void onEventFound(Event event);
    }

    public interface EventListCallback {
        void onEventsLoaded(List<Event> events);
    }
}