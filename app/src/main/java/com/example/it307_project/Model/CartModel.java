package com.example.it307_project.Model;

import java.io.Serializable;

public class CartModel implements Serializable {

    private String name,imageByte,id;
    private float price;
    private float total;
    private int qty,imageResid;

    public CartModel(String id,String name, float price, float total, int qty, int imageResid, String imageByte) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.total = total;
        this.qty = qty;
        this.imageByte = imageByte;
        this.imageResid = imageResid;
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

    public int getImageResid() {
        return imageResid;
    }

    public String getImageByte() {
        return imageByte;
    }

    public String getId() {
        return id;
    }
}
