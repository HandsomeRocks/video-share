package com.example.foart.models;

import java.util.List;

public class Post {
    String id, gameId, viewerId, content;
    int postType; // 1 = video, 2 = text
    int likesCount, commentsCount;
    List<String> commentIDList;

    public Post(String id, String gameId, String viewerId, String content, int postType) {
        this.id = id;
        this.gameId = gameId;
        this.viewerId = viewerId;
        this.content = content;
        this.postType = postType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public String getViewerId() {
        return viewerId;
    }

    public void setViewerId(String viewerId) {
        this.viewerId = viewerId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getPostType() {
        return postType;
    }

    public void setPostType(int postType) {
        this.postType = postType;
    }

    public int getLikesCount() {
        return likesCount;
    }

    public void setLikesCount(int likesCount) {
        this.likesCount = likesCount;
    }

    public int getCommentsCount() {
        return commentsCount;
    }

    public void setCommentsCount(int commentsCount) {
        this.commentsCount = commentsCount;
    }

    public List<String> getCommentIDList() {
        return commentIDList;
    }

    public void setCommentIDList(List<String> commentIDList) {
        this.commentIDList = commentIDList;
    }
}
