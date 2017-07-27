package com.example;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 如果不配置spring.xml默认是按启动文件的地址去扫描，所以必须放到最外面
 * 所有类都必须使用配置，
 * 比如restcontroller，service，mapper等 
 * 如果有xml文件就可以通过配置路径mybatis自动扫描，而不配置的话必须写@mapper
 * @author yingjie.chen
 *
 */
@SpringBootApplication
public class Run {

	public static void main(String[] args) {
		SpringApplication.run(Run.class, args);
	}
}
