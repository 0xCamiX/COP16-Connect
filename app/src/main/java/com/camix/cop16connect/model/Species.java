package com.camix.cop16connect.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "species")
public class Species {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String scientificName;
    private String commonName;
    private String conservationStatus;
    private int kingdomId;
    private int phylumId;
    private int classId;
    private int orderId;
    private int familyId;
    private int genusId;
    private int habitId;

    public Species(int id, String scientificName, String commonName, String conservationStatus, int kingdomId, int phylumId, int classId, int orderId, int familyId, int genusId, int habitId) {
        this.id = id;
        this.scientificName = scientificName;
        this.commonName = commonName;
        this.conservationStatus = conservationStatus;
        this.kingdomId = kingdomId;
        this.phylumId = phylumId;
        this.classId = classId;
        this.orderId = orderId;
        this.familyId = familyId;
        this.genusId = genusId;
        this.habitId = habitId;
    }

    // Constructor sin argumentos requerido por Room
    public Species() {}

    // Getters y setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getScientificName() {
        return scientificName;
    }

    public void setScientificName(String scientificName) {
        this.scientificName = scientificName;
    }

    public String getCommonName() {
        return commonName;
    }

    public void setCommonName(String commonName) {
        this.commonName = commonName;
    }

    public String getConservationStatus() {
        return conservationStatus;
    }

    public void setConservationStatus(String conservationStatus) {
        this.conservationStatus = conservationStatus;
    }

    public int getKingdomId() {
        return kingdomId;
    }

    public void setKingdomId(int kingdomId) {
        this.kingdomId = kingdomId;
    }

    public int getPhylumId() {
        return phylumId;
    }

    public void setPhylumId(int phylumId) {
        this.phylumId = phylumId;
    }

    public int getClassId() {
        return classId;
    }

    public void setClassId(int classId) {
        this.classId = classId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getFamilyId() {
        return familyId;
    }

    public void setFamilyId(int familyId) {
        this.familyId = familyId;
    }

    public int getGenusId() {
        return genusId;
    }

    public void setGenusId(int genusId) {
        this.genusId = genusId;
    }

    public int getHabitId() {
        return habitId;
    }

    public void setHabitId(int habitId) {
        this.habitId = habitId;
    }
}