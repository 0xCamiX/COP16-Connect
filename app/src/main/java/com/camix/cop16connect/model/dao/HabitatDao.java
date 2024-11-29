package com.camix.cop16connect.model.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import com.camix.cop16connect.model.Habitat;

@Dao
public interface HabitatDao {
    @Insert
    void insert(Habitat habitat);

    @Update
    void update(Habitat habitat);

    @Delete
    void delete(Habitat habitat);

    @Query("SELECT * FROM habitats WHERE name = :name LIMIT 1")
    Habitat findByName(String name);

    @Query("SELECT * FROM habitats WHERE id = :id LIMIT 1")
    Habitat get(int id);

    @Query("SELECT * FROM habitats WHERE name = :name")
    Habitat getByName(String name);

    @Query("SELECT * FROM habitats")
    List<Habitat> getAll();
}