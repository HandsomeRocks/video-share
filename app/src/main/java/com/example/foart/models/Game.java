package com.example.foart.models;

public class Game {
    private String id, sportsCodeId,leagueId, team1Id,team2Id, venue, startingTime, endingTime;
    private int likesCount, commentsCount;

    public Game(String id, String sportsCodeId, String leagueId, String team1Id, String team2Id, String startingTime, String endingTime, int likesCount, int commentsCount) {
        this.id = id;
        this.sportsCodeId = sportsCodeId;
        this.leagueId = leagueId;
        this.team1Id = team1Id;
        this.team2Id = team2Id;
        this.startingTime = startingTime;
        this.endingTime = endingTime;
        this.likesCount = likesCount;
        this.commentsCount = commentsCount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSportsCodeId() {
        return sportsCodeId;
    }

    public void setSportsCodeId(String sportsCodeId) {
        this.sportsCodeId = sportsCodeId;
    }

    public String getLeagueId() {
        return leagueId;
    }

    public void setLeagueId(String leagueId) {
        this.leagueId = leagueId;
    }

    public String getTeam1Id() {
        return team1Id;
    }

    public void setTeam1Id(String team1Id) {
        this.team1Id = team1Id;
    }

    public String getTeam2Id() {
        return team2Id;
    }

    public void setTeam2Id(String team2Id) {
        this.team2Id = team2Id;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public String getStartingTime() {
        return startingTime;
    }

    public void setStartingTime(String startingTime) {
        this.startingTime = startingTime;
    }

    public String getEndingTime() {
        return endingTime;
    }

    public void setEndingTime(String endingTime) {
        this.endingTime = endingTime;
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
}
