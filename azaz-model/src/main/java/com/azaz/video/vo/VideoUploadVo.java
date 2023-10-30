package com.azaz.video.vo;

import lombok.Data;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;

/**
 * @author c'y'x
 */
@Data
public class VideoUploadVo {
    private String videoUrl;
    private String coverUrl;
}
