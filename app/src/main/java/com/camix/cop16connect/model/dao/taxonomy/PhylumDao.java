package com.camix.cop16connect.model.dao.taxonomy;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.camix.cop16connect.model.taxonomy.Phylum;

import java.util.List;

@Dao
public interface PhylumDao {
    @Query("SELECT * FROM phyla")
    List<Phylum> getAll();

    @Query("SELECT * FROM phyla WHERE id = :id")
    Phylum getById(int id);

    @Insert
    void insert(Phylum phylum);

    @Update
    void update(Phylum phylum);

    @Delete
    void delete(Phylum phylum);
}