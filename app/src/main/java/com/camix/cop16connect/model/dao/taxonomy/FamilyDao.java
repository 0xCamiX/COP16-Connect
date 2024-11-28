package com.camix.cop16connect.model.dao.taxonomy;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.camix.cop16connect.model.taxonomy.Family;

import java.util.List;

@Dao
public interface FamilyDao {
    @Query("SELECT * FROM families")
    List<Family> getAll();

    @Query("SELECT * FROM families WHERE id = :id")
    Family getById(int id);

    @Insert
    void insert(Family family);

    @Update
    void update(Family family);

    @Delete
    void delete(Family family);
}