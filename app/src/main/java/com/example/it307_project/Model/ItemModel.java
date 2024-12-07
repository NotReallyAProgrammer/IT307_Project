package com.example.it307_project.Model;

public class ItemModel {
    private String itemName;
    private String itemCategory;
    private String itemQty;
    private String itemPrice;

    public ItemModel(String itemPrice, String itemQty, String itemCategory, String itemName) {
        this.itemPrice = itemPrice;
        this.itemQty = itemQty;
        this.itemCategory = itemCategory;
        this.itemName = itemName;
    }

    public String getItemName() {
        return itemName;
    }

    public String getItemCategory() {
        return itemCategory;
    }

    public String getItemQty() {
        return itemQty;
    }

    public String getItemPrice() {
        return itemPrice;
    }
}
