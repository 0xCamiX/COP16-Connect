package com.camix.cop16connect.controller;

import android.content.Context;
import com.camix.cop16connect.database.AppDatabase;
import com.camix.cop16connect.model.User;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class UserController {
    private AppDatabase db;
    private ExecutorService executorService;

    public UserController(Context context) {
        db = AppDatabase.getInstance(context);
        executorService = Executors.newSingleThreadExecutor();
    }

    public void addUser(User user, Runnable callback) {
        executorService.execute(() -> {
            db.userDao().insertAll(user);
            callback.run();
        });
    }

    public void updateUser(User user, Runnable callback) {
        executorService.execute(() -> {
            db.userDao().update(user);
            callback.run();
        });
    }

    public void deleteUser(String usernameOrEmail, Runnable callback) {
        executorService.execute(() -> {
            User user = db.userDao().findByUsernameOrEmail(usernameOrEmail);
            if (user != null) {
                db.userDao().delete(user);
            }
            callback.run();
        });
    }

    public void findUserByUsername(String username, UserCallback callback) {
        executorService.execute(() -> {
            User user = db.userDao().findByUsername(username);
            callback.onUserFound(user);
        });
    }

    public void loadUsers(UserListCallback callback) {
        executorService.execute(() -> {
            List<User> users = db.userDao().getAll();
            callback.onUsersLoaded(users);
        });
    }

    public interface UserCallback {
        void onUserFound(User user);
    }

    public interface UserListCallback {
        void onUsersLoaded(List<User> users);
    }
}