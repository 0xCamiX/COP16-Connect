package com.camix.cop16connect.model.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.camix.cop16connect.model.Habitat;

import java.util.List;

@Dao
public interface HabitatDao {
    @Query("SELECT * FROM habitats")
    List<Habitat> getAll();

    @Query("SELECT * FROM habitats WHERE id = :id")
    Habitat getById(int id);

    @Query("SELECT * FROM habitats WHERE name = :name")
    Habitat getByName(String name);

    @Insert
    void insert(Habitat habitat);

    @Update
    void update(Habitat habitat);

    @Delete
    void delete(Habitat habitat);
}