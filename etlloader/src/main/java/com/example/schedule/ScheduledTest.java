package com.example.schedule;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

/**
 * SpringBoot自带的Scheduled，
 * 可以将它看成一个轻量级的Quartz，
 * 而且使用起来比Quartz简单许多
 * @author yingjie.chen
 * @EnableScheduling 代表boot容器启动的时候加载schedule
 * 默认是单线程，这边只做扫描单线程即可
 */
@EnableScheduling
@Component
public class ScheduledTest {

	
//	private static final Logger logger = LoggerFactory.getLogger(ScheduledTest.class);
	
	/**
	 * 定时扫描
	 */
	/*@Scheduled(cron="0/30 * 9-21 * * ?") 
	public void executeTest(){
		Thread current = Thread.currentThread(); 
		logger.info("扫描任务:"+current.getId()+ ",任务名:"+current.getName());
	}
	
	
	@Scheduled(cron="0 0/2 8-20 * * ?") 
    public void executeFileDownLoadTask() {

        // 间隔2分钟,执行工单上传任务     
        Thread current = Thread.currentThread();  
        System.out.println("定时任务1:"+current.getId());
        logger.info("ScheduledTest.executeFileDownLoadTask 定时任务1:"+current.getId()+ ",name:"+current.getName());
    }

    @Scheduled(cron="0 0/1 8-20 * * ?") 
    public void executeUploadTask() {

        // 间隔1分钟,执行工单上传任务              
        Thread current = Thread.currentThread();  
        System.out.println("定时任务2:"+current.getId());
        logger.info("ScheduledTest.executeUploadTask 定时任务2:"+current.getId() + ",name:"+current.getName());
    }

    @Scheduled(cron="0 0/3 5-23 * * ?") 
    public void executeUploadBackTask() {

        // 间隔3分钟,执行工单上传任务                          
        Thread current = Thread.currentThread();  
        System.out.println("定时任务3:"+current.getId());
        logger.info("ScheduledTest.executeUploadBackTask 定时任务3:"+current.getId()+ ",name:"+current.getName());
    } */
}
