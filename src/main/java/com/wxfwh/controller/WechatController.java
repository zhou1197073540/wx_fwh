package com.wxfwh.controller;

import com.wxfwh.service.CoreService;
import com.wxfwh.utils.MessageUtil;
import com.wxfwh.utils.SignUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Controller
public class WechatController {
    @ResponseBody
    @GetMapping("/test")
    public String firstValidate(){
        return "test";
    }

    /**
     * 微信公众号后台第一次验证
     */
    @ResponseBody
    @GetMapping("/wc")
    public String firstValidate(HttpServletRequest request, HttpServletResponse response) throws Exception{
        String signature = request.getParameter("signature");
        String timestamp = request.getParameter("timestamp");
        String nonce = request.getParameter("nonce");
        String echostr = request.getParameter("echostr");
        if (SignUtil.checkSignature(signature, timestamp, nonce)) {
            System.out.println("微信第一次验证成功");
            return echostr;
        }
        System.out.println("微信第一次验证失败");
        return null;
    }

    @ResponseBody
    @PostMapping("/wc")
    public String doPost (HttpServletRequest request,HttpServletResponse response)throws Exception{
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        String msg_signature = request.getParameter("msg_signature");
        String signature = request.getParameter("signature");
        String timestamp = request.getParameter("timestamp");
        String nonce = request.getParameter("nonce");
        String encryptType = request.getParameter("encrypt_type");
        String respXml=null;
        if ("aes".equals(encryptType)) {
            Map<String,String> receiveMsgMap= MessageUtil.decryptMessage(signature,timestamp,nonce,request);
            //业务的处理方法，返回xml格式字符串结果
            String testXml= CoreService.processMap(receiveMsgMap);
            respXml = MessageUtil.ecryptMsg(testXml, timestamp, nonce);
        }else{
            respXml= CoreService.process(request);
            System.out.println(respXml);
        }
        return respXml;
    }


}
