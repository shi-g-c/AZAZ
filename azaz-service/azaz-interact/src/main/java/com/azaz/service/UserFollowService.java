package com.azaz.service;

import com.azaz.interact.dto.UserFollowDto;
import com.azaz.response.ResponseResult;
import org.springframework.stereotype.Service;

/**
 * 关注服务接口
 * @author shigc
 */
@Service
public interface UserFollowService {
    /**
     * 关注或者取消关注
     * @param userFollowDto 关注信息
     * @return  关注结果
     */
    ResponseResult doFollow(UserFollowDto userFollowDto);
}
