package com.wxfwh.timer;

import com.alibaba.fastjson.JSONObject;
import com.wxfwh.beans.StockTemplateBean;
import com.wxfwh.message.TemplateData;
import com.wxfwh.service.TemplateService;
import com.wxfwh.utils.AccessTokenUtil;
import com.wxfwh.utils.PropertiesUtil;
import com.wxfwh.utils.SampleHttpUtil;
import com.wxfwh.wxapi.WeiXinApi;
import com.wxfwh.wxapi.URLCONST;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Component
public class TempleMsgTimer {
    private static final Logger logger = LoggerFactory.getLogger(TempleMsgTimer.class);

    @Autowired
    TemplateService templateService;

//    @Scheduled(cron = "0 15 14 * * MON-FRI")
    public void stockTempleMsg() throws Exception {
        System.out.println("this is timer process!!");
        List<StockTemplateBean> listRet = templateService.selectStockTemplateData();
        List<String> openIds = WeiXinApi.getWeiXinUsrs();
        for (StockTemplateBean one : listRet) {
            Map<String, String> oneMsg = new HashMap<String, String>();
            oneMsg.put("first", "股票建仓提醒");
            oneMsg.put("keyword1", one.getTicker());
            oneMsg.put("keyword2", one.getStockName() + "(" + one.getIndustryID() + ")");
            oneMsg.put("keyword3", String.valueOf(one.getClose()) + "元/股");
            oneMsg.put("keyword4", one.getDate());
            oneMsg.put("keyword5", one.getType());
            oneMsg.put("remark", "建议持仓小于1天，信息仅供参考，请独立做出决策");
            sendMsg(oneMsg, openIds);
        }
        templateService.updateStatus();
    }


    private void sendMsg(Map<String, String> content, List<String> openIds) throws Exception {
        TemplateData data = TemplateData.New();
        String access_tocken = AccessTokenUtil.getAccessToken();
        Iterator<Map.Entry<String, String>> iterator = content.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, String> entry = iterator.next();
            data.add(entry.getKey(), entry.getValue(),
                    settingColor(entry.getKey()));
        }
        data.setTemplate_id(PropertiesUtil.getProperty("template_id_templeMsg"));
        data.setTopcolor("#743A3A");
        data.setUrl("www.baidu.com");// url先空着，以后有了再加上
        for (String openid : openIds) {
            String jsonStr = data.setTouser("oGYKl0jX6ATj0bSvAtb7uhUkHsB8").build();// oGYKl0jX6ATj0bSvAtb7uhUkHsB8
            String result = SampleHttpUtil.doPost(
                    String.format(URLCONST.TEMPLATE_SEND, access_tocken), jsonStr);
            System.out.println(result);
        }
    }

    private String settingColor(String entry_key) {
        String[] keys_values = PropertiesUtil.getProperty("template_color").split(",");
        for (String key_value : keys_values) {
            try {
                if (StringUtils.isEmpty(key_value)) continue;
                String[] strs = key_value.split("[:：]");
                if (strs[0].contains(entry_key)) {
                    return strs.length == 2 ? strs[1] : "#000000";
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return "#000000";
    }

}
