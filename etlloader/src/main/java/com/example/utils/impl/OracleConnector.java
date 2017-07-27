package com.example.utils.impl;

import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.example.dto.datasource.DataSource;
import com.example.dto.datasource.UserQuery;
import com.example.utils.DbConnector;
import com.example.utils.DbHelper;

/**
 * oracleimpl
 * @author yingjie.chen
 *
 */
@Component //TTxx
public class OracleConnector implements DbConnector{

	@Override
	public JdbcTemplate getConnection(DataSource dataSource) {
		return DbHelper.getJdbcTemplate(dataSource);
	}

	@Override
	public boolean testConnection(JdbcTemplate connection, String testSql) {
		return DbHelper.testConnection(connection, testSql);
	}

	@Override
	public List<String> listTables(JdbcTemplate connection) {
		return DbHelper.listTables(connection);
	}

	@Override
	public List<Map<String, Object>> preview(JdbcTemplate connection,
			UserQuery userQuery) {
		return DbHelper.preview(connection,userQuery);
	}

}
