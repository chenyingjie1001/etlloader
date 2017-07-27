package com.example.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;




import org.springframework.jdbc.core.JdbcTemplate;



public class LocalDbUtil {

	// 取可执行任务SQL
	static String sql_executetask = "select * from executetask t where t.exestatus = 0 ";

	//根据taskid取到任务明细
	static String sql_gettaskbyid = "select * from task t where t.id = ?";
	
	//根据sourceid的数据源明细
	static String sql_getdatasourcebyid = "select * from datasource t where t.id = ?";
	
	//查找初始化任务
	static String sql_Inittask = "select * from executetask t where t.exestatus = -1 ";
	
	//设置超时状态，为了不影响任务继续执行，超时状态单独列出来
	static String sql_timeout = "update executetask t set t.exestatus = 4 where id = ?";
	
	//更新任务为就绪状态
	static String sql_ready = "update executetask t set t.exestatus = 0 where id = ?";
	
	//查找任务的依赖关系
	static String sql_depend = "select * from dp.model_ods where model_id = ?";
	
	//查找任务是否执行
	static String sql_isexecute = "select * from etl.executetask where exestatus = 2 and taskid = ? and createdate between to_date(?, 'yyyy-MM-dd hh24:mi:ss') and to_date(?, 'yyyy-MM-dd hh24:mi:ss')";
	
	//删除表
	static String sql_dropTable = "drop table ${tableName} ";
	
	//查询正在执行的任务
	static String sql_getRunTasks="select * from executetask t where t.exestatus = 1";
			
	public static List<Map<String, Object>> getExecuteTasks(JdbcTemplate conn) {
		return conn.queryForList(sql_executetask);
	}
	
	public static Map<String, Object> getTaskById(JdbcTemplate conn, Object id){
		return conn.queryForMap(sql_gettaskbyid, id);
	}
	//目标库写死
	public static Map<String, Object> getDataSourceByid(JdbcTemplate conn, Object id){
		Map<String, Object> map = DBUtil.getOdsOrModelMap(id);
		if(map == null){
			map = conn.queryForMap(sql_getdatasourcebyid, id);
		}
		return map;
	}
	
	public static List<Map<String, Object>> getInitTasks(JdbcTemplate conn) {
		return conn.queryForList(sql_Inittask);
	}

	
	public static void updateTimeout(JdbcTemplate conn, Object id){
		conn.update(sql_timeout, id);
	}
	
	public static void updateReady(JdbcTemplate conn, Object id){
		conn.update(sql_ready, id);
	}
	
	public static List<Map<String, Object>> getDepend(JdbcTemplate conn, Object taskid){
		return conn.queryForList(sql_depend, taskid);
	}
	
	public static boolean isExecute(JdbcTemplate conn, Object id, String leftdate, String rightdate){
		if(conn.queryForList(sql_isexecute, id, leftdate, rightdate).size() > 0){
			return true ;
		}
		return false;
	}
	
	public static void dropTable(JdbcTemplate conn, String tableName){
		conn.execute(sql_dropTable.replace("${tableName}",tableName));
	}
	
	public static void exeFun(JdbcTemplate conn, String callFun){
		conn.execute(callFun);
	}
	
	
	public static List<Map<String, Object>> getRunTasks(JdbcTemplate conn){
		return conn.queryForList(sql_getRunTasks);
	}
}
