package com.example.it307_project.Model;

import java.io.Serializable;

public class AllItemModel implements Serializable {
    private String itemName,itemCategory,itemImgByte;
    private int itemQuantity,itemImg;
    private float itemSrp,itemPrice,itemProfit;


    public AllItemModel(String itemName, String itemCategory, int itemQuantity, float itemSrp, float itemPrice, float itemProfit, int itemImg,String itemImgByte) {
        this.itemName = itemName;
        this.itemCategory = itemCategory;
        this.itemQuantity = itemQuantity;
        this.itemSrp = itemSrp;
        this.itemPrice = itemPrice;
        this.itemProfit = itemProfit;
        this.itemImg = itemImg;
        this.itemImgByte = itemImgByte;
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

    public String getItemImgByte() {
        return itemImgByte;
    }

    public String getItemCategory() {
        return itemCategory;
    }
}
