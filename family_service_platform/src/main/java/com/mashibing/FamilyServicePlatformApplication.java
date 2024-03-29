package com.mashibing;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication
@MapperScan
public class FamilyServicePlatformApplication {

	public static void main(String[] args) {
		SpringApplication.run(FamilyServicePlatformApplication.class, args);
	}

}
