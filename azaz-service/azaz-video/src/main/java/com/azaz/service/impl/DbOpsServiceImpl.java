package com.azaz.service.impl;

import com.azaz.exception.RedissonLockException;
import com.azaz.service.DbOpsService;
import com.azaz.video.pojo.VideoLike;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
/**
 * @author c'y'x
 * 此类用作数据库操作，包括redis和mongodb
 */
@Slf4j
@Service
public class DbOpsServiceImpl implements DbOpsService {

    @Resource
    RedisTemplate redisTemplate;
    @Resource
    RedissonClient redissonClient;
    @Resource
    MongoTemplate mongoTemplate;


    @Override
    /**
     * 向redis中的set中添加值(加了分布式锁)
     * @param userId 当前用户id
     * @param key set的key
     * @return
     */
    public boolean addIntSafely(String key,int num) {
        //获取分布式锁
        RLock lock = redissonClient.getLock(key);
        try {
            //尝试上锁，5s没加上则抛异常
            if(lock.tryLock(5, TimeUnit.SECONDS)){
                //取出之前的数字
                Integer exNum = (Integer)this.redisTemplate.opsForValue().get(key);
                //加上数字存入
                this.redisTemplate.opsForValue().set(key,exNum+num);
                return true;
            }
            else {
                return false;
            }
        }
        catch (Exception e){
            log.error("redisson加锁失败{}",e);
            throw new RedissonLockException();
        }
        finally {
            lock.unlock();
        }
    }

    /**
     * 将数据插入mongoDB中
     * @param userId 用户id
     * @param videoId 视频id
     * @param type 操作类型(1点赞 2收藏 3评论)
     * @param ops 添加到对应字段的参数
     */
    @Override
    @Async
    public void insertIntoMongo(Long userId,Long videoId,int type,Object ops) {
        //查询当用户id和视频id所在的字段
        Query query=Query.query
                (Criteria.where("userId").is(userId).
                        and("videoId").is(videoId));
        VideoLike videoLike = mongoTemplate.findOne(query, VideoLike.class);
        //如果不存在，则添加userId和videoId
        if(videoLike==null){
            videoLike.setVideoId(videoId);
            videoLike.setUserId(userId);
        }
        //根据类型对相应字段进行更新操作
        switch (type){
            //点赞
            case 1:
                videoLike.setIsLike((Integer) ops);
                break;
            //收藏
            case 2:
                videoLike.setIsCollect((Integer) ops);
                break;
            //评论
            case 3:
                videoLike.setCommentList((ArrayList<String>) ops);
        }
        mongoTemplate.save(videoLike);
    }




}
