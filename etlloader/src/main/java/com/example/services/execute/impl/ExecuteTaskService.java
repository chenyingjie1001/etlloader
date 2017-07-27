package com.example.services.execute.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.dto.execute.ExecuteTask;
import com.example.mapper.execute.ExecuteMapper;
import com.example.mapper.task.TaskMapper;
import com.example.schedule.ITaskBaseConfigService;
import com.example.services.execute.IExecuteTaskService;
@Transactional
@Service
public class ExecuteTaskService implements IExecuteTaskService {
	
	private static final Logger logger = LoggerFactory.getLogger(ExecuteTaskService.class);
	
	@Autowired
	private ExecuteMapper mapper;
	
	@Autowired
	private TaskMapper taskMapper;

	@Autowired
	private ITaskBaseConfigService service;
	
	@Override
	public List<ExecuteTask> find(ExecuteTask t) {
		List<ExecuteTask> list = null;
		try {
			list = mapper.find(t);
			for(ExecuteTask executeTask : list){
				executeTask.setTask(taskMapper.findTaskById(executeTask.getTaskid()));
			}
		} catch (Exception e) {
		}
		return list;
	}

	@Override
	public int add(ExecuteTask t) {
		logger.info("*-*-*-*-*-*-添加可执行任务："+t.toString());
		try {
			return mapper.add(t);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("添加可执行任务  出错ExecuteTask："+t);
			logger.error("libzone.cn exception:", e);
			return 0;
		}
	}

	@Override
	public int update(ExecuteTask t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(String ids) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int runTask(Map<String,Object> map) {
		logger.info("*-*-*-*-*-*-*-添加重试任务："+map.toString());
		int sign = 0;
		try {
			ExecuteTask exetask = new ExecuteTask();
			exetask.setTaskid((Integer)map.get("taskId"));
			exetask.setCreatedate(new Date((Long)map.get("createDate")));
			exetask.setIsall((String)map.get("isall"));
			exetask.setExestatus(-1);
			sign = mapper.add(exetask);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("添加重试任务  出错map："+map);
			logger.error("libzone.cn exception:", e);
		}
		return sign;
	}

}
