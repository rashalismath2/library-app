package com.ismathlifehacks.library.Model;

import java.io.Serializable;

public class Book implements Serializable {

    private String title;
    private String author;
    private int id;
    private String description;
    private String url;

    public String getReview_id() {
        return review_id;
    }

    public void setReview_id(String review_id) {
        this.review_id = review_id;
    }

    private String review_id="fEzuYrBo6fo";

    public int getRatings() {
        return ratings;
    }

    public void setRatings(int ratings) {
        this.ratings = ratings;
    }

    private int ratings;

    public int getPublished() {
        return published;
    }

    public void setPublished(int published) {
        this.published = published;
    }

    private int published;

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    private String tag;


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


}
