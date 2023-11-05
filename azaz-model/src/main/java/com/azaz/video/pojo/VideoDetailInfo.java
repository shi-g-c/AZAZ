package com.azaz.video.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author c'y'x
 */
@Data
public class VideoDetailInfo {
    private Long id;
    private Long authorId;
    private String title;
    private Integer section;
    private String coverUrl;
    private String videoUrl;
    private Integer status;
    private Long likes;
    private Long collects;
    private Long comments;
    private boolean isLiked;
    private boolean isCollected;
    private String userName;
    private String image;
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    private String createTime;


}
