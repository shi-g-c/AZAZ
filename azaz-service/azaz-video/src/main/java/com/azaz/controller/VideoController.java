package com.azaz.controller;

import com.azaz.response.ResponseResult;
import com.azaz.service.VideoDoLikeService;
import com.azaz.service.VideoUploadService;
import com.azaz.video.dto.VideoPublishDto;
import com.azaz.video.pojo.Video;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;

/**
 * 视频相关功能
 * @author c'y'x
 */
@RestController
@RequestMapping("/azaz/video")
public class VideoController {
    @Resource
    private VideoUploadService videoUploadService;
    @Resource
    VideoDoLikeService videoDoLikeService;

    //发布视频
    @PostMapping("/publish")
    public ResponseResult publish(VideoPublishDto videoPublishDto){
        return videoUploadService.publish(videoPublishDto);
    }
    //上传文件
    @PostMapping("/upload")
    public ResponseResult upload(MultipartFile file){
        return videoUploadService.upload(file);
    }
    //type为1点赞，为0取消点赞
    @PostMapping("/doLike")
    public ResponseResult doLike(Long videoId,Long authorId,int type){
        return videoDoLikeService.doLike(videoId,authorId,type);
    }

    //type为1收藏，为0取消收藏
    @PostMapping("/doCollect")
    public ResponseResult doCollect(Long videoId,Long authorId,int type){
        return videoDoLikeService.doCollect(videoId,authorId,type);
    }
    //每次获取10个视频
    @GetMapping("/getVideos")
    public ResponseResult getVideo(Integer lastVideoId){return videoUploadService.getVideos(lastVideoId);}

    @GetMapping("/isLike")
    public ResponseResult isLike(Long videoId){
        return videoDoLikeService.isLike(videoId);
    }

    //得到用户被赞数
    @GetMapping("/getUserLikes")
    public ResponseResult<Integer> getUserLikes(Long userId){
        return videoDoLikeService.getUserLikes(userId);
    }

    /**
     * 这个收藏总数不需要，需要一个返回作品总数的接口，见feign客户端
     * @param userId
     * @return
     */
    //得到用户被收藏数
    @GetMapping("/getUserWorks")
    public ResponseResult<Integer> getUserCollects(Long userId){
        return videoDoLikeService.getUserCollects(userId);
    }

    //得到用户发布过的所有视频

    /**
     * 应该是分页
     * @param userId
     * @return
     */
    @GetMapping("/getAllVideos")
    public ResponseResult<List<Video>> getAllVideos(Long userId){
        return videoDoLikeService.getAllVideos(userId);
    }

    //得到用户点赞过的所有视频
    /**
     * 应该是分页
     * @return
     */
    @GetMapping("/likes")
    public ResponseResult likes(){
        return videoDoLikeService.showLikesList();
    }

    //得到用户收藏过的所有视频
    /**
     * 应该是分页
     * @return
     */
    @GetMapping("/collects")
    public ResponseResult collects(){
        return videoDoLikeService.showCollectsList();
    }
}
