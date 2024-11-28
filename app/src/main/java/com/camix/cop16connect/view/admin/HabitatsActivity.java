package com.camix.cop16connect.view.admin;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.camix.cop16connect.R;
import com.camix.cop16connect.adapter.HabitatAdapter;
import com.camix.cop16connect.controller.HabitatController;
import com.camix.cop16connect.model.Habitat;

import java.util.ArrayList;
import java.util.List;

public class HabitatsActivity extends AppCompatActivity {

    private EditText etHabitatName, etHabitatDescription, etHabitatImage;
    private Button btnAddHabitat, btnUpdateHabitat, btnDeleteHabitat;
    private ListView lvHabitats;
    private HabitatController habitatController;
    private HabitatAdapter habitatAdapter;
    private List<Habitat> habitatList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habitats);

        etHabitatName = findViewById(R.id.et_habitat_name);
        etHabitatDescription = findViewById(R.id.et_habitat_description);
        etHabitatImage = findViewById(R.id.et_habitat_image);
        btnAddHabitat = findViewById(R.id.btn_add_habitat);
        btnUpdateHabitat = findViewById(R.id.btn_update_habitat);
        btnDeleteHabitat = findViewById(R.id.btn_delete_habitat);
        lvHabitats = findViewById(R.id.lv_habitats);

        habitatController = new HabitatController(this);
        habitatList = new ArrayList<>();
        habitatAdapter = new HabitatAdapter(this, habitatList);
        lvHabitats.setAdapter(habitatAdapter);

        btnAddHabitat.setOnClickListener(v -> addHabitat());
        btnUpdateHabitat.setOnClickListener(v -> showUpdateDialog());
        btnDeleteHabitat.setOnClickListener(v -> deleteHabitat());

        loadHabitats();
    }

    private void addHabitat() {
        String name = etHabitatName.getText().toString().trim();
        String description = etHabitatDescription.getText().toString().trim();
        String image = etHabitatImage.getText().toString().trim();

        if (name.isEmpty() || description.isEmpty() || image.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        Habitat habitat = new Habitat(0, name, description, image);
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
                    Button btnConfirmUpdate = dialog.findViewById(R.id.btn_confirm_update);

                    etUpdateName.setText(habitat.getName());
                    etUpdateDescription.setText(habitat.getDescription());
                    etUpdateImage.setText(habitat.getImage());

                    btnConfirmUpdate.setOnClickListener(v -> {
                        String updatedName = etUpdateName.getText().toString().trim();
                        String updatedDescription = etUpdateDescription.getText().toString().trim();
                        String updatedImage = etUpdateImage.getText().toString().trim();

                        if (updatedName.isEmpty() || updatedDescription.isEmpty() || updatedImage.isEmpty()) {
                            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        Habitat updatedHabitat = new Habitat(habitat.getId(), updatedName, updatedDescription, updatedImage);
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