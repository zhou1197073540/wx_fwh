package com.wxfwh.mapper;

import com.wxfwh.beans.StockInfo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface StockMapper {

    @Transactional
    @Select("select * from wx_daily_stock_info where uid=#{uid} and date=#{date}")
    StockInfo selectStockInfoByUidDate(@Param("uid") String uid,@Param("date") String date);

    @Transactional
    @Select("select * from wx_daily_stock_info where uid=#{uid} and date<=#{date} order by date desc limit 50")
    List<StockInfo> selectStockInfoByUid(@Param("uid") String uid,@Param("date") String date);
}
