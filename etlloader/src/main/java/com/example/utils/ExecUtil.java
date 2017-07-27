package com.example.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

import com.example.exe.run.ThreadMsg;

public class ExecUtil {

	private static final Logger logger = LoggerFactory.getLogger(ExecUtil.class);
	
	/**
	 * @param str
	 * @param m
	 * @throws IOException 
	 */
	public static void exec(String str, Map<String, Object> m, JdbcTemplate conn) throws IOException{
		logger.debug("*-*-*-*-*id为"+m.get("id")+"任务 马上进入执行器*-*-*-*-*-*-*");
		Process process = Runtime.getRuntime().exec(str);
		new ThreadMsg(process, m, conn).run();
	}
	/**
	 * 返回不会为null
	 * @param process
	 * @param isError
	 * @return
	 */
	public static String getMsg(Process process, boolean isError){
		String wrap = JobUtil.getSystem();
		InputStream in = null ;
		if(isError){
			in = process.getErrorStream();
		}else{
			in = process.getInputStream();
		}
		BufferedReader br = new BufferedReader(new InputStreamReader(in, Charset.forName("UTF-8")));
		String result = "";
		String read = null;
		try {
			while((read = br.readLine()) != null){
				result += read + wrap;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
	/**
	 * DECLARE clobValue executetask.exelog%TYPE; BEGIN clobValue := 'XXX';
	 * --字段内容 UPDATE executetask T SET T.exelog = clobValue WHERE id=49; COMMIT;
	 * END; /
	 * 
	 * @param m
	 */
	public static void execPlsql(JdbcTemplate conn, Map<String, Object> m) {
		System.out.println("------------------开始更新id为"+m.get("id")+"日志-----------------------------");
		String plsql = " DECLARE clobValue executetask.exelog%TYPE; BEGIN clobValue := '"+m.get("exelog")+"'; "
				+ " UPDATE executetask T SET T.exelog = clobValue WHERE id="+m.get("id")+"; COMMIT; "
				+ " END; ";
		conn.execute(plsql);
		clearMap(m);
	}

	/**
	 * oracle的date类型写法
	 * 
	 * @return
	 */
	public static String sqlDate(String dbType) {
		String sysdate = "";
		//oracle
		switch (dbType.toUpperCase()) {
		case "ORACLE":
			sysdate = "sysdate";
			break;
		default:
			sysdate = "sysdate";
			break;
		}
		return sysdate;
	}

	/**
	 * update executetask t set t.taskid = ? t.exedate = ? and t.completedate =
	 * ? and t.exestatus = ? and t.exelog = ? where id = ?
	 * 
	 * @param m
	 * @param conn
	 */
	public static void updateExecuteTask(Map<String, Object> m, JdbcTemplate conn) {
		logger.debug("*-*-*-*-*id为"+m.get("id")+"任务 为更新状态做准备*-*-*-*-*-*-*");
		Object exedate = m.get("exedate");
		Object completedate = m.get("completedate");
		Object exestatus = m.get("exestatus");
		Object id = m.get("id");
		Object retrytime = m.get("retrytime");
		String sql = "update executetask t set t.retrytime = " + retrytime;
		if (exedate != null) {
			sql += " , t.exedate = " + exedate;
		}
		if (completedate != null) {
			sql += " , t.completedate = " + completedate;
		}
		if (exestatus != null) {
			sql += " , t.exestatus = " + exestatus;
		}
		sql += " where id = " + id;
		conn.execute(sql);
		clearMap(m);
	}

	public static void clearMap(Map<String, Object> m) {
		m.put("exedate", null);
		m.put("completedate", null);
		m.put("exestatus", null);
		m.put("exelog", null);
	}

	/**
	 * 组装器
	 * 
	 * @param job
	 * @param reader
	 * @param writer
	 * @param simpleTask
	 * @param simpleDataSource
	 * @return
	 */
	public static String AssembleJson(String job, String reader, String writer,
			Map<String, Object> simpleTask, Map<String, Object> m, Map<String, Object> simpleDataSource, Map<String, Object> simpleDataTarget) {

		String[] columns=(simpleTask.get("CHECKCOLUMNS").toString()).split(",");
		Object columnsFormat="";
		for(int i=0;i<columns.length;i++){
			columnsFormat=columnsFormat+columns[i];
			if(i!=columns.length-1){
				columnsFormat=columnsFormat+"\",\"";
			}
		}
		simpleTask.put("CHECKCOLUMNS", columnsFormat);
		Iterator<Entry<String, Object>> it = simpleDataSource.entrySet()
				.iterator();
		while (it.hasNext()) {
			Entry<String, Object> entity = it.next();
			if (entity.getValue() == null) {
				entity.setValue("");
			}
			reader = reader.replace("$p_source_"
					+ entity.getKey().toLowerCase(), "\""
					+ entity.getValue().toString() + "\"");
		}
		Iterator<Entry<String, Object>> itTarget = simpleDataTarget.entrySet().iterator();
		while(itTarget.hasNext()){
			Entry<String, Object> entity = itTarget.next();
			if (entity.getValue() == null) {
				entity.setValue("");
			}
			writer = writer.replace("$p_target_"
					+ entity.getKey().toLowerCase(), "\""
							+ entity.getValue().toString() + "\"");
		}
		Iterator<Entry<String, Object>> iter = simpleTask.entrySet().iterator();
		while (iter.hasNext()) {
			Entry<String, Object> entity = iter.next();
			if (entity.getValue() == null) {
				entity.setValue("");
			}
			//如果全量 wheresql设为空
			if("1".equals(m.get("isall"))){
				if("wheresql".equals(entity.getKey().toLowerCase())){
					entity.setValue("");
				}
			}
			reader = reader.replace("$p_source_"
					+ entity.getKey().toLowerCase(), "\""
					+ entity.getValue().toString() + "\"");
			writer = writer.replace("$p_target_"
					+ entity.getKey().toLowerCase(), "\""
					+ entity.getValue().toString() + "\"");
		}
		//使数据不重复
		String presql = "delete from " + simpleTask.get("targettable") + " ";
		if(simpleTask.get("wheresql") != null && !"".equals(simpleTask.get("wheresql"))){
			presql += "where "+ simpleTask.get("wheresql");
		}
		if ("1".equals(m.get("isall"))) {
			presql = "truncate table " + simpleTask.get("targettable") ;
		}
		writer = writer.replace("$p_target_presql", "\""+presql+"\"");
		job = job.replace("${reader}", reader).replace("${writer}", writer);
		return job;
	}
	
	public static void main(String[] args) throws Exception {
	}
}
