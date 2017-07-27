package com.example.services.task;

import java.util.List;
import java.util.Map;

import com.example.dto.base.Table;
import com.example.dto.datasource.DataSource;
import com.example.dto.task.ManualTask;
import com.example.dto.task.Allconfig;
import com.example.dto.task.Task;
import com.example.services.base.IBaseService;

public interface ITaskService extends IBaseService<Task>{

	List<DataSource> listDataSources();

	List<Table> listTablesByDataSources(int id);

	int updateTaskStatus(Map<String, Object> map);

	int manualTask(ManualTask manualTask);

	List<Task> findAllTask(int id);
	
	List<String> findDateOperation(int id);
	
	int updateWhere(Allconfig config);
	
	DataSource findDSById(int id);
	
	int update_delete(Task t);
	
	List<Task> findExeTask();
	
	List<Task> findTasksByParentId(int parentid);
	
	List<Map<String, Object>> findHaveChildren(Task task);
	
	List<String> choosedTables(int id);
	
	Allconfig getWhereConf(int taskid);
	
	List<Map<String, Object>> preview(Map<String, Object> map);
}
