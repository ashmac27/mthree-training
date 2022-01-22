package com.sg.capstone.service;

import com.sg.capstone.data.HashtagDAO;
import com.sg.capstone.data.PostDAO;
import com.sg.capstone.data.UserDAO;
import com.sg.capstone.model.Hashtag;
import com.sg.capstone.model.Post;
import com.sg.capstone.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This class is responsible for the business logic of the content management system
 */
@Service
public class ContentManagementServiceImpl implements ContentManagementService {

    @Autowired
    HashtagDAO hashtagDAO;

    @Autowired
    PostDAO postDAO;

    @Autowired
    UserDAO userDAO;

    // Gets all the post while considering or not the expired date, published date, approved post, and/or tags of the post
    @Override
    public List<Post> getAllPosts(boolean expired, boolean published, boolean approved, String[] tags) {
        List<Post> result;
        if (expired) {
            result = postDAO.getUnexpiredPosts(LocalDateTime.now());
        } else {
            result = postDAO.getAllPosts();
        }

        // Filters list by if they've been published and approved
        List<Post> tempResult = result.stream().filter(post -> {
            return (!published || post.getPublishDate() == null || post.getPublishDate().isBefore(LocalDateTime.now())) &&
                    (!approved || post.isApproved());
        }).collect(Collectors.toList());
        if (tags == null || tags.length == 0) {
            return tempResult;
        }
        return tempResult.stream().filter(post -> {
            // Replace all hashtags with lowercase versions (for comparing)
            List<String> tagList = hashtagDAO.getHashtagsByPostId(post.getPostId()).stream().map(tag -> tag.getTag().toLowerCase()).collect(Collectors.toList());
            // If there ARE tags, filter by them
            for (String tag : tags) {
                // If tag exists, post is valid
                if (tagList.contains(tag.toLowerCase())) {
                    return true;
                }
            }
            return false;
        }).collect(Collectors.toList());
    }

    // Gets a post by Id while considering or not the expired date, published date, approved post, and/or tags of the post
    @Override
    public Post getPostById(int postId, boolean expired, boolean published, boolean approved, String[] tags) {
        Post post = postDAO.getPostById(postId);
        // Post has expired, return nothing
        if (expired && post.getExpireDate() != null && post.getExpireDate().isBefore(LocalDateTime.now())) {
            return null;
        }
        // Post has not been published, return nothing
        if (published && post.getPublishDate() != null && post.getPublishDate().isAfter(LocalDateTime.now())) {
            return null;
        }
        // Post has not been approved, return nothing
        if (approved && !post.isApproved()) {
            return null;
        }

        // If there are no tags to filter by, return post
        if (tags == null || tags.length == 0) {
            return post;
        }
        // Replace all hashtags with lowercase versions (for comparing)
        List<String> tagList = hashtagDAO.getHashtagsByPostId(postId).stream().map(tag -> tag.getTag().toLowerCase()).collect(Collectors.toList());
        // If there ARE tags, filter by them
        for (String tag : tags) {
            // If tag exists, post is valid
            if (tagList.contains(tag.toLowerCase())) {
                return post;
            }
        }
        // Found no posts with one of these tags, return null
        return null;
    }

    // Allows approval of a post
    @Override
    public boolean approvePostById(int postId) {
        Post post = postDAO.getPostById(postId);
        post.setApproved(true);
        return postDAO.editPost(postId, post);
    }

    // Adds a post to the system with the tags
    @Override
    public Post addPost(Post post, String[] tags) {
        Post addedPost = postDAO.addPost(post);
        if (tags != null) {
            for (String tag : tags) {
                Hashtag hashtag = new Hashtag();
                hashtag.setPostId(addedPost.getPostId());
                hashtag.setTag(tag);
                hashtagDAO.add(hashtag);
            }
        }
        return addedPost;
    }

    // Edits a post and its tags
    @Override
    public boolean editPost(Post post, String[] tags) {
        postDAO.getPostById(post.getPostId());
        if (tags != null) {
            for (String tag : tags) {
                Hashtag hashtag = new Hashtag();
                hashtag.setPostId(post.getPostId());
                hashtag.setTag(tag);
                hashtagDAO.add(hashtag);
            }
        }
        return postDAO.editPost(post.getPostId(), post);
    }

    // Delete a post by id
    @Override
    public boolean deletePostById(int postId) {
        return postDAO.deletePost(postId);
    }

    // Add a user to the system
    @Override
    public User addUser(User user) {
        return userDAO.addUser(user);
    }
}
