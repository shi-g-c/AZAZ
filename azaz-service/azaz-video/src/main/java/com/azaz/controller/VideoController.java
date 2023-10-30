package com.azaz.controller;

import com.azaz.response.ResponseResult;
import com.azaz.service.VideoDoLikeService;
import com.azaz.service.VideoUploadService;
import com.azaz.service.impl.VideoDoLikeServiceImpl;
import com.azaz.video.dto.VideoPublishDto;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

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
    public ResponseResult doLike(Long videoId,int type){
        return videoDoLikeService.doLike(videoId,type);
    }
    //type为1收藏，为0取消收藏
    @PostMapping("/collect")
    public ResponseResult doCollect(Long videoId,int type){
        return videoDoLikeService.doLike(videoId,type);
    }

}
