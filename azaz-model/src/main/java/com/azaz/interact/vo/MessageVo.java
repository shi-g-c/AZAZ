package com.azaz.interact.vo;

import com.azaz.user.dto.UserPersonInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 私信vo
 * @author shigc
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MessageVo {

    /**
    * 发送者
    */
    private UserPersonInfo sender;
    /**
     * 发送者
     */
    private UserPersonInfo receiver;

    /**
     * 私信id
     */
    private Long messageId;

    /**
    * 私信内容
    */
    private String messageContent;

    /**
    * 发送时间
    */
    private LocalDateTime createdTime;
}
