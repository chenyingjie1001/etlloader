package com.example.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

import com.example.dto.datasource.DataSource;

public class DBUtil {
	
	private static final Logger logger = LoggerFactory.getLogger(DBUtil.class);
	
	//查询字段及其类型
	static String sql_FindColumnType_oracle = "select COLUMN_NAME as COLUMN_NAME,DATA_TYPE as DATA_TYPE,"
			+ "DATA_LENGTH as DATA_LENGTH,"
			+ "DATA_PRECISION as  DATA_PRECISION,"
			+ "DATA_SCALE as DATA_SCALE"
			+ " from user_tab_columns where table_name = upper('${tableName}')";
	
	//查询列
	static String sql_FindColumn_oracle="select COLUMN_NAME as COLUMN_NAME from user_tab_columns where table_name = upper('${tableName}')";
	
	//查询表是否存在
	static String sql_FindTable_oracle="select * from user_tables where table_name = upper('${tableName}')";
	//
	public static String findCreateTableSQL(JdbcTemplate jdbcTemplate,String tableName){
		return null;
	}
	
	public static void exeCreateTableSQL(String exeSql){
		
	}
	/**
	 * 创建表
	 * 通过拼接(Oracle)
	 * @param datasource
	 * @param DStableName
	 * @param targetsource
	 * @param TStableName
	 */
	public static int createTableBycol(DataSource datasource,String DStableName,DataSource targetsource,String TStableName){
		DStableName = DStableName.replaceAll("\\s*", "");
		TStableName = TStableName.replaceAll("\\s*", "");
		//数据库为Oracle
		StringBuffer CreatetableSQL = new StringBuffer();
		CreatetableSQL.append("create table "+TStableName + " (");
		JdbcTemplate connDatasource = DbHelper.getJdbcTemplate(datasource);
		JdbcTemplate connTargetsource = DbHelper.getJdbcTemplate(targetsource);
		//判断表是否存在
		String sqlexist=(sqlFindTable(targetsource.getType().toUpperCase())).replace("${tableName}", TStableName);
		List<Map<String, Object>> isexist = connTargetsource.queryForList(sqlexist);
		if(isexist.size() != 0){
			logger.error("-----在id为:"+targetsource.getId()+" 的数据库  “"+TStableName+"” 表已经存在---------");
			return 1;
		}
		String sql = (sqlFindCreatetable(datasource.getType().toUpperCase())).replace("${tableName}", DStableName);
		List<Map<String, Object>> list_col = connDatasource.queryForList(sql);
		for(int i=0 ;i<list_col.size();i++){
			Map<String, Object> col=list_col.get(i);
			CreatetableSQL.append(" ").append((String)col.get("COLUMN_NAME")).append(" ");
			if(((String)col.get("DATA_TYPE")).compareTo("VARCHAR2")==0||
					((String)col.get("DATA_TYPE")).compareTo("CHAR")==0){
				CreatetableSQL.append((String)col.get("DATA_TYPE")).append("(").append(col.get("DATA_LENGTH")).append(")");
			}else if(((String)col.get("DATA_TYPE")).compareTo("NUMBER")==0){
				CreatetableSQL.append("NUMBER");
				if(col.get("DATA_PRECISION")!=null){
					CreatetableSQL.append("(").append(col.get("DATA_PRECISION"));
					if(("0".compareTo((col.get("DATA_SCALE")).toString()))==0){
						CreatetableSQL.append(")");
					}else{
						CreatetableSQL.append(",").append(col.get("DATA_SCALE")).append(")");
					}
				}
			}else{
				CreatetableSQL.append((String)col.get("DATA_TYPE"));
			}
			if(i!=list_col.size()-1){
				CreatetableSQL.append(",");
			}
		}
		CreatetableSQL.append(")");
		try {
			connTargetsource.execute(CreatetableSQL.toString());//执行建语句
		} catch (Exception e) {
			logger.error("-----在id为:"+targetsource.getId()+" 的数据库创建  “"+TStableName+"” 表失败-----------------");
			logger.error(e.toString());
			e.printStackTrace();
			return 2;
		}
		return 0;
	}
	
	/**
	 * 通过查询源表建表语句建立新表
	 * @param datasource
	 * @param DStableName
	 * @param targetsource
	 * @param TStableName
	 */
	public static void createTableBysql(DataSource datasource,String DStableName,DataSource targetsource,String TStableName){
		JdbcTemplate connDatasource = DbHelper.getJdbcTemplate(datasource);
		JdbcTemplate connTargetsource = DbHelper.getJdbcTemplate(targetsource);
		String sql = (sqlFindCreatetable(datasource.getType().toUpperCase())).replace("${tableName}", DStableName);
		List<Map<String, Object>> list_col = connDatasource.queryForList(sql);
			connTargetsource.execute("");//执行建语句
	}
	
	/**
	 * 获取源表所有列
	 * @param datasource
	 * @param DStableName
	 * @return
	 */
	public static String getColumn(DataSource datasource,String DStableName){
		String columnName = "";
		try {
			JdbcTemplate connDatasource = DbHelper.getJdbcTemplate(datasource);
			List<Map<String, Object>> list_col = connDatasource.queryForList((sqlFindColumn(datasource.getType())).replace("${tableName}", DStableName));
			
			for(int i=0 ;i<list_col.size();i++){
				Map<String, Object> col=list_col.get(i);
				columnName = columnName + col.get("COLUMN_NAME");
				if(i!=list_col.size()-1){
					columnName = columnName + ",";
				}
			}
			return columnName;
		
		} catch (Exception e) {
			logger.error("获取DS："+datasource+" 表："+DStableName+"列出错------"+e.toString());
		}
		return columnName;
	}
	/**
	 * 根据不同数据库返回不同SQL(查询列及类型)
	 * 
	 * @param dbType
	 * @return String
	 */
	private static String sqlFindCreatetable(String dbType) {
		switch (dbType.toUpperCase()) {
		case "ORACLE":
			return sql_FindColumnType_oracle;
		case "MYSQL":
			return null;
		case "POSTGRES":
			return null;
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
	 * 根据不同数据库返回不同SQL(查询列)
	 * 
	 * @param dbType
	 * @return String
	 */
	private static String sqlFindColumn(String dbType) {
		switch (dbType.toUpperCase()) {
		case "ORACLE":
			return sql_FindColumn_oracle;
		case "MYSQL":
			return null;
		case "POSTGRES":
			return null;
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
	 * 根据不同数据库返回不同SQL(查询表是否存在)
	 * 
	 * @param dbType
	 * @return String
	 */
	private static String sqlFindTable(String dbType) {
		switch (dbType.toUpperCase()) {
		case "ORACLE":
			return sql_FindTable_oracle;
		case "MYSQL":
			return null;
		case "POSTGRES":
			return null;
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
	
	
	public static DataSource getOdsOrModel(int id){
		DataSource targetsource = new DataSource();
		if(id == 1){
			targetsource.setClassname(PropertiesUtil.properties.getProperty("ods.datasource.driver-class-name"));
			targetsource.setUrl(PropertiesUtil.properties.getProperty("ods.datasource.url"));
			targetsource.setUsername(PropertiesUtil.properties.getProperty("ods.datasource.username"));
			targetsource.setPassword(PropertiesUtil.properties.getProperty("ods.datasource.password"));
			targetsource.setType("oracle");
		}else if(id == 81){
			targetsource.setType("oracle");
			targetsource.setClassname(PropertiesUtil.properties.getProperty("model.datasource.driver-class-name"));
			targetsource.setUrl(PropertiesUtil.properties.getProperty("model.datasource.url"));
			targetsource.setUsername(PropertiesUtil.properties.getProperty("model.datasource.username"));
			targetsource.setPassword(PropertiesUtil.properties.getProperty("model.datasource.password"));
		}
		return targetsource;
	}
	
	public static Map<String, Object> getOdsOrModelMap(Object id){
		Map<String, Object> map = null;
		if(Integer.parseInt(id.toString()) == 1){
			map = new HashMap<String, Object>();
			map.put("url", PropertiesUtil.properties.get("ods.datasource.url"));
			map.put("username", PropertiesUtil.properties.get("ods.datasource.username"));
			map.put("password", PropertiesUtil.properties.get("ods.datasource.password"));
			map.put("type", "oracle");
		}else if(Integer.parseInt(id.toString()) == 81){
			map = new HashMap<String, Object>();
			map.put("url", PropertiesUtil.properties.get("model.datasource.url"));
			map.put("username", PropertiesUtil.properties.get("model.datasource.username"));
			map.put("password", PropertiesUtil.properties.get("model.datasource.password"));
			map.put("type", "oracle");
		}
		return map;
	}
}
