package com.example.services.base;

import java.util.List;

public interface IBaseService<T> {

	List<T> find(T t);

	int add(T t);

	int update(T t) ;

	/**
	 * 这里不属于业务层已经具体到单个删除
	 * @param id
	 */
	int delete(String ids);
}
