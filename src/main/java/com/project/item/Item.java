package com.project.item;

public class Item {
    private String categoryName;
    private String itemName;
    private int quantity;
    private double price;

    public Item() {
    }

    public Item(String categoryName, String itemName, int quantity, double price) {
        this.categoryName = categoryName;
        this.itemName = itemName;
        this.quantity = quantity;
        this.price = price;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "ItemName: " + itemName  +
                ", Quantity=" + quantity +
                ", Price=" + price +
                "\n";
    }
}
