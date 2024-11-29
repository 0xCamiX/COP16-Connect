package com.camix.cop16connect.view.admin;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.camix.cop16connect.R;
import com.camix.cop16connect.adapter.SpeciesAdapter;
import com.camix.cop16connect.controller.HabitatController;
import com.camix.cop16connect.controller.SpeciesController;
import com.camix.cop16connect.model.Habitat;
import com.camix.cop16connect.model.Species;

import java.util.ArrayList;
import java.util.List;

public class SpeciesActivity extends AppCompatActivity {

    private EditText etScientificName, etCommonName, etSpeciesDescription;
    private Spinner spinnerConservationStatus, spinnerKingdom, spinnerPhylum, spinnerClass, spinnerOrder, spinnerFamily, spinnerGenus, spinnerHabitat;
    private Button btnAddSpecies, btnUpdateSpecies, btnDeleteSpecies;
    private ListView lvSpecies;
    private SpeciesController speciesController;
    private HabitatController habitatController;
    private SpeciesAdapter speciesAdapter;
    private List<Species> speciesList;
    private List<Habitat> habitats;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_species);

        etScientificName = findViewById(R.id.et_scientific_name);
        etCommonName = findViewById(R.id.et_common_name);
        etSpeciesDescription = findViewById(R.id.et_species_description);

        spinnerConservationStatus = findViewById(R.id.spinner_conservation_status);
        spinnerKingdom = findViewById(R.id.spinner_kingdom);
        spinnerPhylum = findViewById(R.id.spinner_phylum);
        spinnerClass = findViewById(R.id.spinner_class);
        spinnerOrder = findViewById(R.id.spinner_order);
        spinnerFamily = findViewById(R.id.spinner_family);
        spinnerGenus = findViewById(R.id.spinner_genus);
        spinnerHabitat = findViewById(R.id.spinner_habitat);

        btnAddSpecies = findViewById(R.id.btn_add_species);
        btnUpdateSpecies = findViewById(R.id.btn_update_species);
        btnDeleteSpecies = findViewById(R.id.btn_delete_species);
        lvSpecies = findViewById(R.id.lv_species);

        habitatController = new HabitatController(this);
        speciesController = new SpeciesController(this);

        speciesList = new ArrayList<>();
        speciesAdapter = new SpeciesAdapter(this, speciesList);
        lvSpecies.setAdapter(speciesAdapter);

        setSpinnerAdapter(spinnerConservationStatus, R.array.conservation_status_array);
        setSpinnerAdapter(spinnerKingdom, R.array.kingdom_array);
        setSpinnerAdapter(spinnerPhylum, R.array.phylum_array);
        setSpinnerAdapter(spinnerClass, R.array.class_array);
        setSpinnerAdapter(spinnerOrder, R.array.order_array);
        setSpinnerAdapter(spinnerFamily, R.array.family_array);
        setSpinnerAdapter(spinnerGenus, R.array.genus_array);

        btnAddSpecies.setOnClickListener(v -> addSpecies());
        btnUpdateSpecies.setOnClickListener(v -> showUpdateSpeciesDialog());
        btnDeleteSpecies.setOnClickListener(v -> deleteSpecies());

        loadHabitats();
        loadSpecies();
    }

    private void loadHabitats() {
        habitatController.loadHabitats(habitats -> runOnUiThread(() -> {
            this.habitats = habitats;
            ArrayAdapter<Habitat> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, habitats);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerHabitat.setAdapter(adapter);
        }));
    }

    private void loadSpecies() {
        speciesController.loadSpecies(speciesList -> runOnUiThread(() -> {
            if (speciesList != null) {
                speciesAdapter.updateSpecies(speciesList);
            }
        }));
    }

    private void addSpecies() {
        String name = etScientificName.getText().toString().trim();
        String description = etSpeciesDescription.getText().toString().trim();
        String commonName = etCommonName.getText().toString().trim();

        if (name.isEmpty() || description.isEmpty() || commonName.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        Species newSpecies = new Species();
        newSpecies.setScientificName(name);
        newSpecies.setCommonName(commonName);
        newSpecies.setDescription(description);
        newSpecies.setConservationStatus(spinnerConservationStatus.getSelectedItem().toString());
        newSpecies.setKingdom(spinnerKingdom.getSelectedItem().toString());
        newSpecies.setPhylum(spinnerPhylum.getSelectedItem().toString());
        newSpecies.setClassTax(spinnerClass.getSelectedItem().toString());
        newSpecies.setOrder(spinnerOrder.getSelectedItem().toString());
        newSpecies.setFamily(spinnerFamily.getSelectedItem().toString());
        newSpecies.setGenus(spinnerGenus.getSelectedItem().toString());
        newSpecies.setHabit(spinnerHabitat.getSelectedItem().toString());

        speciesController.addSpecies(newSpecies, () -> runOnUiThread(() -> {
            Toast.makeText(this, "Species added successfully", Toast.LENGTH_SHORT).show();
            loadSpecies();
        }));
    }

    private void deleteSpecies() {
        String speciesScientificName = etScientificName.getText().toString().trim();
        if (speciesScientificName.isEmpty()) {
            Toast.makeText(this, "Please enter the species name to delete", Toast.LENGTH_SHORT).show();
            return;
        }

        speciesController.findSpeciesByScientificName(speciesScientificName, species -> {
            if (species != null) {
                speciesController.deleteSpeciesByScientificName(speciesScientificName, () -> runOnUiThread(() -> {
                    Toast.makeText(this, "Species deleted successfully", Toast.LENGTH_SHORT).show();
                    loadSpecies();
                }));
            } else {
                runOnUiThread(() -> Toast.makeText(this, "Species not found", Toast.LENGTH_SHORT).show());
            }
        });
    }

    private void showUpdateSpeciesDialog() {
        String speciesName = etScientificName.getText().toString().trim();
        if (speciesName.isEmpty()) {
            Toast.makeText(this, "Please enter the species name to update", Toast.LENGTH_SHORT).show();
            return;
        }

        speciesController.findSpeciesByScientificName(speciesName, species -> {
            if (species != null) {
                runOnUiThread(() -> {
                    LayoutInflater inflater = getLayoutInflater();
                    View dialogView = inflater.inflate(R.layout.dialog_update_species, null);

                    EditText etUpdateScientificName = dialogView.findViewById(R.id.et_update_scientific_name);
                    EditText etUpdateCommonName = dialogView.findViewById(R.id.et_update_common_name);
                    EditText etUpdateDescription = dialogView.findViewById(R.id.et_update_species_description);
                    Spinner spinnerUpdateConservationStatus = dialogView.findViewById(R.id.spinner_update_conservation_status);
                    Spinner spinnerUpdateKingdom = dialogView.findViewById(R.id.spinner_update_kingdom);
                    Spinner spinnerUpdatePhylum = dialogView.findViewById(R.id.spinner_update_phylum);
                    Spinner spinnerUpdateClass = dialogView.findViewById(R.id.spinner_update_class);
                    Spinner spinnerUpdateOrder = dialogView.findViewById(R.id.spinner_update_order);
                    Spinner spinnerUpdateFamily = dialogView.findViewById(R.id.spinner_update_family);
                    Spinner spinnerUpdateGenus = dialogView.findViewById(R.id.spinner_update_genus);
                    Spinner spinnerUpdateHabit = dialogView.findViewById(R.id.spinner_update_habitat);
                    Button btnUpdateConfirm = dialogView.findViewById(R.id.btn_update_confirm);
                    Button btnUpdateCancel = dialogView.findViewById(R.id.btn_update_cancel);

                    etUpdateScientificName.setText(species.getScientificName());
                    etUpdateCommonName.setText(species.getCommonName());
                    etUpdateDescription.setText(species.getDescription());

                    setSpinnerAdapter(spinnerUpdateConservationStatus, R.array.conservation_status_array);
                    setSpinnerAdapter(spinnerUpdateKingdom, R.array.kingdom_array);
                    setSpinnerAdapter(spinnerUpdatePhylum, R.array.phylum_array);
                    setSpinnerAdapter(spinnerUpdateClass, R.array.class_array);
                    setSpinnerAdapter(spinnerUpdateOrder, R.array.order_array);
                    setSpinnerAdapter(spinnerUpdateFamily, R.array.family_array);
                    setSpinnerAdapter(spinnerUpdateGenus, R.array.genus_array);

                    ArrayAdapter<Habitat> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, habitats);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerUpdateHabit.setAdapter(adapter);

                    setSpinnerSelection(spinnerUpdateConservationStatus, species.getConservationStatus());
                    setSpinnerSelection(spinnerUpdateKingdom, species.getKingdom());
                    setSpinnerSelection(spinnerUpdatePhylum, species.getPhylum());
                    setSpinnerSelection(spinnerUpdateClass, species.getClassTax());
                    setSpinnerSelection(spinnerUpdateOrder, species.getOrder());
                    setSpinnerSelection(spinnerUpdateFamily, species.getFamily());
                    setSpinnerSelection(spinnerUpdateGenus, species.getGenus());
                    setSpinnerSelection(spinnerUpdateHabit, species.getHabit());

                    AlertDialog.Builder builder = new AlertDialog.Builder(SpeciesActivity.this);
                    builder.setView(dialogView);
                    AlertDialog dialog = builder.create();

                    btnUpdateConfirm.setOnClickListener(v -> {
                        Species updatedSpecies = new Species();
                        updatedSpecies.setScientificName(etUpdateScientificName.getText().toString().trim());
                        updatedSpecies.setCommonName(etUpdateCommonName.getText().toString().trim());
                        updatedSpecies.setDescription(etUpdateDescription.getText().toString().trim());
                        updatedSpecies.setConservationStatus(spinnerUpdateConservationStatus.getSelectedItem().toString());
                        updatedSpecies.setKingdom(spinnerUpdateKingdom.getSelectedItem().toString());
                        updatedSpecies.setPhylum(spinnerUpdatePhylum.getSelectedItem().toString());
                        updatedSpecies.setClassTax(spinnerUpdateClass.getSelectedItem().toString());
                        updatedSpecies.setOrder(spinnerUpdateOrder.getSelectedItem().toString());
                        updatedSpecies.setFamily(spinnerUpdateFamily.getSelectedItem().toString());
                        updatedSpecies.setGenus(spinnerUpdateGenus.getSelectedItem().toString());
                        updatedSpecies.setHabit(spinnerUpdateHabit.getSelectedItem().toString());

                        speciesController.updateSpeciesByScientificName(speciesName, updatedSpecies, () -> runOnUiThread(() -> {
                            Toast.makeText(SpeciesActivity.this, "Species updated successfully", Toast.LENGTH_SHORT).show();
                            loadSpecies();
                            dialog.dismiss();
                        }));
                    });

                    btnUpdateCancel.setOnClickListener(v -> dialog.dismiss());

                    dialog.show();
                });
            } else {
                runOnUiThread(() -> Toast.makeText(this, "Species not found", Toast.LENGTH_SHORT).show());
            }
        });
    }

    private void setSpinnerAdapter(Spinner spinner, int arrayResId) {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, arrayResId, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    private void setSpinnerSelection(Spinner spinner, String value) {
        ArrayAdapter<CharSequence> adapter = (ArrayAdapter<CharSequence>) spinner.getAdapter();
        if (adapter != null) {
            int position = adapter.getPosition(value);
            spinner.setSelection(position);
        }
    }
}