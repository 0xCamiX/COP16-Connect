package com.camix.cop16connect.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.camix.cop16connect.R;
import com.camix.cop16connect.database.AppDatabase;
import com.camix.cop16connect.model.User;
import com.camix.cop16connect.view.admin.AdminActivity;

public class LoginActivity extends AppCompatActivity {
    private EditText etUsername, etPassword, etEmail;
    private Button btnLogin;
    private TextView btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        etEmail = findViewById(R.id.etEmail);
        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.tvRegister);

        btnLogin.setOnClickListener(view -> {
            String username = etUsername.getText().toString();
            String password = etPassword.getText().toString();

            AppDatabase db = AppDatabase.getInstance(this);
            new Thread(() -> {
                User user = db.userDao().login(username, password);

                if (user != null) {
                    runOnUiThread(() -> {
                        Toast.makeText(this, "Welcome " + user.getUsername(), Toast.LENGTH_SHORT).show();

                        if ("admin".equals(user.getRole())) {
                            Intent intent = new Intent(LoginActivity.this, AdminActivity.class);
                            startActivity(intent);
                        } else if ("user".equals(user.getRole())) {
                            Intent intent = new Intent(LoginActivity.this, UserClientActivity.class);
                            startActivity(intent);
                        }
                    });
                } else {
                    runOnUiThread(() -> Toast.makeText(this, "Invalid credentials", Toast.LENGTH_SHORT).show());
                }
            }).start();
        });

        btnRegister.setOnClickListener(view -> {
            String username = etUsername.getText().toString();
            String password = etPassword.getText().toString();
            String email = etEmail.getText().toString();

            AppDatabase db = AppDatabase.getInstance(this);
            new Thread(() -> {
                int userCount = db.userDao().getUserCount();
                String role = userCount == 0 ? "admin" : "user";

                User newUser = new User();
                newUser.setUsername(username);
                newUser.setPassword(password);
                newUser.setRole(role);
                newUser.setEmail(email);

                db.userDao().insertAll(newUser);

                runOnUiThread(() -> Toast.makeText(this, "Registration successful", Toast.LENGTH_SHORT).show());
            }).start();
        });
    }
}