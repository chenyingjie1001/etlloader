package com.example.exe.run;

import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import com.example.utils.ExecUtil;
import com.example.utils.JobUtil;
import com.example.utils.LocalDbUtil;
import com.example.utils.PropertiesUtil;
import com.example.utils.ReplaceUtils;

/**
 * 
 * @author yingjie.chen
 *
 */
public class TaskRun implements Runnable {
	
	private static final Logger logger = LoggerFactory.getLogger(TaskRun.class);
	
	private Map<String, Object> m;

	private JdbcTemplate conn;
	
	private Map<String, Object> simpleTask;

	public TaskRun(Map<String, Object> m, JdbcTemplate conn, Map<String, Object> simpleTask) {
		this.m = m;
		this.conn = conn;
		this.simpleTask = simpleTask;
	}

	/**
	 * 每个任务的执行器 任务外面有conn连接可以继续用
	 */
	@Override
	public void run() {
		try {
			logger.info("*-*-*-*-*id为"+m.get("id")+"任务开始执行*-*-*-*-*-*-*");
			// 记录该任务执行次数
			int retrytime = 0;
			if (m.get("retrytime") != null) {
				retrytime = Integer.parseInt(m.get("retrytime").toString()) + 1;
			}
			m.put("retrytime", retrytime);
			ExecUtil.clearMap(m);
			String writerJson = "";
			Object taskid = m.get("taskid");
			// 设置执行时间 进行中
			m.put("exedate", ExecUtil.sqlDate("ORACLE"));
			m.put("exestatus", 1);
			// 更新状态
			ExecUtil.updateExecuteTask(m, conn);
			
			//调用存储过程
			String sql=(String)simpleTask.get("SQL");
			if(sql != null && sql != "" && sql.toUpperCase().contains("CALL")){
				logger.debug("*-*-*-*-*id为"+m.get("id")+"为模型任务任务 调用存储过程*-*-*-*-*-*-*");
				sql = ReplaceUtils.replaceDate(m, sql);
				LocalDbUtil.exeFun(conn, sql);
				logger.debug("*-*-*-*-*id为"+m.get("id")+"的存储过程调用成功*-*-*-*-*-*-*");
				m.put("completedate", ExecUtil.sqlDate("ORACLE"));
				m.put("exestatus", 2);
				ExecUtil.updateExecuteTask(m, conn);
				m.put("exelog", "调用存储过程:"+sql.replace("'", "''")+" 成功");
				ExecUtil.execPlsql(conn, m);
				return;
			}
			
			//shell
			String cmd = "";
			if(simpleTask.get("des") != null && simpleTask.get("des").toString().toLowerCase().contains("shell")){
				logger.debug("*-*-*-*-*id为"+m.get("id")+"为shell任务 正在进行任务配置*-*-*-*-*-*-*");
				cmd = "sh " + simpleTask.get("sql");
			}else{
				logger.debug("*-*-*-*-*id为"+m.get("id")+"为传输任务 正在进行任务配置*-*-*-*-*-*-*");
				// datasourceid有值，意味着不是模型任务。因为模型任务是没有datasourceid的
				Map<String, Object> simpleDataSource = LocalDbUtil
						.getDataSourceByid(conn, simpleTask.get("datasourceid"));
				Map<String, Object> simpleDataTarget = LocalDbUtil
						.getDataSourceByid(conn, simpleTask.get("targetsourceid"));
				String job = JobUtil.createJson(simpleDataSource.get("type")
						.toString(), "", null);
				// 判断是否是sql模式
				String reader = JobUtil.createJson(simpleDataSource.get("type")
						.toString(), "reader", simpleTask.get("sql"));
				String writer = JobUtil.createJson(simpleDataSource.get("type")
						.toString(), "writer", null);
				writerJson = ExecUtil.AssembleJson(job, reader, writer, simpleTask, m, 
						simpleDataSource, simpleDataTarget);
				/*********增量任务时间输入******************************/
				writerJson = ReplaceUtils.replaceDate(m, writerJson);
				
				String filename = JobUtil.writeFile(writerJson, taskid, m.get("id"));
				// 执行
				cmd = "python "
						+ PropertiesUtil.properties.getProperty("dir.datax") + " "
						+ PropertiesUtil.properties.getProperty("dir.json")+"/"+ taskid + "/"
						+ filename;
			}
			logger.debug("*-*-*-*-*id为"+m.get("id")+"任务配置完成*-*-*-*-*-*-*");
			ExecUtil.exec(cmd, m, conn);
		} catch (Exception e) {
			logger.error("id为"+m.get("id")+"任务出错");
			logger.error("libzone.cn exception:", e);
			m.put("completedate", ExecUtil.sqlDate("ORACLE"));
			m.put("exestatus", 3);
			ExecUtil.updateExecuteTask(m, conn);
			m.put("exelog", e.getMessage().replace("'", "''"));
			ExecUtil.execPlsql(conn, m);
		}
	}
}
