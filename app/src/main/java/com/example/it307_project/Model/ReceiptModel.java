package com.example.it307_project.Model;

import java.io.Serializable;

public class ReceiptModel implements Serializable {

    private String name;
    private int qty;
    private float price;

    public ReceiptModel(String name, int qty, float price) {
        this.name = name;
        this.qty = qty;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public int getQty() {
        return qty;
    }

    public float getPrice() {
        return price;
    }
}
