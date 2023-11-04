package com.azaz.service;

import com.azaz.response.ResponseResult;
import com.azaz.video.pojo.Video;
import com.azaz.video.vo.VideoDetail;
import com.azaz.video.vo.VideoInfo;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author c'y'x
 */
@Service
public interface VideoDoLikeService {
    ResponseResult doLike(Long videoId,Long authorId,int type);
    ResponseResult doCollect(Long videoId,Long authorId,int type);
    ResponseResult doComment(Long videoId, Long parentId,String content);
    ResponseResult<Integer> getUserLikes(Long userId);
    ResponseResult<Integer> getUserWorks(Long userId);
    ResponseResult getPublishedVideos(Integer currentPage,Integer userId);
    ResponseResult showCollectsList(Integer currentPage);
    ResponseResult getCommentList(Long commentId,Long videoId);




}
