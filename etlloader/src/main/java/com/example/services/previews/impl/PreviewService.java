package com.example.services.previews.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.example.dto.datasource.DataSource;
import com.example.dto.datasource.UserQuery;
import com.example.dto.previews.Relationship;
import com.example.mapper.previews.PreviewMapper;
import com.example.mapper.task.TaskMapper;
import com.example.services.previews.IPreviewService;
import com.example.services.task.impl.TaskService;
import com.example.utils.DBUtil;
import com.example.utils.DbConnector;

@Service
public class PreviewService implements IPreviewService {

	private static final Logger logger = LoggerFactory.getLogger(PreviewService.class);
	
	@Autowired
	private PreviewMapper mapper;
	
	@Autowired
	private TaskMapper taskMapper;
	
	@Autowired
	private DbConnector dbConnector;

	@Override
	public List<Relationship> find(Relationship t) {
		List<Relationship> list = null;
		try {
			list = mapper.find(t);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("PreviewService find列表出错 Relationship:"+t);
			logger.error("libzone.cn exception:", e);
		}
		return list;
	}

	@Override
	public List<Map<String, Object>> preview(Relationship t) {
		List<Map<String, Object>> list = null;
		try {
//			list = mapper.preview(t);
			//目标库写死
			DataSource dataSource = DBUtil.getOdsOrModel(1);
			JdbcTemplate jdbcTemplate = dbConnector.getConnection(dataSource);
			UserQuery u = new UserQuery();
			u.setTablename(t.getTablename());
			list = dbConnector.preview(jdbcTemplate, u);
			if(list != null && list.size() == 0){
				String dbType = TaskService.dbType(jdbcTemplate);
				List<Map<String, Object>> list_col = jdbcTemplate
						.queryForList(TaskService.sqlForTypetables(dbType).replace(
								"$tableName", t.getTablename()));
				Map<String, Object> nedmap = new HashMap<String, Object>();
				for(Map<String, Object> amp : list_col){
					Object colname = amp.get("COLUMN_NAME");
					nedmap.put(colname.toString(), null);
				}
				list.add(nedmap);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("PreviewService preview列表出错 Relationship:"+t);
			logger.error("libzone.cn exception:", e);
		}
		return list;
	}

	@Override
	public int add(Relationship t) {
		logger.info("*-*-*-*-*-*-*-添加Relationship："+t.toString());
		int i = 0;
		try {
			i = mapper.add(t);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("PreviewService add列表出错 Relationship:"+t);
			logger.error("libzone.cn exception:", e);
		}
		return i;
	}

	@Override
	public int update(Relationship t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(String ids) {
		logger.info("*-*-*-*-*-*-*-删除Relationship  ids："+ids);
		for (String id : ids.split(",")) {
			try {
				mapper.delete(Integer.parseInt(id));
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("PreviewService delete列表出错 ids:"+ids);
				logger.error("libzone.cn exception:", e);
				return 0;
			}
		}
		return 1;
	}

	@Override
	public int update_delete(Relationship tempt) {
		logger.info("*-*-*-*-*-*-*-更新Relationship："+tempt.toString());
		int i = 0;
		try {
			i = mapper.update_delete(tempt);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("PreviewService update_delete列表出错 Relationship:"+tempt);
			logger.error("libzone.cn exception:", e);
		}
		return i;
	}

}
