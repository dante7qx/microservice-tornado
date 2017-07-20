package com.tornado.sysmgr.dao;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.tornado.sysmgr.dao.mapper")
public class TornadoSysMgrDaoApplication {
	
	public static void main(String[] args) throws Exception {
		SpringApplication.run(TornadoSysMgrDaoApplication.class, args);
	}

	
}
