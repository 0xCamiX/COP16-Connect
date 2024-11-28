package com.camix.cop16connect.model.taxonomy;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "classes")
public class ClassTax {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private int phylumId;

    public ClassTax(int id, String name, int phylumId) {
        this.id = id;
        this.name = name;
        this.phylumId = phylumId;
    }

    // Constructor sin argumentos requerido por Room
    public ClassTax() {
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

    public int getPhylumId() {
        return phylumId;
    }

    public void setPhylumId(int phylumId) {
        this.phylumId = phylumId;
    }
}