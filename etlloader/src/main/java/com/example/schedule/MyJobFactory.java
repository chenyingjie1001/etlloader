package com.example.schedule;

import org.quartz.spi.TriggerFiredBundle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.scheduling.quartz.SpringBeanJobFactory;

/**
 * 弃用
 * @author yingjie.chen
 *
 */
public class MyJobFactory extends SpringBeanJobFactory {

	
	/**
	 * springboot并不支持 很奇怪 可能是没有加入某个配置
	 */
	@Autowired
	private AutowireCapableBeanFactory beanFactory;
	/**
	 * 由于quarz自己创建job不受spring管理，导致service都不能autowired
	 * 在此重写createjob
	 */
	@Override
	protected Object createJobInstance(TriggerFiredBundle bundle)
			throws Exception {
		Object jobInstance = super.createJobInstance(bundle);
		beanFactory.autowireBean(jobInstance);
        return jobInstance;
	}
}
