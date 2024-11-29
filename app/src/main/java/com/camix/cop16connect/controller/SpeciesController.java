package com.camix.cop16connect.controller;

import android.content.Context;

import com.camix.cop16connect.database.AppDatabase;
import com.camix.cop16connect.model.Species;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SpeciesController {
    private AppDatabase db;
    private ExecutorService executorService;

    public SpeciesController(Context context) {
        db = AppDatabase.getInstance(context);
        executorService = Executors.newSingleThreadExecutor();
    }

    public void addSpecies(Species species, Runnable callback) {
        executorService.execute(() -> {
            db.speciesDao().insert(species);
            callback.run();
        });
    }

    public void updateSpeciesByScientificName(String scientificName, Species updatedSpecies, Runnable callback) {
        executorService.execute(() -> {
            Species species = db.speciesDao().getByScientificName(scientificName);
            if (species != null) {
                updatedSpecies.setId(species.getId());
                db.speciesDao().update(updatedSpecies);
            }
            callback.run();
        });
    }

    public void deleteSpeciesByScientificName(String scientificName, Runnable callback) {
        executorService.execute(() -> {
            Species species = db.speciesDao().getByScientificName(scientificName);
            if (species != null) {
                db.speciesDao().delete(species);
            }
            callback.run();
        });
    }

    public void findSpeciesByScientificName(String scientificName, SpeciesCallback callback) {
        executorService.execute(() -> {
            Species species = db.speciesDao().getByScientificName(scientificName);
            callback.onSpeciesFound(species);
        });
    }

    public void loadSpecies(SpeciesListCallback callback) {
        executorService.execute(() -> {
            List<Species> species = db.speciesDao().getAll();
            callback.onSpeciesLoaded(species);
        });
    }

    public interface SpeciesCallback {
        void onSpeciesFound(Species species);
    }

    public interface SpeciesListCallback {
        void onSpeciesLoaded(List<Species> species);
    }
}