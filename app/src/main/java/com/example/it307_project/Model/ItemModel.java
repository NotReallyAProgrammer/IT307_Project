package com.example.it307_project.Model;

public class ItemModel {
    private String itemName,itemCategory;
    private int itemQty;
    private int itemImg;
    private float itemPrice;

    public ItemModel(float itemPrice, int itemQty, String itemCategory, String itemName, int itemImg) {
        this.itemPrice = itemPrice;
        this.itemQty = itemQty;
        this.itemCategory = itemCategory;
        this.itemName = itemName;
        this.itemImg = itemImg;
    }

    public String getItemName() {
        return itemName;
    }

    public String getItemCategory() {
        return itemCategory;
    }

    public int getItemQty() {
        return itemQty;
    }

    public float getItemPrice() {
        return itemPrice;
    }

    public int getItemImg() {
        return itemImg;
    }
}
