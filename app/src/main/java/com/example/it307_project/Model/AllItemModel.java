package com.example.it307_project.Model;

public class AllItemModel {
    private String itemName;
    private int itemQuantity;
    private float itemSrp,itemPrice,itemProfit;

    public AllItemModel(String itemName, int itemQuantity, float itemSrp, float itemPrice, float itemProfit) {
        this.itemName = itemName;
        this.itemQuantity = itemQuantity;
        this.itemSrp = itemSrp;
        this.itemPrice = itemPrice;
        this.itemProfit = itemProfit;
    }

    public String getItemName() {
        return itemName;
    }

    public int getItemQuantity() {
        return itemQuantity;
    }

    public float getItemSrp() {
        return itemSrp;
    }

    public float getItemPrice() {
        return itemPrice;
    }

    public float getItemProfit() {
        return itemProfit;
    }
}
