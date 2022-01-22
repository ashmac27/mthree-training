package com.sg.capstone.data;

import com.sg.capstone.TestApplicationConfiguration;
import com.sg.capstone.model.Post;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestApplicationConfiguration.class)
class PostDAODatabaseTest {

    @Autowired
    PostDAO postDAO;

    @BeforeEach
    void setUp() {
    }

    @Test
    @Sql(scripts = {"file:Capstone_Schema_Test.sql", "file:Capstone_data.sql"})
    void addPost() {
        Post p = new Post();
        p.setApproved(true);
        p.setTitle("title");
        p.setContent("Content");
        assertThrows(Exception.class, () -> postDAO.addPost(p));
        p.setUserId(1); // User ID 1 should be set
        Post newPost = postDAO.addPost(p);
        assertNotEquals(null, newPost);
        assertEquals(newPost, postDAO.getPostById(newPost.getPostId()));
    }

    @Test
    @Sql(scripts = {"file:Capstone_Schema_Test.sql", "file:Capstone_data.sql"})
    void getAllPosts() {
        List<Post> allPosts = postDAO.getAllPosts();
        postDAO.getAllPosts().forEach(post -> {
            // Asserts that each post is in the list and the DAO
            assertTrue(postDAO.deletePost(post.getPostId()));
            assertTrue(allPosts.remove(post));
        });
        // Makes sure there are no lingering posts
        assertEquals(0, allPosts.size());
    }

    @Test
    @Sql(scripts = {"file:Capstone_Schema_Test.sql", "file:Capstone_data.sql"})
    void getExpiredPosts() {
        LocalDateTime now = LocalDateTime.now();
        List<Post> expiredPosts = postDAO.getExpiredPosts(now);
        for (Post p : postDAO.getAllPosts()) {
            // either it's not in the expired post list
            assertTrue((!expiredPosts.contains(p) || (p.getExpireDate() != null && p.getExpireDate().isBefore(now))));
        }
    }

    @Test
    @Sql(scripts = {"file:Capstone_Schema_Test.sql", "file:Capstone_data.sql"})
    void getUnexpiredPosts() {
        LocalDateTime now = LocalDateTime.now();
        List<Post> unexpiredPosts = postDAO.getUnexpiredPosts(now);
        for (Post p : postDAO.getAllPosts()) {
            // either there is no expire date, expire is after now, or this isn't in the expired list
            assertTrue((p.getExpireDate() == null || p.getExpireDate().isAfter(now) || !unexpiredPosts.contains(p)));
        }
    }

    @Test
    @Sql(scripts = {"file:Capstone_Schema_Test.sql", "file:Capstone_data.sql"})
    void getPostById() {
        List<Post> allPosts = postDAO.getAllPosts();
        assertThrows(Exception.class, () -> postDAO.getPostById(-1));
        for (Post p : allPosts) {
            assertEquals(p, postDAO.getPostById(p.getPostId()));
        }
    }

    @Test
    @Sql(scripts = {"file:Capstone_Schema_Test.sql", "file:Capstone_data.sql"})
    void editPost() {
        Post p = postDAO.getAllPosts().get(0);
        Post newPost = postDAO.getPostById(p.getPostId());
        assertEquals(p, newPost);
        newPost.setContent(newPost.getContent() + " addition to the post");
        assertTrue(postDAO.editPost(newPost.getPostId(), newPost));
        assertFalse(postDAO.editPost(-1, p));
        assertFalse(p.equals(newPost));
        assertEquals(newPost, postDAO.getPostById(newPost.getPostId()));
    }

    @Test
    @Sql(scripts = {"file:Capstone_Schema_Test.sql", "file:Capstone_data.sql"})
    void deletePost() {
        List<Post> allPosts = postDAO.getAllPosts();
        for (Post p : allPosts) {
            assertTrue(postDAO.deletePost(p.getPostId()));
            assertFalse(postDAO.deletePost(p.getPostId()));
        }
    }
}