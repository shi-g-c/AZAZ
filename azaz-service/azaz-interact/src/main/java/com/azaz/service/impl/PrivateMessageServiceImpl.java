package com.azaz.service.impl;

import com.alibaba.fastjson2.JSON;
import com.azaz.constant.InteractConstant;
import com.azaz.exception.ErrorParamException;
import com.azaz.exception.NullParamException;
import com.azaz.interact.dto.MessageListDto;
import com.azaz.interact.dto.MessageSendDto;
import com.azaz.interact.pojo.PrivateMessage;
import com.azaz.interact.vo.MessageListVo;
import com.azaz.interact.vo.MessageVo;
import com.azaz.mapper.PrivateMessageMapper;
import com.azaz.response.ResponseResult;
import com.azaz.service.PrivateMessageService;
import com.azaz.utils.ThreadLocalUtil;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 私信service
 * @author shigc
 */
@Service
public class PrivateMessageServiceImpl implements PrivateMessageService {

    @Resource
    private PrivateMessageMapper privateMessageMapper;

    @Resource
    private StringRedisTemplate stringRedisTemplate;


    /**
     * 发送私信
     * @param messageSendDto 私信发送dto
     * @return ResponseResult
     */
    @Override
    public ResponseResult sendPrivateMessage(MessageSendDto messageSendDto) {
        //1.1 校验参数
        if (messageSendDto == null || messageSendDto.getReceiverId() == null
                || messageSendDto.getContent() == null || messageSendDto.getStatus() == null) {
            throw new NullParamException();
        }
        //1.2 校验参数
        if (messageSendDto.getContent().length() > InteractConstant.MESSAGE_MAX_LENGTH) {
            throw new ErrorParamException("私信内容不能超过" + InteractConstant.MESSAGE_MAX_LENGTH + "个字符！");
        }
        //1.3 校验参数
        if (messageSendDto.getStatus() != 0 && messageSendDto.getStatus() != 1) {
            throw new ErrorParamException("私信类型错误！");
        }
        //2.检查是否互关
        Long userId = ThreadLocalUtil.getUserId();
        // TODO 检查是否互关
        boolean ifFollowEachOther = true;
        if (!ifFollowEachOther && messageSendDto.getStatus() == 1) {
            //2.1 未互关，不能分享视频
            throw new ErrorParamException("未互关不能分享视频！");
        }
        if (!ifFollowEachOther && messageSendDto.getStatus() == 0) {
            //2.3 未互关，检查是否已发送三条私信
            Integer count = privateMessageMapper.countBySenderId(userId);
            if (count >= InteractConstant.MESSAGE_MAX_COUNT) {
                throw new ErrorParamException("最多向未互关朋友发送三条私信！");
            }
        }
        //3. 发送私信
        PrivateMessage privateMessage = PrivateMessage.builder()
                .messageContent(messageSendDto.getContent())
                .receiverId(messageSendDto.getReceiverId())
                .senderId(ThreadLocalUtil.getUserId())
                .createTime(LocalDateTime.now())
                .status(messageSendDto.getStatus())
                .build();
        //3.1 向数据库中添加私信，并且填充id
        privateMessageMapper.insert(privateMessage);
        //3.2 将私信转换为vo
        // 5.3 封装私信vo
        MessageVo messageVo = MessageVo.builder()
                .senderId(userId.toString())
                .messageId(privateMessage.getId().toString())
                .messageContent(privateMessage.getMessageContent())
                .status(privateMessage.getStatus())
                .build();
        //3.2 向redis中添加私信,key为小id-大id
        String messageKey = InteractConstant.REDIS_PRIVATE_MESSAGE_KEY + Math.min(userId, messageSendDto.getReceiverId())
                + "-" + Math.max(userId, messageSendDto.getReceiverId()) + ":";
        //3.3 设置私信过期时间为一周
        stringRedisTemplate.opsForList().leftPush(messageKey, JSON.toJSONString(messageVo));
        stringRedisTemplate.expire(messageKey, 7, java.util.concurrent.TimeUnit.DAYS);
        //3.4 检查私信数量是否超过30条，超过则删除最早的一条
        Long size = stringRedisTemplate.opsForList().size(messageKey);
        if (size == null || size > InteractConstant.REDIS_PRIVATE_MESSAGE_MAX_COUNT) {
            stringRedisTemplate.opsForList().rightPop(messageKey);
        }
        return ResponseResult.successResult();
    }

    /**
     * 私信列表
     * @param messageListDto 私信列表dto
     * @return ResponseResult
     */
    @Override
    public ResponseResult<MessageListVo> privateMessageList(MessageListDto messageListDto) {
        //1. 校验参数
        if (messageListDto == null || messageListDto.getFriendId() == null) {
            throw new NullParamException();
        }
        //2. 从redis中获取私信
        Long userId = ThreadLocalUtil.getUserId();
        Long friendId = messageListDto.getFriendId();
        Long lastMessageId = messageListDto.getLastMessageId();
        if (lastMessageId == null) {
            lastMessageId = 0L;
        }
        //3. 查询redis中是否包含私信key
        String messageKey = InteractConstant.REDIS_PRIVATE_MESSAGE_KEY + Math.min(userId, friendId)
                + "-" + Math.max(userId, friendId) + ":";
        //3.1 查询redis中是否包含私信key
        Boolean hasKey = stringRedisTemplate.hasKey(messageKey);
        if (hasKey == null || !hasKey) {
            //3.1.1 redis中没有私信key，从数据库中查询
            return getMessagesInDb(userId, friendId, lastMessageId);
        }
        //4. 从redis中获取私信
        List<String> messageList = stringRedisTemplate.opsForList().range(messageKey, 0, -1);
        if (messageList == null || messageList.isEmpty()) {
            // 4.1 redis中没有私信，从数据库中查询
            return getMessagesInDb(userId, friendId, lastMessageId);
        }
        // 5. 将私信转换为vo
        List<MessageVo> messageVoList = new ArrayList<>();
        for (String message : messageList) {
            MessageVo messageVo = JSON.parseObject(message, MessageVo.class);
            messageVoList.add(messageVo);
        }
        Integer total = messageVoList.size();
        MessageListVo messageListVo = MessageListVo.builder()
                .messages(messageVoList)
                .total(total)
                .lastMessageId(messageVoList.get(messageVoList.size() - 1).getMessageId())
                .build();
        return ResponseResult.successResult(messageListVo);
    }


    /**
     * 从数据库中获取私信
     * @param userId 用户id
     * @param friendId 好友id
     * @param lastMessageId 最后一条私信id
     * @return ResponseResult
     */
    private ResponseResult<MessageListVo> getMessagesInDb(Long userId, Long friendId,
                                                          Long lastMessageId) {
        // 1. 从数据库中查询私信
        List<PrivateMessage> privateMessageList = privateMessageMapper.selectByUserIdAndFriendId(userId, friendId, lastMessageId);
        // 2. 将私信转换为vo
        List<MessageVo> messageVoList = new ArrayList<>();
        for (PrivateMessage privateMessage : privateMessageList) {
            // 2.3 封装私信vo
            MessageVo messageVo = MessageVo.builder()
                    .messageId(privateMessage.getId().toString())
                    .messageContent(privateMessage.getMessageContent())
                    .senderId(privateMessage.getSenderId().toString())
                    .status(privateMessage.getStatus())
                    .build();
            messageVoList.add(messageVo);
        }
        // 3. 封装私信列表vo
        Integer total = messageVoList.size();
        MessageListVo messageListVo = MessageListVo.builder()
                .messages(messageVoList)
                .total(total)
                .lastMessageId(messageVoList.get(messageVoList.size() - 1).getMessageId())
                .build();
        return ResponseResult.successResult(messageListVo);
    }

}