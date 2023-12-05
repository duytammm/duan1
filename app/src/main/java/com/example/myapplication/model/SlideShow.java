package com.example.myapplication.model;

public class SlideShow {
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private int resourceId;

    public SlideShow(int id, int resourceId) {
        this.id = id;
        this.resourceId = resourceId;
    }

    public int getResourceId() {
        return resourceId;
    }

    public void setResourceId(int resourceId) {
        this.resourceId = resourceId;
    }
}
