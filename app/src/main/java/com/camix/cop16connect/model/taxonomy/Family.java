package com.camix.cop16connect.model.taxonomy;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "families")
public class Family {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private int orderId;

    public Family(int id, String name, int orderId) {
        this.id = id;
        this.name = name;
        this.orderId = orderId;
    }

    // Constructor sin argumentos requerido por Room
    public Family() {
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

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }
}