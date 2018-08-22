package com.nice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@ServletComponentScan
@MapperScan("com.nice.dao")
//@EnableScheduling//开启定时任务
//@EnableAsync	 //开启异步调用
@EnableSwagger2
public class NiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(NiceApplication.class, args);
	}
}
