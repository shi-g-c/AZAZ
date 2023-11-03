package com.azaz.video.pojo;

import lombok.Data;

import java.util.List;

/**
 * 查询收藏列表和发布列表需要的类
 * @author c'y'x
 */
@Data
public class VideoList {
    public List<Video>videoList;
    public Integer total;

}
