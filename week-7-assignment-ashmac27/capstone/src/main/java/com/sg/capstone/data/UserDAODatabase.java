package com.sg.capstone.data;

import com.sg.capstone.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * This class represents the DAO that is responsible for database related functions to users
 */
@Repository
public class UserDAODatabase implements UserDAO {

    @Autowired
    private JdbcTemplate jdbc;

    // Gets a list of all the users
    @Override
    public List<User> getAllUsers() {
        final String SELECT_ALL_USERS = "SELECT * " + "FROM users";
        List<User> allUsers = jdbc.query(SELECT_ALL_USERS, new UserMapper());
        return allUsers;
    }

    // Gets user by its id
    @Override
    public User getUserById(int userId) {
        final String SELECT_USER = "SELECT * " +
                "FROM users " +
                "WHERE userId = ?";
        User user = jdbc.queryForObject(SELECT_USER, new UserMapper(), userId);
        return user;
    }

    // Adds a user
    @Override
    public User addUser(User user) {
        final String ADD_USER = "INSERT INTO users " +
                "(FirstName, LastName, `Role`) " +
                "VALUES (?, ?, ?)";
        jdbc.update(ADD_USER, user.getFirstName(), user.getLastName(), user.getRole());

        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        user.setUserId(newId);
        return user;
    }

    // Edits a user
    @Override
    public Boolean editUser(int userId, User user) {
        final String UPDATE_USER = "UPDATE users SET " +
                "FirstName = ?, " +
                "LastName = ?, " +
                "`Role` = ? " +
                "WHERE userId = ?";
        return jdbc.update(UPDATE_USER,
                user.getFirstName(),
                user.getLastName(),
                user.getRole(),
                userId) > 0;
    }

    // Deletes a user
    @Override
    public Boolean deleteUser(int userId) {
        final String DELETE_USER = "DELETE FROM users " +
                "WHERE UserId = ?";
        return jdbc.update(DELETE_USER, userId) > 0;
    }

    public final static class UserMapper implements RowMapper<User> {

        @Override
        public User mapRow(ResultSet rs, int i) throws SQLException {
            User vehicle = new User();
            vehicle.setUserId(rs.getInt("UserId"));
            vehicle.setFirstName(rs.getString("FirstName"));
            vehicle.setLastName(rs.getString("FirstName"));
            vehicle.setRole(rs.getString("Role"));
            return vehicle;
        }
    }
}
