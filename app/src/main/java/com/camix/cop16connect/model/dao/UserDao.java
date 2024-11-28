package com.camix.cop16connect.model.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import com.camix.cop16connect.model.User;
import java.util.List;

@Dao
public interface UserDao {
    @Query("SELECT * FROM users")
    List<User> getAll();

    @Query("SELECT * FROM users WHERE id IN (:userIds)")
    List<User> loadAllByIds(int[] userIds);

    @Query("SELECT * FROM users WHERE username LIKE :username LIMIT 1")
    User findByUsername(String username);

    @Query("SELECT * FROM users WHERE email LIKE :email LIMIT 1")
    User findByEmail(String email);

    @Query("SELECT * FROM users WHERE username = :usernameOrEmail OR email = :usernameOrEmail LIMIT 1")
    User findByUsernameOrEmail(String usernameOrEmail);

    @Query("SELECT * FROM users WHERE username = :username AND password = :password LIMIT 1")
    User login(String username, String password);

    @Insert
    void insertAll(User... users);

    @Update
    void update(User user);

    @Delete
    void delete(User user);

    @Query("SELECT COUNT(*) FROM users")
    int getUserCount();
}