package com.camix.cop16connect.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "phyla")
public class Phylum {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private int kingdomId;

    public Phylum(int id, String name, int kingdomId) {
        this.id = id;
        this.name = name;
        this.kingdomId = kingdomId;
    }

    // Constructor sin argumentos requerido por Room
    public Phylum() {}

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

    public int getKingdomId() {
        return kingdomId;
    }

    public void setKingdomId(int kingdomId) {
        this.kingdomId = kingdomId;
    }
}