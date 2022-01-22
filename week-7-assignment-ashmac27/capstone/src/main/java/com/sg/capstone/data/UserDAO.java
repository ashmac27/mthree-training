package com.sg.capstone.data;

import com.sg.capstone.model.User;
import java.util.List;

public interface UserDAO {

    // Gets list of all users
    List<User> getAllUsers();

    // Gets user by its id
    User getUserById(int userId);

    // Adds a user
    User addUser(User user);

    // Edits a user
    Boolean editUser(int userId, User user);

    // Deletes a user
    Boolean deleteUser(int userId);
}
