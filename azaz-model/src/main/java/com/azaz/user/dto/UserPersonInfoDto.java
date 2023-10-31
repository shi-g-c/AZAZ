package com.azaz.user.dto;

import lombok.Builder;
import lombok.Data;

/**
 * @author shigc
 */
@Data
@Builder
public class UserPersonInfoDto {

    /**
     * 用户id
     */
    private Long id;
    /**
     * 用户个人信息
     */
    private String username;
    /**
     * 用户头像
     */
    private String image;
    /**
     * 用户签名
     */
    private String signature;
}
