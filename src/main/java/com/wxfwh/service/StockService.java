package com.wxfwh.service;

import com.wxfwh.beans.StockInfo;
import com.wxfwh.mapper.StockMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StockService {
    @Autowired
    StockMapper stockMapper;

    public StockInfo selectStockInfoByUidDate(String uid, String date) {
        return stockMapper.selectStockInfoByUidDate(uid,date);
    }

    public List<StockInfo> selectStockInfoByUid(String uid,String date) {
        return stockMapper.selectStockInfoByUid(uid,date);
    }
}
