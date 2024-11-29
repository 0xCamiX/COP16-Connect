package com.camix.cop16connect.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.camix.cop16connect.model.Event;
import com.camix.cop16connect.model.Habitat;
import com.camix.cop16connect.model.Location;
import com.camix.cop16connect.model.Species;
import com.camix.cop16connect.model.User;
import com.camix.cop16connect.model.dao.EventDao;
import com.camix.cop16connect.model.dao.HabitatDao;
import com.camix.cop16connect.model.dao.LocationDao;
import com.camix.cop16connect.model.dao.SpeciesDao;
import com.camix.cop16connect.model.dao.UserDao;

@Database(entities = {User.class, Event.class, Habitat.class, Species.class, Location.class}, version = 4)
public abstract class AppDatabase extends RoomDatabase {
    private static volatile AppDatabase INSTANCE;

    public static AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    AppDatabase.class, "COP16_DATABASE")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    public abstract UserDao userDao();

    public abstract EventDao eventDao();

    public abstract HabitatDao habitatDao();

    public abstract SpeciesDao speciesDao();

    public abstract LocationDao locationDao();
}