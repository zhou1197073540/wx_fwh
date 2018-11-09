package com.wxfwh.mapper;

import com.wxfwh.beans.StockInfo;
import com.wxfwh.beans.StockTemplateBean;
import com.wxfwh.beans.SignalData;
import com.wxfwh.beans.WxUser;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public interface TemplateMap {

    /**
     * 查找status=1的数据
     */
    @Transactional
    @Select("SELECT \"ticker\",\"stockName\",\"industryID\",\"close\",\"date\",\"type\" FROM " +
            "wx_stock_template_msg WHERE \"status\"=0.0  ORDER BY \"value\" DESC LIMIT 5")
    List<StockTemplateBean> selectStockTemplateData();

    @Transactional
    @Update("update wx_stock_template_msg SET \"status\"=1.0")
    void updateStatus();


    /**
     * 查找所有微信公众号 的用户微信openid
     */
    @Transactional
    @Select("select wx_openid from user_wx_info")
    List<String> selectSubscribeWXOpenId();

    @Transactional
    @Select("select * from signal_data where is_send='0' ORDER BY \"date\" DESC LIMIT 5")
    List<SignalData> selectListMsg();

    @Transactional
    @Update("UPDATE signal_data SET is_send='1'")
    void updateIsSend();

//================================================================
    @Transactional
    @Select("select * from wx_daily_stock_info where date=#{date}")
    List<StockInfo> selectListStockInfo(@Param("date") String todayDate);

    @Transactional
    @Select("select * from wx_user_info")
    List<WxUser> selectFinanceWXOpenId();
}
