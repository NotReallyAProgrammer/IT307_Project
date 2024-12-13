package com.example.it307_project.Model;

public class CartModel {

    private String name;
    private float price;
    private float total;

    public CartModel(String name, float price, float total) {
        this.name = name;
        this.price = price;
        this.total = total;
    }

    public String getName() {
        return name;
    }

    public float getPrice() {
        return price;
    }

    public float getTotal() {
        return total;
    }
}
