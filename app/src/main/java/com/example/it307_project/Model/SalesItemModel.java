package com.example.it307_project.Model;

public class SalesItemModel {
    private String name,category;
    private float price;
    private int img;

    public SalesItemModel(String name, String category, float price, int img) {
        this.name = name;
        this.category = category;
        this.price = price;
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public float getPrice() {
        return price;
    }

    public int getImg() {
        return img;
    }

    public String getCategory() {
        return category;
    }
}
