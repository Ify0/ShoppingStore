package com.example.shoppingstore;

import java.io.Serializable;

public class Product implements Serializable {
    String title;
    String category;
    String manufacturer;
    String price;
    String productId;
    int quantity;
    String imageUrl;

    public Product(String title, String category, String manufacturer, String price, String productId, int quantity, String imageUrl){
        this.title = title;
        this.category = category;
        this.manufacturer = manufacturer;
        this.price = price;
        this.productId = productId;
        this.quantity = quantity;
        this.imageUrl = imageUrl;
    }
    public Product(String title, String category, String manufacturer, String price){
        this.title = title;
        this.category = category;
        this.manufacturer = manufacturer;
        this.price = price;
    }
    public Product(String title, String category, String manufacturer, String price, int quantity, String imageUrl){
        this.title = title;
        this.category = category;
        this.manufacturer = manufacturer;
        this.price = price;
        this.quantity = quantity;
        this.imageUrl = imageUrl;
    }
    public Product(String title, String category, String manufacturer, String price, int quantity){
        this.title = title;
        this.category = category;
        this.manufacturer = manufacturer;
        this.price = price;
        this.quantity = quantity;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
