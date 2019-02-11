package com.common.lightweight;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.common.lightweight.mapper")
public class CommonApplication {

	public static void main(String[] args) {
		//设置自定义初始化代码的执行
		SpringApplication springApplication = new SpringApplication(CommonApplication.class);
		springApplication.addListeners(new ApplicationStartup());
		springApplication.run(args);
	}
}
