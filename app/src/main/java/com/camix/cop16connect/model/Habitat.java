package com.camix.cop16connect.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "habitats")
public class Habitat {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private String description;

    private String image;
    private String locationId;

    public Habitat(int id, String name, String description, String image, String locationId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.image = image;
        this.locationId = locationId;
    }

    // Constructor sin argumentos requerido por Room
    public Habitat() {
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return name;
    }
}