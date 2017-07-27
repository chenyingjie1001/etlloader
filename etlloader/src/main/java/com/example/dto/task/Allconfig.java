package com.example.dto.task;

import java.io.Serializable;

import lombok.Data;

/**
 * 增量情况配置
 * @author yingjie.chen
 *
 */
public @Data class Allconfig implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1996866135171208637L;

	private int taskid;
	
	private String isall = "0"; //开启增量 即默认不开启为0
	
	private String status; //0增量1表达式
	
	private String fields; //字段 a,b,c
	
	private String expression; //表达式(sql)
}
