package com.azaz.service;

import com.azaz.response.ResponseResult;
import com.azaz.user.dto.UserPersonInfo;
import com.azaz.user.vo.UserPersonalInfoVo;
import org.springframework.stereotype.Service;

/**
 * 用户信息服务接口
 * @author shigc
 */
@Service
public interface UserInfoService {
    /**
     * 获取用户个人信息
     * @param userId 用户id
     * @return ResponseResult<UserPersonalInfoVo> 用户个人信息
     */
    ResponseResult<UserPersonalInfoVo> getUserPersonalInfo(Long userId);

    /**
     * 更新用户个人信息
     * @param userPersonInfo 用户个人信息
     * @return ResponseResult 更新结果
     */
    ResponseResult updateUserPersonalInfo(UserPersonInfo userPersonInfo);
}
