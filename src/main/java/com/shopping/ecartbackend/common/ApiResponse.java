package com.shopping.ecartbackend.common;

import java.time.LocalDateTime;

public class ApiResponse {
    private boolean isSucces;
    private String message;

    public ApiResponse(){};

    public ApiResponse(boolean isSucces, String message){
        this.isSucces = isSucces;
        this.message = message;
    }

    public boolean isSucces() {
        return isSucces;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message){
        this.message = message;
    }

    public String getLocalTime(){
        return LocalDateTime.now().toString();
    }

}
