package com.camix.cop16connect.model.taxonomy;

public class Genus {
    private int id;
    private String name;
    private String description;
    private int familyId;

    public Genus(int id, String name, String description, int familyId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.familyId = familyId;
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

    public int getFamilyId() {
        return familyId;
    }

    public void setFamilyId(int familyId) {
        this.familyId = familyId;
    }
}
