package com.camix.cop16connect.model.dao.taxonomy;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.camix.cop16connect.model.taxonomy.ClassTax;

import java.util.List;

@Dao
public interface ClassTaxDao {
    @Query("SELECT * FROM classes")
    List<ClassTax> getAll();

    @Query("SELECT * FROM classes WHERE id = :id")
    ClassTax getById(int id);

    @Insert
    void insert(ClassTax classTax);

    @Update
    void update(ClassTax classTax);

    @Delete
    void delete(ClassTax classTax);
}