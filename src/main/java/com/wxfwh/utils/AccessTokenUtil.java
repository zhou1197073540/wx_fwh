package com.wxfwh.utils;


import com.alibaba.fastjson.JSONObject;
import com.wxfwh.constant.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class AccessTokenUtil {

    private static String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=%s&secret=%s";
    public static String access_token = "";

    public static final Logger log = LoggerFactory.getLogger(AccessTokenUtil.class);

    public static String getAccessToken() {
        boolean exist = RedisUtil.getJedis().exists(Constant.redis_access_token);
        log.info("redis exist access_token {}", exist);
        String accessToken = "";
        if (exist) {
            accessToken = RedisUtil.getJedis().get(Constant.redis_access_token);
        } else {
            accessToken = setAccessToken();
            log.info("generate new access_token :{}", accessToken);
        }
        return accessToken;
    }

    private static String setAccessToken() {
        try {
            String html = SampleHttpUtil.getResult(String.format(url, Constant.appId, Constant.encodingAesKey));
            JSONObject json = JSONObject.parseObject(html);
            if (json.containsKey("access_token")) {
                String token = json.getString("access_token");
                RedisUtil.getJedis().setex(Constant.redis_access_token, Constant.redis_access_token_time, token);
                access_token = token;
                return token;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
