package com.example.utils;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import com.example.dto.datasource.DataSource;
import com.example.dto.datasource.UserQuery;

public class DbHelper {

	private static final Logger logger = LoggerFactory.getLogger(DbHelper.class);
	
	static Map<String, JdbcTemplate> jdbcMap = new HashMap<String, JdbcTemplate>();

	// 查询用户表SQL
	static String sql_User_tables_oracle = "SELECT TABLE_NAME FROM  User_tables order by TABLE_NAME";

	static String sql_User_tables_mysql = "show tables";

	static String sql_User_tables_pg = null;

	static String sql_User_tables_db2 = null;

	static String sql_User_tables_sqlserver = null;

	// 预览数据SQL
	static String sql_preview = "SELECT * FROM ";

	static String sql_preview_Oracle_limit = " where ROWNUM<=10";

	static String sql_preview_mysqlOrpg_limit = " limit 10";

	// 连接测试SQL
	static String sql_test_Oracle = "select 1 from dual";

	static String sql_test_mysqlOrpg = "select 1";

	/**
	 * 获得数据库连接
	 * 
	 * @param dataSource
	 * @return JdbcTemplate
	 */
	public static JdbcTemplate getJdbcTemplate(DataSource dataSource) {

		String key = StringUtils.join(
				new String[] { dataSource.getClassname(), dataSource.getUrl(),
						dataSource.getUsername(), dataSource.getPassword() },
				"|");
		JdbcTemplate connection = jdbcMap.get(key);
		if (connection != null) {
			return connection;
		} else {
			BasicDataSource basicdatasource = new BasicDataSource();
			basicdatasource.setDriverClassName(dataSource.getClassname());
			basicdatasource.setUrl(dataSource.getUrl());
			basicdatasource.setUsername(dataSource.getUsername());
			basicdatasource.setPassword(dataSource.getPassword());
			basicdatasource.setMaxTotal(3);
			connection = new JdbcTemplate(basicdatasource);
			jdbcMap.put(key, connection);
			return connection;
		}
	}

	/**
	 * 测试数据库连接
	 * 
	 * @param connection
	 * @param testSql
	 * @return boolean
	 */
	public static boolean testConnection(JdbcTemplate connection, String testSql) {
		try {
			connection.execute(testSql(dbType(connection)));
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		return true;
	}

	/**
	 * 查询所有用户表
	 * 
	 * @param connection
	 * @return List<String>
	 */
	public static List<String> listTables(JdbcTemplate connection) {
		List<String> listTable = null;
		try {
			listTable = connection.queryForList(
					sqlForListtables(dbType(connection)), String.class);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		}
		return listTable;
	}

	/**
	 * 预览数据
	 * 
	 * @param connection
	 * @param userQuery
	 * @return List<Map<String, Object>>
	 */
	public static List<Map<String, Object>> preview(JdbcTemplate connection,
			UserQuery userQuery) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		List<Map<String, Object>> list = null;
		try {
			list = connection.queryForList(sql_preview
					+ userQuery.getTablename() + sqlLimit(dbType(connection)));
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("utils preview 出错 值 connection："+connection+" UserQuery:"+userQuery);
			logger.error("libzone.cn exception:", e);
		}
		try {
			//转换时间类型为字符串
			if(list != null && list.size() != 0){
				for(Map<String, Object> map:list){
					for(String key:map.keySet()){
						if (map.get(key) instanceof Timestamp || map.get(key) instanceof Date) {
							String dateString=simpleDateFormat.format(map.get(key));
							map.put(key, dateString);
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("转换时间类型为字符串 utils preview 出错 值 connection："+connection+" UserQuery:"+userQuery);
			logger.error("libzone.cn exception:", e);
		}
		return list;
	}

	/**
	 * 判断数据库类型
	 * 
	 * @param connection
	 * @return String
	 */
	private static String dbType(JdbcTemplate connection) {
		try {
			String driverClassName = ((BasicDataSource) connection
					.getDataSource()).getDriverClassName();
			if (DataBase.ORACLE.getClassname().equals(driverClassName)) {
				return "ORACLE";
			} else if (DataBase.MYSQL.getClassname().equals(driverClassName)) {
				return "MYSQL";
			} else if (DataBase.POSTGRES.getClassname().equals(driverClassName)) {
				return "POSTGRES";
			} else if (DataBase.DB2.getClassname().equals(driverClassName)) {
				return "DB2";
			} else if (DataBase.SQLSERVER.getClassname()
					.equals(driverClassName)) {
				return "SQLSERVER";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "OTHER";
	}

	/**
	 * 根据不同数据库返回不同SQL(所有用户表)
	 * 
	 * @param dbType
	 * @return String
	 */
	private static String sqlForListtables(String dbType) {
		switch (dbType) {
		case "ORACLE":
			return sql_User_tables_oracle;
		case "MYSQL":
			return sql_User_tables_mysql;
		case "POSTGRES":
			return sql_User_tables_pg;
		case "DB2":
			return sql_User_tables_db2;
		case "SQLSERVER":
			return sql_User_tables_sqlserver;
		case "OTHER":
			return null;
		default:
			return null;
		}
	}

	/**
	 * 根据不同数据库返回不同SQL(限定条数)
	 * 
	 * @param dbType
	 * @return String
	 */
	private static String sqlLimit(String dbType) {
		switch (dbType) {
		case "ORACLE":
			return sql_preview_Oracle_limit;
		case "MYSQL":
			return sql_preview_mysqlOrpg_limit;
		case "POSTGRES":
			return sql_preview_mysqlOrpg_limit;
		case "DB2":
			return null;
		case "SQLSERVER":
			return null;
		case "OTHER":
			return null;
		default:
			return null;
		}
	}

	/**
	 * 生成测试Sql
	 * 
	 * @param dbType
	 * @return String
	 */
	private static String testSql(String dbType) {
		switch (dbType) {
		case "ORACLE":
			return sql_test_Oracle;
		case "MYSQL":
			return sql_test_mysqlOrpg;
		case "POSTGRES":
			return sql_test_mysqlOrpg;
		case "DB2":
			return null;
		case "SQLSERVER":
			return null;
		case "OTHER":
			return null;
		default:
			return null;
		}
	}

}
