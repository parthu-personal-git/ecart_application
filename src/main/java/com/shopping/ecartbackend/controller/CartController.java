package com.shopping.ecartbackend.controller;

import com.shopping.ecartbackend.common.ApiResponse;
import com.shopping.ecartbackend.domain.CartItem;
import com.shopping.ecartbackend.domain.CartModel;
import com.shopping.ecartbackend.model.Cart;
import com.shopping.ecartbackend.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/shopping/carts")
public class CartController {
    @Autowired
    private CartService cartService;

    @GetMapping("")
    public ResponseEntity<CartItem> getCartItems(){
        CartItem cartItem = cartService.getCartItems();
        return new ResponseEntity<>(cartItem, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addToCart(@RequestBody CartModel cartModel){
        Cart cart = cartService.addCart(cartModel);
        return new ResponseEntity<>(new ApiResponse(true,"cart has been created"),HttpStatus.CREATED);
    }

    @GetMapping("/cart/{id}")
    public ResponseEntity<Cart> getCart(@PathVariable(value = "id") int id){
        Cart cart =  cartService.getCartById(id);
        return new ResponseEntity<>(cart, HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse> updateCart(@PathVariable(value = "id") int id, @RequestBody  CartModel cartModel){
        Cart cart = cartService.updateCartObject(id,cartModel);
        return new ResponseEntity<>(new ApiResponse(true,"cart has been updated has updated"), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleteCart(@PathVariable(value = "id") int id){
        cartService.deleteCart(id);
        return new ResponseEntity<>(new ApiResponse(true,"A cart has been deleted"), HttpStatus.OK);
    }

    //not tested
    @DeleteMapping("/")
    public ResponseEntity<ApiResponse> deleteAllCarts(){
        cartService.deleteAllCartObjects();
        return new ResponseEntity<>(new ApiResponse(true,"All carts has been deleted"), HttpStatus.OK);
    }


}
