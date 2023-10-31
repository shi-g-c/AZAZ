package com.azaz.video.dto;

import lombok.Data;

/**
 * @author c'y'x
 */
@Data
public class VideoPublishDto {
    private String videoUrl;
    private String coverUrl;
    private String title;
    private Integer section;
}
