package com.mobiotics.deeptimay.deeptimaymobiotics.model;

import java.io.Serializable;

public class Records implements Serializable {

    private String id;

    private String title;

    private String description;

    private String thumb;

    private String url;

    private String startTime;

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "ClassPojo [id = " + id + ", title = " + title + ", description = " + description + ", startTime = " + startTime + ", thumb = " + thumb + ", url = " + url + "]";
    }
}
