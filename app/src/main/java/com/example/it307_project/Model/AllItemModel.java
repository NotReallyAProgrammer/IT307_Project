package com.example.it307_project.Model;

public class AllItemModel {
    private String itemName,itemCategory;
    private int itemQuantity,itemImg;
    private float itemSrp,itemPrice,itemProfit;


    public AllItemModel(String itemName, String itemCategory, int itemQuantity, float itemSrp, float itemPrice, float itemProfit, int itemImg) {
        this.itemName = itemName;
        this.itemCategory = itemCategory;
        this.itemQuantity = itemQuantity;
        this.itemSrp = itemSrp;
        this.itemPrice = itemPrice;
        this.itemProfit = itemProfit;
        this.itemImg = itemImg;
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

    public int getItemImg() {
        return itemImg;
    }

    public String getItemCategory() {
        return itemCategory;
    }
}
