package com.example.dto.datasource;

import java.io.Serializable;

import lombok.Data;

/**
 * pojo
 * @author yingjie.chen
 *
 */
@Data
public class UserQuery implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4858323502241192095L;
	private DataSource dataSource;
	private String tablename;
}
