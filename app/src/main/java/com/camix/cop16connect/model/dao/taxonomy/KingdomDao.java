package com.camix.cop16connect.model.dao.taxonomy;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.camix.cop16connect.model.taxonomy.Kingdoms;

import java.util.List;

@Dao
public interface KingdomDao {
    @Query("SELECT * FROM kingdoms")
    List<Kingdoms> getAll();

    @Query("SELECT * FROM kingdoms WHERE id = :id")
    Kingdoms getById(int id);

    @Insert
    void insert(Kingdoms kingdom);

    @Update
    void update(Kingdoms kingdom);

    @Delete
    void delete(Kingdoms kingdom);
}