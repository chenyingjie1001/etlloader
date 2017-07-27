package com.example.controller.execute;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.controller.base.BaseController;
import com.example.dto.execute.ExecuteTask;
import com.example.services.execute.IExecuteTaskService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 
 * @author yingjie.chen
 * @date:2017年6月7日 16:30:52
 * @version 1.0
 */
@Api(description="任务执行情况")
@RestController
@RequestMapping(value="/execute/", method=RequestMethod.POST)
public class ExecuteController extends BaseController{
	
	@Autowired
	private IExecuteTaskService service;
	/**
	 * 
	 * @param executeTask
	 * @return
	 */
	@ApiOperation("任务执行情况查询")
	@RequestMapping("find")
	public Object find(@RequestBody ExecuteTask executeTask){
		PageHelper.startPage(executeTask.getPager().getPageNum(), executeTask.getPager().getPageSize());
		List<ExecuteTask> listdata=service.find(executeTask);
		if(listdata != null){
			return success(new PageInfo<ExecuteTask>(listdata));
		}
		return failure();
	}
	/**
	 * {"taskId":108,"createDate":1483254732000}
	 * 时间戳
	 * @param id
	 * @return
	 */
	@ApiOperation("任务重试")
	@RequestMapping("runTask")
	public Object runTask(@RequestBody Map<String, Object> map){
		if(service.runTask(map)==1){
			return success();
		}
		return failure();
	}
}
