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
import androidx.appcompat.app.AppCompatActivity;
import com.camix.cop16connect.R;
import com.camix.cop16connect.controller.UserController;
import com.camix.cop16connect.model.User;
import com.camix.cop16connect.adapter.UserAdapter;
import java.util.List;

public class UsersActivity extends AppCompatActivity {

    private EditText etUserName, etUserEmail, etUserPassword;
    private Spinner spinnerUserRole;
    private Button btnAddUser, btnUpdateUser, btnDeleteUser;
    private ListView lvUsers;
    private UserController userController;
    private User selectedUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);

        etUserName = findViewById(R.id.et_user_name);
        etUserEmail = findViewById(R.id.et_user_email);
        etUserPassword = findViewById(R.id.et_user_password);
        spinnerUserRole = findViewById(R.id.spinner_user_role);
        btnAddUser = findViewById(R.id.btn_add_user);
        btnUpdateUser = findViewById(R.id.btn_update_user);
        btnDeleteUser = findViewById(R.id.btn_delete_user);
        lvUsers = findViewById(R.id.lv_users);

        userController = new UserController(this);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.user_roles, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerUserRole.setAdapter(adapter);

        btnAddUser.setOnClickListener(v -> addUser());
        btnUpdateUser.setOnClickListener(v -> showUpdateUserDialog());
        btnDeleteUser.setOnClickListener(v -> deleteUser());

        loadUsers();
    }

    private void showUpdateUserDialog() {
        String username = etUserName.getText().toString();
        userController.findUserByUsername(username, user -> {
            if (user != null) {
                runOnUiThread(() -> {
                    LayoutInflater inflater = getLayoutInflater();
                    View dialogView = inflater.inflate(R.layout.dialog_update_user, null);

                    EditText etUpdateUserName = dialogView.findViewById(R.id.et_update_user_name);
                    EditText etUpdateUserEmail = dialogView.findViewById(R.id.et_update_user_email);
                    EditText etUpdateUserPassword = dialogView.findViewById(R.id.et_update_user_password);
                    Button btnUpdateConfirm = dialogView.findViewById(R.id.btn_update_confirm);
                    Button btnUpdateCancel = dialogView.findViewById(R.id.btn_update_cancel);

                    etUpdateUserName.setText(user.getUsername());
                    etUpdateUserEmail.setText(user.getEmail());
                    etUpdateUserPassword.setText(user.getPassword());

                    AlertDialog.Builder builder = new AlertDialog.Builder(UsersActivity.this);
                    builder.setView(dialogView);
                    AlertDialog dialog = builder.create();

                    btnUpdateConfirm.setOnClickListener(v -> {
                        user.setUsername(etUpdateUserName.getText().toString());
                        user.setEmail(etUpdateUserEmail.getText().toString());
                        user.setPassword(etUpdateUserPassword.getText().toString());
                        userController.updateUser(user, this::loadUsers);
                        dialog.dismiss();
                    });

                    btnUpdateCancel.setOnClickListener(v -> dialog.dismiss());

                    dialog.show();
                });
            } else {
                runOnUiThread(() -> {
                    // Handle the case where the user is not found
                    // For example, show a message or log an error
                });
            }
        });
    }

    private void addUser() {
        User newUser = new User();
        newUser.setUsername(etUserName.getText().toString());
        newUser.setEmail(etUserEmail.getText().toString());
        newUser.setPassword(etUserPassword.getText().toString());
        newUser.setRole(spinnerUserRole.getSelectedItem().toString());
        userController.addUser(newUser, this::loadUsers);
    }

    private void deleteUser() {
        String usernameOrEmail = etUserName.getText().toString();
        userController.deleteUser(usernameOrEmail, this::loadUsers);
    }

    private void loadUsers() {
        userController.loadUsers(users -> runOnUiThread(() -> {
            if (users != null) {
                UserAdapter adapter = new UserAdapter(UsersActivity.this, users);
                lvUsers.setAdapter(adapter);
            }
        }));
    }
}