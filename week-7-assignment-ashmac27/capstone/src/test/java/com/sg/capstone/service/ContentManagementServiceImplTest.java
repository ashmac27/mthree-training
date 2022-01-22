package com.sg.capstone.service;

import com.sg.capstone.TestApplicationConfiguration;
import com.sg.capstone.data.HashtagDAO;
import com.sg.capstone.data.PostDAO;
import com.sg.capstone.data.UserDAO;
import com.sg.capstone.model.Post;
import com.sg.capstone.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestApplicationConfiguration.class)
class ContentManagementServiceImplTest {

    @Autowired
    ContentManagementService service;

    @Autowired
    PostDAO postDAO;

    @Autowired
    HashtagDAO hashtagDAO;

    @Autowired
    UserDAO userDAO;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    @Sql(scripts = {"file:Capstone_Schema_Test.sql", "file:Capstone_data.sql"})
    void addPost() {
        Post p = new Post();
        p.setApproved(true);
        p.setTitle("title");
        p.setContent("Content");
        assertThrows(Exception.class, () -> service.addPost(p, null));
        p.setUserId(1); // User ID 1 should be set
        Post newPost = service.addPost(p, null);
        assertNotEquals(null, newPost);
        p.setPostId(newPost.getPostId());
        p.setDateAdded(newPost.getDateAdded());
        assertEquals(newPost, p);
    }

    @Test
    @Sql(scripts = {"file:Capstone_Schema_Test.sql", "file:Capstone_data.sql"})
    void addUser() {
        User u = new User();
        u.setFirstName("Test");
        u.setLastName("User");
        // User is incomplete
        assertThrows(Exception.class, () -> service.addUser(u));
        u.setRole("member");
        User newUser = service.addUser(u);
        assertNotEquals(null, newUser);
        u.setUserId(newUser.getUserId());
        assertEquals(newUser, u);
    }

    @Test
    @Sql(scripts = {"file:Capstone_Schema_Test.sql", "file:Capstone_data.sql"})
    void approvePostById() {
        Post p = new Post();
        p.setTitle("testTitle");
        p.setContent("test content");
        p.setApproved(false);
        p.setUserId(1);
        p = service.addPost(p, null);
        assertTrue(service.approvePostById(p.getPostId()));
        assertTrue(service.getPostById(p.getPostId(), false, false, false, null).isApproved());
    }

    @Test
    @Sql(scripts = {"file:Capstone_Schema_Test.sql", "file:Capstone_data.sql"})
    void deletePostById() {
        for (Post p : postDAO.getAllPosts()) {
            assertTrue(service.deletePostById(p.getPostId()));
            assertFalse(service.deletePostById(p.getPostId()));
        }
    }

    @Test
    @Sql(scripts = {"file:Capstone_Schema_Test.sql", "file:Capstone_data.sql"})
    void editPost() {
        Post p = service.getAllPosts(false, false, false, null).get(0);
        Post newPost = service.getPostById(p.getPostId(), false, false, false, null);
        assertEquals(p, newPost);
        newPost.setContent(newPost.getContent() + " addition to the post");
        assertTrue(service.editPost(newPost, null));
        assertFalse(p.equals(newPost));
        p.setContent(newPost.getContent());
        assertEquals(newPost, p);
    }

    @Test
    @Sql(scripts = {"file:Capstone_Schema_Test.sql", "file:Capstone_data.sql"})
    void getAllPosts() {
        List<Post> allPosts = service.getAllPosts(false, false, false, null);
        service.getAllPosts(false, false, false, null).forEach(post -> {
            // Asserts that each post is in the list and the DAO
            assertTrue(service.deletePostById(post.getPostId()));
            assertTrue(allPosts.remove(post));
        });
        // Makes sure there are no lingering posts
        assertEquals(0, allPosts.size());
    }

    @Test
    @Sql(scripts = {"file:Capstone_Schema_Test.sql", "file:Capstone_data.sql"})
    void getPostById() {
        List<Post> allPosts = service.getAllPosts(false, false, false, null);
        assertThrows(Exception.class, () -> service.getPostById(-1, false, false, false, null));
        for (Post p : allPosts) {
            assertEquals(p, service.getPostById(p.getPostId(), false, false, false, null));
        }
    }
}