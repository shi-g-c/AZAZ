package com.azaz.service;

import com.azaz.response.ResponseResult;
import org.springframework.stereotype.Service;

/**
 * @author c'y'x
 */
@Service
public interface VideoDoLikeService {
    ResponseResult doLike(Long videoId,Long authorId,int type);
    ResponseResult doCollect(Long videoId,Long authorId,int type);
    ResponseResult isLike(Long videoId);
    Integer getUserLikes();
}
