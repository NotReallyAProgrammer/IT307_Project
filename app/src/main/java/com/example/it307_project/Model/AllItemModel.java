package com.example.it307_project.Model;

import java.io.Serializable;

public class AllItemModel implements Serializable {
    private  String itemName;
    private  String category;
    private  int itemQuantity;
    private  String itemId;
    private  float itemPrice;
    private  float itemSRP;
    private  float profit;
    private  int imageResId;
    private  String itemImage;

    public AllItemModel(String itemId,String itemName, String category,
                        int itemQuantity,float itemPrice, float itemSRP, float profit,
                        int imageResId, String itemImage) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.category = category;
        this.itemQuantity = itemQuantity;
        this.itemPrice = itemPrice;
        this.itemSRP = itemSRP;
        this.profit = profit;
        this.imageResId = imageResId;
        this.itemImage = itemImage;
    }
    public String getItemName() {
        return itemName;
    }

    public String getCategory() {
        return category;
    }
    public int getItemQuantity() {
        return itemQuantity;
    }
    public float getItemPrice() {
        return itemPrice;
    }
    public float getItemSRP() {
        return itemSRP;
    }

    public float getProfit() {
        return profit;
    }
    public int getImageResId() {
        return imageResId;
    }
    public String getItemImage() {
        return itemImage;
    }
    public String getItemId() {
        return itemId;
    }
}
