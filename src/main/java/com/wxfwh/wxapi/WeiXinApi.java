package com.wxfwh.wxapi;

import com.alibaba.fastjson.JSONObject;
import com.wxfwh.utils.AccessTokenUtil;
import com.wxfwh.utils.SampleHttpUtil;

import java.util.ArrayList;
import java.util.List;

import static com.wxfwh.wxapi.URLCONST.USER_GET;

public class WeiXinApi {
    /**
     * 获取关注了微信服务号的用户，
     *
     * @return
     */
    public static List<String> getWeiXinUsrs() {
        List<String> openIds = new ArrayList<String>();
        String access_tocken = AccessTokenUtil.getAccessToken();
        String jsonStr = null;
        try {
            jsonStr = SampleHttpUtil.getResult(String.format(USER_GET, access_tocken));
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (null == jsonStr) return openIds;
        JSONObject object = JSONObject.parseObject(jsonStr);
        if (object.containsKey("data")) {
            JSONObject obj = JSONObject.parseObject(object.getString("data"));
            if (obj.containsKey("openid")) {
                Object[] array = obj.getJSONArray("openid").toArray();
                for (Object arr : array) {
                    openIds.add(arr.toString());
                }
            }
        }
        return openIds;
    }



    public static void main(String[] args) {
        List<String> ss = getWeiXinUsrs();
        for (String s : ss) {
            System.out.println(s);
        }
    }

}
