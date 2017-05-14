package com.zhaolei;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@MapperScan("com.zhaolei.dao")
@SpringBootApplication
public class GraApplication {

	public static void main(String[] args) {
		SpringApplication.run(GraApplication.class, args);
	}
}
