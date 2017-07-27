package com.example.utils;

import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;

import com.example.dto.datasource.DataSource;
import com.example.dto.datasource.UserQuery;

/**
 * 
 * @author yingjie.chen
 *
 */
public interface DbConnector {

	/**
	 * 
	 * @param dataSource
	 * @return
	 */
	JdbcTemplate getConnection(DataSource dataSource);
	/**
	 * 
	 * @param connection
	 * @param testSql
	 * @return
	 */
	boolean testConnection(JdbcTemplate connection, String testSql);
	/**
	 * 
	 * @param connection
	 * @return
	 */
	List<String> listTables(JdbcTemplate connection);
	/**
	 * 
	 * @param connection
	 * @param userQuery
	 * @return
	 */
	List<Map<String, Object>> preview(JdbcTemplate connection, UserQuery userQuery);
}
