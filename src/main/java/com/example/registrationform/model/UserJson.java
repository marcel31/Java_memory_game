package com.example.registrationform.model;

import java.util.List;

public interface UserJson {
    List<User> getAllUsers();
    User getUserByUsername(String username);
    void addUser(User user);
    void updateUser(User user);
    void deleteUser(User user);
}
