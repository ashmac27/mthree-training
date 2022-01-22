package com.sg.capstone.service;

import com.sg.capstone.model.Post;
import com.sg.capstone.model.User;
import java.util.List;

public interface ContentManagementService {
    
    /**
     * Gets all posts based on simple filters (Valid is all set to true)
     * @param expired If true, excludes expired posts
     * @param published If true, excludes posts that haven't been published
     * @param approved if true, excludes posts that haven't been approved
     * @param tags any tags to filter by. If none, set null
     * @return list of posts according to filters
     */
    public List<Post> getAllPosts(boolean expired, boolean published, boolean approved, String[] tags);
    
    /**
     * Gets a post by postid, based on these filters (Valid is all set to true)
     * @param postId ID of the post to get
     * @param expired If true, excludes expired posts
     * @param published If true, excludes posts that haven't been published
     * @param approved if true, excludes posts that haven't been approved
     * @param tags any tags to filter by. If none, set null
     * @return A post based on given filters.
     */
    public Post getPostById(int postId, boolean expired, boolean published, boolean approved, String[] tags);
    
    /**
     * Approves post based on Id
     * @param postId ID of post to approve
     * @return True if post was found and updated, false otherwise 
     */
    public boolean approvePostById(int postId);
    
    /**
     * Adds a post to the DAO
     * @param post Post to be added
     * @param tags List of tags connected to this post
     * @return If successful, the new post with generated values
     */
    public Post addPost(Post post, String[] tags);
    
    /**
     * Edits post in the DAO
     * @param post Post to be updated
     * @param tags List of tags connected to this post
     * @return True if post was found and updated successfully
     */
    public boolean editPost(Post post, String[] tags);
    
    /**
     * Deletes post
     * @param postId ID of post to delete
     * @return True if post exists and was deleted successfully
     */
    public boolean deletePostById(int postId);
    
    /**
     * Adds user
     * @param user User to add
     * @return User with generated fields
     */
    public User addUser(User user);
}
