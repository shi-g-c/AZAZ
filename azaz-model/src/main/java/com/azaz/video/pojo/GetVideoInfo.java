package com.azaz.video.pojo;

import lombok.Data;

import java.util.List;

/**
 * @author c'y'x
 * 上传视频流需要的类
 */
@Data
public class GetVideoInfo {
    List<VideoDetailInfo>videoList;
    Integer total;
    Integer lastVideoId;

}
