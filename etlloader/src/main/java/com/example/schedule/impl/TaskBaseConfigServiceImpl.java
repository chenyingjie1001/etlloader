package com.example.schedule.impl;

import java.util.Date;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;
import com.example.dto.task.Task;
import com.example.schedule.QuartzJob;
import com.example.schedule.ITaskBaseConfigService;
@Service
//@ImportResource("classpath:quartz-spring.xml")
public class TaskBaseConfigServiceImpl implements ITaskBaseConfigService {
	
	private static final Logger logger = LoggerFactory.getLogger(TaskBaseConfigServiceImpl.class);
	
	@Autowired
	private SchedulerFactoryBean schedulerFactoryBean;
	
	/**
	 * 加载任务调度
	 * @param task
	 */
	@Override
	public void loadTask(Task task) {
		logger.info(new Date()+"**准备加载任务到调度器**-------任务ID:"+task.getId());
		try {
		Scheduler scheduler = schedulerFactoryBean.getScheduler();
		// 任务名称和任务组设置规则：
		// 名称：task_1 ..
		// 组 ：group_1 ..
		TriggerKey triggerKey = TriggerKey.triggerKey(
				"task_"+task.getId(), "group_"+task.getId());
		
		//删除调度器中的任务
		delTask(task.getId());
		
		try {
			CronTrigger trigger = (CronTrigger) scheduler
					.getTrigger(triggerKey);
			// 不存在，创建一个
			if (null == trigger) {
				JobDetail jobDetail = JobBuilder
						.newJob(QuartzJob.class)
						.withIdentity("task_"+task.getId(),
								"group_"+task.getId()).build();
				jobDetail.getJobDataMap().put("scheduleJob",task);
				// 表达式调度构建器 执行频率
				CronScheduleBuilder scheduleBuilder = CronScheduleBuilder
						.cronSchedule(task.getFrequency());
				// 按新的表达式构建一个新的trigger
				trigger = TriggerBuilder
						.newTrigger()
						.withIdentity("task_"+task.getId(),
								"group_"+task.getId())
								.withSchedule(scheduleBuilder).build();
				scheduler.scheduleJob(jobDetail, trigger);
			} else {
				// trigger已存在，则更新相应的定时设置
				CronScheduleBuilder scheduleBuilder = CronScheduleBuilder
						.cronSchedule(task.getFrequency());
				// 按新的cronExpression表达式重新构建trigger
				trigger = trigger.getTriggerBuilder().withIdentity(triggerKey)
						.withSchedule(scheduleBuilder).build();
				// 按新的trigger重新设置job执行
				scheduler.rescheduleJob(triggerKey, trigger);
			}

		} catch (SchedulerException e) {
			e.printStackTrace();
			logger.error("loadTask 1出错task:" + task.toString());
			logger.error("libzone.cn exception:", e);
		}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("loadTask 2出错task:" + task.toString());
			logger.error("libzone.cn exception:", e);
		}
		logger.info(new Date()+"**加载任务到调度器完成**-------任务ID:"+task.getId());
		logger.info(new Date()+"**加载任务到调度器完成**-------task任务信息:"+task);
	}
	
	/**
	 * 暂停任务
	 * @param taskId
	 */
	@Override
	public void pauseTask(int taskId) {

		Scheduler scheduler = schedulerFactoryBean.getScheduler();
		JobKey jobKey = JobKey.jobKey("task_" +taskId, "group_" + taskId);
		try {
			scheduler.pauseJob(jobKey);
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 恢复任务
	 * 恢复时 会立刻执行一次？？
	 * @param taskId
	 */
	@Override
	public void resumeTask(int taskId){
		Scheduler scheduler = schedulerFactoryBean.getScheduler();
		JobKey jobKey = JobKey.jobKey("task_" +taskId, "group_" + taskId);
		try {
			scheduler.resumeJob(jobKey);
		} catch (SchedulerException e) {
			e.printStackTrace();
			logger.error("resumeTask 出错taskId:" + taskId);
			logger.error("libzone.cn exception:", e);
		}
	}
	
	/**
	 * 删除任务
	 * @param taskId
	 */
	@Override
	public void delTask(int taskId) {
		Scheduler scheduler = schedulerFactoryBean.getScheduler();
		JobKey jobKey = JobKey.jobKey("task_" +taskId, "group_" + taskId);
		try {
			scheduler.deleteJob(jobKey);
			logger.info("删除调度器任务：" + taskId +" 成功");
		} catch (SchedulerException e) {
			e.printStackTrace();
			logger.error("删除调度器任务 出错taskId:" + taskId);
			logger.error("libzone.cn exception:", e);
		}
	}
	
	/**
	 * 立刻运行任务
	 * @param taskId
	 */
	@Override
	public void runTask(int taskId) {
		Scheduler scheduler = schedulerFactoryBean.getScheduler();
		JobKey jobKey = JobKey.jobKey("task_" +taskId, "group_" + taskId);
		//Assert.notNull(jobKey, "taskId:" + taskId + "任务未加载或不存在，执行失败");
		try {
			scheduler.triggerJob(jobKey);
		} catch (SchedulerException e) {
			e.printStackTrace();
			logger.error("runTask 出错taskId:" + taskId);
			logger.error("libzone.cn exception:", e);
		}

	}
	
	/**
	 * 执行频率改变后 更新任务(loadTask 已经满足)
	 * 只更新了已加载任务的频率（在内存中），并没有更新数据库中任务的执行频率，暂时不需要
	 * @param task
	 */
	@Override
	public void updateTask(Task task) {
		Scheduler scheduler = schedulerFactoryBean.getScheduler();

		TriggerKey triggerKey = TriggerKey.triggerKey("task_" +task.getId(), "group_" + task.getId());

		//获取trigger，即在spring配置文件中定义的 bean id="myTrigger"
		CronTrigger trigger;
		try {
			trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
			//表达式调度构建器
			CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(task.
					getFrequency());
			//按新的时间表达式重新构建trigger
			trigger = trigger.getTriggerBuilder().withIdentity(triggerKey)
					.withSchedule(scheduleBuilder).build();
			//按新的trigger重新设置job执行
			scheduler.rescheduleJob(triggerKey, trigger);
		} catch (SchedulerException e) {
			e.printStackTrace();
			logger.error("updateTask 出错Task:" + task.toString());
			logger.error("libzone.cn exception:", e);
		}
	}
}
