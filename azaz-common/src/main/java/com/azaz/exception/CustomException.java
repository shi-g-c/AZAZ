package com.azaz.exception;


import com.azaz.enums.AppHttpCodeEnum;
//自定义异常类，手动抛出异常
public class CustomException extends RuntimeException {

    private AppHttpCodeEnum appHttpCodeEnum;

    public CustomException(AppHttpCodeEnum appHttpCodeEnum){
        this.appHttpCodeEnum = appHttpCodeEnum;
    }

    public AppHttpCodeEnum getAppHttpCodeEnum() {
        return appHttpCodeEnum;
    }
}
