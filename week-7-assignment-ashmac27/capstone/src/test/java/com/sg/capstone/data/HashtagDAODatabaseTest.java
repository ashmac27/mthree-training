package com.sg.capstone.data;

import com.sg.capstone.TestApplicationConfiguration;
import com.sg.capstone.model.Hashtag;
import com.sg.capstone.model.Post;
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
class HashtagDAODatabaseTest {

    @Autowired
    private HashtagDAO hashtagDAO;

    @Autowired
    private PostDAO postDAO;

    @BeforeEach
    void setUp() {
    }

    @Test
    @Sql(scripts = {"file:Capstone_Schema_Test.sql", "file:Capstone_data.sql"})
    void getAllHashtags() {
        List<Hashtag> allTags = hashtagDAO.getAllHashtags();
        allTags.forEach(tag -> {
            // Hashtag WAS in the database
            assertTrue(hashtagDAO.delete(tag));
        });
        // DAO should be empty
        assertEquals(0, hashtagDAO.getAllHashtags().size());

        allTags.forEach(tag -> hashtagDAO.add(tag));

        for (Hashtag h : hashtagDAO.getAllHashtags()) {
            assertTrue(allTags.remove(h)); // If true, it's been successfully removed
        }
        // Tag list should be empty, meaning all tags exist
        assertEquals(0, allTags.size());
    }

    @Test
    @Sql(scripts = {"file:Capstone_Schema_Test.sql", "file:Capstone_data.sql"})
    void getHashtagsByPostId() {
        Post newPost = new Post();
        newPost.setApproved(true);
        newPost.setContent("Content");
        newPost.setTitle("title");
        newPost.setExpireDate(null);
        newPost.setPublishDate(null);
        newPost.setUserId(1); //1 should be a guarantee

        newPost = postDAO.addPost(newPost);
        Hashtag tag = new Hashtag(newPost.getPostId(), "Test");
        Hashtag tag2 = new Hashtag(newPost.getPostId(), "test2");
        hashtagDAO.add(tag);
        hashtagDAO.add(tag2);

        for (Hashtag h : hashtagDAO.getHashtagsByPostId(newPost.getPostId())) {
            assertTrue(h.equals(tag) || h.equals(tag2));
        }

        // Must be 2 different hashtags
        assertEquals(2, hashtagDAO.getHashtagsByPostId(newPost.getPostId()).size());

        for (Hashtag h : hashtagDAO.getAllHashtags()) {
            // Either it's one of these two tags, or it's not a tag for this post
            assertTrue(h.equals(tag) || h.equals(tag2) || h.getPostId() != newPost.getPostId());
        }
    }

    @Test
    @Sql(scripts = {"file:Capstone_Schema_Test.sql", "file:Capstone_data.sql"})
    void hashtagExists() {
        List<Hashtag> tagList = hashtagDAO.getAllHashtags();
        for (Hashtag tag : tagList) {
            // Tag should exist
            assertTrue(hashtagDAO.hashtagExists(tag));
            assertTrue(hashtagDAO.delete(tag));
            // Tag should not exist
            assertFalse(hashtagDAO.delete(tag));
            assertFalse(hashtagDAO.hashtagExists(tag));
        }
    }

    @Test
    @Sql(scripts = {"file:Capstone_Schema_Test.sql", "file:Capstone_data.sql"})
    void add() {
        Post newPost = new Post();
        newPost.setApproved(true);
        newPost.setContent("Content");
        newPost.setTitle("title");
        newPost.setExpireDate(null);
        newPost.setPublishDate(null);
        newPost.setUserId(1); //1 should be a guarantee

        newPost = postDAO.addPost(newPost);
        Hashtag tag = new Hashtag(newPost.getPostId(), "Test");
        // Same tag should be returned
        assertEquals(tag, hashtagDAO.add(tag));
        // Tag should not be returned
        assertThrows(Exception.class, () -> {
            hashtagDAO.add(tag);
        });

    }

    @Test
    @Sql(scripts = {"file:Capstone_Schema_Test.sql", "file:Capstone_data.sql"})
    void delete() {
        List<Hashtag> tagList = hashtagDAO.getAllHashtags();
        for (Hashtag tag : tagList) {
            // Tag should exist
            assertTrue(hashtagDAO.hashtagExists(tag));
            assertTrue(hashtagDAO.delete(tag));
            // Tag should not exist
            assertFalse(hashtagDAO.delete(tag));
            assertFalse(hashtagDAO.hashtagExists(tag));
        }
    }
}