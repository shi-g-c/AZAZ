package com.azaz.controller;


import com.azaz.interact.dto.UserFollowDto;
import com.azaz.response.ResponseResult;
import com.azaz.service.UserFollowService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 关注
 * @author shigc
 */
@RestController
@RequestMapping("/azaz/interact/follow")
public class UserFollowController {
    /**
     * 关注服务
     */
    @Resource
    private UserFollowService userFollowService;

    /**
     * 关注或者取消关注
     * @param userFollowDto 关注信息
     * @return 关注结果
     */
    @PostMapping("/do")
    public ResponseResult doFollow(UserFollowDto userFollowDto) {
        return userFollowService.doFollow(userFollowDto);
    }
}
