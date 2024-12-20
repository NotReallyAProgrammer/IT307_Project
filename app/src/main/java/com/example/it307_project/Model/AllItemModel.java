package com.example.it307_project.Model;

import java.io.Serializable;

public class AllItemModel implements Serializable {
    // Define the attributes of the model class as final
    private final String itemName;
    private final String category;

    private final int itemQuantity;
    private final String itemId;
    private final float itemPrice;
    private final float itemSRP;
    private final float profit;
    private final int imageResId;
    private final String itemImage;

    // Constructor to initialize the attributes
    public AllItemModel(String itemId,String itemName, String category, int itemQuantity,float itemPrice, float itemSRP, float profit, int imageResId, String itemImage) {
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

    // Getter methods to access the attributes
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
