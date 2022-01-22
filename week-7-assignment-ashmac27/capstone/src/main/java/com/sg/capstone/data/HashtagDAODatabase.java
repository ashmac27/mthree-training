package com.sg.capstone.data;

import com.sg.capstone.model.Hashtag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * This class represents the DAO that is responsible for database related functions to hashtags
 */
@Repository
public class HashtagDAODatabase implements HashtagDAO {

    @Autowired
    private final JdbcTemplate template;

    public HashtagDAODatabase(JdbcTemplate template) {
        this.template = template;
    }

    // Gets all hashtag
    @Override
    public List<Hashtag> getAllHashtags() {
        return template.query("SELECT * FROM hashtags", new HashtagRowMapper());
    }

    // Gets all unique tags
    @Override
    public List<Hashtag> getAllUniqueHashtags() {
        return template.query("SELECT DISTINCT LOWER(Tag) AS Tag FROM hashtags", new HashtagRowMapper());
    }

    // Gets all the hashtag from a post
    @Override
    public List<Hashtag> getHashtagsByPostId(int postId) {
        return template.query("SELECT * FROM hashtags WHERE PostId = ?", new HashtagRowMapper(), postId);
    }

    // Gets a list of hashtag with the same name
    @Override
    public List<Hashtag> getHashtagListByTag(String tag) {
        return template.query("SELECT * FROM hashtags WHERE LOWER(Tag) = LOWER( ? )", new HashtagRowMapper(), tag);
    }

    // Check if the tag exists
    @Override
    public boolean hashtagExists(Hashtag hashtag) {
        return template.query("SELECT * FROM hashtags WHERE PostId = ? AND LOWER(Tag) = LOWER( ? )", new HashtagRowMapper(), hashtag.getPostId(), hashtag.getTag()).size() > 0;
    }

    // Adds a hashtag
    @Override
    public Hashtag add(Hashtag hashtag) {
        template.update("INSERT INTO hashtags (PostId,Tag) VALUES (?,?)", hashtag.getPostId(), hashtag.getTag());
        return hashtag;
    }

    // Deletes a hashtag
    @Override
    public boolean delete(Hashtag hashtag) {
        final String sql = "DELETE FROM hashtags WHERE PostId = ? AND Tag = ?;";
        return template.update(sql, hashtag.getPostId(), hashtag.getTag()) > 0;
    }

    private final class HashtagRowMapper implements RowMapper<Hashtag> {
        @Override
        public Hashtag mapRow(ResultSet rs, int i) throws SQLException {
            return new Hashtag(rs.getInt("PostId"), rs.getString("Tag"));
        }

    }
}
