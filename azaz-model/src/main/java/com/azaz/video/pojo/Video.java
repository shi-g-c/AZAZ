package com.azaz.video.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author c'y'x
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("tb_video")
public class Video {
    @TableId(type = IdType.AUTO)
    private Long id;

    @TableField("author_id")
    private Long authorId;


    @TableField("title")
    private String title;

    /**
     * 分区，0为热门，其他待定
     */
    @TableField("section")
    private Integer section;


    @TableField("cover_url")
    private String coverUrl;

    @TableField("video_url")
    private String videoUrl;

    /**
     * 视频状态。0正常    1删除
     */
    @TableField("status")
    private Integer status;

    @TableField("create_time")
    private LocalDateTime createTime;

    @TableField("update_time")
    private LocalDateTime updateTime;

    @TableField("likes")
    private Long likes;

    @TableField("collects")
    private Long collects;

    @TableField("comments")
    private Long comments;



}
