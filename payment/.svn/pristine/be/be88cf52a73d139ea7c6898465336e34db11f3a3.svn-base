package com.firesoon.paymentmapper.chargeBillCollect;

import com.firesoon.dto.user.User;
import com.firesoon.paymentmapper.base.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import java.util.Map;

@Mapper
public interface ChargeBillCollectMapper extends BaseMapper<User> {

	List<Map<String, Object>> getAllMoney(Map<String, Object> paramMap);

	List<Map<String, Object>> getMonthMax(Map<String, Object> paramMap);

	List<Map<String, Object>> getYearMax(Map<String, Object> paramMap);

	List<Map<String, Object>> getDepartmentMoney(Map<String, Object> paramMap);

	List<Map<String, Object>> getData(Map<String, Object> paramMap);
	
	List<Map<String, Object>> getAllDepartData(Map<String, Object> paramMap);

}
