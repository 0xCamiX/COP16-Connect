package com.camix.cop16connect.model;

import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;

import java.util.List;

public class LocationWithHabitats {
    @Embedded
    public Location location;

    @Relation(
            parentColumn = "id",
            entityColumn = "id",
            associateBy = @Junction(HabitatLocationCrossRef.class)
    )
    public List<Habitat> habitats;
}