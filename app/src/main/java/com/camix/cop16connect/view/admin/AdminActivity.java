package com.camix.cop16connect.view.admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.camix.cop16connect.R;

public class AdminActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        Button btnManageSpecies = findViewById(R.id.btn_manage_species);
        Button btnManageLocations = findViewById(R.id.btn_manage_locations);
        Button btnManageHabitats = findViewById(R.id.btn_manage_habitats);
        Button btnManageEvents = findViewById(R.id.btn_manage_events);
        Button btnManageUsers = findViewById(R.id.btn_manage_users);

        btnManageSpecies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminActivity.this, SpeciesActivity.class));
            }
        });

        btnManageLocations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminActivity.this, LocationActivity.class));
            }
        });

        btnManageHabitats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminActivity.this, HabitatsActivity.class));
            }
        });

        btnManageEvents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminActivity.this, EventActivity.class));
            }
        });

        btnManageUsers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminActivity.this, UsersActivity.class));
            }
        });
    }
}