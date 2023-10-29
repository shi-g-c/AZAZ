package com.azaz.service;

import com.azaz.response.ResponseResult;
import com.azaz.video.dto.VideoPublishDto;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author c'y'x
 */
@Service
public interface VideoUploadService {
      ResponseResult publish(VideoPublishDto videoPublishDto);

      ResponseResult upload(MultipartFile file);
}
