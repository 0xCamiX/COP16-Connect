package com.camix.cop16connect.controller;

import android.content.Context;

import com.camix.cop16connect.database.AppDatabase;
import com.camix.cop16connect.model.Habitat;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HabitatController {
    private AppDatabase db;
    private ExecutorService executorService;

    public HabitatController(Context context) {
        db = AppDatabase.getInstance(context);
        executorService = Executors.newSingleThreadExecutor();
    }

    public void addHabitat(Habitat habitat, Runnable callback) {
        executorService.execute(() -> {
            db.habitatDao().insert(habitat);
            callback.run();
        });
    }

    public void updateHabitatByName(String name, Habitat updatedHabitat, Runnable callback) {
        executorService.execute(() -> {
            Habitat habitat = db.habitatDao().getByName(name);
            if (habitat != null) {
                updatedHabitat.setId(habitat.getId());
                db.habitatDao().update(updatedHabitat);
            }
            callback.run();
        });
    }

    public void deleteHabitatByName(String name, Runnable callback) {
        executorService.execute(() -> {
            Habitat habitat = db.habitatDao().getByName(name);
            if (habitat != null) {
                db.habitatDao().delete(habitat);
            }
            callback.run();
        });
    }

    public void findHabitatById(int id, HabitatCallback callback) {
        executorService.execute(() -> {
            Habitat habitat = db.habitatDao().getById(id);
            callback.onHabitatFound(habitat);
        });
    }

    public void loadHabitats(HabitatListCallback callback) {
        executorService.execute(() -> {
            List<Habitat> habitats = db.habitatDao().getAll();
            callback.onHabitatsLoaded(habitats);
        });
    }

    public interface HabitatCallback {
        void onHabitatFound(Habitat habitat);
    }

    public interface HabitatListCallback {
        void onHabitatsLoaded(List<Habitat> habitats);
    }
}