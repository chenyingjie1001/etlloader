package com.example.schedule;


import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.dto.execute.ExecuteTask;
import com.example.dto.task.Task;
import com.example.services.execute.impl.ExecuteTaskService;
import com.example.utils.SpringUtil;


public class QuartzJob implements Job {
	
	private static final Logger logger = LoggerFactory.getLogger(QuartzJob.class);
	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		logger.info("insert int executetask table *************************");
		try {
			Task task = (Task) context.getMergedJobDataMap().get(
					"scheduleJob");
			ExecuteTask exetask = new ExecuteTask();
			//插入任务时候只需要taskid createdate exestatus 三个参数即可
			exetask.setTaskid(task.getId());
			exetask.setCreatedate(context.getScheduledFireTime());
			exetask.setExestatus(-1);
			exetask.setIsall(task.getIsall());
			ExecuteTaskService service = (ExecuteTaskService) SpringUtil.getBean("executeTaskService");
			service.add(exetask);
			logger.info("******************接收到的值******************:"+task);
			logger.info("理论触发时间:"+context.getScheduledFireTime());//理论触发时间？？context.getScheduledFireTime()
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
	
	}

}
