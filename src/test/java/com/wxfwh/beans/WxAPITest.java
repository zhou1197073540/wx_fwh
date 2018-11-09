package com.wxfwh.beans;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.UnsupportedEncodingException;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class WxAPITest {
    @Autowired
    private Environment env;

    @Test
    public void testSelect() throws UnsupportedEncodingException {
        System.out.println(env.getProperty("template_id_templeMsg"));
    }

}