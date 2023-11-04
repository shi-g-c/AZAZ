package com.azaz.controller;

import com.azaz.response.ResponseResult;
import com.azaz.service.VideoDoLikeService;
import com.azaz.service.VideoUploadService;
import com.azaz.video.dto.VideoPublishDto;
import com.azaz.video.pojo.Video;
import com.azaz.video.vo.VideoDetail;
import com.azaz.video.vo.VideoInfo;
import org.springframework.web.bind.annotation.*;
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

    /**
     *评论视频
     */
    @PostMapping("/doComment")
    public ResponseResult doComment(Long videoId, Long parentId,String content){
        return videoDoLikeService.doComment(videoId,parentId,content);
    }
    //每次获取10个视频
    @GetMapping("/getVideos")
    public ResponseResult getVideo(Integer lastVideoId){return videoUploadService.getVideos(lastVideoId);}

    //得到用户获赞总数
    @GetMapping("/getUserLikes")
    public ResponseResult<Integer> getUserLikes(@RequestParam("userId")Long userId){
        return videoDoLikeService.getUserLikes(userId);
    }

    /**
     * 作品总数
     * @param userId
     * @return
     */
    @GetMapping("/getUserWorks")
    public ResponseResult<Integer> getUserWorks(@RequestParam("userId")Long userId){
        return videoDoLikeService.getUserWorks(userId);
    }


    /**
     * 得到用户发布过的视频列表
     * @return
     */
    @GetMapping("/getPublishedVideos")
    public ResponseResult<List<Video>> getAllVideos(Integer currentPage,Integer userId){
        return videoDoLikeService.getPublishedVideos(currentPage,userId);
    }


    //当前用户收藏过的视频列表
    /**
     * 应该是分页
     * @return
     */
    @GetMapping("/showCollectList")
    public ResponseResult collects(Integer currentPage,Integer userId){
        return videoDoLikeService.showCollectsList(currentPage);
    }

    /**
     * 得到当前评论的子评论
     * @param commentId 当前评论的id
     */
    @GetMapping ("/getCommentList")
    public ResponseResult doComment(Long commentId,Long videoId)
    {
        return videoDoLikeService.getCommentList(commentId,videoId);
    }


    // TODO
    /**
     * 得到视频信息
     * @param videoId
     * @return
     */
    @GetMapping("/info")
    public ResponseResult<VideoInfo> getVideoInfo(@RequestParam("videoId") Long videoId){
        return videoUploadService.getVideoInfo(videoId);
    }

    // TODO
    /**
     * 得到视频信息
     * @param videoId
     * @return
     */
    @GetMapping("/detailInfo")
    public ResponseResult<VideoDetail> getVideoDetailInfo(@RequestParam("videoId")Long videoId){
        return videoUploadService.getVideoDetailInfo(videoId);
    }
}
