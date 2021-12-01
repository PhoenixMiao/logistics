package com.phoenix.logistics;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan("undestiny.stell.mapper")
public class StellApplication {

	public static void main(String[] args) {
		SpringApplication.run(StellApplication.class, args);
	}

}
