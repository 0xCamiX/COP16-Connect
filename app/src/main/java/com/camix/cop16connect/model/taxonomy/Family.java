package com.camix.cop16connect.model.taxonomy;

public class Family {
    private int id;
    private String name;
    private String description;
    private int orderId;

    public Family(int id, String name, String description, int orderId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.orderId = orderId;
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

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }
}
