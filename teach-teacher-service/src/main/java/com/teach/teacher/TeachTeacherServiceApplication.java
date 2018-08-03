package com.teach.teacher;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import tk.mybatis.spring.annotation.MapperScan;

@MapperScan("com.teach.teacher.mapper")
@EnableDiscoveryClient
@SpringBootApplication
public class TeachTeacherServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(TeachTeacherServiceApplication.class, args);
	}
}
