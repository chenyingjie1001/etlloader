package com.example.services.datasource;

import com.example.dto.datasource.DataSource;
import com.example.services.base.IBaseService;

/**
 * 
 * @author yingjie.chen
 *
 */
public interface IDateSourceService extends IBaseService<DataSource>{

	DataSource findById(int id);
	/**
	 * 测试连接服务
	 * @param dataSource
	 * @return
	 */
	boolean test(DataSource dataSource);
}
