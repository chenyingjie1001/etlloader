package com.example.mapper.task;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.example.dto.datasource.DataSource;
import com.example.dto.task.Task;
import com.example.mapper.base.BaseMapper;


@Mapper
public interface TaskMapper extends BaseMapper<Task>{
	/**
	 * 查询所有数据源
	 * @return
	 */
	List<DataSource> findAllDS() throws Exception;
	/**
	 * 通过Id查询数据源
	 * @param id
	 * @return
	 */
	DataSource findDSById(int id) throws Exception;
	/**
	 * 更新任务状态
	 * @param map
	 * @return
	 */
	int updateTaskStatus(Map<String, Object> map) throws Exception;
	/**
	 * 通过父任务Id查询父任务及其所有子任务
	 * @param id
	 * @return
	 */
	List<Task> findAllTask(int id) throws Exception;
	/**
	 * 通过Id查询任务
	 * @param id
	 * @return
	 */
	Task findTaskById(int id) throws Exception;
	/**
	 * 查询可以被加载的任务
	 * @return
	 */
	List<Task> findExeTask() throws Exception;
	/**
	 * 通过父任务Id,删除其所有子任务
	 * @param ParentId
	 * @return
	 */
	int deleteSubtask(int ParentId) throws Exception;
	/**
	 * 查询全部的父任务
	 * @return
	 * @throws Exception
	 */
	List<Task> findTasksByParentId(int ParentId) throws Exception;
	
	List<Map<String, Object>> findIncloudChildren(Task t);
	
	/**
	 * 查询增量配置信息
	 * @param taskid
	 * @return
	 */
	String findWhereConf(int taskid);
}