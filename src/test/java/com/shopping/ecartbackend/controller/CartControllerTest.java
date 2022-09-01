package com.shopping.ecartbackend.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shopping.ecartbackend.common.Utility;
import com.shopping.ecartbackend.domain.CartItem;
import com.shopping.ecartbackend.domain.CartItemSingle;
import com.shopping.ecartbackend.domain.CartModel;
import com.shopping.ecartbackend.domain.ProductModel;
import com.shopping.ecartbackend.model.Cart;
import com.shopping.ecartbackend.model.Category;
import com.shopping.ecartbackend.model.Product;
import com.shopping.ecartbackend.service.CartService;
import com.shopping.ecartbackend.service.ProductService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class CartControllerTest {
    @InjectMocks
    CartController cartController = new CartController();

    @Mock
    CartService cartService;

    @Autowired
    private MockMvc mockMvc;



    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(cartController).build();
    }

    @Test
    public void getCartItems_shouldReturnAllCartItems() throws Exception {
        CartItem cartItem = getCartItems();
        when(cartService.getCartItems()).thenReturn(cartItem);
        mockMvc.perform(MockMvcRequestBuilders.get("/shopping/carts/")).andExpect(status().isOk());
        assertEquals(cartItem.getTotalCost(),100.00);
    }

    @Test
    public void getCart_returnsSingleCart() throws Exception {
        Cart cart = getCartObject();
        when(cartService.getCartById(anyInt())).thenReturn(cart);
        mockMvc.perform(
                        MockMvcRequestBuilders.get("/shopping/carts/cart/{id}",4))
                .andExpect(status().isOk());
        assertEquals(cart.getCartId(),1);
        assertEquals(cart.getQuantity(),1);
    }

    @Test
    public void addToCart_returnSingleCart() throws Exception {
        Cart cart = getCartObject();
        String url = "/shopping/carts/add";
        String inputJson = mapRequestToJson(cart);
        when(cartService.addCart(any(CartModel.class))).thenReturn(cart);
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post(url)
                .accept(MediaType.APPLICATION_JSON).content(inputJson)
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        assertEquals(HttpStatus.CREATED.value(), response.getStatus());
        verify(cartService,timeout(1)).addCart(any(CartModel.class));
    }

    @Test
    public void updateCart_returnSingleCart() throws Exception {
        Cart cart = getCartObject();
        String url = "/shopping/carts/update/{id}";
        String inputJson = mapRequestToJson(cart);
        when(cartService.updateCartObject(anyInt(),any(CartModel.class))).thenReturn(cart);
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .put(url,3)
                .accept(MediaType.APPLICATION_JSON).content(inputJson)
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        verify(cartService,timeout(1)).updateCartObject(anyInt(),any(CartModel.class));

    }

    @Test
    public void deleteCart_shouldDeleteCart() throws Exception {
        mockMvc.perform(
                        MockMvcRequestBuilders.delete("/shopping/carts/delete/{id}",4))
                .andExpect(status().isOk());
        verify(cartService,times(1)).deleteCart(anyInt());
    }

    @Test
    public void deleteAllCarts_shouldDeleteCart() throws Exception {
        mockMvc.perform(
                        MockMvcRequestBuilders.delete("/shopping/carts/"))
                .andExpect(status().isOk());
        verify(cartService,times(1)).deleteAllCartObjects();
    }



    private Cart getCartObject(){
        Cart cart = new Cart();
        cart.setCartId(1);
        cart.setProduct(getProduct());
        cart.setCreatedDate(new Date());
        cart.setQuantity(1);
        return cart;
    }

    private CartItem getCartItems(){
        CartItem cartItem = new CartItem();
        cartItem.setTotalCost(100.00);
        cartItem.setCartItemSingleList(getListOfCartItems());
        return cartItem;
    }

    private List<CartItemSingle> getListOfCartItems(){
        List<CartItemSingle> cartItemSingleList = new ArrayList<>();
        cartItemSingleList.add(getSingleCartItem());
        cartItemSingleList.add(getSingleCartItem());
        return cartItemSingleList;
    }

    private CartItemSingle getSingleCartItem(){
        CartItemSingle cartItemSingle = new CartItemSingle();
        cartItemSingle.setProduct(getProduct());
        return cartItemSingle;
    }

    private Product getProduct(){
        Product product = new Product();
        product.setPrice(0.00);
        product.setCategory(getCategoryObject());
        product.setId(1);
        product.setImageURL("www.imageurl.com");
        product.setDescription("description");
        product.setProductName("product1");
        return product;
    }
    private Category getCategoryObject(){
        Category category = new Category();
        category.setCategoryName("category1");
        category.setCategoryId(1);
        category.setDescription("description1");
        category.setImageURL("www.imageurl.com");
        return category;
    }

    public String mapRequestToJson(Object object) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(object);
    }


}
