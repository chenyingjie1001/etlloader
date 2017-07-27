package com.example.schedule;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
/**
 * 
 * @author yingjie.chen
 *
 */
@Configuration
public class ScheduleConfig {

	/**
	 * 把SchedulerFactoryBean 初始化到spring容器
	 * 类似配置中<bean ...SchedulerFactoryBean>
	 * 当不用sts插件 纯java项目时候@bean在@PostConstruct方法中无法调用，原因不详
	 * @return
	 */
	@Bean
	public SchedulerFactoryBean getSchedulerFactoryBean(){
		return new SchedulerFactoryBean();
	}
}
