package com.wxfwh.service;

import com.wxfwh.constant.MessageType;
import com.wxfwh.message.BaseMessage;
import com.wxfwh.message.TextMessage;
import com.wxfwh.utils.MessageUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Map;

public class CoreService {

    public static String processMap(Map<String, String> receiveMsgMap) {
        // 发送方帐号
        String fromUserName = (String) receiveMsgMap.get("FromUserName");
        // 开发者微信号
        String toUserName = (String) receiveMsgMap.get("ToUserName");
        // 消息类型
        String msgType = (String) receiveMsgMap.get("MsgType");
        // 消息内容
        String content = (String) receiveMsgMap.get("Content");

        BaseMessage base = new BaseMessage();
        base.setToUserName(fromUserName).setFromUserName(toUserName)
                .setCreateTime(new Date().getTime()).setMsgType(msgType);
        // 文本消息
        if (msgType.equals(MessageType.REQ_MESSAGE_TYPE_TEXT)) {
            TextMessage txtMsg = new TextMessage(base);
            txtMsg.setContent("你发送的是文本消息");
            return MessageUtil.messageToXml(txtMsg);
        }
        // 图片消息
        else if (msgType.equals(MessageType.REQ_MESSAGE_TYPE_IMAGE)) {
        }
        // 语音消息
        else if (msgType.equals(MessageType.REQ_MESSAGE_TYPE_VOICE)) {
        }
        // 视频消息
        else if (msgType.equals(MessageType.REQ_MESSAGE_TYPE_VIDEO)) {
        }
        // 视频消息
        else if (msgType.equals(MessageType.REQ_MESSAGE_TYPE_SHORTVIDEO)) {
        }
        // 地理位置消息
        else if (msgType.equals(MessageType.REQ_MESSAGE_TYPE_LOCATION)) {
        }
        // 链接消息
        else if (msgType.equals(MessageType.REQ_MESSAGE_TYPE_LINK)) {
        }
        // 事件推送
        else if (msgType.equals(MessageType.REQ_MESSAGE_TYPE_EVENT)) {
            // 事件类型
            String eventType = (String) receiveMsgMap.get("Event");
            // 关注
            if (eventType.equals(MessageType.EVENT_TYPE_SUBSCRIBE)) {
            }
            // 取消关注
            else if (eventType.equals(MessageType.EVENT_TYPE_UNSUBSCRIBE)) {
                // TODO 取消订阅后用户不会再收到公众账号发送的消息，因此不需要回复
            }
            // 扫描带参数二维码
            else if (eventType.equals(MessageType.EVENT_TYPE_SCAN)) {
                // TODO 处理扫描带参数二维码事件
            }
            // 上报地理位置
            else if (eventType.equals(MessageType.EVENT_TYPE_LOCATION)) {
                // TODO 处理上报地理位置事件
            }
            // 自定义菜单
            else if (eventType.equals(MessageType.EVENT_TYPE_CLICK)) {
                // TODO 处理菜单点击事件
            }
        }
        return null;
    }

    public static String process(HttpServletRequest request) {
        String replyXmlMsg = null;
        try {
            // 调用parseXmlToMap方法解析请求消息
            Map<String,String> requestMap = MessageUtil.parseXmlToMap(request);
            replyXmlMsg=processMap(requestMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return replyXmlMsg;
    }
}
