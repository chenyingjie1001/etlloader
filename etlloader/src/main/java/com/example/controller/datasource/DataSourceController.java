package com.example.controller.datasource;

import java.util.List;
import java.util.Map;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.example.controller.base.BaseController;
import com.example.dto.datasource.DataSource;
import com.example.services.datasource.IDateSourceService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * datasource
 * @author yingjie.chen
 * @date:2017年6月7日 16:30:52
 * @version 1.0
 */
@Api(description="数据源接口服务")
@RestController
@RequestMapping(value="/datasource/", method=RequestMethod.POST)
public class DataSourceController extends BaseController{

	@Autowired
	private IDateSourceService service;
	/**
	 * 数据源添加
	 * @param dataSource
	 * @return
	 */
	@ApiOperation(value="添加", notes="数据源添加")
	@RequestMapping("add")
	public Object add(@ApiParam(value="数据源对象") @RequestBody DataSource dataSource){
		if(service.add(dataSource)==1){
			return success();
		}
		return failure();
	}
	/**
	 * 
	 * @param dataSource
	 * @return
	 */
	@ApiOperation(value="修改", notes="数据源修改")
	@RequestMapping("update")
	public Object update(@ApiParam(value="数据源对象") @RequestBody DataSource dataSource){
		if(service.update(dataSource)==1){
			return success();
		}
		return failure();
	}
	/**
	 * {"ids":"1,2,3"}
	 * @param id
	 * @return
	 */
	@ApiOperation(value="删除", notes="数据源删除")
	@RequestMapping("delete")
	public Object delete(@RequestBody Map<String,String> ids){
		if(service.delete(ids.get("ids"))==1){
			return success();
		}
		return failure();
	}
	/**
	 * 
	 * @param dataSource
	 * @return
	 */
	@ApiOperation(value="查询", notes="数据源查询")
	@RequestMapping("find")
	public Object find(@ApiParam(value="数据源对象") @RequestBody DataSource dataSource){
		PageHelper.startPage(dataSource.getPager().getPageNum(), dataSource.getPager().getPageSize());
		List<DataSource> data = service.find(dataSource);
		if(data != null){
			return success(new PageInfo<DataSource>(data));
		}
		return failure();
	}
	/**
	 * 
	 * @param dataSource
	 * @return
	 */
	@ApiOperation(value="连接测试", notes="数据源连接测试")
	@RequestMapping("test")
	public Object test(@ApiParam(value="数据源对象") @RequestBody DataSource dataSource){
		if(service.test(dataSource)){
			return success();
		}
		return failure();
	}
	/**
	 * {"id":1}
	 * @param dataSource
	 * @return
	 */
	@ApiOperation(value="获取一个数据源", notes="通过Id获取数据源信息")
	@RequestMapping("getDataSourceById")
	public Object getDataSourceById(@ApiParam(value="数据源对象") @RequestBody Map<String, Integer> map){
		DataSource data=service.findById(map.get("id"));
		if(data != null){
			return success(data);
		}
		return failure();
	}
}
