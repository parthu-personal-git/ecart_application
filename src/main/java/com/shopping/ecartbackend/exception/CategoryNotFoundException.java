package com.shopping.ecartbackend.exception;

public class CategoryNotFoundException extends RuntimeException{

    public CategoryNotFoundException(int id){
        super("Couldn't find Category with id : " + id);
    }
}
