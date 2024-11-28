package com.camix.cop16connect.model.dao.taxonomy;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import com.camix.cop16connect.model.taxonomy.Genus;
import java.util.List;

@Dao
public interface GenusDao {
    @Query("SELECT * FROM genera")
    List<Genus> getAll();

    @Query("SELECT * FROM genera WHERE id = :id")
    Genus getById(int id);

    @Insert
    void insert(Genus genus);

    @Update
    void update(Genus genus);

    @Delete
    void delete(Genus genus);
}