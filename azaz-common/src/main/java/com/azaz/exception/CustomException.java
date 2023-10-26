package com.azaz.exception;


import com.azaz.enums.AppHttpCodeEnum;
import lombok.Getter;

/**
 * 自定义异常
 * @author cyx
 */
@Getter
public class CustomException extends RuntimeException {

    private final AppHttpCodeEnum appHttpCodeEnum;

    public CustomException(AppHttpCodeEnum appHttpCodeEnum){
        this.appHttpCodeEnum = appHttpCodeEnum;
    }

}
