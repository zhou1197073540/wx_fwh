package com.wxfwh.timer;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.wxfwh.beans.SignalData;
import com.wxfwh.message.TemplateData;
import com.wxfwh.service.TemplateService;
import com.wxfwh.utils.AccessTokenUtil;
import com.wxfwh.utils.PropertiesUtil;
import com.wxfwh.utils.SampleHttpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import static com.wxfwh.wxapi.URLCONST.TEMPLATE_SEND;

/**
 * 官网订阅微信服务号 发送股票模版消息
 */
@Component
public class SubscribeMsgTimer {
	@Autowired
    TemplateService templateService;

//    @Scheduled(cron = "0 0 16 * * MON-FRI")
	public void execute() throws Exception{
		System.out.println("this is timer process!!");
		List<String> openids = templateService.selectSubscribeWXOpenId();
		List<SignalData> listRet = templateService.selectListMsg();
		for (SignalData one : listRet) {
			try {
				Map<String, String> oneMsg = new HashMap<String, String>();
				oneMsg.put("first", "启航一号");
				oneMsg.put("keyword1", one.getTicker());
				oneMsg.put("keyword2", one.getName());
				oneMsg.put("keyword3", one.getPrice() + "元/股");
				oneMsg.put("keyword4", one.getDate());
				if (one.getVolume().contains("-")) {
					oneMsg.put("keyword5",
							"卖出" + one.getVolume().replace("-", "") + "股");
				} else {
					oneMsg.put("keyword5", "买入" + one.getVolume() + "股");
				}
				oneMsg.put("remark", "信息仅供参考,如有问题,请联系客服。");
				sendMsg(oneMsg, openids);
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
        templateService.updateIsSend();
		
	}

	private void sendMsg(Map<String, String> content, List<String> openIds) {
		TemplateData data = TemplateData.New();
		String access_tocken = AccessTokenUtil.getAccessToken();
		Iterator<Map.Entry<String, String>> iterator = content.entrySet()
				.iterator();
		while (iterator.hasNext()) {
			Map.Entry<String, String> entry = iterator.next();
			data.add(entry.getKey(), entry.getValue(),
					settingColor(entry.getKey()));
		}
		data.setTemplate_id(PropertiesUtil.getProperty("template_id_templeMsg"));
		data.setTopcolor("#743A3A");
		data.setUrl("");// url先空着，以后有了再加上
		for (String openid : openIds) {
			try {
				String jsonStr = data.setTouser(openid).build();// oGYKl0jX6ATj0bSvAtb7uhUkHsB8
				String result = SampleHttpUtil.doPost(
						String.format(TEMPLATE_SEND, access_tocken), jsonStr);
				System.out.println(result);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private String settingColor(String entry_key) {
		String[] keys_values = PropertiesUtil.getProperty("template_color")
				.split(",");
		for (String key_value : keys_values) {
			try {
				if (StringUtils.isEmpty(key_value))
					continue;
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
