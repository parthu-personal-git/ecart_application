package com.shopping.ecartbackend.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import java.util.Date;

@JsonRootName("order")
public class OrderModel {
    @JsonProperty("id")
    private int id;

    @JsonProperty("order_number")
    private int orderNumber;

    @JsonProperty("product_id")
    private int productId;
    @JsonProperty("quantity")
    private int quantity;

    @JsonProperty("user_id")
    private int userId;

    @JsonProperty("price")
    private double price;

    @JsonProperty("created_date")
    private Date createdDate;

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
