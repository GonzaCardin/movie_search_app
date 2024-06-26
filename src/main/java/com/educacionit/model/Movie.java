package com.educacionit.model;

import java.util.List;

public class Movie {
    private String id;
    private String name;
    private String officialSiteUrl;
    private String imageUrl;
    private byte[] image;
    private List<Genre> genres;

    public Movie(String id, String name, String officialSiteUrl, String imageUrl,byte[] image, List<Genre> genres) {
        this.id = id;
        this.name = name;
        this.officialSiteUrl = officialSiteUrl;
        this.imageUrl = imageUrl;
        this.genres = genres;
        this.image = image;
    }

    public Movie() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOfficialSiteUrl() {
        return officialSiteUrl;
    }

    public void setOfficialSiteUrl(String officialSiteUrl) {
        this.officialSiteUrl = officialSiteUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }
    
    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

}
