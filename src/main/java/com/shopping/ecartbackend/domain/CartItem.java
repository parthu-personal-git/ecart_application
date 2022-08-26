package com.shopping.ecartbackend.domain;

import java.util.List;

public class CartItem {
    private List<CartItemSingle> cartItemSingleList;
    private double totalCost;

    public CartItem(){};

    public List<CartItemSingle> getCartItemSingleList() {
        return cartItemSingleList;
    }

    public void setCartItemSingleList(List<CartItemSingle> cartItemSingleList) {
        this.cartItemSingleList = cartItemSingleList;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }
}
