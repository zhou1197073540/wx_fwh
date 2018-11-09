package com.wxfwh.mapper;

import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public interface TestMap {

    @Select("select name from test")
    String selectFromTest();
}
