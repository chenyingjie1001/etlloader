package com.example.services.task.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.dbcp2.BasicDataSource;
import org.quartz.TriggerUtils;
import org.quartz.impl.triggers.CronTriggerImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.dto.base.Table;
import com.example.dto.datasource.DataSource;
import com.example.dto.datasource.UserQuery;
import com.example.dto.execute.ExecuteTask;
import com.example.dto.previews.Relationship;
import com.example.dto.task.ManualTask;
import com.example.dto.task.Allconfig;
import com.example.dto.task.Task;
import com.example.mapper.datasource.DataSourceMapper;
import com.example.mapper.previews.PreviewMapper;
import com.example.mapper.task.TaskMapper;
import com.example.schedule.ITaskBaseConfigService;
import com.example.services.execute.impl.ExecuteTaskService;
import com.example.services.task.ITaskService;
import com.example.utils.BeanUtil;
import com.example.utils.CloneUtil;
import com.example.utils.DBUtil;
import com.example.utils.DataBase;
import com.example.utils.DbConnector;
import com.example.utils.DbHelper;
import com.example.utils.LocalDbUtil;
import com.example.utils.PatchTableName;
import com.example.utils.PropertiesUtil;
import com.example.utils.TestCron;
import com.example.utils.WarnException;

@Service
@Transactional
public class TaskService implements ITaskService {

	private static final Logger logger = LoggerFactory
			.getLogger(TaskService.class);

	// 查询边字段类型SQL
	static String sql_FieldType_oracle = "select COLUMN_NAME as COLUMN_NAME,DATA_TYPE as DATA_TYPE from user_tab_columns where table_name = upper('$tableName')";

	// 数据库时间类型列表
	static String time_Type_Oracle = "DATE,TIMESTAMP,INTERVAL";

	@Autowired
	private TaskMapper mapper;

	@Autowired
	private DataSourceMapper dataSourceMapper;

	@Autowired
	private PreviewMapper previewMapper;

	@Autowired
	private DbConnector dbConnector;

	@Autowired
	private ITaskBaseConfigService service;

	@Autowired
	private ExecuteTaskService executeTaskService;

	@Override
	public List<Task> find(Task t) {
		//logger.info("*-*-*-*-*-*-*-查询任务："+t.toString());
		List<Task> tasks = null;
		try {
			tasks = mapper.find(t);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("find任务出错" + t.toString());
			logger.error("libzone.cn exception:", e);
		}
		return tasks;
	}

	@Override
	public int add(Task t) {
		logger.info("*-*-*-*-*-*-*-添加任务："+t.toString());
		String result = "";
		List<Task> listTask = null;
		String addTableNames = t.getChecktables();
		Set<String> existingTable = new HashSet<>();
		if (!TestCron.test(t.getFrequency())) {
			throw new RuntimeException("--------Cron 表达式异常----------");
		}
		try {
			t.setParentid(0);
			// 判断 如果 是新增任务进行add,如果是编辑任务 进行 update
			if (t.getId() == null || t.getId() == 0) {
				mapper.add(t);
			} else {
				Set<String> newset = new HashSet<>();
				Set<String> oldset = new HashSet<>();
				Set<String> resultSet = new HashSet<>();
				listTask = mapper.findAllTask(t.getId());// 查询原父任务及其所有子任务
				Task oldTask = mapper.findTaskById(t.getId());// 查询原父任务
				String NewChecktables = t.getChecktables();
				String oldChecktables = oldTask.getChecktables();
				String[] newTableNames = NewChecktables.toUpperCase()
						.split(",");
				String[] oldTableNames = oldChecktables.toUpperCase()
						.split(",");
				for (String tableName : newTableNames) {
					newset.add(tableName);
				}
				for (String tableName : oldTableNames) {
					oldset.add(tableName);
				}
				resultSet = BeanUtil.setOperation(newset, oldset);
				addTableNames = resultSet.toString().replaceAll("\\[", "")
						.replaceAll("\\]", "");
				existingTable = BeanUtil.setIntersection(newset, oldset);
				mapper.update(t);
			}
			int id = t.getId();
			DataSource dataSource = mapper.findDSById(t.getDatasourceid());
//			DataSource targetsource = mapper.findDSById(t.getTargetsourceid());
			//目标库写死
			DataSource targetsource = DBUtil.getOdsOrModel(t.getTargetsourceid());
			for (String tName : addTableNames.split(",")) {
				tName = tName.replaceAll("\\s*", "");
				if ("".equals(tName)) {
					continue;
				}
				String targettableNamePostfix = "_" + t.getDatasourceid();
				// String targettableName = tName + targettableNamePostfix;
				// 判断表名是否大于30个字符,大于则截取
				String targettableName = PatchTableName.PatchTName(tName,
						targettableNamePostfix);
				// 建表
				int res = DBUtil.createTableBycol(dataSource, tName,
						targetsource, targettableName);
				if (res == 1) {
					// 说明表已存在 但继续进行一下处理
					result += "表：" + tName + "在数据库:" + targetsource.getId()
							+ "已存在...";
					continue;
				} else if (res == 2) {
					// 说明表不存在 建表异常 终止执行 建表 建下一个
					result += "表：" + tName + "在数据库:" + targetsource.getId()
							+ "创建失败...";
					continue;
				}
				String checkcolumns = DBUtil.getColumn(dataSource, tName);
				Task subtask = CloneUtil.clone(t);
				subtask.setCheckcolumns(checkcolumns);
				subtask.setParentid(id);
				subtask.setChecktables("");
				subtask.setSourcetable(tName);
				subtask.setTargettable(targettableName);
				// subtask.setTargetsourceid(1);
				mapper.add(subtask);
				if ("1".compareTo(subtask.getTaskstatus()) == 0) {
					service.loadTask(subtask);
				}
				// 添加到relationship表 ，表和源库的关系，用于查看
				String sourcename = dataSourceMapper.findById(
						subtask.getDatasourceid()).getSourcename();
				Relationship relatObj = new Relationship();
				// sourcename, subtask.getTargettable(), subtask.getId(),
				// subtask.getParentid(), null
				relatObj.setSourcename(sourcename);
				relatObj.setTablename(subtask.getTargettable());
				relatObj.setTaskid(subtask.getId());
				relatObj.setParentid(subtask.getParentid());
				previewMapper.add(relatObj);
			}
			if ("1".compareTo(t.getTaskstatus()) == 0) {
				for (String tName : existingTable) {
					for (Task task : listTask) {
						if (task.getSourcetable().toUpperCase()
								.equals(tName.toUpperCase())) {
							// 判断是否是父任务 如果不是则加载 201707170930
							if (task.getParentid() != 0) {
								task.setFrequency(t.getFrequency());
								task.setTasktype(t.getTasktype());
								task.setRetry(t.getRetry());
								task.setRetrywaittime(t.getRetrywaittime());
								task.setOutofretrytime(t.getOutofretrytime());
								task.setDes(t.getDes());
								mapper.update(task);
								service.loadTask(task);
								break;
							}
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("添加任务出错" + t.toString());
			logger.error("libzone.cn exception:", e);
			return 0;
		}
		if (result != "") {
			throw new WarnException(result);
		}
		return 1;
	}

	/**
	 * 更新前删除父任务及其子任务 原接口
	 */
	// @Override
	public int update_delete2(Task t) {
		if (!TestCron.test(t.getFrequency())) {
			throw new RuntimeException("--------Cron 表达式异常----------");
		}
		try {
			List<Task> listTask = mapper.findAllTask(t.getId());// 查询父任务及其所有子任务
			mapper.deleteSubtask(t.getId());// 删除子任务
			Relationship obj = new Relationship();
			obj.setParentid(t.getId());
			previewMapper.update_delete(obj);
			mapper.delete(t.getId());// 删除父任务本身
			for (Task task : listTask) {
				if (task.getParentid() != 0) {
					service.delTask(task.getId());// 删除调度器中的任务
					// 获取目标数据源id
					int targetSourceId = task.getTargetsourceid();
					JdbcTemplate ODSJdbcTemplate = DbHelper
							.getJdbcTemplate(DBUtil.getOdsOrModel(targetSourceId));
					LocalDbUtil.dropTable(ODSJdbcTemplate,
							task.getTargettable());// 删除目标库中任务所对应的表
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("删除更新任务出错" + t.toString());
			logger.error("libzone.cn exception:", e);
			return 0;
		}
		add(t);
		return 1;
	}

	/**
	 * 更新前删除父任务及其子任务 新接口
	 */
	@Override
	public int update_delete(Task t) {
		logger.info("*-*-*-*-*-*-更新任务："+t.toString());
		Set<String> newset = new HashSet<>();
		Set<String> oldset = new HashSet<>();
		Set<String> result = new HashSet<>();
		if (!TestCron.test(t.getFrequency())) {
			throw new RuntimeException("--------Cron 表达式异常----------");
		}
		try {
			List<Task> listTask = mapper.findAllTask(t.getId());// 查询父任务及其所有子任务
			Task oldTask = mapper.findTaskById(t.getId());// 查询父任务
			String NewChecktables = t.getChecktables();
			String oldChecktables = oldTask.getChecktables();
			String[] newTableNames = NewChecktables.toUpperCase().split(",");
			String[] oldTableNames = oldChecktables.toUpperCase().split(",");
			for (String tableName : newTableNames) {
				newset.add(tableName);
			}
			for (String tableName : oldTableNames) {
				oldset.add(tableName);
			}
			result = BeanUtil.setOperation(oldset, newset);
			// 删除 编辑后没有选择的表所对应的任务
			for (String dropTname : result) {
				for (Task task : listTask) {
					if (task.getSourcetable().toUpperCase()
							.equals(dropTname.toUpperCase())) {
						// 判断是否是父任务 20170717092200 修改
						if (task.getParentid() != 0) {
							delete(task.getId().toString());
							break;
						}
					}
				}
			}
			add(t);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("删除更新任务出错..." + t.toString());
			logger.error("libzone.cn exception:", e);
		}
		return 1;
	}

	@Override
	public int delete(String ids) {
		logger.info("*-*-*-*-*-*-*-删除任务ids："+ids);
		try {
			String[] ids_strs = ids.split(",");
			for (String id : ids_strs) {
				List<Task> listTask = mapper.findAllTask(Integer.parseInt(id));
				for (Task task : listTask) {
					mapper.delete(task.getId());
					previewMapper.delete(task.getId());// 删除relationship的数据
					if (task.getParentid() != 0) {
						service.delTask(task.getId());// 删除调度器中的任务
						// 获取目标数据源id
						int targetSourceId = task.getTargetsourceid();
						//目标库写死
						JdbcTemplate ODSJdbcTemplate = DbHelper
								.getJdbcTemplate(DBUtil.getOdsOrModel(targetSourceId));
						LocalDbUtil.dropTable(ODSJdbcTemplate,
								task.getTargettable());// 删除目标库中任务所对应的表
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("删除任务出错ids:" + ids);
			logger.error("libzone.cn exception:", e);
			return 0;
		}
		return 1;
	}

	@Override
	public List<DataSource> listDataSources() {
		List<DataSource> datasources = null;
		try {
			datasources = mapper.findAllDS();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("获取数据源列表出错");
			logger.error("libzone.cn exception:", e);
		}
		return datasources;
	}

	@Override
	public List<Table> listTablesByDataSources(int id) {
		List<String> strs = null;
		List<String> strsODS = null;
		List<Table> tables = new ArrayList<Table>();
		Map<String, String> mapTable_id = new HashMap<String, String>();
		try {
			strs = dbConnector.listTables(dbConnector.getConnection(mapper
					.findDSById(id)));
			// 目标库写死
			strsODS = dbConnector.listTables(dbConnector.getConnection(DBUtil.getOdsOrModel(1)));
			for (String str : strs) {
				// 判断表名是否大于30个字符,大于则截取
				String tName = PatchTableName.PatchTName(str, "_" + id);
				mapTable_id.put(tName, str);
			}
			for (String key : mapTable_id.keySet()) {
				Table table = new Table();
				table.setTitle(mapTable_id.get(key));
				table.setExist(false);
				for (String str : strsODS) {
					if (key.equals(str)) {
						table.setExist(true);
						break;
					}
				}
				tables.add(table);
			}
			/*
			 * strs =
			 * dbConnector.listTables(dbConnector.getConnection(mapper.findDSById
			 * (id))); for (String str : strs) { Table t = new Table();
			 * t.setTitle(str); tables.add(t); }
			 */
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("获取listTablesByDataSources出错id" + id);
			logger.error("libzone.cn exception:", e);
		}
		return tables;
	}

	@Override
	public int updateTaskStatus(Map<String, Object> map) {
		logger.info("*-*-*-*-*-*-*-更新任务状态："+map.toString());
		try {
			mapper.updateTaskStatus(map);
			List<Task> listTask = mapper.findAllTask((int) map.get("id"));
			if ("0".compareTo(map.get("taskstatus").toString()) == 0) {
				for (Task task : listTask) {
					if (task.getParentid() != 0) {
						service.delTask(task.getId());
					}
				}
			} else {
				for (Task task : listTask) {
					if (task.getParentid() != 0) {
						service.loadTask(task);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("updateTaskStatus出错map" + map.toString());
			logger.error("libzone.cn exception:", e);
			return 0;
		}
		return 1;
	}

	@Override
	public int manualTask(ManualTask manualTask) {
		logger.info("*-*-*-*-*添加手动执行任务："+manualTask.toString());
		try {
			if ("1".compareTo(manualTask.getIsall()) == 0) {
				// 如是全量抽取则只生成一条执行任务，createdate 为当前时间
				ExecuteTask exetask = new ExecuteTask();
				exetask.setTaskid(manualTask.getId());
				exetask.setCreatedate(new Date());
				exetask.setExestatus(-1);
				exetask.setIsall(manualTask.getIsall());
				executeTaskService.add(exetask);
			} else if ("0".compareTo(manualTask.getIsall()) == 0) {
				// 增量抽取
				CronTriggerImpl cronTriggerImpl = new CronTriggerImpl();
				Task task = mapper.findTaskById(manualTask.getId());
				cronTriggerImpl.setCronExpression(task.getFrequency());
				// 获得时间段内执行时间
				List<Date> dates = TriggerUtils.computeFireTimesBetween(
						cronTriggerImpl, null, manualTask.getStartdate(),
						manualTask.getEnddate());
				for (Date date : dates) {
					ExecuteTask exetask = new ExecuteTask();
					exetask.setTaskid(manualTask.getId());
					exetask.setCreatedate(date);
					exetask.setExestatus(-1);
					executeTaskService.add(exetask);
				}
			} else {
				throw new Exception("--------isall 字段异常----------");
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("手动执行任务出错manualTask" + manualTask.toString());
			logger.error("libzone.cn exception:", e);
			return 0;
		}
		return 1;
	}

	@Override
	public List<Task> findAllTask(int id) {
		List<Task> tasks = null;
		try {
			tasks = mapper.findAllTask(id);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("findAllTask出错");
			logger.error("libzone.cn exception:", e);
		}
		return tasks;
	}

	@Override
	public List<String> findDateOperation(int id) {
		List<String> resultList = new ArrayList<String>();
		try {
			Task task = mapper.findTaskById(id);
			DataSource DataSource = mapper.findDSById(task.getDatasourceid());
			JdbcTemplate jdbcTemplate = dbConnector.getConnection(DataSource);
			String dbType = dbType(jdbcTemplate);
			String timeKeywords = stringTypeByDbType(dbType);
			List<Map<String, Object>> list_col = jdbcTemplate
					.queryForList(sqlForTypetables(dbType).replace(
							"$tableName", task.getSourcetable()));
			for (Map<String, Object> col : list_col) {
				if (timeKeywords.contains(((String) col.get("DATA_TYPE"))
						.toUpperCase())) {
					resultList.add((String) col.get("COLUMN_NAME"));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("findDateOperation出错 id:" + id);
			logger.error("libzone.cn exception:", e);
		}
		return resultList;
	}

	@Override
	public int updateWhere(Allconfig config) {
		logger.info("*-*-*-*-*-*-*-*-*-更新增量条件："+config.toString());
		Task t = new Task();
		t.setId(config.getTaskid());
		t.setIsall("0");
		if (!"1".equals(config.getIsall()) && config.getTaskid() != 0) {
			switch (config.getStatus()) {
			case "1": // 表达式
				t.setWheresql(config.getExpression());
				break;
			default: // table
				String[] fields = config.getFields().split(",");
				String wheresql = "";
				for (int i = 0; i < fields.length; i++) {
					if (i == fields.length - 1) {
						wheresql += fields[i]
								+ " >= to_date(${OCCUR_DATE,-1},'yyyy-MM-dd') and "
								+ fields[i]
								+ " < to_date(${OCCUR_DATE},'yyyy-MM-dd') ";
					} else {
						wheresql += fields[i]
								+ " >= to_date(${OCCUR_DATE,-1},'yyyy-MM-dd') and "
								+ fields[i]
								+ " < to_date(${OCCUR_DATE},'yyyy-MM-dd') and ";
					}
				}
				t.setWheresql(wheresql);
				break;
			}
		}
		try {
			return mapper.update(t);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("updateWhere出错 config:" + config);
			logger.error("libzone.cn exception:", e);
			return 0;
		}
	}

	@Override
	public DataSource findDSById(int id) {
		try {
			return mapper.findDSById(id);
		} catch (Exception e) {
			logger.error("findDSById出错 id:" + id);
			logger.error("libzone.cn exception:", e);
			return null;
		}
	}

	/**
	 * 判断数据库类型
	 * 
	 * @param connection
	 * @return String
	 */
	public static String dbType(JdbcTemplate connection) {
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
			logger.error("dbType出错 ");
			logger.error("libzone.cn exception:", e);
			e.printStackTrace();
		}
		return "OTHER";
	}

	/**
	 * 根据不同数据库返回不同SQL(字段类型)
	 * 
	 * @param dbType
	 * @return String
	 */
	public static String sqlForTypetables(String dbType) {
		switch (dbType) {
		case "ORACLE":
			return sql_FieldType_oracle;
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
	 * 根据不同数据库返回所包含系统时间字段
	 * 
	 * @param dbType
	 * @return String
	 */
	private static String stringTypeByDbType(String dbType) {
		switch (dbType) {
		case "ORACLE":
			return time_Type_Oracle;
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

	@Override
	public int update(Task t) {
		return 0;
	}

	@Override
	public List<Task> findExeTask() {
		try {
			return mapper.findExeTask();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("findExeTask出错 ");
			logger.error("libzone.cn exception:", e);
		}
		return null;
	}

	@Override
	public List<Task> findTasksByParentId(int parentid) {
		List<Task> list = null;
		try {
			list = mapper.findTasksByParentId(parentid);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("findTasksByParentId出错 parentid:" + parentid);
			logger.error("libzone.cn exception:", e);
		}
		return list;
	}

	@Override
	public List<Map<String, Object>> findHaveChildren(Task t) {
		List<Map<String, Object>> tasks = null;
		try {
			// 先查询父任务
			t.setParentid(0);
			tasks = mapper.findIncloudChildren(t);
			// 转成map 为了方便添加children属性
			for (Map<String, Object> map : tasks) {
				List<Task> ts = mapper.findTasksByParentId(Integer.parseInt(map
						.get("ID").toString()));
				// PageInfo<Task> pagets = new PageInfo<Task>(ts);
				map.put("children", ts);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("findHaveChildren出错 Task:" + t.toString());
			logger.error("libzone.cn exception:", e);
		}
		return tasks;
	}

	@Override
	public List<String> choosedTables(int id) {
		List<String> listTableName = new ArrayList<>();
		try {
			Task task = mapper.findTaskById(id);
			String[] tableNames = task.getChecktables().split(",");
			for (String tableName : tableNames) {
				listTableName.add(tableName);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("choosedTables出错 id:" + id);
			logger.error("libzone.cn exception:", e);
		}
		return listTableName;
	}

	@Override
	public Allconfig getWhereConf(int taskid) {
		logger.info("*-*-*-*-*-*-*-获取增量信息taskid："+taskid);
		String fields = "";
		Allconfig allconfig = new Allconfig();
		try {
			allconfig.setTaskid(taskid);
			List<String> resultList = findDateOperation(taskid);
			String whereSql = mapper.findWhereConf(taskid);
			if (whereSql == null || "".equals(whereSql)) {
				return allconfig;
			}
			String whereSqlUp = whereSql.toUpperCase();
			for (int i = 0; i < resultList.size(); i++) {
				String col = resultList.get(i);
				if (whereSqlUp.contains(col.toUpperCase())) {
					fields += col + ",";
				}
			}
			if(!"".equals(fields)){
				fields = fields.substring(0, fields.length()-1);
			}
			allconfig.setFields(fields);
			allconfig.setExpression(whereSql);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("getWhereConf出错 taskid:" + taskid);
			logger.error("libzone.cn exception:", e);
		}
		return allconfig;
	}

	@Override
	public List<Map<String, Object>> preview(Map<String, Object> map) {
		logger.info("*-*-*-*-*-*-*-预览数据："+map.toString());
		UserQuery userQuery = new UserQuery();
		List<Map<String, Object>> list = null;
		try {
			userQuery.setTablename((String) map.get("tablename"));
			DataSource dataSource = mapper.findDSById((Integer) map
					.get("sourceid"));
			JdbcTemplate jdbcTemplate = dbConnector.getConnection(dataSource);
			list = dbConnector.preview(jdbcTemplate, userQuery);
			if(list != null && list.size() == 0){
				String dbType = dbType(jdbcTemplate);
				List<Map<String, Object>> list_col = jdbcTemplate
						.queryForList(sqlForTypetables(dbType).replace(
								"$tableName", userQuery.getTablename()));
				Map<String, Object> nedmap = new HashMap<String, Object>();
				for(Map<String, Object> amp : list_col){
					Object colname = amp.get("COLUMN_NAME");
					nedmap.put(colname.toString(), null);
				}
				list.add(nedmap);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("task preview出错 map:" + map.toString());
			logger.error("libzone.cn exception:", e);
		}
		return list;
	}
}
