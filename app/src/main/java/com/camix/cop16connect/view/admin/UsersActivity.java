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
import com.camix.cop16connect.database.AppDatabase;
import com.camix.cop16connect.model.User;
import com.camix.cop16connect.adapter.UserAdapter;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class UsersActivity extends AppCompatActivity {

    private EditText etUserName, etUserEmail, etUserPassword;
    private Spinner spinnerUserRole;
    private Button btnAddUser, btnUpdateUser, btnDeleteUser;
    private ListView lvUsers;
    private AppDatabase db;
    private ExecutorService executorService;
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

        // Initialize the database and executor service
        db = AppDatabase.getInstance(this);
        executorService = Executors.newSingleThreadExecutor();

        // Set up the spinner for user roles
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.user_roles, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerUserRole.setAdapter(adapter);

        btnAddUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addUser();
            }
        });

        btnUpdateUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showUpdateUserDialog();
            }
        });

        btnDeleteUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteUser();
            }
        });

        // Load users into ListView
        loadUsers();
    }

    private void showUpdateUserDialog() {
        String username = etUserName.getText().toString();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                User user = db.userDao().findByUsername(username);
                if (user != null) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
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

                            btnUpdateConfirm.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    user.setUsername(etUpdateUserName.getText().toString());
                                    user.setEmail(etUpdateUserEmail.getText().toString());
                                    user.setPassword(etUpdateUserPassword.getText().toString());
                                    executorService.execute(new Runnable() {
                                        @Override
                                        public void run() {
                                            db.userDao().update(user);
                                            runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    loadUsers();
                                                    dialog.dismiss();
                                                }
                                            });
                                        }
                                    });
                                }
                            });

                            btnUpdateCancel.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialog.dismiss();
                                }
                            });

                            dialog.show();
                        }
                    });
                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            // Handle the case where the user is not found
                            // For example, show a message or log an error
                        }
                    });
                }
            }
        });
    }

    private void addUser() {
        User newUser = new User();
        newUser.setUsername(etUserName.getText().toString());
        newUser.setEmail(etUserEmail.getText().toString());
        newUser.setPassword(etUserPassword.getText().toString());
        newUser.setRole(spinnerUserRole.getSelectedItem().toString());
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                db.userDao().insertAll(newUser);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        loadUsers();
                    }
                });
            }
        });
    }

    private void deleteUser() {
        String usernameOrEmail = etUserName.getText().toString();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                User user = db.userDao().findByUsernameOrEmail(usernameOrEmail);
                if (user != null) {
                    db.userDao().delete(user);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            loadUsers();
                        }
                    });
                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            // Handle the case where the user is not found
                            // For example, show a message or log an error
                        }
                    });
                }
            }
        });
    }

    private void loadUsers() {
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                final List<User> users = db.userDao().getAll();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (users != null) {
                            UserAdapter adapter = new UserAdapter(UsersActivity.this, users);
                            lvUsers.setAdapter(adapter);
                        }
                    }
                });
            }
        });
    }
}