package com.example.foart.models;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Viewer {
    private String id, nickname, email, mobile, avatar;
    private List<String> sharedPostIDList, likedPostIDList, likedCommentIDList;

    public Viewer() {
        // Defualt constructor required for calls to Datasnapshot.getValue(Post.class)
    }

    public Viewer(String id, String nickname, String email, String mobile, String avatar) {
        this.id = id;
        this.nickname = nickname;
        this.email = email;
        this.mobile = mobile;
        this.avatar = avatar;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public List<String> getSharedPostIDList() {
        return sharedPostIDList;
    }

    public void setSharedPostIDList(List<String> sharedPostIDList) {
        this.sharedPostIDList = sharedPostIDList;
    }

    public List<String> getLikedPostIDList() {
        return likedPostIDList;
    }

    public void setLikedPostIDList(List<String> likedPostIDList) {
        this.likedPostIDList = likedPostIDList;
    }

    public List<String> getLikedCommentIDList() {
        return likedCommentIDList;
    }

    public void setLikedCommentIDList(List<String> likedCommentIDList) {
        this.likedCommentIDList = likedCommentIDList;
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("nickname", nickname);
        result.put("email", email);
        result.put("mobile", mobile);
        result.put("avatar", avatar);
        result.put("sharedPostIDList", sharedPostIDList);
        result.put("likedPostIDList", likedPostIDList);
        result.put("likedCommentIDList", likedCommentIDList);

        return result;
    }
}
