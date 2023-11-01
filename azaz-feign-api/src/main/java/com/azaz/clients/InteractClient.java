package com.azaz.clients;

import com.azaz.response.ResponseResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 交互服务客户端接口
 * @author shigc
 */
@FeignClient("azaz-service-interact")
public interface InteractClient {

    /**
     * 获取关注总数
     * @param userId 用户id
     * @return 关注总数
     */
    @GetMapping("/azaz/interact/follow/num")
    ResponseResult<Integer> getFollowNum(@RequestParam("userId") Long userId);

    /**
     * 获取粉丝总数
     * @param userId 用户id
     * @return 粉丝总数
     */
    @GetMapping("/azaz/interact/fansNum")
    ResponseResult<Integer> getFansNum(@RequestParam("userId") Long userId);
}
