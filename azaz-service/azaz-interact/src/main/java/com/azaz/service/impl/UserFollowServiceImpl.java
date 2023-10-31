package com.azaz.service.impl;

import com.azaz.constant.InteractConstant;
import com.azaz.exception.ErrorOperationException;
import com.azaz.exception.ErrorParamException;
import com.azaz.exception.NullParamException;
import com.azaz.interact.dto.UserFollowDto;
import com.azaz.response.ResponseResult;
import com.azaz.utils.ThreadLocalUtil;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 关注服务接口实现类
 * @author shigc
 */
@Service
public class UserFollowServiceImpl implements com.azaz.service.UserFollowService{

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Resource
    private RedissonClient redissonClient;

    /**
     * 关注或者取消关注
     * @param userFollowDto 关注信息
     * @return 关注结果
     */
    @Override
    public ResponseResult doFollow(UserFollowDto userFollowDto) {
        // 1.校验参数
        if (userFollowDto == null || userFollowDto.getUserId() == null || userFollowDto.getType() == null) {
            throw new NullParamException();
        }
        if (!userFollowDto.getType().equals(InteractConstant.FOLLOW_CODE)
                && !userFollowDto.getType().equals(InteractConstant.UNFOLLOW_CODE)) {
            throw new ErrorParamException("关注类型错误!");
        }
        // 2.关注或者取消关注
        Long myId = ThreadLocalUtil.getUserId();
        Long otherId = userFollowDto.getUserId();
        // 2.1.不能对自己进行此操作
        if (myId.equals(otherId)) {
            throw new ErrorParamException("不能对自己进行此操作!");
        }
        // 创建锁对象
        RLock redisLock = redissonClient.getLock(InteractConstant.REDIS_LOCK_FOLLOW_KEY + myId);
        // 尝试获取锁
        boolean isLock = redisLock.tryLock();
        if(!isLock){
            throw new ErrorOperationException("请勿频繁操作!");
        }
        try {
            if (userFollowDto.getType().equals(InteractConstant.FOLLOW_CODE)) {
                // 3.关注
                // 3.2 将关注信息存入redis
                Long result = stringRedisTemplate.opsForSet().add(InteractConstant.REDIS_FOLLOW_KEY + myId, otherId.toString());
                if (result == null || result == 0) {
                    throw new ErrorOperationException("已关注，请勿重复操作!");
                }
                // 3.1 将redis中的关注总数+1
                stringRedisTemplate.opsForValue().increment(InteractConstant.REDIS_FOLLOW_NUM_KEY + myId);
                // 3.3 查询是否是互关好友，是的话加入互关列表
                Boolean isFriend = stringRedisTemplate.opsForSet().isMember(InteractConstant.REDIS_FOLLOW_KEY + otherId, myId.toString());
                if (Boolean.TRUE.equals(isFriend)){
                    stringRedisTemplate.opsForSet().add(InteractConstant.REDIS_FRIEND_KEY + myId, otherId.toString());
                    stringRedisTemplate.opsForSet().add(InteractConstant.REDIS_FRIEND_KEY + otherId, myId.toString());
                }
                return ResponseResult.successResult();
            }
            // 4.取消关注，查看是否确实关注了
            Boolean isFollow = stringRedisTemplate.opsForSet().isMember(InteractConstant.REDIS_FOLLOW_KEY + myId, otherId.toString());
            if (!Boolean.TRUE.equals(isFollow)) {
                throw new ErrorOperationException("请先关注!");
            }
            // 4.1 将redis中的关注总数-1
            stringRedisTemplate.opsForValue().decrement(InteractConstant.REDIS_FOLLOW_NUM_KEY + myId);
            // 4.2 查询是否是互关好友，是的话从互关列表中删除
            Boolean isFriend = stringRedisTemplate.opsForSet().isMember(InteractConstant.REDIS_FOLLOW_KEY + otherId, myId.toString());
            if (Boolean.TRUE.equals(isFriend)){
                stringRedisTemplate.opsForSet().remove(InteractConstant.REDIS_FRIEND_KEY + myId, otherId.toString());
                stringRedisTemplate.opsForSet().remove(InteractConstant.REDIS_FRIEND_KEY + otherId, myId.toString());
            }
            // 4.3 将关注信息从redis中删除
            stringRedisTemplate.opsForSet().remove(InteractConstant.REDIS_FOLLOW_KEY + myId, otherId.toString());
            return ResponseResult.successResult();
        } finally {
            // 释放锁
            redisLock.unlock();
        }
    }
}
