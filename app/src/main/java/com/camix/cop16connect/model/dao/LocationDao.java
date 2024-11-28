package com.camix.cop16connect.model.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import com.camix.cop16connect.model.Location;
import java.util.List;

@Dao
public interface LocationDao {
    @Query("SELECT * FROM locations")
    List<Location> getAll();

    @Query("SELECT * FROM locations WHERE id = :id")
    Location getById(int id);

    @Query("SELECT * FROM locations WHERE name = :name")
    Location getByName(String name);

    @Query("SELECT * FROM locations WHERE latitude = :latitude AND longitude = :longitude")
    Location getByPosition(String latitude, String longitude);

    @Insert
    void insert(Location location);

    @Update
    void update(Location location);

    @Delete
    void delete(Location location);
}