package com.example.services.datasource.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.dto.datasource.DataSource;
import com.example.mapper.datasource.DataSourceMapper;
import com.example.services.datasource.IDateSourceService;
import com.example.utils.DataBase;
import com.example.utils.DbConnector;

@Transactional
@Service
public class DataSourceService implements IDateSourceService {

	private static final Logger logger = LoggerFactory.getLogger(DataSourceService.class);
	
	@Autowired
	private DataSourceMapper mapper;

	@Autowired
	private DbConnector dbConnector;

	@Override
	public List<DataSource> find(DataSource t) {
		//logger.info("*-*-*-*-*-*-*-*-查询数据源："+t.toString());
		try {
			return mapper.find(t);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("find 数据源 出错DataSource："+t);
			logger.error("libzone.cn exception:", e);
			return null;
		}
	}

	@Override
	public int add(DataSource t) {
		logger.info("*-*-*-*-*-*-*-添加数据源："+t.toString());
		try {
			t.setClassname(getDriverCalss(t.getType()));
			t.setUrl(getUrl(t));
			return mapper.add(t);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("add 数据源 出错DataSource："+t);
			logger.error("libzone.cn exception:", e);
		}
		return 0;
	}

	@Override
	public int update(DataSource t) {
		logger.info("*-*-*-*-*-*-*-更新数据源："+t.toString());
		try {
			t.setClassname(getDriverCalss(t.getType()));
			t.setUrl(getUrl(t));//更新url
			return mapper.update(t);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("update 数据源 出错DataSource："+t);
			logger.error("libzone.cn exception:", e);
		}
		return 0;
	}

	@Override
	public int delete(String ids) {
		logger.info("*-*-*-*-*-*-*-*-删除数据源ids："+ids);
		try {
			String[] ids_strs = ids.split(",");
			for (String id : ids_strs) {
				mapper.delete(Integer.parseInt(id));
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("delete 数据源 出错ids："+ids);
			logger.error("libzone.cn exception:", e);
			return 0;
		}
		return 1;
	}

	@Override
	public boolean test(DataSource dataSource) {
		return dbConnector.testConnection(
				dbConnector.getConnection(dataSource), null);
	}

	@Override
	public DataSource findById(int id) {
		try {
			return mapper.findById(id);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("findById 数据源 出错id："+id);
			logger.error("libzone.cn exception:", e);
			return null;
		}
	}

	/**
	 * 通过数据库类型获取相应驱动
	 * 
	 * @param type
	 * @return
	 */
	private String getDriverCalss(String type) {
		if(type == null){
			return null;
		}
		switch (type.toUpperCase()) {
		case "ORACLE":
			return DataBase.ORACLE.getClassname();
		case "MYSQL":
			return DataBase.MYSQL.getClassname();
		case "POSTGRES":
			return DataBase.POSTGRES.getClassname();
		default:
			return null;
		}
	}
	
	/**
	 * 通过数据库类型获取相应url前缀
	 * 
	 * @param DataSource
	 * @return
	 */
	private String getUrl(DataSource dataSource) {
		if(dataSource.getType() == null){
			return null;
		}
		switch ((dataSource.getType()).toUpperCase()) {
		case "ORACLE":
			return "jdbc:oracle:thin:@"+dataSource.getIp()+":"+dataSource.getPort()+":"+dataSource.getSchema();
		case "MYSQL":
			return "jdbc:mysql://";
		case "POSTGRES":
			return null;
		default:
			return null;
		}
	}

}
