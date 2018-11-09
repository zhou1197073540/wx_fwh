package com.wxfwh.service;

import com.wxfwh.beans.SignalData;
import com.wxfwh.beans.StockInfo;
import com.wxfwh.beans.StockTemplateBean;
import com.wxfwh.beans.WxUser;
import com.wxfwh.mapper.TemplateMap;
import com.wxfwh.utils.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TemplateService {
    @Autowired
    TemplateMap templateMap;

    public List<StockTemplateBean> selectStockTemplateData() {
        return templateMap.selectStockTemplateData();
    }

    public void updateStatus() {
        templateMap.updateStatus();
    }
    //==================下面是关注官网发送定时模板消息===========
    public List<String> selectSubscribeWXOpenId() {
        return templateMap.selectSubscribeWXOpenId();
    }

    public List<SignalData> selectListMsg() {
        return templateMap.selectListMsg();
    }

    public void updateIsSend() {
        templateMap.updateIsSend();
    }
//=================发送代客理财业务==============================================
    public List<StockInfo> selectListStockInfo() throws Exception{
        return templateMap.selectListStockInfo(TimeUtil.getTodayDate());
    }

    public List<WxUser> selectFinanceWXOpenId() {
        return templateMap.selectFinanceWXOpenId();
    }
}
