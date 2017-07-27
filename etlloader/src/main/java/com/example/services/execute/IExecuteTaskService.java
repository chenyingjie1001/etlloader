package com.example.services.execute;


import java.util.Map;

import com.example.dto.execute.ExecuteTask;
import com.example.services.base.IBaseService;

public interface IExecuteTaskService extends IBaseService<ExecuteTask>{

	int runTask(Map<String,Object> map);
}
