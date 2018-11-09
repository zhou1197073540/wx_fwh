package com.wxfwh.testXML;

import com.wxfwh.message.ImageMessage;
import com.wxfwh.message.TextMessage;
import com.wxfwh.utils.MessageUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class Testxml {

    @Test
    public void testTextXml(){
        TextMessage msg=new TextMessage();
        msg.setFromUserName("zcx");
        msg.setContent("你好啊");
        msg.setCreateTime(new Date().getTime());
        msg.setMsgType("text");
        msg.setToUserName("pony");
        String xmlStr= MessageUtil.messageToXml(msg);
        System.out.println(xmlStr);
    }
    @Test
    public void testImgXml(){
        ImageMessage msg=new ImageMessage();
        msg.setFromUserName("zcx");
        msg.setCreateTime(new Date().getTime());
        msg.setMsgType("text");
        msg.setToUserName("pony");
        msg.setMediaId("123456789");
        String xmlStr= MessageUtil.messageToXml(msg);
        System.out.println(xmlStr);
    }
}
