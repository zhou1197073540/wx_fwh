package com.wxfwh;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@MapperScan("com.wxfwh.mapper")
public class XiaoxinApplication {

	public static void main(String[] args) {
		SpringApplication.run(XiaoxinApplication.class, args);
	}
}
