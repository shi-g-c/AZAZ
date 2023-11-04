package com.azaz.service;

import com.azaz.response.ResponseResult;
import com.azaz.video.dto.VideoPublishDto;
import com.azaz.video.pojo.Video;
import com.azaz.video.vo.VideoDetail;
import com.azaz.video.vo.VideoInfo;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author c'y'x
 */
@Service
public interface VideoUploadService {
      /**
       * 发布视频
       * @param videoPublishDto 视频信息
       * @return 是否成功
       */
      ResponseResult publish(VideoPublishDto videoPublishDto);

      /**
       * 上传视频
       * @param file 视频文件
       * @return 是否成功
       */
      ResponseResult upload(MultipartFile file);

      /**
       * 获取视频列表
       * @param lastVideoId 最后一个视频的id
       * @return 视频列表
       */
      ResponseResult getVideos(Integer lastVideoId);

      /**
       * 获取视频信息
       * @param videoId 视频id
       * @return 视频信息
       */
      Video getVideoById(Integer videoId);

      /**
       * 获取视频信息
       * @param videoId 视频id
       * @return 视频信息
       */
      ResponseResult<VideoInfo> getVideoInfo(Long videoId);

      /**
       * 获取视频详细信息
       * @param videoId 视频id
       * @return 视频详细信息
       */
      ResponseResult<VideoDetail> getVideoDetailInfo(Long videoId);

}
