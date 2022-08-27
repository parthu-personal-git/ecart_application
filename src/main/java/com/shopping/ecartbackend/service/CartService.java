package com.shopping.ecartbackend.service;

import com.shopping.ecartbackend.dao.CartRepository;
import com.shopping.ecartbackend.domain.CartItem;
import com.shopping.ecartbackend.domain.CartItemSingle;
import com.shopping.ecartbackend.domain.CartModel;
import com.shopping.ecartbackend.exception.CartNotFoundException;
import com.shopping.ecartbackend.model.Cart;
import com.shopping.ecartbackend.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CartService {

    @Autowired
    private ProductService productService;

    @Autowired
    private CartRepository cartRepository;

    public Cart addCart(CartModel cartModel){
        //get product by id
        Product product = productService.findById(cartModel.getProductId());
        //once we get the valid product then set the cart properties
        Cart cart = new Cart();
        cart.setProduct(product);
        cart.setQuantity(cartModel.getQuantity());
        cart.setCreatedDate(new Date());
        cartRepository.save(cart);
        //CartModel cartModelObject = convertCartToModel(cart);
        return  cart;
    }

    public Cart getCartById(int cartId){
        Cart cart = cartRepository.findById(cartId).get();
        //throw an exception if the cart is empty
        if(cart == null){
            throw new CartNotFoundException(cartId);
        }
        return cart;
    }

    public Cart updateCartObject(int id, CartModel cartModel) {
        Product product  = productService.findById(cartModel.getProductId());
        Cart cart = new Cart();
        //need to add updated date field in db as well as entity
        cart.setCreatedDate(new Date());
        cart.setProduct(product);
        cart.setQuantity(cartModel.getQuantity());
        return  cart;
        //if cart
    }

    public void deleteCart(int id) {
        Cart cart = cartRepository.findById(id).get();
        //throw an exception if not found
        cartRepository.deleteById(id);
    }

    public void deleteAllCartObjects() {
        cartRepository.deleteAll();
    }

    private CartModel convertCartToModel(Cart cart){
        CartModel cartModel = new CartModel();
        cartModel.setCreatedDate(cart.getCreatedDate());
        cartModel.setId(cart.getCartId());
        cartModel.setProductId(cart.getProduct().getId());
        cartModel.setQuantity(cart.getQuantity());
        return cartModel;
    }

    public CartItem getCartItems(){
        double totalCost = 0;
        List<Cart> cartList = cartRepository.findAll();
        List<CartItemSingle> cartItems = new ArrayList<>();
        for(Cart cart : cartList){
            CartItemSingle cartItemSingle = new CartItemSingle(cart);
            totalCost += cartItemSingle.getQuantity() * cart.getProduct().getPrice();
            cartItems.add(cartItemSingle);
        }
        CartItem cartItem = new CartItem();
        cartItem.setTotalCost(totalCost);
        cartItem.setCartItemSingleList(cartItems);
        return cartItem;
    }

}