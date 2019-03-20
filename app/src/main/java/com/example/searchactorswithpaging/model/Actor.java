package com.example.searchactorswithpaging.model;

import com.google.gson.annotations.SerializedName;

public class Actor {

    @SerializedName("profile_path")
    private String profilePath ;
    @SerializedName("name")
    private String name ;
    @SerializedName("popularity")
    private String popularity ;

    public Actor(String profilePath, String name, String popularity) {
        this.profilePath = profilePath;
        this.name = name;
        this.popularity = popularity;
    }

    public String getProfilePath() {
        return profilePath;
    }

    public String getName() {
        return name;
    }

    public String getPopularity() {
        return popularity;
    }

}