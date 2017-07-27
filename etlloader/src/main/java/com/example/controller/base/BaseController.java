package com.example.controller.base;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.security.sasl.AuthenticationException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.dto.base.MyExceptionResponse;
import com.example.dto.base.ResultMessage;
import com.example.utils.WarnException;
import com.github.pagehelper.PageInfo;

/**
 * 可以做一些异常控制，查询等通用方法
 * 
 * @author yingjie.chen
 */

@ControllerAdvice
public class BaseController {

	// @ExceptionHandler(RuntimeException.class)
	// @ExceptionHandler(value={RuntimeException.class,MyRuntimeException.class})
	@ExceptionHandler
	// 处理所有异常
	@ResponseBody
	// 在返回自定义相应类的情况下必须有，这是@ControllerAdvice注解的规定
	public MyExceptionResponse exceptionHandler(Exception e,
			HttpServletRequest request, HttpServletResponse response) {
		MyExceptionResponse resp = new MyExceptionResponse();
		resp.setTimestamp(new Date());
		resp.setMsg(e.getMessage());
		if (e instanceof AuthenticationException) {
			resp.setHttpCode(401);
		} else if (e instanceof WarnException){
			resp.setHttpCode(200);
		} else if (e instanceof RuntimeException) {
			resp.setHttpCode(300);
		} else {
			resp.setHttpCode(500);
		}
		return resp;
	}

	public ResultMessage<Object> success() {
		ResultMessage<Object> resp = new ResultMessage<Object>();
		setSuccess(resp);
		return resp;
	}

	public <T> ResultMessage<T> success(T t) {
		ResultMessage<T> resp = new ResultMessage<T>();
		setSuccess(resp);
		resp.setT(t);
		return resp;
	}

	public <T> ResultMessage<T> success(List<T> data, T t) {
		ResultMessage<T> resp = new ResultMessage<T>();
		setSuccess(resp);
		resp.setData(data);
		return resp;
	}

	public ResultMessage<Object> success(List<Map<String, Object>> mapdata) {
		ResultMessage<Object> resp = new ResultMessage<Object>();
		setSuccess(resp);
		resp.setMapdata(mapdata);
		return resp;
	}

	public ResultMessage<Object> success(Map<String, Object> m) {
		ResultMessage<Object> resp = new ResultMessage<Object>();
		setSuccess(resp);
		resp.setM(m);
		return resp;
	}

	public <T> ResultMessage<T> success(PageInfo<T> pageinfo) {
		ResultMessage<T> resp = new ResultMessage<T>();
		setSuccess(resp);
		resp.setPageinfo(pageinfo);
		return resp;
	}

	public Object failure() {
		throw new RuntimeException("服务器异常");
	}

	public <T> void setSuccess(ResultMessage<T> resp) {
		resp.setHttpCode(200);
		resp.setTimestamp(new Date());
		resp.setMsg("成功");
	}
}
