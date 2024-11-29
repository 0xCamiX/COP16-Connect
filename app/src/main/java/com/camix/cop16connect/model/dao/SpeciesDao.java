package com.camix.cop16connect.model.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.camix.cop16connect.model.Species;

import java.util.List;

@Dao
public interface SpeciesDao {

    @Insert
    void insert(Species species);

    @Update
    void update(Species species);

    @Delete
    void delete(Species species);

    @Query("SELECT * FROM species")
    List<Species> getAll();

    @Query("SELECT * FROM species WHERE id = :id")
    Species getById(int id);

    @Query("SELECT * FROM species WHERE scientificName = :scientificName")
    Species getByScientificName(String scientificName);

    @Query("SELECT * FROM species WHERE commonName = :commonName")
    Species getByCommonName(String commonName);

    @Query("SELECT * FROM species WHERE conservationStatus = :conservationStatus")
    List<Species> getByConservationStatus(String conservationStatus);

    @Query("SELECT * FROM species WHERE kingdom = :kingdom")
    List<Species> getByKingdom(String kingdom);

    @Query("SELECT * FROM species WHERE phylum = :phylum")
    List<Species> getByPhylum(String phylum);

    @Query("SELECT * FROM species WHERE classTax = :classTax")
    List<Species> getByClassTax(String classTax);

    @Query("SELECT * FROM species WHERE `order` = :order")
    List<Species> getByOrder(String order);

    @Query("SELECT * FROM species WHERE family = :family")
    List<Species> getByFamily(String family);

    @Query("SELECT * FROM species WHERE genus = :genus")
    List<Species> getByGenus(String genus);

    @Query("SELECT * FROM species WHERE habit = :habit")
    List<Species> getByHabit(String habit);
}