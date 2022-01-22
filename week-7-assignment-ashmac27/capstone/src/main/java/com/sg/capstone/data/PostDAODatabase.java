package com.sg.capstone.data;

import com.sg.capstone.model.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This class represents the DAO that is responsible for database related functions to Posts
 */
@Repository
public class PostDAODatabase implements PostDAO {

    @Autowired
    private JdbcTemplate jdbc;

    // Adds a post
    @Override
    public Post addPost(Post post) {
        post.setDateAdded(LocalDateTime.now());
        final String ADD_POST = "INSERT INTO posts " +
                "(Title, Content, DateAdded, Approved, PublishDate, ExpireDate, UserId) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbc.update((Connection conn) -> {
            PreparedStatement statement = conn.prepareStatement(ADD_POST, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, post.getTitle());
            statement.setString(2, post.getContent());
            statement.setTimestamp(3, Timestamp.valueOf(post.getDateAdded()));
            statement.setBoolean(4, post.isApproved());
            // These dates can be null
            if (post.getPublishDate() == null) {
                statement.setNull(5, Types.NULL);
            } else {
                statement.setTimestamp(5, Timestamp.valueOf(post.getPublishDate()));
            }
            if (post.getExpireDate() == null) {
                statement.setNull(6, Types.NULL);
            } else {
                statement.setTimestamp(6, Timestamp.valueOf(post.getExpireDate()));
            }
            statement.setInt(7, post.getUserId());
            return statement;
        }, keyHolder);
        // Gets record with all values generated
        return getPostById(keyHolder.getKey().intValue());
    }

    // Gets list of all posts
    @Override
    public List<Post> getAllPosts() {
        final String SELECT_ALL_POSTS = "SELECT * " + "FROM posts";
        List<Post> allPosts = jdbc.query(SELECT_ALL_POSTS, new PostMapper());
        return allPosts;
    }

    // Gets list of all posts
    @Override
    public List<Post> getExpiredPosts(LocalDateTime expireDate) {
        // Gets the list of post that can be expired
        List<Post> getPostsThatCanBeExpired = getAllPosts().stream()
                .filter(post -> post.getExpireDate() != null)
                .collect(Collectors.toList());

        // Gets a list of post that expired
        return getPostsThatCanBeExpired.stream()
                .filter(post -> post.getExpireDate().isBefore(expireDate))
                .collect(Collectors.toList());
    }

    // Gets list of all posts
    @Override
    public List<Post> getUnexpiredPosts(LocalDateTime expireDate) {
        // Gets the list of post that can expired
        List<Post> getPostsThatCanBeExpired = getAllPosts().stream()
                .filter(post -> post.getExpireDate() != null)
                .collect(Collectors.toList());

        // Gets the list of post that are not expired
        return getPostsThatCanBeExpired.stream()
                .filter(post -> post.getExpireDate().isAfter(expireDate))
                .collect(Collectors.toList());
    }

    // Get posts by its id
    @Override
    public Post getPostById(int postId) {
        final String SELECT_POST = "SELECT * " +
                "FROM posts " +
                "WHERE postId = ?";
        Post post = jdbc.queryForObject(SELECT_POST, new PostMapper(), postId);
        return post;
    }

    // Edits a post
    @Override
    public Boolean editPost(int postId, Post post) {
        final String UPDATE_POST = "UPDATE posts SET " +
                "Title = ?, " +
                "Content = ?, " +
                "Approved = ?, " +
                "PublishDate = ?, " +
                "ExpireDate = ?, " +
                "UserId = ? " +
                "WHERE postId = ?";
        return jdbc.update(UPDATE_POST,
                post.getTitle(),
                post.getContent(),
                post.isApproved(),
                // These parameters can be null, which needs to be handled
                post.getPublishDate(),
                post.getExpireDate(),
                post.getUserId(),
                postId) > 0;

    }

    // Deletes a post
    @Override
    public Boolean deletePost(int postId) {
        final String DELETE_POST = "DELETE FROM posts " +
                "WHERE PostId = ?";
        return jdbc.update(DELETE_POST, postId) > 0;

    }

    public final static class PostMapper implements RowMapper<Post> {
        @Override
        public Post mapRow(ResultSet rs, int i) throws SQLException {
            Post post = new Post();
            post.setPostId(rs.getInt("PostId"));
            post.setTitle(rs.getString("Title"));
            post.setContent(rs.getString("Content"));
            post.setDateAdded(rs.getTimestamp("DateAdded").toLocalDateTime());
            post.setApproved(rs.getBoolean("Approved"));
            // publishDate and ExpireDate can be null
            try {
                post.setPublishDate(rs.getTimestamp("PublishDate").toLocalDateTime());
            } catch (Exception e) {
                post.setPublishDate(null);
            }
            try {
                post.setExpireDate(rs.getTimestamp("ExpireDate").toLocalDateTime());
            } catch (Exception e) {
                post.setExpireDate(null);
            }
            post.setUserId(rs.getInt("UserId"));
            return post;
        }
    }
}
