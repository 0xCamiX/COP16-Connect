package com.camix.cop16connect.model.dao.taxonomy;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.camix.cop16connect.model.taxonomy.Order;

import java.util.List;

@Dao
public interface OrderDao {
    @Query("SELECT * FROM orders")
    List<Order> getAll();

    @Query("SELECT * FROM orders WHERE id = :id")
    Order getById(int id);

    @Insert
    void insert(Order order);

    @Update
    void update(Order order);

    @Delete
    void delete(Order order);
}