package com.azaz.service;

import com.azaz.video.pojo.VideoLike;
import org.springframework.stereotype.Service;

/**
 * @author c'y'x
 * 数据库操作接口
 */
@Service
public interface DbOpsService {
    boolean addIntSafely(String key,int num);
    void insertIntoMongo(Long userId,Long videoId,int type,Object ops);
    Long getSumFromDb(Long videoId);
}
