package com.sg.capstone.controller;

import com.sg.capstone.model.Post;
import com.sg.capstone.service.ContentManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * This controller contains the different endpoints used by a regular user
 */
@RestController
@RequestMapping("/home")
public class HomeController {

    @Autowired
    private ContentManagementService service;

    // Gets all the valid posts
    @GetMapping("/posts")
    public List<Post> getAllValidPost() {
        return service.getAllPosts(false, true, true, null);
    }

    // Gets a valid post by id
    @GetMapping("/posts/{postId}")
    public Post getValidPostById(@PathVariable int postId) {
        return service.getPostById(postId, false, true, true, null);
    }
}
