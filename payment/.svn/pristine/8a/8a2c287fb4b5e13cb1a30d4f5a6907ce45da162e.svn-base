package com.firesoon.paymentmapper.chargeBillDetail;

import com.firesoon.dto.user.User;
import com.firesoon.paymentmapper.base.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import java.util.Map;

@Mapper
public interface ChargeBillDetailMapper extends BaseMapper<User> {

	List<Map<String, Object>> findChargeBillDetail(Map<String, Object> map);

	void insertChargeBillDetail(Map<String, Object> map);

	void deleteChargeBillDetail(Map<String, Object> map);

	void updateDepartment(Map<String, Object> map);

	void updateWrite(Map<String, Object> map);

	List<Map<String, Object>> getAllChargeDepartment(Map<String, Object> map);

	List<Map<String, Object>> getAllChargeItem(Map<String, Object> map);
	List<Map<String, Object>> getAllItemname(Map<String, Object> map);
	List<Map<String, Object>> getAllChargereason(Map<String, Object> map);

	List<Map<String, Object>> getExcelTable(Map<String, Object> map);

	List<Map<String, Object>> getAppealMessage(Map<String, Object> map);

	List<Map<String, Object>> getSectionLevelMonth();

	List<Map<String, Object>> getAllDepartmentConfig();

	List<Map<String, Object>> getAllAppealConfig(Map<String, Object> map);

	List<Map<String, Object>> getAllExcelChargeDepartment(Map<String, Object> map);

	List<Map<String, Object>> getCity();

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
	int updateConditState(Map<String, Object> map);

	/**
	 * 	查询所有的就医方式
	 * @param map
	 * @return
	 */
	List<Map<String, Object>> allJyfs(Map<String, Object> map);

	/**
	 *	 所有的参保类型
	 * @param map
	 * @return
	 */
	List<Map<String, Object>> allCblx(Map<String, Object> map);

	/**
	 * 初始化用户与条件关系表
	 * @param map
	 */
	void initUserCondition(Map<String, Object> map);
}
