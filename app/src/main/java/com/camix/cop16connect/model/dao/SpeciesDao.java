package com.camix.cop16connect.model.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import com.camix.cop16connect.model.Species;
import java.util.List;

@Dao
public interface SpeciesDao {
    @Query("SELECT * FROM species")
    List<Species> getAll();

    @Query("SELECT * FROM species WHERE id = :id")
    Species getById(int id);

    @Insert
    void insert(Species species);

    @Update
    void update(Species species);

    @Delete
    void delete(Species species);
}