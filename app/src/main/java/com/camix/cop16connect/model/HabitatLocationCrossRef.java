package com.camix.cop16connect.model;

import androidx.room.Entity;

@Entity(primaryKeys = {"habitatId", "locationId"})
public class HabitatLocationCrossRef {
    public int habitatId;
    public int locationId;

    public HabitatLocationCrossRef(int habitatId, int locationId) {
        this.habitatId = habitatId;
        this.locationId = locationId;
    }
}