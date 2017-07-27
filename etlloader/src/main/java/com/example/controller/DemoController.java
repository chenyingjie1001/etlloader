package com.example.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.dto.Area;
import com.example.services.DemoService;

@Api(description="测试用列")
@RestController 
@RequestMapping(value="/demo/", method=RequestMethod.POST)
public class DemoController {

	@Autowired
	private DemoService service;
	
	
	@Autowired
	private HttpServletRequest request;
	
	/**
	 * @ApiParam会把传参type改成application/json形式
	 * 某种情况下必须和@requestBody合用
	 * @param ip
	 * @return
	 */
	@ApiOperation(value="测试接口", notes="测试接口详细描述1")
	@RequestMapping(value="getAreas")
	public List<Area> getAreas(@ApiParam @RequestBody String ip){
		System.out.println(ip);
		return service.getAreas();
	}
	
	@ApiOperation(value="测试接口", notes="测试接口详细描述2")
	@GetMapping("setSession")
	public Object setSession(@RequestBody Map<String, String> user){
		request.getSession().setAttribute("user", user);
		return "success";
    }
	
	@ApiOperation(value="测试接口", notes="测试接口详细描述3")
	@GetMapping("getObject")
	public Object getObject(HttpServletRequest request){
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("SessionId", request.getSession().getId());
        map.put("user", request.getSession().getAttribute("user"));
		return map;
	}
}
