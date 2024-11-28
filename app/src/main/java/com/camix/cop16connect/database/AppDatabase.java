package com.camix.cop16connect.database;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import com.camix.cop16connect.model.User;
import com.camix.cop16connect.model.Event;
import com.camix.cop16connect.model.Habitat;
import com.camix.cop16connect.model.Species;
import com.camix.cop16connect.model.Location;
import com.camix.cop16connect.model.taxonomy.Kingdoms;
import com.camix.cop16connect.model.taxonomy.Phylum;
import com.camix.cop16connect.model.taxonomy.ClassTax;
import com.camix.cop16connect.model.taxonomy.Order;
import com.camix.cop16connect.model.taxonomy.Family;
import com.camix.cop16connect.model.taxonomy.Genus;
import com.camix.cop16connect.model.dao.UserDao;
import com.camix.cop16connect.model.dao.EventDao;
import com.camix.cop16connect.model.dao.HabitatDao;
import com.camix.cop16connect.model.dao.SpeciesDao;
import com.camix.cop16connect.model.dao.LocationDao;
import com.camix.cop16connect.model.dao.taxonomy.KingdomDao;
import com.camix.cop16connect.model.dao.taxonomy.PhylumDao;
import com.camix.cop16connect.model.dao.taxonomy.ClassTaxDao;
import com.camix.cop16connect.model.dao.taxonomy.OrderDao;
import com.camix.cop16connect.model.dao.taxonomy.FamilyDao;
import com.camix.cop16connect.model.dao.taxonomy.GenusDao;

@Database(entities = {User.class, Event.class, Habitat.class, Species.class, Location.class,
        Kingdoms.class, Phylum.class, ClassTax.class, Order.class, Family.class, Genus.class},
        version = 1)
public abstract class AppDatabase extends RoomDatabase {
    private static volatile AppDatabase INSTANCE;

    public static AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "COP16_DATABASE").build();
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
    public abstract KingdomDao kingdomDao();
    public abstract PhylumDao phylumDao();
    public abstract ClassTaxDao classTaxDao();
    public abstract OrderDao orderDao();
    public abstract FamilyDao familyDao();
    public abstract GenusDao genusDao();
}