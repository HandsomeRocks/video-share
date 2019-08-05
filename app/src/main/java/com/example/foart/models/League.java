package com.example.foart.models;

public class League {
    String id, sportsCodeId, name, logo;

    public League(String id, String sportsCodeId, String name, String logo) {
        this.id = id;
        this.sportsCodeId = sportsCodeId;
        this.name = name;
        this.logo = logo;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }
}
