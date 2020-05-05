package com.example.task222;


import java.io.Serializable;

public class ItemData implements Serializable {
    private int image;
    private String title;
    private String subtitle;


    public ItemData(int image, String title, String subtitle) {
        this.image = image;
        this.title = title;
        this.subtitle = subtitle;

    }

    public Integer getImage() {
        return image;
    }

    public String getTitle() {
        return title;
    }

    public String getSubtitle() {
        return subtitle;
    }


    @Override
    public String toString() {
        return "image: " + this.image + ", title: " + this.title + ", subtitle: " + this.subtitle + " \n";
    }

    }
