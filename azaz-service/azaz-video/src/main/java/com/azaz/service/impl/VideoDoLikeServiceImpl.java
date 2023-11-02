package com.azaz.service.impl;

import com.alibaba.nacos.shaded.io.opencensus.internal.DefaultVisibilityForTesting;
import com.azaz.constant.VideoConstant;
import com.azaz.response.ResponseResult;
import com.azaz.service.DbOpsService;
import com.azaz.service.VideoDoLikeService;
import com.azaz.service.VideoUploadService;
import com.azaz.user.pojo.User;
import com.azaz.utils.ThreadLocalUtil;
import com.azaz.video.pojo.Video;
import com.azaz.video.pojo.VideoLike;
import lombok.extern.slf4j.Slf4j;
import org.redisson.client.protocol.convertor.VoidReplayConvertor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author c'y'x
 */
@Service
@Slf4j
public class VideoDoLikeServiceImpl implements VideoDoLikeService {
    @Resource
    DbOpsService dbOpsService;
    @Resource
    StringRedisTemplate stringRedisTemplate;
    @Resource
    MongoTemplate mongoTemplate;
    @Resource
    VideoUploadService videoUploadService;
    /**
     * 点赞操作
     * @param videoId 视频id
     * @param type type为1点赞，0取消点赞
     * @return
     */
    @Override
    public ResponseResult doLike(Long videoId,Long authorId,int type){
        //set集合的key
        String setKey= VideoConstant.SET_LIKE_KEY+videoId;
        //kv类型的key(key为videoId,value为点赞总数)
        String strKey=VideoConstant.STRING_LIKE_KEY+videoId;
        //视频作者的点赞总数的key
        String userKey=VideoConstant.USER_LIKES_SUM+authorId;
        Long userId = ThreadLocalUtil.getUserId();
        //当前用户点赞的视频集合key
        String nowUserKey=VideoConstant.USER_SET_LIKE_KEY+userId.toString();
        //点赞
        if(type==1){
            //添加到redis，以set方式存储，key为videoId，value为userId
            if(!this.stringRedisTemplate.opsForSet().isMember(setKey, String.valueOf(userId))){
                //添加到redis
                this.stringRedisTemplate.opsForSet().add(setKey, String.valueOf(userId));
                this.stringRedisTemplate.opsForSet().add(nowUserKey,String.valueOf(videoId));
                //异步添加到mongodb
                dbOpsService.insertIntoMongo(userId,videoId, VideoConstant.LIKE_TYPE,1);
                //redis数据加一
                dbOpsService.addIntSafely(strKey,1);
                dbOpsService.addIntSafely(userKey,1);
                return ResponseResult.successResult("点赞成功");
            }
            else {
                return ResponseResult.errorResult("重复点赞");
            }
        }
        //取消点赞
        else {
            //判断是否点过赞
            if(this.stringRedisTemplate.opsForSet().isMember(setKey, String.valueOf(userId))){
                //取消点赞
                this.stringRedisTemplate.opsForSet().remove(setKey, userId.toString());
                this.stringRedisTemplate.opsForSet().remove(nowUserKey,String.valueOf(videoId));
                //异步更新到mongodb
                dbOpsService.insertIntoMongo(userId,videoId,VideoConstant.LIKE_TYPE,0);
                //redis数据减一
                dbOpsService.addIntSafely(strKey,-1);
                dbOpsService.addIntSafely(userKey,-1);
                return ResponseResult.successResult();
             }
            else {
                return ResponseResult.errorResult("重复取消");
            }
        }
    }

    @Override
    public ResponseResult doCollect(Long videoId,Long authorId, int type) {
        //set集合的key
        String setKey= VideoConstant.SET_COLLECT_KEY+videoId;
        //kv类型的key(key为videoId,value为点赞总数)
        String strKey=VideoConstant.STRING_COLLECT_KEY+videoId;
        //视频作者的点赞总数的key
        String userKey=VideoConstant.USER_COLLECT_SUM+authorId;
        //获取userId
        Long userId = ThreadLocalUtil.getUserId();
        //当前用户点赞的视频集合key
        String nowUserKey=VideoConstant.USER_SSET_COLLECT_KEY+userId.toString();
        //收藏操作
        if(type==1){
            //添加到redis，以set方式存储，key为videoId，value为userId
            if(!this.stringRedisTemplate.opsForSet().isMember(setKey, String.valueOf(userId))){
                //添加到redis
                this.stringRedisTemplate.opsForSet().add(setKey, String.valueOf(userId));
                this.stringRedisTemplate.opsForSet().add(nowUserKey,String.valueOf(videoId));
                //异步添加到mongodb
                dbOpsService.insertIntoMongo(userId,videoId, VideoConstant.COLLECT_TYPE,1);
                //redis数据加一
                dbOpsService.addIntSafely(strKey,1);
                dbOpsService.addIntSafely(userKey,1);
                return ResponseResult.successResult();
            }
            else {
                return ResponseResult.errorResult("收藏失败");
            }
        }
        //取消收藏
        else {
            //判断是否收藏
            if(this.stringRedisTemplate.opsForSet().isMember(setKey, String.valueOf(userId))){
                //取消收藏
                this.stringRedisTemplate.opsForSet().remove(setKey, userId.toString());
                this.stringRedisTemplate.opsForSet().remove(nowUserKey,String.valueOf(videoId));
                //异步更新到mongodb
                dbOpsService.insertIntoMongo(userId,videoId,VideoConstant.COLLECT_TYPE,0);
                //redis数据减一
                dbOpsService.addIntSafely(strKey,-1);
                dbOpsService.addIntSafely(userKey,-1);
                return ResponseResult.successResult();
            }
            else {
                return ResponseResult.errorResult("取消收藏失败");
            }
        }
    }

    /**
     * 判断当前用户是否对当前视频进行点赞
     * @param videoId
     * @return
     */
    @Override
    public ResponseResult isLike(Long videoId){
        Long userId = ThreadLocalUtil.getUserId();
        //获取key
        String setLikeKey = VideoConstant.SET_LIKE_KEY+videoId.toString();
        //判断key是否存在
        if(stringRedisTemplate.hasKey(setLikeKey)){
            //如果userId在set里，说明已经点赞
            if(stringRedisTemplate.opsForSet().isMember(setLikeKey,userId.toString())){
                return ResponseResult.successResult((Integer)1);
            }
            else {
                return ResponseResult.successResult((Integer)0);
            }
        }
        //key不存在就在mongo里面找
        else{
            //查询当用户id和视频id所在的字段
            Query query=Query.query
                    (Criteria.where("userId").is(userId.toString()).
                            and("videoId").is(videoId.toString()));
            VideoLike videoLike = mongoTemplate.findOne(query, VideoLike.class);
            //如果此字段不存在，直接返回0
            if(videoLike==null||videoLike.getIsLike()==0){
                return ResponseResult.successResult((Integer)0);
            }
            else {
                return ResponseResult.successResult((Integer)1);
            }

        }
    }

    /**
     * 展示当前用户点赞过的视频
     * @return
     */
    @Override
    public ResponseResult showLikesList(){
        Long userId = ThreadLocalUtil.getUserId();
        Set<String> videoIds = stringRedisTemplate.opsForSet().members(VideoConstant.USER_SET_LIKE_KEY + userId.toString());
        List<Video>list=new ArrayList<>();
        for (String videoId : videoIds) {
            Video video = videoUploadService.getVideoById(Integer.parseInt(videoId));
            list.add(video);
        }
        return ResponseResult.successResult(list);
    }
    /**
     * 展示当前用户收藏过的视频
     * @return
     */
    @Override
    public ResponseResult showCollectsList(){
        Long userId = ThreadLocalUtil.getUserId();
        Set<String> videoIds = stringRedisTemplate.opsForSet().members(VideoConstant.USER_SSET_COLLECT_KEY + userId.toString());
        List<Video>list=new ArrayList<>();
        for (String videoId : videoIds) {
            Video video = videoUploadService.getVideoById(Integer.parseInt(videoId));
            list.add(video);
        }
        return ResponseResult.successResult(list);
    }




    /**
     * 得到当前用户的点赞量
     * @return
     */
    @Override
    public ResponseResult<Integer> getUserLikes(Long userId1){
        String userId = userId1.toString();
        String userKey=VideoConstant.USER_LIKES_SUM+userId;
        String s = stringRedisTemplate.opsForValue().get(userKey);
        if(s==null){
            return ResponseResult.successResult(0);
        }else{
            return ResponseResult.successResult(Integer.parseInt(s));
        }
    }
    /**
     * 得到当前用户的收藏量
     * @return
     */
    @Override
    public ResponseResult<Integer> getUserCollects(Long userId1){
        String userId = userId1.toString();
        String userKey=VideoConstant.USER_COLLECT_SUM+userId;
        String s = stringRedisTemplate.opsForValue().get(userKey);
        if(s==null){
            return ResponseResult.successResult(0);
        }else{
            return ResponseResult.successResult(Integer.parseInt(s));
        }
    }

    /**
     * 返回当前用户发布的所有视频
     * @return
     */
    @Override
    public ResponseResult<List<Video>> getAllVideos(Long userId1){
        //得到当前用户id
        String userId=userId1.toString();
        //得到对应key
        String key=VideoConstant.USER_VIDEO_SET+userId;
        //得到当前用户发布的所有video
        Set<String> videoIds = stringRedisTemplate.opsForSet().members(key);
        List<Video>videos=new ArrayList<>();
        for (String videoId : videoIds) {
            Video video = videoUploadService.getVideoById(Integer.parseInt(videoId));
            if(video!=null) {
                videos.add(video);
            }
        }
        return ResponseResult.successResult(videos);
    }





}
