package com.sg.capstone.data;

import com.sg.capstone.model.Hashtag;

import java.util.List;

public interface HashtagDAO {

    /**
     * Gets all tags in the DAO
     *
     * @return All tags in the DAO
     */
    public List<Hashtag> getAllHashtags();

    /**
     * Gets a list of all unique hashtags
     *
     * @return List of unique tags, with postID set to 0
     */
    public List<Hashtag> getAllUniqueHashtags();

    /**
     * Gets the list of hashtags on a post
     *
     * @param postId post to query
     * @return List of all hashtags on a post
     */
    public List<Hashtag> getHashtagsByPostId(int postId);

    /**
     * Gets a list of hashtags that have the same tag
     * (Useful for getting posts by tag)
     *
     * @param hashtag Tag to search by
     * @return List of hashtags with the given tag
     */
    public List<Hashtag> getHashtagListByTag(String hashtag);

    /**
     * Returns whether or not a hashtag exists
     *
     * @param hashtag tag to verify
     */
    public boolean hashtagExists(Hashtag hashtag);

    /**
     * Adds hashtag to the DAO
     *
     * @param hashtag hashtag to add
     * @return true if successfully added
     */
    public Hashtag add(Hashtag hashtag);

    /**
     * Deletes a hashtag from the DAO
     *
     * @param hashtag tag to be deleted
     * @return True if found and deleted, false otherwise
     */
    public boolean delete(Hashtag hashtag);

    // Because hashtags don't have an ID, they can't be updated. And they don't need to be
}
