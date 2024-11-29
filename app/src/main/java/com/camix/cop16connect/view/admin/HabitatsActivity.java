// HabitatsActivity.java
package com.camix.cop16connect.view.admin;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

import com.camix.cop16connect.R;
import com.camix.cop16connect.adapter.HabitatAdapter;
import com.camix.cop16connect.controller.HabitatController;
import com.camix.cop16connect.controller.LocationController;
import com.camix.cop16connect.model.Habitat;
import com.camix.cop16connect.model.Location;

import java.util.ArrayList;
import java.util.List;

public class HabitatsActivity extends AppCompatActivity {

    private EditText etHabitatName, etHabitatDescription, etHabitatImage;
    private Spinner spinnerHabitatLocationId;
    private Button btnAddHabitat, btnUpdateHabitat, btnDeleteHabitat;
    private ListView lvHabitats;
    private HabitatController habitatController;
    private LocationController locationController;
    private HabitatAdapter habitatAdapter;
    private List<Habitat> habitatList;
    private List<Location> locationList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habitats);

        etHabitatName = findViewById(R.id.et_habitat_name);
        etHabitatDescription = findViewById(R.id.et_habitat_description);
        etHabitatImage = findViewById(R.id.et_habitat_image);
        spinnerHabitatLocationId = findViewById(R.id.spinner_habitat_location_id);
        btnAddHabitat = findViewById(R.id.btn_add_habitat);
        btnUpdateHabitat = findViewById(R.id.btn_update_habitat);
        btnDeleteHabitat = findViewById(R.id.btn_delete_habitat);
        lvHabitats = findViewById(R.id.lv_habitats);

        habitatController = new HabitatController(this);
        locationController = new LocationController(this);
        habitatList = new ArrayList<>();
        locationList = new ArrayList<>();
        habitatAdapter = new HabitatAdapter(this, habitatList);
        lvHabitats.setAdapter(habitatAdapter);

        btnAddHabitat.setOnClickListener(v -> addHabitat());
        btnUpdateHabitat.setOnClickListener(v -> showUpdateDialog());
        btnDeleteHabitat.setOnClickListener(v -> deleteHabitat());

        loadHabitats();
        loadLocations();
    }

    private void loadLocations() {
        locationController.loadLocations(locations -> runOnUiThread(() -> {
            locationList.clear();
            locationList.addAll(locations);
            ArrayAdapter<Location> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, locationList);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerHabitatLocationId.setAdapter(adapter);
        }));
    }

    private void addHabitat() {
        String name = etHabitatName.getText().toString().trim();
        String description = etHabitatDescription.getText().toString().trim();
        String image = etHabitatImage.getText().toString().trim();
        Location selectedLocation = (Location) spinnerHabitatLocationId.getSelectedItem();

        if (name.isEmpty() || description.isEmpty() || image.isEmpty() || selectedLocation == null) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        String locationId = String.valueOf(selectedLocation.getId());

        Habitat habitat = new Habitat(0, name, description, image, locationId);
        habitatController.addHabitat(habitat, () -> {
            runOnUiThread(() -> {
                Toast.makeText(this, "Habitat added successfully", Toast.LENGTH_SHORT).show();
                loadHabitats();
            });
        });
    }

    private void showUpdateDialog() {
        String name = etHabitatName.getText().toString().trim();
        if (name.isEmpty()) {
            Toast.makeText(this, "Please enter the habitat name to update", Toast.LENGTH_SHORT).show();
            return;
        }

        habitatController.findHabitatById(name, habitat -> {
            if (habitat != null) {
                runOnUiThread(() -> {
                    Dialog dialog = new Dialog(this);
                    dialog.setContentView(R.layout.dialog_update_habitat);

                    EditText etUpdateName = dialog.findViewById(R.id.et_update_habitat_name);
                    EditText etUpdateDescription = dialog.findViewById(R.id.et_update_habitat_description);
                    EditText etUpdateImage = dialog.findViewById(R.id.et_update_habitat_image);
                    Spinner spinnerUpdateLocationId = dialog.findViewById(R.id.spinner_update_habitat_location_id); // Cambiado a Spinner
                    Button btnConfirmUpdate = dialog.findViewById(R.id.btn_confirm_update);

                    etUpdateName.setText(habitat.getName());
                    etUpdateDescription.setText(habitat.getDescription());
                    etUpdateImage.setText(habitat.getImage());

                    // Cargar locaciones en el Spinner
                    ArrayAdapter<Location> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, locationList);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerUpdateLocationId.setAdapter(adapter);

                    // Seleccionar la locación actual
                    for (int i = 0; i < locationList.size(); i++) {
                        if (locationList.get(i).getId() == Integer.parseInt(habitat.getLocationId())) {
                            spinnerUpdateLocationId.setSelection(i);
                            break;
                        }
                    }

                    btnConfirmUpdate.setOnClickListener(v -> {
                        String updatedName = etUpdateName.getText().toString().trim();
                        String updatedDescription = etUpdateDescription.getText().toString().trim();
                        String updatedImage = etUpdateImage.getText().toString().trim();
                        Location selectedLocation = (Location) spinnerUpdateLocationId.getSelectedItem();

                        if (updatedName.isEmpty() || updatedDescription.isEmpty() || updatedImage.isEmpty() || selectedLocation == null) {
                            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        // Asegurarse de guardar el ID de la locación
                        String updatedLocationId = String.valueOf(selectedLocation.getId());

                        Habitat updatedHabitat = new Habitat(habitat.getId(), updatedName, updatedDescription, updatedImage, updatedLocationId);
                        habitatController.updateHabitatByName(name, updatedHabitat, () -> {
                            runOnUiThread(() -> {
                                Toast.makeText(this, "Habitat updated successfully", Toast.LENGTH_SHORT).show();
                                loadHabitats();
                                dialog.dismiss();
                            });
                        });
                    });

                    dialog.show();
                });
            } else {
                runOnUiThread(() -> Toast.makeText(this, "Habitat not found", Toast.LENGTH_SHORT).show());
            }
        });
    }

    private void deleteHabitat() {
        String name = etHabitatName.getText().toString().trim();
        if (name.isEmpty()) {
            Toast.makeText(this, "Please enter the habitat name to delete", Toast.LENGTH_SHORT).show();
            return;
        }

        habitatController.deleteHabitatByName(name, () -> {
            runOnUiThread(() -> {
                Toast.makeText(this, "Habitat deleted successfully", Toast.LENGTH_SHORT).show();
                loadHabitats();
            });
        });
    }

    private void loadHabitats() {
        habitatController.loadHabitats(habitats -> runOnUiThread(() -> {
            habitatList.clear();
            habitatList.addAll(habitats);
            habitatAdapter.updateHabitats(habitatList);
        }));
    }
}