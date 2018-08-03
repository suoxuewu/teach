package com.teach.service;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import tk.mybatis.spring.annotation.MapperScan;

@MapperScan("com.teach.**.mapper")
@org.mybatis.spring.annotation.MapperScan("com.teach.mapper")
@EnableDiscoveryClient
@SpringBootApplication
public class TeachStudentServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(TeachStudentServiceApplication.class, args);
	}
}
