package com.azaz.controller;

import com.azaz.response.ResponseResult;
import com.azaz.service.UserInfoService;
import com.azaz.user.dto.UserPersonInfo;
import com.azaz.user.vo.UserPersonalInfoVo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 用户信息控制器
 * @author shigc
 */
@RestController
@RequestMapping("/azaz/user")
public class UserInfoController {

    @Resource
    private UserInfoService userInfoService;

    /**
     * 获取用户个人信息
     * @param userId 用户id
     * @return ResponseResult<UserPersonalInfoVo> 用户个人信息
     */
    @GetMapping("/personal")
    public ResponseResult<UserPersonalInfoVo> getUserPersonalInfo(Long userId){
        return userInfoService.getUserPersonalInfo(userId);
    }


    /**
     * 更新用户个人信息
     * @param userPersonInfo 用户个人信息
     * @return ResponseResult 更新结果
     */
    @PutMapping("/personal")
    public ResponseResult updateUserPersonalInfo(UserPersonInfo userPersonInfo){
        return userInfoService.updateUserPersonalInfo(userPersonInfo);
    }
}
