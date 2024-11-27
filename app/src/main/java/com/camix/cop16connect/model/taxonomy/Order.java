package com.camix.cop16connect.model.taxonomy;

public class Order {
    private int id;
    private String name;
    private String description;
    private int classId;

    public Order(int id, String name, String description, int classId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.classId = classId;
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

    public int getClassId() {
        return classId;
    }

    public void setClassId(int classId) {
        this.classId = classId;
    }
}
