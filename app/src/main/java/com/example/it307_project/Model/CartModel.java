package com.example.it307_project.Model;

public class CartModel {

    private String name;
    private float price;
    private float total;
    private int qty,image;

    public CartModel(String name, float price, float total, int qty, int image) {
        this.name = name;
        this.price = price;
        this.total = total;
        this.qty = qty;
        this.image = image;
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

    public int getQty() {
        return qty;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public int getImage() {
        return image;
    }
}
