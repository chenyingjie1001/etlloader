package com.example.dto.datasource;

import java.io.Serializable;

import com.example.dto.base.Pager;

import lombok.Data;

/**
 * pojo
 * @author yingjie.chen
 *
 */
@Data
public class DataSource implements Serializable{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -3990371091471035325L;
	
	private Integer id;
	private String sourcename;
	private String method;
	private String ip;
	private String port;
	private String dbname;
	private String schema;
	private String username;
	private String password;
	private String classname;
	private String url;
	private String testsql;
	private String type;
	private String istarget; //是否目标库 1是
	private Pager pager = new Pager();
}
