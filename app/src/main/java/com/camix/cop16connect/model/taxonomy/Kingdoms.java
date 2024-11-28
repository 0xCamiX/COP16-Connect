package com.camix.cop16connect.model.taxonomy;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "kingdoms")
public class Kingdoms {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;

    public Kingdoms(int id, String name) {
        this.id = id;
        this.name = name;
    }

    // Constructor sin argumentos requerido por Room
    public Kingdoms() {
    }

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
}
