package com.camix.cop16connect.model.taxonomy;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "orders")
public class Order {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private int classId;

    public Order(int id, String name, int classId) {
        this.id = id;
        this.name = name;
        this.classId = classId;
    }

    // Constructor sin argumentos requerido por Room
    public Order() {}

    // Getters y setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getClassId() {
        return classId;
    }

    public void setClassId(int classId) {
        this.classId = classId;
    }
}