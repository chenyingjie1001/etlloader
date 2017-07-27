package com.example.exe.run;

import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import com.example.utils.ExecUtil;

/**
 * 
 * @author yingjie.chen
 *
 */
public class ThreadMsg {

	private static final Logger logger = LoggerFactory
			.getLogger(ThreadMsg.class);

	private Process process;

	private Map<String, Object> m;

	private JdbcTemplate conn;

	public ThreadMsg(Process process, Map<String, Object> m, JdbcTemplate conn) {
		this.process = process;
		this.m = m;
		this.conn = conn;
	}

	public void run() {
		String returnmsg = "";
		try {
			logger.debug("*-*-*-*-*id为" + m.get("id") + "任务 正在被监控*-*-*-*-*-*-*");
			//String errorMsg = ExecUtil.getMsg(process, true);
			String inputMsg = ExecUtil.getMsg(process, false);
			logger.debug("*-*-*-*-*id为" + m.get("id")
					+ "任务 正在执行. wait...*-*-*-*-*-*-*");
			Integer exitValue = process.waitFor();
			// exitValue = process.exitValue();

			returnmsg = inputMsg.replace("'", "''");
			if (exitValue != null && exitValue == 0) {
				// 执行成功
				logger.debug("*-*-*-*-*id为" + m.get("id")
						+ "任务 执行成功*-*-*-*-*-*-*");
				m.put("exestatus", 2);
			} else {
				// 执行失败
				logger.debug("*-*-*-*-*id为" + m.get("id")
						+ "任务 执行失败*-*-*-*-*-*-*");
				m.put("exestatus", 3);
				/*if (!"".equals(errorMsg)) {
					returnmsg = errorMsg.replace("'", "''");
				}*/
			}
		} catch (Exception e) {
			logger.error("*-*-*-*-*-*-*id为" + m.get("id") + "任务出错*-*-*-*-*-*-*");
			logger.error("libzone.cn exception:", e);
			m.put("exestatus", 3);
			returnmsg = e.getMessage();
			e.printStackTrace();
		} finally {
			// 更新状态
			m.put("completedate", ExecUtil.sqlDate("ORACLE"));
			ExecUtil.updateExecuteTask(m, conn);
			m.put("exelog", returnmsg);
			ExecUtil.execPlsql(conn, m);
			logger.debug("*-*-*-*-*id为" + m.get("id") + "任务 结束*-*-*-*-*-*-*");
		}
	}
}
