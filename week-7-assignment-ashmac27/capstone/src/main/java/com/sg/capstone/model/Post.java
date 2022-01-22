package com.sg.capstone.model;

import java.time.LocalDateTime;

/**
 * This class represents a post entity
 */
public class Post {
    private int postId, userId;
    private boolean approved;
    private String title, content;
    private LocalDateTime dateAdded, publishDate, expireDate;

    public Post(int postId, int userId, boolean approved, String title, String content, LocalDateTime dateAdded, LocalDateTime publishDate, LocalDateTime expireDate) {
        this.postId = postId;
        this.userId = userId;
        this.approved = approved;
        this.title = title;
        this.content = content;
        this.dateAdded = dateAdded;
        this.publishDate = publishDate;
        this.expireDate = expireDate;
    }

    public Post(int userId, boolean approved, String title, String content, LocalDateTime publishDate, LocalDateTime expireDate) {
        this.userId = userId;
        this.approved = approved;
        this.title = title;
        this.content = content;
        this.publishDate = publishDate;
        this.expireDate = expireDate;
    }
    
    public Post() {
        
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(LocalDateTime dateAdded) {
        this.dateAdded = dateAdded;
    }

    public LocalDateTime getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(LocalDateTime publishDate) {
        this.publishDate = publishDate;
    }

    public LocalDateTime getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(LocalDateTime expireDate) {
        this.expireDate = expireDate;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 67 * hash + this.postId;
        hash = 67 * hash + this.userId;
        hash = 67 * hash + (this.approved ? 1 : 0);
        hash = 67 * hash + (this.title != null ? this.title.hashCode() : 0);
        hash = 67 * hash + (this.content != null ? this.content.hashCode() : 0);
        hash = 67 * hash + (this.dateAdded != null ? this.dateAdded.hashCode() : 0);
        hash = 67 * hash + (this.publishDate != null ? this.publishDate.hashCode() : 0);
        hash = 67 * hash + (this.expireDate != null ? this.expireDate.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Post other = (Post) obj;
        if (this.postId != other.postId) {
            return false;
        }
        if (this.userId != other.userId) {
            return false;
        }
        if (this.approved != other.approved) {
            return false;
        }
        if ((this.title == null) ? (other.title != null) : !this.title.equals(other.title)) {
            return false;
        }
        if ((this.content == null) ? (other.content != null) : !this.content.equals(other.content)) {
            return false;
        }
        if (this.dateAdded != other.dateAdded && (this.dateAdded == null || !this.dateAdded.equals(other.dateAdded))) {
            return false;
        }
        if (this.publishDate != other.publishDate && (this.publishDate == null || !this.publishDate.equals(other.publishDate))) {
            return false;
        }
        if (this.expireDate != other.expireDate && (this.expireDate == null || !this.expireDate.equals(other.expireDate))) {
            return false;
        }
        return true;
    }
}
