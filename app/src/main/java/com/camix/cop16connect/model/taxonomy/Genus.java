package com.camix.cop16connect.model.taxonomy;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "genera")
public class Genus {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private int familyId;

    public Genus(int id, String name, int familyId) {
        this.id = id;
        this.name = name;
        this.familyId = familyId;
    }

    // Constructor sin argumentos requerido por Room
    public Genus() {}

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

    public int getFamilyId() {
        return familyId;
    }

    public void setFamilyId(int familyId) {
        this.familyId = familyId;
    }
}