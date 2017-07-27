package com.example.quartz;

import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.dto.task.Task;
import com.example.schedule.ITaskBaseConfigService;
import com.example.services.task.ITaskService;
@Component
public class InitTask {
	
	private static final Logger logger = LoggerFactory.getLogger(InitTask.class);
	
	@Autowired
	private ITaskBaseConfigService service;
	
	@Autowired
	private ITaskService taskService;
	/**
	 * 初始化所有任务加入到调度器
	 */
	@PostConstruct
	public void initTask(){
		logger.info("***********************开始任务初始化***********************");
		List<Task> tasks = taskService.findExeTask();
		for(Task t : tasks){
			service.loadTask(t);
		}
		logger.info("***********************任务初始化完成*********共初始化任务： "+tasks.size()+" 条");
	}
}
