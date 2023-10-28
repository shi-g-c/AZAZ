package com.azaz.service.impl;

import com.azaz.exception.UserNotExitedException;
import com.azaz.exception.UserNotLoginException;
import com.azaz.mapper.UserMapper;
import com.azaz.response.ResponseResult;
import com.azaz.user.dto.UserPersonInfo;
import com.azaz.user.pojo.User;
import com.azaz.user.vo.UserPersonalInfoVo;
import com.azaz.utils.ThreadLocalUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 用户信息服务实现类
 * @author shigc
 */
@Service
@Log4j2
public class UserInfoServiceImpl implements com.azaz.service.UserInfoService{

    @Resource
    private UserMapper userMapper;

    /**
     * 获取用户个人信息
     * @param userId 用户id
     * @return ResponseResult
     */
    @Override
    public ResponseResult<UserPersonalInfoVo> getUserPersonalInfo(Long userId) {
        // 1. 获取到用户的id,如果为空,则说明是查自己，从ThreadLocal中获取
        if (userId == null){
            userId = ThreadLocalUtil.getUserId();
        }
        log.info("用户个人信息查询: {}", userId);
        // 1.1 校验用户id是否为空
        if (userId == null){
            throw new UserNotLoginException();
        }
        // 2. 根据用户id查询用户信息, 只需要 用户名、头像、签名
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("username", "image", "signature").eq("id", userId);
        User user = userMapper.selectOne(queryWrapper);
        // 2.1 校验用户信息是否为空
        if (user == null){
            throw new UserNotExitedException();
        }
        // 2.2 拷贝属性
        UserPersonalInfoVo userPersonalInfoVo = new UserPersonalInfoVo();
        BeanUtils.copyProperties(user, userPersonalInfoVo);
        // 3. 封装返回结果
        return ResponseResult.successResult(userPersonalInfoVo);
    }

    /**
     * 更新用户个人信息
     * @param userPersonInfo 用户个人信息
     * @return ResponseResult
     */
    @Override
    public ResponseResult updateUserPersonalInfo(UserPersonInfo userPersonInfo) {
        // 1. 获取到用户的id
        Long userId = ThreadLocalUtil.getUserId();
        log.info("用户个人信息更新: {}", userId);
        // 1.1 校验用户id是否为空
        if (userId == null){
            throw new UserNotLoginException();
        }
        // 2. 更新用户信息
        User user = new User();
        BeanUtils.copyProperties(userPersonInfo, user);
        user.setId(userId);
        userMapper.updateById(user);
        return ResponseResult.successResult();
    }
}
