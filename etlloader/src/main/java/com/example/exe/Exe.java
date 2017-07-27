package com.example.exe;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

import com.example.dto.datasource.DataSource;
import com.example.exe.run.TaskRun;
import com.example.utils.DbHelper;
import com.example.utils.ExecUtil;
import com.example.utils.LocalDbUtil;
import com.example.utils.PropertiesUtil;

/**
 * 执行任务
 * 
 * @author yingjie.chen
 *
 */
public class Exe {

	private static final Logger logger = LoggerFactory.getLogger(Exe.class);

	static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	/**
	 * 当任务执行太多来不及执行，默认先放入workQueue里面 不写最大值  无穷大
	 */
	static BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<Runnable>();

	/**
	 * corePoolSize - 池中所保存的线程数，包括空闲线程。 maximumPoolSize - 池中允许的最大线程数。
	 * keepAliveTime - 当线程数大于核心时，此为终止前多余的空闲线程等待新任务的最长时间。 unit - keepAliveTime
	 * 参数的时间单位。 workQueue - 执行前用于保持任务的队列。此队列仅保持由 execute 方法提交的 Runnable 任务。
	 */
	static ThreadPoolExecutor pool = new ThreadPoolExecutor(10, 15, 1, TimeUnit.SECONDS, workQueue);

	public static void main(String[] args) {
		while (true) {
			try {
				JdbcTemplate conn = DbHelper.getJdbcTemplate(getLocalDataSource());
				List<Map<String, Object>> executetasks = updateTaskStatus(conn); // 更新任务状态
				ifTimeOut(conn); // 是否超时
				dealFaultTask(conn); // 处理失败的任务
//				List<Map<String, Object>> executetasks = LocalDbUtil.getExecuteTasks(conn);
				for (Map<String, Object> map : executetasks) {
					try {
						Map<String, Object> simpleTask = LocalDbUtil.getTaskById(conn, map.get("taskid"));
						TaskRun task = new TaskRun(map, conn, simpleTask);
						pool.submit(task);
//						Future<?> future = pool.submit(task);
//						try {
//							future.get(Integer.parseInt(simpleTask.get("outofretrytime").toString()), TimeUnit.MINUTES);
//						} catch (TimeoutException e) {
//							logger.warn("ID:" + map.get("id") + "日期" + map.get("createdate") + "任务超时");
//							LocalDbUtil.updateTimeout(conn, map.get("id"));
//						}
					} catch (Exception e) {
						logger.error("taskid为:"+map.get("taskid")+"的任务出错    "+e.getMessage());
						ExecUtil.clearMap(map);
						map.put("completedate", ExecUtil.sqlDate("ORACLE"));
						map.put("exestatus", 3);
						ExecUtil.updateExecuteTask(map, conn);
						map.put("exelog", e);
						ExecUtil.execPlsql(conn, map);
						e.printStackTrace();
					}
				}
			} catch (Exception e) {
				logger.error(e.getMessage());
				e.printStackTrace();
			}
			try {
				Thread.sleep(1000 * 60 * 2);
			} catch (InterruptedException e) {
				logger.error(e.getMessage());
				e.printStackTrace();
			}
		}
	}

	static DataSource getLocalDataSource() {
		DataSource source = new DataSource();
		source.setClassname(PropertiesUtil.properties.getProperty("spring.datasource.driver-class-name"));
		source.setUrl(PropertiesUtil.properties.getProperty("spring.datasource.url"));
		source.setUsername(PropertiesUtil.properties.getProperty("spring.datasource.username"));
		source.setPassword(PropertiesUtil.properties.getProperty("spring.datasource.password"));
		return source;
	}

	/**
	 * 更新任务状态
	 * 
	 * @param jdbcTemplate
	 */
	public static List<Map<String, Object>> updateTaskStatus(JdbcTemplate jdbcTemplate) {
		List<Map<String, Object>> executeTasks = new ArrayList<Map<String,Object>>();
		List<Map<String, Object>> inittasks = LocalDbUtil.getInitTasks(jdbcTemplate);
		logger.debug("准备处理初始化的任务："+inittasks.toString());
		for (Map<String, Object> map : inittasks) {
			/**
			 * 检查任务是否可以就绪 checkTask() 包括： 1. 判断是否是自依赖任务 是否现在可以执行 2. 判断是否依赖其他任务
			 * 是否现在可以执行 更新任务状态为 就绪状态 0
			 */

			if (checkTask(map, jdbcTemplate)) {
				LocalDbUtil.updateReady(jdbcTemplate, map.get("ID"));
				executeTasks.add(map);
			}
		}
		return executeTasks;
	}

	/**
	 * 处理失败的任务
	 * 
	 * @param jdbcTemplate
	 */
	public static void dealFaultTask(JdbcTemplate jdbcTemplate) {
		/**
		 * 处理失败的任务是否需要重试等。。。
		 */
	}

	/**
	 * 判断是否超时
	 * 
	 * @param jdbcTemplate
	 */
	public static void ifTimeOut(JdbcTemplate conn) {
		List<Map<String, Object>> runTasks = LocalDbUtil.getRunTasks(conn);
		for(Map<String, Object> runtask:runTasks){
			Date exedate = (Date)runtask.get("exedate");
			Map<String, Object> task = LocalDbUtil.getTaskById(conn, runtask.get("TASKID"));
			long timeInterval = (new Date().getTime()-exedate.getTime())/(1000 * 60);
			if(task.get("OUTOFRETRYTIME") != null && Long.parseLong(task.get("OUTOFRETRYTIME").toString()) != 0){
				if(timeInterval > Long.parseLong(task.get("OUTOFRETRYTIME").toString())){
					//更新任务状态为 4 超时
					LocalDbUtil.updateTimeout(conn, runtask.get("id"));
				}
			}
		}
	}

	/**
	 * 判断当前任务的依赖是否完成(包含依赖和自依赖)
	 * 
	 * @param taskId
	 * @param creatrDate
	 * @return
	 */
	public static boolean checkTask(Map<String, Object> map, JdbcTemplate conn) {
		Object taskid = map.get("taskid");
		Object createdate = map.get("createdate");
		//获取依赖
		List<Map<String, Object>> depends = LocalDbUtil.getDepend(conn, taskid);
		if(depends == null || depends.size() == 0){
			return true;
		}
		boolean result = false;
		Date taskCreatTime = null;
		try {
			taskCreatTime = simpleDateFormat.parse(createdate.toString());
			Calendar cal = Calendar.getInstance();
			cal.setTime(taskCreatTime);
			/**
			 * 所有依赖和自依赖均完成返回true
			 */
			for(Map<String, Object> d : depends){
				logger.debug("为id是:"+map.get("ID")+" 查找依赖taskid为："+ d.get("ods_id"));
				cal.add(Calendar.DATE, Integer.parseInt(d.get("left").toString()));
				String leftdate = simpleDateFormat.format(cal.getTime());
				cal.setTime(taskCreatTime);
				cal.add(Calendar.DATE, Integer.parseInt(d.get("right").toString()));
				String rightdate = simpleDateFormat.format(cal.getTime());
				if(!LocalDbUtil.isExecute(conn, d.get("ods_id"), leftdate, rightdate)){
					logger.debug("可执行id:"+map.get("ID")+" 依赖taskid："+ d.get("ods_id") + "不符合条件");
					result = false;
					break;
				}else{
					result = true;
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return result;
	}
}
