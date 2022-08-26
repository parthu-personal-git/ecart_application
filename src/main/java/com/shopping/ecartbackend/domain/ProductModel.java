package com.shopping.ecartbackend.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import java.io.Serializable;

@JsonRootName(value = "product")
public class ProductModel implements Serializable {
    @JsonProperty(value = "id")
    int productId;
    @JsonProperty(value = "category_id")
    int categoryId;
    @JsonProperty(value = "price")
    double price;
    @JsonProperty(value = "product_name")
    String productName;
    @JsonProperty(value = "descritpion")
    String description;
    @JsonProperty(value = "image_url")
    String imageURL;

    public ProductModel(){};

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }
}
