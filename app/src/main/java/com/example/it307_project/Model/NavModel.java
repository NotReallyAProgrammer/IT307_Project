package com.example.it307_project.Model;

public class NavModel {
    private String title;
    private String disc;
    private String buttonName;

    public NavModel(String title, String disc, String buttonName) {
        this.title = title;
        this.disc = disc;
        this.buttonName = buttonName;
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
}
