package com.azaz.clients;


import com.azaz.response.ResponseResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 视频客户端
 * @author shigc
 */
@FeignClient("azaz-service-video")
public interface VideoClient {

    /**
     * 得到用户被赞数
     * @param userId 用户id
     * @return 被赞数
     */
    @GetMapping("/azaz/video/getUserLikes")
    ResponseResult<Integer> getUserLikes(@RequestParam("userId") Long userId);

    /**
     * 得到用户作品总数
     * @param userId 用户id
     * @return 作品总数
     */
    @GetMapping("/azaz/video/getUserWorks")
    ResponseResult<Integer> getUserWorks(@RequestParam("userId")Long userId);

}
