package com.wxfwh.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wxfwh.beans.StockInfo;
import com.wxfwh.dto.RespDto;
import com.wxfwh.service.StockService;
import com.wxfwh.utils.StringHandle;
import com.wxfwh.utils.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Controller
public class StockController {
    @Autowired
    StockService stockService;

    @GetMapping("/checkinfo/{command}")
    @ResponseBody
    public RespDto checkinfos(HttpServletRequest request, @PathVariable String command) throws Exception {
        String uid = request.getParameter("uid");
        String date = request.getParameter("date");
        if (StringHandle.checkEmpty(uid)) return new RespDto(false, 10000, "参数uid不能为空");
        if(StringHandle.checkEmpty(date)) date= TimeUtil.getTodayDate();
        StockInfo stock = stockService.selectStockInfoByUidDate(uid,date);
        if (null == stock ) {
            return new RespDto(false, 10010, "success", "查询内容不存在");
        }
        return new RespDto(true, 10010, "success", selByCommand(stock,command));
    }

    @GetMapping("/info/total_assets")
    @ResponseBody
    public RespDto getUserInfo(HttpServletRequest request) throws Exception {
        String uid = request.getParameter("uid");
        String date = request.getParameter("date");
        if(StringHandle.checkEmpty(uid)) return new RespDto(true, 10010, "success", "uid参数为空");
        if(StringHandle.checkEmpty(date)) date= TimeUtil.getTodayDate();
        List<StockInfo> infos=stockService.selectStockInfoByUid(uid,date);
        List<Map<String, String>> list_map = new ArrayList<>();
        for (StockInfo info : infos) {
            Map<String, String> map = new LinkedHashMap<>();
            String content=info.getBalance_info();
            if (null!=content&&content.contains("\"总资产\":")) {
                String assets = content.substring(content.indexOf("\"总资产\":") + 6, content.length());
                assets = assets.substring(0, assets.indexOf(","));
                map.put("time", info.getDate());
                map.put("assets", assets);
                list_map.add(map);
            }
        }
        return new RespDto(true, 10010, "success", list_map);
    }


    public Object selByCommand(StockInfo stock,String type){
        if(type.equals("position")){
            return JSONArray.parseArray(stock.getPosition_info());
        }else if(type.equals("todayentrusts")){
            return JSONArray.parseArray(stock.getTrusts_info());
        }else if(type.equals("userInfo")){
            return JSONObject.parse(stock.getAccount_info());
        }else {
            return null;
        }
    }

}
