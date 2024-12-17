package com.example.it307_project.Model;

public class NavModel {
    private String title;
    private String disc;
    private String buttonName;
    private int image;


    public NavModel(String title, String disc, String buttonName, int image) {
        this.title = title;
        this.disc = disc;
        this.buttonName = buttonName;
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public String getDisc() {
        return disc;
    }

    public String getButtonName() {
        return buttonName;
    }

    public int getImage() {
        return image;
    }
}
