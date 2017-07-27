package com.example.controller.previews;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.controller.base.BaseController;
import com.example.dto.previews.Relationship;
import com.example.services.previews.IPreviewService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Api(description="ods预览数据")
@RestController 
@RequestMapping(value="/preview/", method=RequestMethod.POST)
public class PreviewController extends BaseController{
	

	@Autowired
	private IPreviewService service;
	
	@ApiOperation(value="查询", notes="查询所有表")
	@RequestMapping(value="find")
	public Object find(@RequestBody Relationship tempt){
		PageHelper.startPage(tempt.getPager().getPageNum(), tempt.getPager().getPageSize());
		List<Relationship> listdata = service.find(tempt);
		if(listdata != null){
			return success(new PageInfo<Relationship>(listdata));
		}
		return failure();
	}
	
	
	@ApiOperation(value="预览数据", notes="预览数据")
	@RequestMapping(value="preview")
	public Object preview(@RequestBody  Relationship tempt){
		List<Map<String, Object>> listdata = service.preview(tempt);
		if(listdata != null){
			return success(listdata);
		}
		return failure();
	}
}
