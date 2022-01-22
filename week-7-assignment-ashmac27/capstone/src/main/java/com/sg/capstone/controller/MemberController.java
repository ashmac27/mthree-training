package com.sg.capstone.controller;

import com.sg.capstone.model.Post;
import com.sg.capstone.service.ContentManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * This controller contains the different endpoints used by a member of the blog
 */
@RestController
@RequestMapping("/member")
public class MemberController {

    @Autowired
    private ContentManagementService service;

    // Gets all the posts from the system
    @GetMapping("/posts")
    public List<Post> getAllPosts() {
        return service.getAllPosts(false, false, false, null);
    }

    // Gets all the unexpired posts from the system
    @GetMapping("/posts/unexpired")
    public List<Post> getAllUnexpiredPosts() {
        return service.getAllPosts(true, false, false, null);
    }

    // Gets all expired posts from the system
    @GetMapping("/posts/expired")
    public List<Post> getAllExpiredPosts() {
        // Filter out posts that aren't expired
        return service.getAllPosts(false, true, false, null).stream().filter(post -> post.getExpireDate() != null && post.getExpireDate().isBefore(LocalDateTime.now())).collect(Collectors.toList());
    }

    // Gets a posts from the system by id
    @PostMapping("/posts/{postId}")
    public Post getPostById(@PathVariable int postId) {
        return service.getPostById(postId, false, false, false, null);
    }

    // Adds a post to the system, but needs approval
    @PostMapping("/addpost")
    public Post addPostWithPendingApproval(@RequestBody Post post) {
        Matcher m = Pattern.compile("(#\\S+)").matcher(post.getContent());
        List<String> tagList = new ArrayList<String>();
        while (m.find()) {
            String find = m.group();
            if (!tagList.contains(find.toLowerCase())) tagList.add(find.substring(1));
        }
        post.setApproved(false);
        return service.addPost(post, tagList.toArray(new String[0]));
    }
}