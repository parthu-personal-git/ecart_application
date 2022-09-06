package com.shopping.ecartbackend.service;

import com.shopping.ecartbackend.dao.CartRepository;
import com.shopping.ecartbackend.dao.UserRepository;
import com.shopping.ecartbackend.domain.CartItem;
import com.shopping.ecartbackend.domain.CartItemSingle;
import com.shopping.ecartbackend.domain.CartModel;
import com.shopping.ecartbackend.exception.CartNotFoundException;
import com.shopping.ecartbackend.exception.EmptyInputException;
import com.shopping.ecartbackend.exception.ProductNotFoundException;
import com.shopping.ecartbackend.model.Cart;
import com.shopping.ecartbackend.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class CartService {

    static Logger logger = Logger.getLogger(String.valueOf(CartService.class));

    @Autowired
    private ProductService productService;

    @Autowired
    private UserService userService;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private UserRepository userRepository;

    public Cart addCart(CartModel cartModel){
        //get product by id
        logger.info("starting the CartService:addCart service");
        if(cartModel.getProductId() == 0 || cartModel.getQuantity() == 0){
            throw new EmptyInputException("input fields are empty ", HttpStatus.BAD_REQUEST);
        }
        Product product = productService.findById(cartModel.getProductId());
//        User user = userService.getUserById(cartModel.getUserId());
//        userRepository.findBy(1);
        //throw an exception here " product not found
//        if(product.getId() == 0 || product.getProductName() == null){
//            logger.log(Level.WARNING, "no product record found for the cart ");
//            throw new ProductNotFoundException(0);
//        }


        //once we get the valid product then set the cart properties
        Cart cart = new Cart();
        cart.setProduct(product);
        cart.setQuantity(cartModel.getQuantity());
        cart.setCreatedDate(new Date());
//        cart.setUser(user);
        cartRepository.save(cart);
        logger.info("ending the CartService:addCart service");
        //CartModel cartModelObject = convertCartToModel(cart);
        return  cart;
    }

    public Cart getCartById(int cartId){
        logger.info("starting the CartService:getCartById service");
        Cart cart = cartRepository.findById(cartId).get();
        //throw an exception if the cart is empty
        if(cart == null){
            logger.log(Level.WARNING, "no cart record found for given id ");
            throw new CartNotFoundException(cartId);
        }
        logger.info("ending the CartService:getCartById service");
        return cart;
    }

    public Cart updateCartObject(int id, CartModel cartModel) {
        logger.info("starting the  CartService:updateCart Service");
        Cart cart = cartRepository.findById(id).get();
        Product product  = productService.findById(cart.getProduct().getId());
//        User user = userService.getUserById(cartModel.getUserId());
        if(product.getId() == 0 || product.getProductName() == null){
            logger.log(Level.WARNING, "no product record found for the cart ");
            throw new ProductNotFoundException(0);
        }

//        Cart cart = new Cart();
//        Cart cart = cartRepository.findById(id).get();
        //need to add updated date field in db as well as entity
        cart.setCreatedDate(new Date());
        cart.setProduct(product);
        cart.setQuantity(cartModel.getQuantity());
//        cart.setUser(user);
        cartRepository.save(cart);
        logger.info("ending the CartService:updateCartObject");
        return  cart;
        //if cart
    }

    public void deleteCart(int id) {
        logger.info("starting the  CartService:deleteCartbyId Service");
        Cart cart = cartRepository.findById(id).get();
        if(cart.getId() == 0 || cart.getCreatedDate() ==  null){
            logger.log(Level.WARNING, "no product record found for the cart ");
        }
        //throw an exception if not found
        cartRepository.deleteById(id);
        logger.info("ending the  CartService:deleteCartbyId Service");
    }

    public void deleteAllCartObjects() {
        logger.info("starting the  CartService:deleteAllCart Service");
        cartRepository.deleteAll();
        logger.info("ending the  CartService:deleteAllCart Service");
    }

    private CartModel convertCartToModel(Cart cart){
        CartModel cartModel = new CartModel();
        cartModel.setCreatedDate(cart.getCreatedDate());
        cartModel.setId(cart.getId());
        cartModel.setProductId(cart.getProduct().getId());
        cartModel.setQuantity(cart.getQuantity());
//        cartModel.setUserId(cart.getUser().getUserId());
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