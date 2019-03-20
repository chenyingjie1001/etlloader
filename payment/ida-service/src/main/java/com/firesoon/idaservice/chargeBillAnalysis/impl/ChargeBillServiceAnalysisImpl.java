package com.firesoon.idaservice.chargeBillAnalysis.impl;

import com.firesoon.idaservice.chargeBillAnalysis.ChargeBillAnalysisService;
import com.firesoon.paymentmapper.chargeBillAnalysis.ChargeBillAnalysisMapper;
import com.google.common.collect.ImmutableMap;

import java.math.BigDecimal;
import java.util.*;

import org.jeecgframework.poi.excel.entity.params.ExcelExportEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Primary
@Service
public class ChargeBillServiceAnalysisImpl implements ChargeBillAnalysisService {

	@Autowired
	private ChargeBillAnalysisMapper chargeBillAnalysisMapper;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public Map<String, Object> queryItemDetail(Map<String, Object> paramMap) {
		String sql = "select table_name from user_tables where table_name like 'IDA_CHARGEBILL_DETAIL_%' and table_name not like '%TEST' ";
		List<Map<String, Object>> tableList = jdbcTemplate.queryForList(sql);
		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("扣款金额", "0");
		resultMap.put("扣款条数", "0");
		resultMap.put("申诉成功金额", "0");
		resultMap.put("申诉失败金额", "0");
		resultMap.put("未申诉金额", "0");
		resultMap.put("申诉成功占比", "0%");
		Map<String, Object> allResultMap = new HashMap<>();
		List<Map<String, Object>> resultList = new ArrayList<>();
		if (tableList.size() > 0) {
			StringBuffer unionSql = new StringBuffer("with ida_chargebill_detail as(");
			for (int i = 0; i < tableList.size(); i++) {
				String tablename = tableList.get(i).get("TABLE_NAME").toString();
				if (i < tableList.size() - 1) {
					unionSql.append(
							" select month,charge_money,isappeal,item_name,doctor_name,charge_reason,deductedept from "
									+ tablename + " union all ");
				} else {
					unionSql.append(
							" select month,charge_money,isappeal,item_name,doctor_name,charge_reason,deductedept from "
									+ tablename + ") ");
				}
			}
			paramMap.put("unionSql", unionSql.toString());
			Map<String, Object> result = new HashMap<>();
			if (chargeBillAnalysisMapper.getReasonDetail(paramMap).size() > 0) {
				result = chargeBillAnalysisMapper.getReasonDetail(paramMap).get(0);
			}

			List<Map<String, Object>> rateList = chargeBillAnalysisMapper.getReasonRate(paramMap);

			result = chargeBillAnalysisMapper.getReasonDetail(paramMap).get(0);
			if (result.get("ALLCHARGE") == null)
				resultMap.put("扣款金额", "0");
			else
				resultMap.put("扣款金额", result.get("ALLCHARGE").toString());
			if (result.get("NUM") == null)
				resultMap.put("扣款条数", "0");
			else
				resultMap.put("扣款条数", result.get("NUM").toString());

			if (rateList.get(0) == null)
				resultMap.put("申诉成功金额", "0");
			else
				resultMap.put("申诉成功金额", rateList.get(0).get("MONEY").toString());
			if (rateList.get(1) == null)
				resultMap.put("申诉失败金额", "0");
			else
				resultMap.put("申诉失败金额", rateList.get(1).get("MONEY").toString());
			if (rateList.get(2) == null)
				resultMap.put("未申诉金额", "0");
			else
				resultMap.put("未申诉金额", rateList.get(2).get("MONEY").toString());
			Double success = Double.valueOf(resultMap.get("申诉成功金额").toString());
			Double allCharge = Double.valueOf(resultMap.get("扣款金额").toString());
			Double rate = 0d;
			if (success + allCharge > 0) {
				rate = success * 100 / (success + allCharge);
				BigDecimal bg = new BigDecimal(rate);
				Double math = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
				if (math > 0)
					resultMap.put("申诉成功占比", math.toString() + "%");
				else
					resultMap.put("申诉成功占比", "0%");
			} else {
				resultMap.put("申诉成功占比", "0%");
			}
			resultList = chargeBillAnalysisMapper.getItemTable(paramMap);
			resultList.forEach(x -> {
				x.put("ID", UUID.randomUUID().toString().replace("-", "").toLowerCase());
			});
			for (Map<String, Object> map : resultList) {
				for (Map.Entry<String, Object> entry : map.entrySet()) {
					if (entry.getValue() == null)
						map.put(entry.getKey(), "0");
				}
				Double mapSuccess = Double.valueOf(map.get("SUCCESSMONEY").toString());
				Double mapAllCharge = Double.valueOf(map.get("ALLCHARGE").toString());
				Double mapRate = 0d;
				mapRate = mapSuccess * 100 / (mapSuccess + mapAllCharge);
				BigDecimal mapBg = new BigDecimal(mapRate);
				Double mapMath = mapBg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
				if (mapMath > 0)
					map.put("SUCCESSRATE", mapMath.toString() + "%");
				else
					map.put("SUCCESSRATE", "0%");
			}
		}
		allResultMap.put("info", resultMap);
		allResultMap.put("pageInfo", resultList);
		return allResultMap;
	}

	@Override
	public Map<String, Object> getItemDepartmentData(Map<String, Object> paramMap) {
		String sql = "select table_name from user_tables where table_name like 'IDA_CHARGEBILL_DETAIL_%' and table_name not like '%TEST' ";
		List<Map<String, Object>> tableList = jdbcTemplate.queryForList(sql);
		Map<String, Object> result = new HashMap<>();
		List<Map<String, Object>> resultList = new ArrayList<>();
		if (tableList.size() > 0) {
			StringBuffer unionSql = new StringBuffer("with ida_chargebill_detail as(");
			for (int i = 0; i < tableList.size(); i++) {
				String tablename = tableList.get(i).get("TABLE_NAME").toString();
				if (i < tableList.size() - 1) {
					unionSql.append(
							" select month,charge_money,isappeal,item_name,doctor_name,charge_reason,deductedept from "
									+ tablename + " union all ");
				} else {
					unionSql.append(
							" select month,charge_money,isappeal,item_name,doctor_name,charge_reason,deductedept from "
									+ tablename + ") ");
				}
			}
			paramMap.put("unionSql", unionSql);
			resultList = chargeBillAnalysisMapper.getDownItemDepartment(paramMap);
			for (Map<String, Object> map : resultList) {
				for (Map.Entry<String, Object> entry : map.entrySet()) {
					if (entry.getValue() == null)
						map.put(entry.getKey(), "0");
				}
				Double money = Double.valueOf(map.get("MONEY").toString());
				Double allMoney = Double.valueOf(paramMap.get("allMoney").toString());
				Double mapRate = 0d;
				if(allMoney > 0) {
					mapRate = money * 100 / allMoney;
				}
				BigDecimal mapBg = new BigDecimal(mapRate);
				Double mapMath = mapBg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
				if (mapMath > 0)
					map.put("RATE", mapMath.toString() + "%");
				else
					map.put("RATE", "0%");
			}
		}
		result.put("pageInfo", resultList);
		return result;
	}

	@Override
	public Map<String, Object> getItemDoctorData(Map<String, Object> paramMap) {
		String sql = "select table_name from user_tables where table_name like 'IDA_CHARGEBILL_DETAIL_%' and table_name not like '%TEST' ";
		List<Map<String, Object>> tableList = jdbcTemplate.queryForList(sql);
		Map<String, Object> result = new HashMap<>();
		List<Map<String, Object>> resultList = new ArrayList<>();
		if (tableList.size() > 0) {
			StringBuffer unionSql = new StringBuffer("with ida_chargebill_detail as(");
			for (int i = 0; i < tableList.size(); i++) {
				String tablename = tableList.get(i).get("TABLE_NAME").toString();
				if (i < tableList.size() - 1) {
					unionSql.append(
							" select month,charge_money,isappeal,item_name,doctor_name,charge_reason,deductedept from "
									+ tablename + " union all ");
				} else {
					unionSql.append(
							" select month,charge_money,isappeal,item_name,doctor_name,charge_reason,deductedept from "
									+ tablename + ") ");
				}
			}
			paramMap.put("unionSql", unionSql);
			resultList = chargeBillAnalysisMapper.getDownItemDoctor(paramMap);
			for (Map<String, Object> map : resultList) {
				for (Map.Entry<String, Object> entry : map.entrySet()) {
					if (entry.getValue() == null)
						map.put(entry.getKey(), "0");
				}
				Double money = Double.valueOf(map.get("MONEY").toString());
				Double allMoney = Double.valueOf(paramMap.get("allMoney").toString());
				Double mapRate = 0d;
				if(allMoney > 0) {
					mapRate = money * 100 / allMoney;
				}
				BigDecimal mapBg = new BigDecimal(mapRate);
				Double mapMath = mapBg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
				if (mapMath > 0)
					map.put("RATE", mapMath.toString() + "%");
				else
					map.put("RATE", "0%");
			}
		}
		result.put("pageInfo", resultList);
		return result;
	}

	@Override
	public Map<String, Object> queryReasonDetail(Map<String, Object> paramMap) {
		String sql = "select table_name from user_tables where table_name like 'IDA_CHARGEBILL_DETAIL_%' and table_name not like '%TEST' ";
		List<Map<String, Object>> tableList = jdbcTemplate.queryForList(sql);
		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("扣款金额", "0");
		resultMap.put("扣款条数", "0");
		resultMap.put("申诉成功金额", "0");
		resultMap.put("申诉失败金额", "0");
		resultMap.put("未申诉金额", "0");
		resultMap.put("申诉成功占比", "0%");
		Map<String, Object> allResultMap = new HashMap<>();
		List<Map<String, Object>> resultList = new ArrayList<>();
		if (tableList.size() > 0) {
			StringBuffer unionSql = new StringBuffer("with ida_chargebill_detail as(");
			for (int i = 0; i < tableList.size(); i++) {
				String tablename = tableList.get(i).get("TABLE_NAME").toString();
				if (i < tableList.size() - 1) {
					unionSql.append(
							" select month,charge_money,isappeal,item_name,doctor_name,charge_reason,deductedept from "
									+ tablename + " union all ");
				} else {
					unionSql.append(
							" select month,charge_money,isappeal,item_name,doctor_name,charge_reason,deductedept from "
									+ tablename + ") ");
				}
			}
			paramMap.put("unionSql", unionSql);
			Map<String, Object> result = new HashMap<>();
			if (chargeBillAnalysisMapper.getReasonDetail(paramMap).size() > 0) {
				result = chargeBillAnalysisMapper.getReasonDetail(paramMap).get(0);
			}

			List<Map<String, Object>> rateList = chargeBillAnalysisMapper.getReasonRate(paramMap);

			result = chargeBillAnalysisMapper.getReasonDetail(paramMap).get(0);
			if (result.get("ALLCHARGE") == null)
				resultMap.put("扣款金额", "0");
			else
				resultMap.put("扣款金额", result.get("ALLCHARGE").toString());
			if (result.get("NUM") == null)
				resultMap.put("扣款条数", "0");
			else
				resultMap.put("扣款条数", result.get("NUM").toString());

			if (rateList.get(0) == null)
				resultMap.put("申诉成功金额", "0");
			else
				resultMap.put("申诉成功金额", rateList.get(0).get("MONEY").toString());
			if (rateList.get(1) == null)
				resultMap.put("申诉失败金额", "0");
			else
				resultMap.put("申诉失败金额", rateList.get(1).get("MONEY").toString());
			if (rateList.get(2) == null)
				resultMap.put("未申诉金额", "0");
			else
				resultMap.put("未申诉金额", rateList.get(2).get("MONEY").toString());
			Double success = Double.valueOf(resultMap.get("申诉成功金额").toString());
			Double allCharge = Double.valueOf(resultMap.get("扣款金额").toString());
			Double rate = 0d;
			if (success + allCharge > 0) {
				rate = success * 100 / (success + allCharge);
				BigDecimal bg = new BigDecimal(rate);
				Double math = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
				if (math > 0)
					resultMap.put("申诉成功占比", math.toString() + "%");
				else
					resultMap.put("申诉成功占比", "0%");
			} else {
				resultMap.put("申诉成功占比", "0%");
			}
			resultList = chargeBillAnalysisMapper.getReasonTable(paramMap);
			resultList.forEach(x -> {
				x.put("ID", UUID.randomUUID().toString().replace("-", "").toLowerCase());
			});
			for (Map<String, Object> map : resultList) {
				for (Map.Entry<String, Object> entry : map.entrySet()) {
					if (entry.getValue() == null)
						map.put(entry.getKey(), "0");
				}
				Double mapSuccess = Double.valueOf(map.get("SUCCESSMONEY").toString());
				Double mapAllCharge = Double.valueOf(map.get("ALLCHARGE").toString());
				Double mapRate = 0d;
				mapRate = mapSuccess * 100 / (mapSuccess + mapAllCharge);
				BigDecimal mapBg = new BigDecimal(mapRate);
				Double mapMath = mapBg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
				if (mapMath > 0)
					map.put("SUCCESSRATE", mapMath.toString() + "%");
				else
					map.put("SUCCESSRATE", "0%");
			}
		}
		allResultMap.put("info", resultMap);
		allResultMap.put("pageInfo", resultList);
		return allResultMap;
	}

	@Override
	public Map<String, Object> getReasonDepartmentData(Map<String, Object> paramMap) {
		String sql = "select table_name from user_tables where table_name like 'IDA_CHARGEBILL_DETAIL_%' and table_name not like '%TEST' ";
		List<Map<String, Object>> tableList = jdbcTemplate.queryForList(sql);
		Map<String, Object> result = new HashMap<>();
		List<Map<String, Object>> resultList = new ArrayList<>();
		if (tableList.size() > 0) {
			StringBuffer unionSql = new StringBuffer("with ida_chargebill_detail as(");
			for (int i = 0; i < tableList.size(); i++) {
				String tablename = tableList.get(i).get("TABLE_NAME").toString();
				if (i < tableList.size() - 1) {
					unionSql.append(
							" select month,charge_money,isappeal,item_name,doctor_name,charge_reason,deductedept from "
									+ tablename + " union all ");
				} else {
					unionSql.append(
							" select month,charge_money,isappeal,item_name,doctor_name,charge_reason,deductedept from "
									+ tablename + ") ");
				}
			}
			paramMap.put("unionSql", unionSql);
			resultList = chargeBillAnalysisMapper.getReasonDownDepartment(paramMap);
			for (Map<String, Object> map : resultList) {
				for (Map.Entry<String, Object> entry : map.entrySet()) {
					if (entry.getValue() == null)
						map.put(entry.getKey(), "0");
				}
				Double money = Double.valueOf(map.get("MONEY").toString());
				Double allMoney = Double.valueOf(paramMap.get("allMoney").toString());
				Double mapRate = 0d;
				if(allMoney > 0) {
					mapRate = money * 100 / allMoney;
				}
				BigDecimal mapBg = new BigDecimal(mapRate);
				Double mapMath = mapBg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
				if (mapMath > 0)
					map.put("RATE", mapMath.toString() + "%");
				else
					map.put("RATE", "0%");
			}
		}
		result.put("pageInfo", resultList);
		return result;
	}

	@Override
	public Map<String, Object> getReasonDoctorData(Map<String, Object> paramMap) {
		String sql = "select table_name from user_tables where table_name like 'IDA_CHARGEBILL_DETAIL_%' and table_name not like '%TEST' ";
		List<Map<String, Object>> tableList = jdbcTemplate.queryForList(sql);
		Map<String, Object> result = new HashMap<>();
		List<Map<String, Object>> resultList = new ArrayList<>();
		if (tableList.size() > 0) {
			StringBuffer unionSql = new StringBuffer("with ida_chargebill_detail as(");
			for (int i = 0; i < tableList.size(); i++) {
				String tablename = tableList.get(i).get("TABLE_NAME").toString();
				if (i < tableList.size() - 1) {
					unionSql.append(
							" select month,charge_money,isappeal,item_name,doctor_name,charge_reason,deductedept from "
									+ tablename + " union all ");
				} else {
					unionSql.append(
							" select month,charge_money,isappeal,item_name,doctor_name,charge_reason,deductedept from "
									+ tablename + ") ");
				}
			}
			paramMap.put("unionSql", unionSql);
			resultList = chargeBillAnalysisMapper.getReasonDownDoctor(paramMap);
			for (Map<String, Object> map : resultList) {
				for (Map.Entry<String, Object> entry : map.entrySet()) {
					if (entry.getValue() == null)
						map.put(entry.getKey(), "0");
				}
				Double money = Double.valueOf(map.get("MONEY").toString());
				Double allMoney = Double.valueOf(paramMap.get("allMoney").toString());
				Double mapRate = 0d;
				if(allMoney > 0) {
					mapRate = money * 100 / allMoney;
				}
				BigDecimal mapBg = new BigDecimal(mapRate);
				Double mapMath = mapBg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
				if (mapMath > 0)
					map.put("RATE", mapMath.toString() + "%");
				else
					map.put("RATE", "0%");
			}
		}
		result.put("pageInfo", resultList);
		return result;
	}

	@Override
	public Map<String, Object> getReasonItemData(Map<String, Object> paramMap) {
		String sql = "select table_name from user_tables where table_name like 'IDA_CHARGEBILL_DETAIL_%' and table_name not like '%TEST' ";
		List<Map<String, Object>> tableList = jdbcTemplate.queryForList(sql);
		Map<String, Object> result = new HashMap<>();
		List<Map<String, Object>> resultList = new ArrayList<>();
		if (tableList.size() > 0) {
			StringBuffer unionSql = new StringBuffer("with ida_chargebill_detail as(");
			for (int i = 0; i < tableList.size(); i++) {
				String tablename = tableList.get(i).get("TABLE_NAME").toString();
				if (i < tableList.size() - 1) {
					unionSql.append(
							" select month,charge_money,isappeal,item_name,doctor_name,charge_reason,deductedept from "
									+ tablename + " union all ");
				} else {
					unionSql.append(
							" select month,charge_money,isappeal,item_name,doctor_name,charge_reason,deductedept from "
									+ tablename + ") ");
				}
			}
			paramMap.put("unionSql", unionSql);
			resultList = chargeBillAnalysisMapper.getReasonDownItem(paramMap);
			for (Map<String, Object> map : resultList) {
				for (Map.Entry<String, Object> entry : map.entrySet()) {
					if (entry.getValue() == null)
						map.put(entry.getKey(), "0");
				}
				Double money = Double.valueOf(map.get("MONEY").toString());
				Double allMoney = Double.valueOf(paramMap.get("allMoney").toString());
				Double mapRate = 0d;
				if(allMoney > 0) {
					mapRate = money * 100 / allMoney;
				}
				BigDecimal mapBg = new BigDecimal(mapRate);
				Double mapMath = mapBg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
				if (mapMath > 0)
					map.put("RATE", mapMath.toString() + "%");
				else
					map.put("RATE", "0%");
			}
		}
		result.put("pageInfo", resultList);
		return result;
	}

	@Override
	public Map<String, Object> queryDepartDetail(Map<String, Object> paramMap) {
		String sql = "select table_name from user_tables where table_name like 'IDA_CHARGEBILL_DETAIL_%' and table_name not like '%TEST' ";
		List<Map<String, Object>> tableList = jdbcTemplate.queryForList(sql);
		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("扣款金额", "0");
		resultMap.put("扣款条数", "0");
		resultMap.put("申诉成功金额", "0");
		resultMap.put("申诉失败金额", "0");
		resultMap.put("未申诉金额", "0");
		resultMap.put("申诉成功占比", "0%");
		Map<String, Object> allResultMap = new HashMap<>();
		List<Map<String, Object>> resultList = new ArrayList<>();
		if (tableList.size() > 0) {
			StringBuffer unionSql = new StringBuffer("with ida_chargebill_detail as(");
			for (int i = 0; i < tableList.size(); i++) {
				String tablename = tableList.get(i).get("TABLE_NAME").toString();
				if (i < tableList.size() - 1) {
					unionSql.append(
							" select month,charge_money,isappeal,item_name,doctor_name,charge_reason,deductedept from "
									+ tablename + " union all ");
				} else {
					unionSql.append(
							" select month,charge_money,isappeal,item_name,doctor_name,charge_reason,deductedept from "
									+ tablename + ") ");
				}
			}
			paramMap.put("unionSql", unionSql);
			Map<String, Object> result = new HashMap<>();
			if (chargeBillAnalysisMapper.getReasonDetail(paramMap).size() > 0) {
				result = chargeBillAnalysisMapper.getReasonDetail(paramMap).get(0);
			}

			List<Map<String, Object>> rateList = chargeBillAnalysisMapper.getReasonRate(paramMap);

			result = chargeBillAnalysisMapper.getReasonDetail(paramMap).get(0);
			if (result.get("ALLCHARGE") == null)
				resultMap.put("扣款金额", "0");
			else
				resultMap.put("扣款金额", result.get("ALLCHARGE").toString());
			if (result.get("NUM") == null)
				resultMap.put("扣款条数", "0");
			else
				resultMap.put("扣款条数", result.get("NUM").toString());

			if (rateList.get(0) == null)
				resultMap.put("申诉成功金额", "0");
			else
				resultMap.put("申诉成功金额", rateList.get(0).get("MONEY").toString());
			if (rateList.get(1) == null)
				resultMap.put("申诉失败金额", "0");
			else
				resultMap.put("申诉失败金额", rateList.get(1).get("MONEY").toString());
			if (rateList.get(2) == null)
				resultMap.put("未申诉金额", "0");
			else
				resultMap.put("未申诉金额", rateList.get(2).get("MONEY").toString());
			Double success = Double.valueOf(resultMap.get("申诉成功金额").toString());
			Double allCharge = Double.valueOf(resultMap.get("扣款金额").toString());
			Double rate = 0d;
			if (success + allCharge > 0) {
				rate = success * 100 / (success + allCharge);
				BigDecimal bg = new BigDecimal(rate);
				Double math = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
				if (math > 0)
					resultMap.put("申诉成功占比", math.toString() + "%");
				else
					resultMap.put("申诉成功占比", "0%");
			} else {
				resultMap.put("申诉成功占比", "0%");
			}
			resultList = chargeBillAnalysisMapper.getDepartTable(paramMap);
			resultList.forEach(x -> {
				x.put("ID", UUID.randomUUID().toString().replace("-", "").toLowerCase());
			});
			for (Map<String, Object> map : resultList) {
				for (Map.Entry<String, Object> entry : map.entrySet()) {
					if (entry.getValue() == null)
						map.put(entry.getKey(), "0");
				}
				Double mapSuccess = Double.valueOf(map.get("SUCCESSMONEY").toString());
				Double mapAllCharge = Double.valueOf(map.get("ALLCHARGE").toString());
				Double mapRate = 0d;
				mapRate = mapSuccess * 100 / (mapSuccess + mapAllCharge);
				BigDecimal mapBg = new BigDecimal(mapRate);
				Double mapMath = mapBg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
				if (mapMath > 0)
					map.put("SUCCESSRATE", mapMath.toString() + "%");
				else
					map.put("SUCCESSRATE", "0%");
			}
		}
		allResultMap.put("info", resultMap);
		allResultMap.put("pageInfo", resultList);
		return allResultMap;
	}

	@Override
	public Map<String, Object> getDepartDoctorData(Map<String, Object> paramMap) {
		String sql = "select table_name from user_tables where table_name like 'IDA_CHARGEBILL_DETAIL_%' and table_name not like '%TEST' ";
		List<Map<String, Object>> tableList = jdbcTemplate.queryForList(sql);
		Map<String, Object> result = new HashMap<>();
		List<Map<String, Object>> resultList = new ArrayList<>();
		if (tableList.size() > 0) {
			StringBuffer unionSql = new StringBuffer("with ida_chargebill_detail as(");
			for (int i = 0; i < tableList.size(); i++) {
				String tablename = tableList.get(i).get("TABLE_NAME").toString();
				if (i < tableList.size() - 1) {
					unionSql.append(
							" select month,charge_money,isappeal,item_name,doctor_name,charge_reason,deductedept from "
									+ tablename + " union all ");
				} else {
					unionSql.append(
							" select month,charge_money,isappeal,item_name,doctor_name,charge_reason,deductedept from "
									+ tablename + ") ");
				}
			}
			paramMap.put("unionSql", unionSql);
			resultList = chargeBillAnalysisMapper.getDepartDownDoctor(paramMap);
			for (Map<String, Object> map : resultList) {
				for (Map.Entry<String, Object> entry : map.entrySet()) {
					if (entry.getValue() == null)
						map.put(entry.getKey(), "0");
				}
				Double money = Double.valueOf(map.get("MONEY").toString());
				Double allMoney = Double.valueOf(paramMap.get("allMoney").toString());
				Double mapRate = 0d;
				if(allMoney > 0) {
					mapRate = money * 100 / allMoney;
				}
				BigDecimal mapBg = new BigDecimal(mapRate);
				Double mapMath = mapBg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
				if (mapMath > 0)
					map.put("RATE", mapMath.toString() + "%");
				else
					map.put("RATE", "0%");
			}
		}
		result.put("pageInfo", resultList);
		return result;
	}

	@Override
	public Map<String, Object> getDepartItemData(Map<String, Object> paramMap) {
		String sql = "select table_name from user_tables where table_name like 'IDA_CHARGEBILL_DETAIL_%' and table_name not like '%TEST' ";
		List<Map<String, Object>> tableList = jdbcTemplate.queryForList(sql);
		Map<String, Object> result = new HashMap<>();
		List<Map<String, Object>> resultList = new ArrayList<>();
		if (tableList.size() > 0) {
			StringBuffer unionSql = new StringBuffer("with ida_chargebill_detail as(");
			for (int i = 0; i < tableList.size(); i++) {
				String tablename = tableList.get(i).get("TABLE_NAME").toString();
				if (i < tableList.size() - 1) {
					unionSql.append(
							" select month,charge_money,isappeal,item_name,doctor_name,charge_reason,deductedept from "
									+ tablename + " union all ");
				} else {
					unionSql.append(
							" select month,charge_money,isappeal,item_name,doctor_name,charge_reason,deductedept from "
									+ tablename + ") ");
				}
			}
			paramMap.put("unionSql", unionSql);
			resultList = chargeBillAnalysisMapper.getDepartDownItem(paramMap);
			for (Map<String, Object> map : resultList) {
				for (Map.Entry<String, Object> entry : map.entrySet()) {
					if (entry.getValue() == null)
						map.put(entry.getKey(), "0");
				}
				Double money = Double.valueOf(map.get("MONEY").toString());
				Double allMoney = Double.valueOf(paramMap.get("allMoney").toString());
				Double mapRate = 0d;
				if(allMoney > 0) {
					mapRate = money * 100 / allMoney;
				}
				BigDecimal mapBg = new BigDecimal(mapRate);
				Double mapMath = mapBg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
				if (mapMath > 0)
					map.put("RATE", mapMath.toString() + "%");
				else
					map.put("RATE", "0%");
			}
		}
		result.put("pageInfo", resultList);
		return result;
	}

	@Override
	public Map<String, Object> getDepartReasonData(Map<String, Object> paramMap) {
		String sql = "select table_name from user_tables where table_name like 'IDA_CHARGEBILL_DETAIL_%' and table_name not like '%TEST' ";
		List<Map<String, Object>> tableList = jdbcTemplate.queryForList(sql);
		Map<String, Object> result = new HashMap<>();
		List<Map<String, Object>> resultList = new ArrayList<>();
		if (tableList.size() > 0) {
			StringBuffer unionSql = new StringBuffer("with ida_chargebill_detail as(");
			for (int i = 0; i < tableList.size(); i++) {
				String tablename = tableList.get(i).get("TABLE_NAME").toString();
				if (i < tableList.size() - 1) {
					unionSql.append(
							" select month,charge_money,isappeal,item_name,doctor_name,charge_reason,deductedept from "
									+ tablename + " union all ");
				} else {
					unionSql.append(
							" select month,charge_money,isappeal,item_name,doctor_name,charge_reason,deductedept from "
									+ tablename + ") ");
				}
			}
			paramMap.put("unionSql", unionSql);
			resultList = chargeBillAnalysisMapper.getDepartDownReason(paramMap);
			for (Map<String, Object> map : resultList) {
				for (Map.Entry<String, Object> entry : map.entrySet()) {
					if (entry.getValue() == null)
						map.put(entry.getKey(), "0");
				}
				Double money = Double.valueOf(map.get("MONEY").toString());
				Double allMoney = Double.valueOf(paramMap.get("allMoney").toString());
				Double mapRate = 0d;
				if(allMoney > 0) {
					mapRate = money * 100 / allMoney;
				}
				BigDecimal mapBg = new BigDecimal(mapRate);
				Double mapMath = mapBg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
				if (mapMath > 0)
					map.put("RATE", mapMath.toString() + "%");
				else
					map.put("RATE", "0%");
			}
		}
		result.put("pageInfo", resultList);
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> dataDownload(Map<String, Object> paramMap) {
		String sql = "select table_name from user_tables where table_name like 'IDA_CHARGEBILL_DETAIL_%' and table_name not like '%TEST' ";
		List<Map<String, Object>> tableList = jdbcTemplate.queryForList(sql);
		List<Map<String, Object>> list = new ArrayList<>();
		List<Map<String, Object>> cols = new ArrayList<>();
		if (tableList.size() > 0) {
			StringBuffer unionSql = new StringBuffer("with ida_chargebill_detail as(");
			for (int i = 0; i < tableList.size(); i++) {
				String tablename = tableList.get(i).get("TABLE_NAME").toString();
				if (i < tableList.size() - 1) {
					unionSql.append(
							" select month,charge_money,isappeal,item_name,doctor_name,charge_reason,deductedept from "
									+ tablename + " union all ");
				} else {
					unionSql.append(
							" select month,charge_money,isappeal,item_name,doctor_name,charge_reason,deductedept from "
									+ tablename + ") ");
				}
			}
			paramMap.put("unionSql", unionSql);
			if ("1".equals(paramMap.get("downloadType").toString())) {
				list = (List<Map<String, Object>>) queryDepartDetail(paramMap).get("pageInfo");
				cols = new ArrayList<>(Arrays.asList(
						ImmutableMap.of("COLUMN_NAME", "科室", "COLUMN_CODE", "MAIN_DEPARTMENT", "DATA_TYPE", "VARCHAR2"),
						ImmutableMap.of("COLUMN_NAME", "扣款金额", "COLUMN_CODE", "ALLCHARGE", "DATA_TYPE", "VARCHAR2"),
						ImmutableMap.of("COLUMN_NAME", "扣款条数", "COLUMN_CODE", "NUM", "DATA_TYPE", "VARCHAR2"),
						ImmutableMap.of("COLUMN_NAME", "申诉成功金额", "COLUMN_CODE", "SUCCESSMONEY", "DATA_TYPE",
								"VARCHAR2"),
						ImmutableMap.of("COLUMN_NAME", "申诉成功占比", "COLUMN_CODE", "SUCCESSRATE", "DATA_TYPE", "VARCHAR2"),
						ImmutableMap.of("COLUMN_NAME", "申诉失败金额", "COLUMN_CODE", "FAILUREMONEY", "DATA_TYPE",
								"VARCHAR2"),
						ImmutableMap.of("COLUMN_NAME", "未申诉金额", "COLUMN_CODE", "NONEMONEY", "DATA_TYPE", "VARCHAR2")));
			} else if ("2".equals(paramMap.get("downloadType").toString())) {
				list = (List<Map<String, Object>>) queryItemDetail(paramMap).get("pageInfo");
				cols = new ArrayList<>(Arrays.asList(
						ImmutableMap.of("COLUMN_NAME", "扣款项目", "COLUMN_CODE", "ITEM_NAME", "DATA_TYPE", "VARCHAR2"),
						ImmutableMap.of("COLUMN_NAME", "扣款金额", "COLUMN_CODE", "ALLCHARGE", "DATA_TYPE", "VARCHAR2"),
						ImmutableMap.of("COLUMN_NAME", "扣款条数", "COLUMN_CODE", "NUM", "DATA_TYPE", "VARCHAR2"),
						ImmutableMap.of("COLUMN_NAME", "申诉成功金额", "COLUMN_CODE", "SUCCESSMONEY", "DATA_TYPE",
								"VARCHAR2"),
						ImmutableMap.of("COLUMN_NAME", "申诉成功占比", "COLUMN_CODE", "SUCCESSRATE", "DATA_TYPE", "VARCHAR2"),
						ImmutableMap.of("COLUMN_NAME", "申诉失败金额", "COLUMN_CODE", "FAILUREMONEY", "DATA_TYPE",
								"VARCHAR2"),
						ImmutableMap.of("COLUMN_NAME", "未申诉金额", "COLUMN_CODE", "NONEMONEY", "DATA_TYPE", "VARCHAR2")));
			} else if ("3".equals(paramMap.get("downloadType").toString())) {
				list = (List<Map<String, Object>>) queryReasonDetail(paramMap).get("pageInfo");
				cols = new ArrayList<>(Arrays.asList(
						ImmutableMap.of("COLUMN_NAME", "扣款原因", "COLUMN_CODE", "CHARGE_REASON", "DATA_TYPE", "VARCHAR2"),
						ImmutableMap.of("COLUMN_NAME", "扣款金额", "COLUMN_CODE", "ALLCHARGE", "DATA_TYPE", "VARCHAR2"),
						ImmutableMap.of("COLUMN_NAME", "扣款条数", "COLUMN_CODE", "NUM", "DATA_TYPE", "VARCHAR2"),
						ImmutableMap.of("COLUMN_NAME", "申诉成功金额", "COLUMN_CODE", "SUCCESSMONEY", "DATA_TYPE",
								"VARCHAR2"),
						ImmutableMap.of("COLUMN_NAME", "申诉成功占比", "COLUMN_CODE", "SUCCESSRATE", "DATA_TYPE", "VARCHAR2"),
						ImmutableMap.of("COLUMN_NAME", "申诉失败金额", "COLUMN_CODE", "FAILUREMONEY", "DATA_TYPE",
								"VARCHAR2"),
						ImmutableMap.of("COLUMN_NAME", "未申诉金额", "COLUMN_CODE", "NONEMONEY", "DATA_TYPE", "VARCHAR2")));
			}
		} else {
			if ("1".equals(paramMap.get("downloadType").toString())) {
				cols = new ArrayList<>(Arrays.asList(
						ImmutableMap.of("COLUMN_NAME", "科室", "COLUMN_CODE", "MAIN_DEPARTMENT", "DATA_TYPE", "VARCHAR2"),
						ImmutableMap.of("COLUMN_NAME", "扣款金额", "COLUMN_CODE", "ALLCHARGE", "DATA_TYPE", "VARCHAR2"),
						ImmutableMap.of("COLUMN_NAME", "扣款条数", "COLUMN_CODE", "NUM", "DATA_TYPE", "VARCHAR2"),
						ImmutableMap.of("COLUMN_NAME", "申诉成功金额", "COLUMN_CODE", "SUCCESSMONEY", "DATA_TYPE",
								"VARCHAR2"),
						ImmutableMap.of("COLUMN_NAME", "申诉成功占比", "COLUMN_CODE", "SUCCESSRATE", "DATA_TYPE", "VARCHAR2"),
						ImmutableMap.of("COLUMN_NAME", "申诉失败金额", "COLUMN_CODE", "FAILUREMONEY", "DATA_TYPE",
								"VARCHAR2"),
						ImmutableMap.of("COLUMN_NAME", "未申诉金额", "COLUMN_CODE", "NONEMONEY", "DATA_TYPE", "VARCHAR2")));
			} else if ("2".equals(paramMap.get("downloadType").toString())) {
				cols = new ArrayList<>(Arrays.asList(
						ImmutableMap.of("COLUMN_NAME", "扣款项目", "COLUMN_CODE", "ITEM_NAME", "DATA_TYPE", "VARCHAR2"),
						ImmutableMap.of("COLUMN_NAME", "扣款金额", "COLUMN_CODE", "ALLCHARGE", "DATA_TYPE", "VARCHAR2"),
						ImmutableMap.of("COLUMN_NAME", "扣款条数", "COLUMN_CODE", "NUM", "DATA_TYPE", "VARCHAR2"),
						ImmutableMap.of("COLUMN_NAME", "申诉成功金额", "COLUMN_CODE", "SUCCESSMONEY", "DATA_TYPE",
								"VARCHAR2"),
						ImmutableMap.of("COLUMN_NAME", "申诉成功占比", "COLUMN_CODE", "SUCCESSRATE", "DATA_TYPE", "VARCHAR2"),
						ImmutableMap.of("COLUMN_NAME", "申诉失败金额", "COLUMN_CODE", "FAILUREMONEY", "DATA_TYPE",
								"VARCHAR2"),
						ImmutableMap.of("COLUMN_NAME", "未申诉金额", "COLUMN_CODE", "NONEMONEY", "DATA_TYPE", "VARCHAR2")));
			} else if ("3".equals(paramMap.get("downloadType").toString())) {
				cols = new ArrayList<>(Arrays.asList(
						ImmutableMap.of("COLUMN_NAME", "扣款原因", "COLUMN_CODE", "CHARGE_REASON", "DATA_TYPE", "VARCHAR2"),
						ImmutableMap.of("COLUMN_NAME", "扣款金额", "COLUMN_CODE", "ALLCHARGE", "DATA_TYPE", "VARCHAR2"),
						ImmutableMap.of("COLUMN_NAME", "扣款条数", "COLUMN_CODE", "NUM", "DATA_TYPE", "VARCHAR2"),
						ImmutableMap.of("COLUMN_NAME", "申诉成功金额", "COLUMN_CODE", "SUCCESSMONEY", "DATA_TYPE",
								"VARCHAR2"),
						ImmutableMap.of("COLUMN_NAME", "申诉成功占比", "COLUMN_CODE", "SUCCESSRATE", "DATA_TYPE", "VARCHAR2"),
						ImmutableMap.of("COLUMN_NAME", "申诉失败金额", "COLUMN_CODE", "FAILUREMONEY", "DATA_TYPE",
								"VARCHAR2"),
						ImmutableMap.of("COLUMN_NAME", "未申诉金额", "COLUMN_CODE", "NONEMONEY", "DATA_TYPE", "VARCHAR2")));
			}
		}
		List<ExcelExportEntity> entityList = new ArrayList<>();
		for (Map<String, Object> col : cols) {
			String name = col.get("COLUMN_NAME").toString();
			String key = col.get("COLUMN_CODE").toString();
			ExcelExportEntity excelExportEntity = new ExcelExportEntity(name, key);
			entityList.add(excelExportEntity);
		}
		Map<String, Object> result = new HashMap<>();
		result.put("list", list);
		result.put("entityList", entityList);
		return result;
	}

	@Override
	public Map<String, Object> getDepartDoctorDetail(Map<String, Object> paramMap) {
		String sql = "select table_name from user_tables where table_name like 'IDA_CHARGEBILL_DETAIL_%' and table_name not like '%TEST' ";
		List<Map<String, Object>> tableList = jdbcTemplate.queryForList(sql);
		Map<String, Object> result = new HashMap<>();
		List<Map<String, Object>> resultList = new ArrayList<>();
		if (tableList.size() > 0) {
			StringBuffer unionSql = new StringBuffer("with ida_chargebill_detail as(");
			for (int i = 0; i < tableList.size(); i++) {
				String tablename = tableList.get(i).get("TABLE_NAME").toString();
				if (i < tableList.size() - 1) {
					unionSql.append(
							" select month,charge_money,isappeal,item_name,doctor_name,charge_reason,deductedept from "
									+ tablename + " union all ");
				} else {
					unionSql.append(
							" select month,charge_money,isappeal,item_name,doctor_name,charge_reason,deductedept from "
									+ tablename + ") ");
				}
			}
			paramMap.put("unionSql", unionSql);
			resultList = chargeBillAnalysisMapper.getDepartDoctor(paramMap);
			for (Map<String, Object> map : resultList) {
				for (Map.Entry<String, Object> entry : map.entrySet()) {
					if (entry.getValue() == null)
						map.put(entry.getKey(), "0");
				}
				Double money = Double.valueOf(map.get("MONEY").toString());
				Double allMoney = Double.valueOf(paramMap.get("allMoney").toString());
				Double mapRate = 0d;
				if(allMoney > 0) {
					mapRate = money * 100 / allMoney;
				}
				BigDecimal mapBg = new BigDecimal(mapRate);
				Double mapMath = mapBg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
				if (mapMath > 0)
					map.put("RATE", mapMath.toString() + "%");
				else
					map.put("RATE", "0%");
			}
		}
		result.put("pageInfo", resultList);
		return result;
	}

	@Override
	public Map<String, Object> getDepartItemDetail(Map<String, Object> paramMap) {
		String sql = "select table_name from user_tables where table_name like 'IDA_CHARGEBILL_DETAIL_%' and table_name not like '%TEST' ";
		List<Map<String, Object>> tableList = jdbcTemplate.queryForList(sql);
		Map<String, Object> result = new HashMap<>();
		List<Map<String, Object>> resultList = new ArrayList<>();
		if (tableList.size() > 0) {
			StringBuffer unionSql = new StringBuffer("with ida_chargebill_detail as(");
			for (int i = 0; i < tableList.size(); i++) {
				String tablename = tableList.get(i).get("TABLE_NAME").toString();
				if (i < tableList.size() - 1) {
					unionSql.append(
							" select month,charge_money,isappeal,item_name,doctor_name,charge_reason,deductedept from "
									+ tablename + " union all ");
				} else {
					unionSql.append(
							" select month,charge_money,isappeal,item_name,doctor_name,charge_reason,deductedept from "
									+ tablename + ") ");
				}
			}
			paramMap.put("unionSql", unionSql);
			resultList = chargeBillAnalysisMapper.getDepartItem(paramMap);
			for (Map<String, Object> map : resultList) {
				for (Map.Entry<String, Object> entry : map.entrySet()) {
					if (entry.getValue() == null)
						map.put(entry.getKey(), "0");
				}
				Double money = Double.valueOf(map.get("MONEY").toString());
				Double allMoney = Double.valueOf(paramMap.get("allMoney").toString());
				Double mapRate = 0d;
				if(allMoney > 0) {
					mapRate = money * 100 / allMoney;
				}
				BigDecimal mapBg = new BigDecimal(mapRate);
				Double mapMath = mapBg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
				if (mapMath > 0)
					map.put("RATE", mapMath.toString() + "%");
				else
					map.put("RATE", "0%");
			}
		}
		result.put("pageInfo", resultList);
		return result;
	}

	@Override
	public Map<String, Object> getDepartReasonDetail(Map<String, Object> paramMap) {
		String sql = "select table_name from user_tables where table_name like 'IDA_CHARGEBILL_DETAIL_%' and table_name not like '%TEST' ";
		List<Map<String, Object>> tableList = jdbcTemplate.queryForList(sql);
		Map<String, Object> result = new HashMap<>();
		List<Map<String, Object>> resultList = new ArrayList<>();
		if (tableList.size() > 0) {
			StringBuffer unionSql = new StringBuffer("with ida_chargebill_detail as(");
			for (int i = 0; i < tableList.size(); i++) {
				String tablename = tableList.get(i).get("TABLE_NAME").toString();
				if (i < tableList.size() - 1) {
					unionSql.append(
							" select month,charge_money,isappeal,item_name,doctor_name,charge_reason,deductedept from "
									+ tablename + " union all ");
				} else {
					unionSql.append(
							" select month,charge_money,isappeal,item_name,doctor_name,charge_reason,deductedept from "
									+ tablename + ") ");
				}
			}
			paramMap.put("unionSql", unionSql);
			resultList = chargeBillAnalysisMapper.getDepartReason(paramMap);
			for (Map<String, Object> map : resultList) {
				for (Map.Entry<String, Object> entry : map.entrySet()) {
					if (entry.getValue() == null)
						map.put(entry.getKey(), "0");
				}
				Double money = Double.valueOf(map.get("MONEY").toString());
				Double allMoney = Double.valueOf(paramMap.get("allMoney").toString());
				Double mapRate = 0d;
				if(allMoney > 0){
					mapRate = money * 100 / allMoney;
				}
				BigDecimal mapBg = new BigDecimal(mapRate);
				Double mapMath = mapBg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
				if (mapMath > 0)
					map.put("RATE", mapMath.toString() + "%");
				else
					map.put("RATE", "0%");
			}
		}
		result.put("pageInfo", resultList);
		return result;
	}

	@Override
	public Map<String, Object> getUtrlaDoctor(Map<String, Object> paramMap) {
		String sql = "select table_name from user_tables where table_name like 'IDA_CHARGEBILL_DETAIL_%' and table_name not like '%TEST' ";
		List<Map<String, Object>> tableList = jdbcTemplate.queryForList(sql);
		Map<String, Object> result = new HashMap<>();
		List<Map<String, Object>> resultList = new ArrayList<>();
		if (tableList.size() > 0) {
			StringBuffer unionSql = new StringBuffer("with ida_chargebill_detail as(");
			for (int i = 0; i < tableList.size(); i++) {
				String tablename = tableList.get(i).get("TABLE_NAME").toString();
				if (i < tableList.size() - 1) {
					unionSql.append(
							" select month,charge_money,isappeal,item_name,doctor_name,charge_reason,deductedept from "
									+ tablename + " union all ");
				} else {
					unionSql.append(
							" select month,charge_money,isappeal,item_name,doctor_name,charge_reason,deductedept from "
									+ tablename + ") ");
				}
			}
			paramMap.put("unionSql", unionSql);
			resultList = chargeBillAnalysisMapper.getUtrlaDoctor(paramMap);
			for (Map<String, Object> map : resultList) {
				for (Map.Entry<String, Object> entry : map.entrySet()) {
					if (entry.getValue() == null)
						map.put(entry.getKey(), "0");
				}
				Double money = Double.valueOf(map.get("MONEY").toString());
				Double allMoney = Double.valueOf(paramMap.get("allMoney").toString());
				Double mapRate = 0d;
				if(allMoney > 0) {
					mapRate = money * 100 / allMoney;
				}
				BigDecimal mapBg = new BigDecimal(mapRate);
				Double mapMath = mapBg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
				if (mapMath > 0)
					map.put("RATE", mapMath.toString() + "%");
				else
					map.put("RATE", "0%");
			}
		}
		result.put("pageInfo", resultList);
		return result;
	}

	@Override
	public Map<String, Object> getUtrlaReason(Map<String, Object> paramMap) {
		String sql = "select table_name from user_tables where table_name like 'IDA_CHARGEBILL_DETAIL_%' and table_name not like '%TEST' ";
		List<Map<String, Object>> tableList = jdbcTemplate.queryForList(sql);
		Map<String, Object> result = new HashMap<>();
		List<Map<String, Object>> resultList = new ArrayList<>();
		if (tableList.size() > 0) {
			StringBuffer unionSql = new StringBuffer("with ida_chargebill_detail as(");
			for (int i = 0; i < tableList.size(); i++) {
				String tablename = tableList.get(i).get("TABLE_NAME").toString();
				if (i < tableList.size() - 1) {
					unionSql.append(
							" select month,charge_money,isappeal,item_name,doctor_name,charge_reason,deductedept from "
									+ tablename + " union all ");
				} else {
					unionSql.append(
							" select month,charge_money,isappeal,item_name,doctor_name,charge_reason,deductedept from "
									+ tablename + ") ");
				}
			}
			paramMap.put("unionSql", unionSql);
			resultList = chargeBillAnalysisMapper.getUtrlaReason(paramMap);
			for (Map<String, Object> map : resultList) {
				for (Map.Entry<String, Object> entry : map.entrySet()) {
					if (entry.getValue() == null)
						map.put(entry.getKey(), "0");
				}
				Double money = Double.valueOf(map.get("MONEY").toString());
				Double allMoney = Double.valueOf(paramMap.get("allMoney").toString());
				Double mapRate = 0d;
				if(allMoney > 0) {
					mapRate = money * 100 / allMoney;
				}
				BigDecimal mapBg = new BigDecimal(mapRate);
				Double mapMath = mapBg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
				if (mapMath > 0)
					map.put("RATE", mapMath.toString() + "%");
				else
					map.put("RATE", "0%");
			}
		}
		result.put("pageInfo", resultList);
		return result;
	}

}
