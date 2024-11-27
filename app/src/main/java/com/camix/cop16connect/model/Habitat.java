package com.camix.cop16connect.model;

import java.util.LinkedList;

public class Habitat {
    private int id;
    private String name;
    private String description;
    // List of location
    private LinkedList<Location> locations;
    private String image;

    public Habitat(int id, String name, String description, LinkedList<Location> locations, String image) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.locations = locations;
        this.image = image;
    }

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

    public LinkedList<Location> getLocations() {
        return locations;
    }

    public void setLocations(LinkedList<Location> locations) {
        this.locations = locations;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
