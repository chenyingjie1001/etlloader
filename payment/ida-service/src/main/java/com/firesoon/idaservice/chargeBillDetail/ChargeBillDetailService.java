package com.firesoon.idaservice.chargeBillDetail;

import java.util.*;

public interface ChargeBillDetailService {
	Map<String, Object> query(Map<String, Object> map);

	void delete(Map<String, Object> map);

	List<Map<String, Object>> allDepartment(Map<String, Object> map);

	List<Map<String, Object>> allExcelDepartment(Map<String, Object> map);

	List<Map<String, Object>> allItem(Map<String, Object> map);
	List<Map<String, Object>> allItemname(Map<String, Object> map);
	List<Map<String, Object>> allchargereason(Map<String, Object> map);

	void updateDepartment(Map<String, Object> map);
	
	List<Map<String, Object>> getAppealMessage(Map<String, Object> map);
	
	void updateWrite(Map<String, Object> map);
	
	Map<String, Object> getSectionLevelMonth();
	
	List<Map<String, Object>> allDepartmentConfig();
	
	Map<String, Object> levelDownload(Map<String, Object> map);

	/**
	 * 	查询筛选条件
	 * @param map
	 * @return
	 */
	List<Map<String, Object>> queryCondit(Map<String, Object> map);

	/**
	 * 	根据id修改筛选条件的状态
	 * @param map
	 * @return
	 */
	String updateConditState(Map<String, Object> map);

	/**
	 * 	查询所有的就医方式
	 * @param map
	 * @return
	 */
	List<Map<String, Object>> allJyfs(Map<String, Object> map);

	/**
	 * 	查询所有的参保类型
	 * @param map
	 * @return
	 */
	List<Map<String, Object>> allCblx(Map<String, Object> map);
	
}
