package com.camix.cop16connect.controller;

import android.content.Context;

import com.camix.cop16connect.database.AppDatabase;
import com.camix.cop16connect.model.Habitat;
import com.camix.cop16connect.model.dao.HabitatDao;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

public class HabitatController {
    private final HabitatDao habitatDao;
    private final ExecutorService executorService;

    public HabitatController(Context context) {
        AppDatabase db = AppDatabase.getInstance(context);
        habitatDao = db.habitatDao();
        executorService = Executors.newSingleThreadExecutor();
    }

    public void addHabitat(Habitat habitat, Runnable callback) {
        executorService.execute(() -> {
            habitatDao.insert(habitat);
            callback.run();
        });
    }

    public void updateHabitatByName(String name, Habitat updatedHabitat, Runnable callback) {
        executorService.execute(() -> {
            Habitat habitat = habitatDao.findByName(name);
            if (habitat != null) {
                updatedHabitat.setId(habitat.getId());
                habitatDao.update(updatedHabitat);
                callback.run();
            }
        });
    }

    public void deleteHabitatByName(String name, Runnable callback) {
        executorService.execute(() -> {
            Habitat habitat = habitatDao.findByName(name);
            if (habitat != null) {
                habitatDao.delete(habitat);
                callback.run();
            }
        });
    }

    public void findHabitatById(String name, Consumer<Habitat> callback) {
        executorService.execute(() -> {
            Habitat habitat = habitatDao.findByName(name);
            callback.accept(habitat);
        });
    }

    public void loadHabitats(Consumer<List<Habitat>> callback) {
        executorService.execute(() -> {
            List<Habitat> habitats = habitatDao.getAll();
            callback.accept(habitats);
        });
    }
}