package com.wxfwh.testXML;

import com.wxfwh.beans.WxUser;
import com.wxfwh.message.TemplateData;
import com.wxfwh.service.TemplateService;
import com.wxfwh.utils.AccessTokenUtil;
import com.wxfwh.utils.PropertiesUtil;
import com.wxfwh.utils.SampleHttpUtil;
import com.wxfwh.utils.TimeUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static com.wxfwh.wxapi.URLCONST.TEMPLATE_SEND;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class TestTemplate {
    @Autowired
    TemplateService templateService;

    public String template_url="http://www.alphataker.com:8080/html/wisdomasset.html?uid=%s&date=%s";

    @Test
    public void sendTemplateMsg() throws Exception {
        List<WxUser> users = templateService.selectFinanceWXOpenId();
        Map<String, String> oneMsg = new HashMap<String, String>();
        oneMsg.put("first", "您好，您当日股票信息已产生");
        oneMsg.put("keyword1", TimeUtil.getDateTime());
        oneMsg.put("keyword2", "点击查看详情！！");
//        oneMsg.put("remark", "建议持仓小于1天，信息仅供参考，请独立做出决策");
        for(WxUser user:users){
            sendMsg(oneMsg,user);
        }
    }
    private void sendMsg(Map<String, String> content,WxUser user) throws Exception {
        TemplateData data = TemplateData.New();
        String access_tocken = AccessTokenUtil.getAccessToken();
        Iterator<Map.Entry<String, String>> iterator = content.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, String> entry = iterator.next();
            data.add(entry.getKey(), entry.getValue(),settingColor(entry.getKey()));
        }
        data.setTemplate_id(PropertiesUtil.getProperty("template_trading"));
        data.setTopcolor("#743A3A");
        data.setUrl(String.format(template_url,user.getUid(),TimeUtil.getTodayDate()));// url先空着，以后有了再加上
        String jsonStr = data.setTouser("oGYKl0jX6ATj0bSvAtb7uhUkHsB8").build();// oGYKl0jX6ATj0bSvAtb7uhUkHsB8
        String result = SampleHttpUtil.doPost(String.format(TEMPLATE_SEND, access_tocken), jsonStr);
        System.out.println(result);
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
