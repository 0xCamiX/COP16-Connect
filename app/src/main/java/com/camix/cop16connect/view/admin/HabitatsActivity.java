package com.camix.cop16connect.view.admin;

import android.app.Dialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

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
        String name = etHabitatName.getText().toString();
        String description = etHabitatDescription.getText().toString();
        String image = etHabitatImage.getText().toString();

        Habitat habitat = new Habitat(0, name, description, image);
        habitatController.addHabitat(habitat, this::loadHabitats);
    }

    private void showUpdateDialog() {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_update_habitat);

        EditText etUpdateName = dialog.findViewById(R.id.et_update_habitat_name);
        EditText etUpdateDescription = dialog.findViewById(R.id.et_update_habitat_description);
        EditText etUpdateImage = dialog.findViewById(R.id.et_update_habitat_image);
        Button btnConfirmUpdate = dialog.findViewById(R.id.btn_confirm_update);

        btnConfirmUpdate.setOnClickListener(v -> {
            String name = etUpdateName.getText().toString();
            String description = etUpdateDescription.getText().toString();
            String image = etUpdateImage.getText().toString();

            Habitat updatedHabitat = new Habitat(0, name, description, image);
            habitatController.updateHabitatByName(name, updatedHabitat, this::loadHabitats);
            dialog.dismiss();
        });

        dialog.show();
    }

    private void deleteHabitat() {
        String name = etHabitatName.getText().toString();
        habitatController.deleteHabitatByName(name, this::loadHabitats);
    }

    private void loadHabitats() {
        habitatController.loadHabitats(habitats -> runOnUiThread(() -> {
            habitatList.clear();
            habitatList.addAll(habitats);
            habitatAdapter.updateHabitats(habitatList);
        }));
    }
}