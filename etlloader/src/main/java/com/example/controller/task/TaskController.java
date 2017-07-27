package com.example.controller.task;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.controller.base.BaseController;
import com.example.dto.base.Table;
import com.example.dto.datasource.DataSource;
import com.example.dto.task.Allconfig;
import com.example.dto.task.ManualTask;
import com.example.dto.task.Task;
import com.example.schedule.ITaskBaseConfigService;
import com.example.services.task.ITaskService;
import com.example.utils.WarnException;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * task
 * @author yingjie.chen
 * @date:2017年6月7日 16:30:52
 * @version 1.0
 */
@Api(description="任务管理服务")
@RestController
@RequestMapping(value="/task/", method=RequestMethod.POST)
public class TaskController extends BaseController{

	private static final Logger logger = LoggerFactory.getLogger(TaskController.class);
	
	@Autowired
	private ITaskService taskService;
	
	@Autowired
	private ITaskBaseConfigService taskConfService;
	
	/**
	 * 第一步，获取全部数据源
	 * @return
	 */
	@ApiOperation(value="第一步，获取全部数据源")
	@RequestMapping("listDataSources")
	public Object listDataSources(){
		List<DataSource> data = taskService.listDataSources();
		if(data != null){
			return success(data, null);
		}
		return failure();
	}
	/**
	 * 第二步，根据选择的数据源，获得全部的表(其中若已在 目标库 中存在的表 标记为 true )
	 * {"id":1}
	 * @param id
	 * @return
	 */
	@ApiOperation(value="第二步，根据选择的数据源，获得全部的表")
	@RequestMapping("listTablesByDataSources")
	public Object listTablesByDataSources(@RequestBody Map<String, Integer> map){
		List<Table> data = taskService.listTablesByDataSources(map.get("id"));
		if(data != null){
			return success(data, null);
		}
		return failure();
	}
	/**
	 * 第三步，任务添加
	 * 传递过来的是一个父任务，根据Checktables产生相应的子任务
	 * @param task
	 * @return
	 * @throws WarnException 
	 */
	@ApiOperation(value="第三步，任务添加")
	@RequestMapping("add")
	public Object add(@RequestBody Task task) throws WarnException{
		if(taskService.add(task) == 1){
			return success();
		}
		return failure();
	}
	/**
	 * 删除，包括批量和单个删除
	 * {"ids":"1,2,3"}
	 * @param ids
	 * @return
	 */
	@ApiOperation(value="删除，包括批量和单个删除,数组一个即为单个删除")
	@RequestMapping("delete")
	public Object delete(@RequestBody Map<String, String> map){
		if(taskService.delete(map.get("ids"))==1){
			return success();
		}
		return failure();
	}
	/**
	 * 修改任务是否启用状态
	 * {"id":1,"taskStatus":"0"}
	 * @param id
	 * @param taskStatus
	 * @return
	 */
	@ApiOperation(value="修改任务是否启用状态")
	@RequestMapping("updateTaskStatus")
	public Object updateTaskStatus(@RequestBody Map<String, Object> map){
		if(taskService.updateTaskStatus(map)==1){
			return success();
		}
		return failure();
	}
	/**
	 * 查询
	 * @param task
	 * @return
	 */
	@ApiOperation(value="查询")
	@RequestMapping("find")
	public Object find(@RequestBody Task task){
		PageHelper.startPage(task.getPager().getPageNum(), task.getPager().getPageSize());
		List<Map<String, Object>> datamap = taskService.findHaveChildren(task) ;
		if(datamap != null){
			return success(new PageInfo<Map<String, Object>>(datamap));
		}
		return failure();
	}

	/**
	 * 手动执行并生成任务
	 * 注意要根据任务的id找到任务的执行频率然后在生成对应的可执行任务taskexecute
	 * @param manualTask
	 * @return
	 */
	@ApiOperation(value="手动执行并生成任务")
	@RequestMapping("manualTask")
	public Object manualTask(@RequestBody ManualTask manualTask){
		if(taskService.manualTask(manualTask)==1){
			return success();
		}
		return failure();
	}
	/**
	 * 找出id的主任务，且parentid的子任务
	 * {"id":1}
	 * @param id
	 * @return
	 */
	@ApiOperation(value="点击更新")
	@RequestMapping("findAllTask")
	public Object findAllTask(@RequestBody Map<String, Integer> map){
		List<Task> data = taskService.findAllTask(map.get("id"));
		if(data != null){
			return success(data, null);
		}
		return failure();
	}
	/**
	 * 先删除后添加
	 * 删除父任务，及其子任务 
	 * 之后根据传递过来的父任务重新生成父任务和子任务
	 * 并保存新任务
	 * @param tasks
	 * @return
	 */
	@ApiOperation(value="保存更新")
	@RequestMapping("update")
	public Object update(@RequestBody Task task){
		if(taskService.update_delete(task)==1){
			return success();
		}
		return failure();
	}
	/**
	 * 查找时间字段，供增量配置
	 * 根据任务Id查找数据源表的时间类型字段
	 * {"id":1}
	 * @param id
	 * @return
	 */
	@ApiOperation(value="查找时间字段，供增量配置")
	@RequestMapping("findDateOperation")
	public Object findDateOperation(@RequestBody Map<String, Integer> map){
		List<String> data = taskService.findDateOperation(map.get("id"));
		if(data != null){
			return success(data, null);
		}
		return failure();
	}
	/**
	 * 保存增量配置
	 * 根据配置信息拼sql保存在where字段里？
	 * @param allconfig
	 * @return
	 */
	@ApiOperation(value="保存增量配置")
	@RequestMapping("updateWhere")
	public Object updateWhere(@RequestBody Allconfig allconfig){
		if(taskService.updateWhere(allconfig) == 1){
			return success();
		}
		return failure();
	}
	/**
	 * 获取增量配置信息
	 * {"taskid":1}
	 * @param taskid
	 * @return Allconfig
	 */
	@ApiOperation(value="获取增量配置信息")
	@RequestMapping("getWhereConf")
	public Object getWhereConf(@RequestBody Map<String, Integer> map){
		Allconfig t = taskService.getWhereConf(map.get("taskid"));
		if(t != null){
			return success(t);
		}
		return failure();
	}
	/**
	 * 原父任务已选择的表
	 * {"id":111}
	 * @param map
	 * @return
	 */
	@ApiOperation(value="查询原父任务已选择的表")
	@RequestMapping("choosedTables")
	public Object choosedTables(@RequestBody Map<String, Integer> map){
		List<String> data = taskService.choosedTables(map.get("id"));
		if(data != null){
			return success(data, null);
		}
		return failure();
	}
	/**
	 * 创建任务数据预览
	 * @param map
	 * @return
	 */
	@ApiOperation(value="预览数据", notes="预览数据")
	@RequestMapping(value="preview")
	public Object preview(@RequestBody  Map<String, Object> map){
		List<Map<String, Object>> mapdata = taskService.preview(map);
		if(mapdata != null){
			return success(mapdata);
		}
		return failure();
	}
	/**
	 * 给其他服务调用
	 * @param task
	 * @return
	 */
	@ApiOperation(value="给其他服务调用,加载到调度器")
	@RequestMapping("loadTaskToSchedule")
	public Object loadTaskToSchedule(@RequestBody Task task){
		taskConfService.loadTask(task);
		return success();
	}
	
	/**
	 * 给其他服务调用 删除调度器里的任务
	 * {"taskids":"1,2,3"}
	 * @param task
	 * @return
	 */
	@ApiOperation(value="给其他服务调用,删除调度器里的任务")
	@RequestMapping("delTaskForModel")
	public Object delTaskForModel(@RequestBody Map<String, String> map){
		logger.debug("外部服务接口 删除调度器里的任务 需要删除的任务map:"+map.toString());
		String taskIds = map.get("taskids");
		if(taskIds == null || taskIds ==""){
			throw new RuntimeException(" *-*-*-*-taskIds 为空-*-*-*-*-*");
		}
		String[] ids = taskIds.split(",");
		for(String id:ids){
			taskConfService.delTask(Integer.parseInt(id));
		}
		return success();
	}
	
}
