package com.camix.cop16connect.model.taxonomy;

public class Phylum {
    private int id;
    private String name;
    private String description;
    private int kingdomId;

    public Phylum(int id, String name, String description, int kingdomId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.kingdomId = kingdomId;
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

    public int getKingdomId() {
        return kingdomId;
    }

    public void setKingdomId(int kingdomId) {
        this.kingdomId = kingdomId;
    }
}
