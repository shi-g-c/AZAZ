package com.azaz.video.pojo;

import lombok.Data;
import org.apache.ibatis.annotations.Select;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * @author c'y'x
 */
@Data
@Document("video_like")
public class VideoLike implements Serializable {
    //主键
    public String id;
    //用户id
    public Long userId;
    //视频id
    public Long videoId;
    //是否点赞(1是2否)
    public Integer isLike;
    //是否收藏(1是2否)
    public Integer isCollect;
    //评论
    public ArrayList<String> commentList;
}
