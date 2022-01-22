package com.sg.capstone.model;

import java.util.Objects;

/**
 * This class represents tags for a post
 */
public class Hashtag {
    private int postId;
    private String tag;

    public Hashtag() {
    }

    public Hashtag(int postId, String tag) {
        this.postId = postId;
        this.tag = tag;
    }

    // Gets the post id
    public int getPostId() {
        return postId;
    }

    // Sets the post id
    public void setPostId(int postId) {
        this.postId = postId;
    }

    // Gets the tag
    public String getTag() {
        return tag;
    }

    // Sets the tag
    public void setTag(String tag) {
        this.tag = tag;
    }

    // Equals method
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Hashtag hashtag = (Hashtag) o;
        return postId == hashtag.postId && Objects.equals(tag, hashtag.tag);
    }

    // Hash code for the equals method
    @Override
    public int hashCode() {
        return Objects.hash(postId, tag);
    }
}
