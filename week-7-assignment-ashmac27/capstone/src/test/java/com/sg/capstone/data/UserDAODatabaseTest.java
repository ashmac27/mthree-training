package com.sg.capstone.data;

import com.sg.capstone.TestApplicationConfiguration;
import com.sg.capstone.model.User;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestApplicationConfiguration.class)
class UserDAODatabaseTest {

    @Autowired
    private UserDAO userDAO;

    @BeforeEach
    public void setUp() throws Exception {
    }

    @Test
    @Sql(scripts = {"file:Capstone_Schema.sql"})
    public void getAllUsers() {
        // Arrange
        User user1 = new User();
        user1.setFirstName("FName");
        user1.setLastName("LName");
        user1.setRole("Manager");

        User user2 = new User();
        user2.setFirstName("FName");
        user2.setLastName("LName");
        user2.setRole("Employee");

        User addedUser1 = userDAO.addUser(user1);
        User addedUser2 = userDAO.addUser(user2);

        // Act
        List<User> allUsers = userDAO.getAllUsers();

        //Assert
        Assert.assertEquals(2, allUsers.size());
    }

    @Test
    @Sql(scripts = {"file:Capstone_Schema.sql"})
    public void getUserById() {
        // Arrange
        User user = new User();
        user.setFirstName("FName");
        user.setLastName("LName");
        user.setRole("Employee");

        // Act
        User addedUser = userDAO.addUser(user);
        User getUserFromDao = userDAO.getUserById(addedUser.getUserId());

        //Assert
        Assert.assertEquals(addedUser.getUserId(), getUserFromDao.getUserId());
    }

    @Test
    @Sql(scripts = {"file:Capstone_Schema.sql"})
    public void addUser() {
        // Arrange
        User user = new User();
        user.setFirstName("FName");
        user.setLastName("LName");
        user.setRole("Employee");

        // Act
        User addedUser = userDAO.addUser(user);

        //Assert
        Assert.assertEquals(user, addedUser);
    }

    @Test
    @Sql(scripts = {"file:Capstone_Schema.sql"})
    public void editUser() {
        // Arrange
        User user = new User();
        user.setFirstName("FName");
        user.setLastName("LName");
        user.setRole("Employee");

        User addedUser = userDAO.addUser(user);

        Assert.assertEquals(user, addedUser);

        // Act
        user.setFirstName("Different");
        boolean isUserEdited = userDAO.editUser(user.getUserId(), user);
        User getUserFromDao = userDAO.getUserById(user.getUserId());

        //Assert
        Assert.assertTrue(isUserEdited);
        Assert.assertEquals(user.getUserId(), getUserFromDao.getUserId());
    }

    @Test
    @Sql(scripts = {"file:Capstone_Schema.sql"})
    public void deleteUser() {
        // Arrange
        User user = new User();
        user.setFirstName("FName");
        user.setLastName("LName");
        user.setRole("Employee");

        User addedUser = userDAO.addUser(user);

        Assert.assertEquals(user, addedUser);

        // Act
        boolean isDeleted = userDAO.deleteUser(user.getUserId());
        List<User> userList = userDAO.getAllUsers();

        //Assert
        Assert.assertTrue(isDeleted);
        Assert.assertFalse(userList.contains(user));
    }
}