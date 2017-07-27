package com.example.mapper.base;

import java.util.List;

public interface BaseMapper<T> {

	List<T> find(T t) throws Exception;
	
	int add(T t) throws Exception;
	
	int update(T t) throws Exception;
	
	/**
	 * 这里不属于业务层已经具体到单个删除
	 * @param id
	 */
	int delete(int id) throws Exception;
}
