package com.example.interceptors;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * spring配置路由 
 * 手动配置拦截器
 * @author yingjie.chen
 *
 */
//@Configuration
public class EtlWebMvcConfigurerAdapter extends WebMvcConfigurerAdapter {

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new EtlInterceptor())
				.addPathPatterns("/datasource/*","/execute/*","/task/*","/preview/*");
		super.addInterceptors(registry);
	}
}
