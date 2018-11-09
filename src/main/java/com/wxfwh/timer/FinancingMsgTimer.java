package com.wxfwh.timer;

import com.wxfwh.beans.StockInfo;
import com.wxfwh.beans.WxUser;
import com.wxfwh.message.TemplateData;
import com.wxfwh.service.TemplateService;
import com.wxfwh.utils.AccessTokenUtil;
import com.wxfwh.utils.PropertiesUtil;
import com.wxfwh.utils.SampleHttpUtil;
import com.wxfwh.utils.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static com.wxfwh.wxapi.URLCONST.TEMPLATE_SEND;

/**
 *用微信发送微信模板消息，发送的是股票交易系统每日产生的收益消息
 */
@Component
public class FinancingMsgTimer {
    public String template_url="http://www.alphataker.com:8080/html/wisdomasset.html?uid=%s&date=%s";
    @Autowired
    TemplateService templateService;

    @Scheduled(cron = "0 0 16 * * MON-FRI")
    public void sendTemplateMsg() throws Exception{
        List<WxUser> users = templateService.selectFinanceWXOpenId();
        Map<String, String> oneMsg = new HashMap<String, String>();
        oneMsg.put("first", "您好，您当日股票信息已产生");
        oneMsg.put("keyword1", TimeUtil.getDateTime());
        oneMsg.put("keyword2", "点击查看详情");
//        oneMsg.put("remark", "信息仅供参考,如有问题,请联系客服。");
        sendMsg(oneMsg, users);
    }
    private void sendMsg(Map<String, String> content, List<WxUser> users) {
        TemplateData data = TemplateData.New();
        String access_tocken = AccessTokenUtil.getAccessToken();
        Iterator<Map.Entry<String, String>> iterator = content.entrySet()
                .iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, String> entry = iterator.next();
            data.add(entry.getKey(), entry.getValue(),settingColor(entry.getKey()));
        }
        data.setTemplate_id(PropertiesUtil.getProperty("template_trading"));
        data.setTopcolor("#743A3A");
        for (WxUser user : users) {
            data.setUrl(String.format(template_url,user.getUid(),TimeUtil.getTodayDate()));
            String jsonStr = data.setTouser(user.getWx_openid()).build();
            try {
                String result = SampleHttpUtil.doPost(
                        String.format(TEMPLATE_SEND, access_tocken), jsonStr);
                System.out.println("发送模板消息返回的result:"+result);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    private String settingColor(String entry_key) {
        String[] keys_values = PropertiesUtil.getProperty("template_trading_gcolor").split(",");
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
