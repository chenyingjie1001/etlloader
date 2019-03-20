package com.firesoon.idaservice.chargeBillDetail.impl;

import java.sql.Clob;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.jeecgframework.poi.excel.entity.params.ExcelExportEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.alibaba.druid.support.json.JSONUtils;
import com.firesoon.dto.base.WarnException;
import com.firesoon.dto.user.User;
import com.firesoon.idaservice.chargeBillDetail.ChargeBillDetailService;
import com.firesoon.paymentmapper.chargeBillDetail.ChargeBillDetailMapper;
import com.firesoon.utils.StringUtil;
import com.firesoon.utils.UserUtil;
import com.github.pagehelper.PageHelper;
import com.google.common.collect.ImmutableMap;

@Primary
@Service
public class ChargeBillDetailServiceImpl implements ChargeBillDetailService {
	
	private static Logger logger = LoggerFactory.getLogger(ChargeBillDetailServiceImpl.class);

	@Autowired
	private ChargeBillDetailMapper chargeBillDetailMapper;

	/**
	 * 根据billtype设置表名
	 * 
	 * @param map
	 */
	private void setMap(Map<String, Object> map) {
		map.put("tablename", "ida_chargebill_detail_" + map.get("billtype"));
	}

	@Override
	public void delete(Map<String, Object> map) {
		chargeBillDetailMapper.deleteChargeBillDetail(map);
	}

	@Override
	public List<Map<String, Object>> queryCondit(Map<String, Object> map) {
		// 获取登录用户
		User user = UserUtil.getUser();
		map.put("userId", user.getId());
		// 根据用户id查询他在这个页面的筛选条件
		List<Map<String, Object>> list = chargeBillDetailMapper.queryCondit(map);
		if(list == null || list.isEmpty()) {
			// 配置表中无该用户数据，根据筛选条件配置表初始化用户与条件关系表
			chargeBillDetailMapper.initUserCondition(map);
			list = chargeBillDetailMapper.queryCondit(map);
		}
		return list;
	}
	
	@Override
	public String updateConditState(Map<String, Object> map) {
		if(map.get("conditionId") == null || map.get("status") == null) {
			throw new WarnException("参数错误");
		}
		// 获取登录用户
		User user = UserUtil.getUser();
		map.put("userId", user.getId());
		int row = chargeBillDetailMapper.updateConditState(map);
		String res;
		if(row == 1) {
			res = "update success";
		}else {
			res = "update fail";
		}
		return res;
	}
	
	@Override
	public List<Map<String, Object>> allJyfs(Map<String, Object> map){
		try {
			return chargeBillDetailMapper.allJyfs(map);
		}catch(Exception e) {
			// 柯桥居民没有就医方式，会报错，记录日志，方便查看
			logger.error(e.getMessage(), e);
			return new ArrayList<>();
		}
	}
	
	@Override
	public List<Map<String, Object>> allCblx(Map<String, Object> map){
		try {
			return chargeBillDetailMapper.allCblx(map);
		}catch(Exception e) {
			// 柯桥居民没有参保类型，会报错，记录日志，方便查看
			logger.error(e.getMessage(), e);
			return new ArrayList<>();
		}
	}
	
	@Override
	public Map<String, Object> query(Map<String, Object> map) {
		String departs = (map.get("chargeDepartments") == null ? "" : map.get("chargeDepartments").toString());
		String isappeal = (map.get("isappeal") == null ? "" : map.get("isappeal").toString());
		String itemname = (map.get("itemname") == null ? "" : map.get("itemname").toString());
		String excelDeparts = (map.get("excelDepartments") == null ? "" : map.get("excelDepartments").toString());
		String kkxm = (map.get("kkxm") == null ? "" : map.get("kkxm").toString());
		String reason = (map.get("reason") == null ? "" : map.get("reason").toString());
		String jyfs = (map.get("jyfs") == null ? "" : map.get("jyfs").toString());
		String cblx = (map.get("cblx") == null ? "" : map.get("cblx").toString());
		
		List<String> departments = new ArrayList<>();
		List<String> excelDepartments = new ArrayList<>();
		List<String> isappeals = new ArrayList<>();
		List<String> itemnames = new ArrayList<>();
		List<String> kkxms = new ArrayList<>();
		List<String> reasons = new ArrayList<>();
		List<String> jyfss = new ArrayList<>();
		List<String> cblxs = new ArrayList<>();
		if (departs.length() > 0) {
			departments = Arrays.asList(departs.split(","));
		}
		if (excelDeparts.length() > 0) {
			excelDepartments = Arrays.asList(excelDeparts.split(","));
		}
		if (isappeal.length() > 0) {
			isappeals = Arrays.asList(isappeal.split(","));
		}
		if (itemname.length() > 0) {
			itemnames = Arrays.asList(itemname.split(","));
		}
		if (kkxm.length() > 0) {
			kkxms = Arrays.asList(kkxm.split(","));
		}
		if (reason.length() > 0) {
			reasons = Arrays.asList(reason.split(","));
		}
		if (jyfs.length() > 0) {
			jyfss = Arrays.asList(jyfs.split(","));
		}
		if (cblx.length() > 0) {
			cblxs = Arrays.asList(cblx.split(","));
		}
		map.put("chargeDepartments", departments);
		map.put("isappeal", isappeals);
		map.put("itemname", itemnames);
		map.put("excelDepartments", excelDepartments);
		map.put("kkxms", kkxms);
		map.put("reasons", reasons);
		map.put("jyfss", jyfss);
		map.put("cblxs", cblxs);
		
		//查询页面展示的字段，用这些字段进行模糊查询
		List<Map<String, Object>> fields = chargeBillDetailMapper.getExcelTable(map);
		map.put("fields", fields);
		
		String pageNum = (map.get("pageNum") == null) ? "1" : map.get("pageNum").toString();
		String pageSize = (map.get("pageSize") == null) ? "10" : map.get("pageSize").toString();
		PageHelper.startPage(Integer.parseInt(pageNum), Integer.parseInt(pageSize));
		
		List<Map<String, Object>> list = chargeBillDetailMapper.findChargeBillDetail(map);
		for (Map<String, Object> m : list) {
			Iterator<Entry<String, Object>> it = m.entrySet().iterator();
			while (it.hasNext()) {
				Entry<String, Object> eneity = it.next();
				if ("APPEALIMG".equals(eneity.getKey())) {
					m.remove(eneity.getKey());
					break;
				}
			}
		}
		
		Map<String, Object> result = new HashMap<>();
		result.put("excelTable", fields);
		result.put("pageInfo", list);
		return result;
	}

	@Override
	public List<Map<String, Object>> allDepartment(Map<String, Object> map) {
		return chargeBillDetailMapper.getAllChargeDepartment(map);
	}

	@Override
	public List<Map<String, Object>> allExcelDepartment(Map<String, Object> map) {
		List<Map<String, Object>> chargeBill = new ArrayList<>();
		List<Map<String, Object>> city = chargeBillDetailMapper.getCity();
		if (!"宁波".equals(city.get(0).get("AREANAME"))) {
			chargeBill = chargeBillDetailMapper.getAllExcelChargeDepartment(map);
		}
		return chargeBill;
	}

	@Override
	public List<Map<String, Object>> allItem(Map<String, Object> map) {
		return chargeBillDetailMapper.getAllChargeItem(map);
	}

	@Override
	public List<Map<String, Object>> allItemname(Map<String, Object> map) {
		return chargeBillDetailMapper.getAllItemname(map);
	}

	@Override
	public List<Map<String, Object>> allchargereason(Map<String, Object> map) {
		return chargeBillDetailMapper.getAllChargereason(map);
	}

	@Override
	public void updateDepartment(Map<String, Object> map) {
		chargeBillDetailMapper.updateDepartment(map);
	}

	@Override
	public List<Map<String, Object>> getAppealMessage(Map<String, Object> map) {
		List<Map<String, Object>> result = chargeBillDetailMapper.getAppealMessage(map);
		result.stream().forEach(m -> m.put("APPEALIMG", StringUtil.Clob2String((Clob) m.get("APPEALIMG"))));
		return result;
	}

	@Override
	public void updateWrite(Map<String, Object> map) {
		map.put("appealimg", JSONUtils.toJSONString(map.get("appealimg")));
		chargeBillDetailMapper.updateWrite(map);
	}

	@Override
	public Map<String, Object> getSectionLevelMonth() {
		List<Map<String, Object>> result = chargeBillDetailMapper.getSectionLevelMonth();
		Map<String, Object> monthMap = new HashMap<>();
		if (result.size() == 0) {
			monthMap.put("levelmonth", new SimpleDateFormat("yyyy-MM").format(new Date()));
		} else {
			monthMap.put("levelmonth", result.get(0).get("LEVELMONTH").toString());
		}
		return monthMap;
	}

	public List<Map<String, Object>> getLowerCaseMap(List<Map<String, Object>> mapList) {
		List<Map<String, Object>> resultList = new ArrayList<>();
		for (Map<String, Object> m : mapList) {
			Iterator<Entry<String, Object>> it = m.entrySet().iterator();
			while (it.hasNext()) {
				Entry<String, Object> eneity = it.next();
				resultList.add(ImmutableMap.of(eneity.getKey().toLowerCase(), eneity.getValue()));
			}
		}
		return mapList;
	}

	@Override
	public List<Map<String, Object>> allDepartmentConfig() {
		return chargeBillDetailMapper.getAllDepartmentConfig();
	}

	@Override
	public Map<String, Object> levelDownload(Map<String, Object> map) {
		String departs = (map.get("chargeDepartments") == null ? "" : map.get("chargeDepartments").toString());
		String isappeal = (map.get("isappeal") == null ? "" : map.get("isappeal").toString());
		String itemname = (map.get("itemname") == null ? "" : map.get("itemname").toString());
		String excelDeparts = (map.get("excelDepartments") == null ? "" : map.get("excelDepartments").toString());
		String kkxm = (map.get("kkxm") == null ? "" : map.get("kkxm").toString());
		String reason = (map.get("reason") == null ? "" : map.get("reason").toString());
		String jyfs = (map.get("jyfs") == null ? "" : map.get("jyfs").toString());
		String cblx = (map.get("cblx") == null ? "" : map.get("cblx").toString());
		
		List<String> departments = new ArrayList<>();
		List<String> excelDepartments = new ArrayList<>();
		List<String> isappeals = new ArrayList<>();
		List<String> itemnames = new ArrayList<>();
		List<String> kkxms = new ArrayList<>();
		List<String> reasons = new ArrayList<>();
		List<String> jyfss = new ArrayList<>();
		List<String> cblxs = new ArrayList<>();
		
		if (departs.length() > 0) {
			departments = Arrays.asList(departs.split(","));
		}
		if (excelDeparts.length() > 0) {
			excelDepartments = Arrays.asList(excelDeparts.split(","));
		}
		if (isappeal.length() > 0) {
			isappeals = Arrays.asList(isappeal.split(","));
		}
		if (itemname.length() > 0) {
			itemnames = Arrays.asList(itemname.split(","));
		}
		if (kkxm.length() > 0) {
			kkxms = Arrays.asList(kkxm.split(","));
		}
		if (reason.length() > 0) {
			reasons = Arrays.asList(reason.split(","));
		}
		if (jyfs.length() > 0) {
			jyfss = Arrays.asList(jyfs.split(","));
		}
		if (cblx.length() > 0) {
			cblxs = Arrays.asList(cblx.split(","));
		}
		map.put("chargeDepartments", departments);
		map.put("isappeal", isappeals);
		map.put("itemname", itemnames);
		map.put("excelDepartments", excelDepartments);
		map.put("kkxms", kkxms);
		map.put("reasons", reasons);
		map.put("jyfss", jyfss);
		map.put("cblxs", cblxs);
		
		List<Map<String, Object>> cols = chargeBillDetailMapper.getExcelTable(map);
		map.put("fields", cols);
		
		List<Map<String, Object>> list = chargeBillDetailMapper.findChargeBillDetail(map);
		
		List<ExcelExportEntity> entityList = new ArrayList<>();
		for (Map<String, Object> col : cols) {
			String name = col.get("COLUMN_NAME").toString();
			String key = col.get("COLUMN_CODE").toString();
			ExcelExportEntity excelExportEntity = new ExcelExportEntity(name, key);
			String[] arg = new String[] { "未填写_0", "已填写_1" };
			String[] appealarg = new String[] { "未申诉_0", "申诉成功_1", "申诉失败_-1" };
			if ("申诉状态".equals(name)) {
				excelExportEntity.setReplace(appealarg);
			} else if ("填写状态".equals(name)) {
				excelExportEntity.setReplace(arg);
			}

			entityList.add(excelExportEntity);
		}
		Map<String, Object> result = new HashMap<>();
		result.put("entityList", entityList);
		result.put("list", list);
		return result;
	}

}
