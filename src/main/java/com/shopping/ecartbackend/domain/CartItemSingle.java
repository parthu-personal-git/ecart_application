package com.shopping.ecartbackend.domain;

import com.shopping.ecartbackend.model.Cart;
import com.shopping.ecartbackend.model.Product;

public class CartItemSingle {
    private int id;
    private int quantity;
    private Product product;

    public CartItemSingle(){};
    public CartItemSingle(Cart cart){
        this.id = cart.getCartId();
        this.quantity = cart.getQuantity();
        this.setProduct(cart.getProduct());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
