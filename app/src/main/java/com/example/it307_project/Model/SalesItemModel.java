package com.example.it307_project.Model;

public class SalesItemModel {
    private String name;
    private float price;

    public SalesItemModel(String name, float price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public float getPrice() {
        return price;
    }
}
