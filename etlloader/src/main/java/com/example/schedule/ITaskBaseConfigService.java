package com.example.schedule;

import com.example.dto.task.Task;

public interface ITaskBaseConfigService {
	/**
	 * 加载任务调度
	 * @param task
	 */
	void loadTask(Task task);
	/**
	 * 暂停任务
	 * @param taskId
	 */
	void pauseTask(int taskId);
	/**
	 * 恢复任务
	 * @param taskId
	 */
	void resumeTask(int taskId);
	/**
	 * 删除任务
	 * @param taskId
	 */
	void delTask(int taskId) ;
	/**
	 * 立刻运行任务
	 * @param taskId
	 */
	void runTask(int taskId);
	/**
	 * 更新任务（执行频率）
	 * @param task
	 */
	void updateTask(Task task) ;
}
