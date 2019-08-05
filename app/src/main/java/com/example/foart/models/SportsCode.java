package com.example.foart.models;

public class SportsCode {
    String Id, name, logo;

    public SportsCode(String id, String name, String logo) {
        Id = id;
        this.name = name;
        this.logo = logo;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
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
