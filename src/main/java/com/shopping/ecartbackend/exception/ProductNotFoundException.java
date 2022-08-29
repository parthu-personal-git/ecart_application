package com.shopping.ecartbackend.exception;

public class ProductNotFoundException extends RuntimeException{
    public ProductNotFoundException(int id){

        super("Couldn't find Product with id : " + id);
    }
}
