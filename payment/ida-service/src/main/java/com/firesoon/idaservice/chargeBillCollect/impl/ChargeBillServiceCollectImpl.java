package com.firesoon.idaservice.chargeBillCollect.impl;

import com.firesoon.idaservice.chargeBillCollect.ChargeBillCollectService;
import com.firesoon.paymentmapper.chargeBillCollect.ChargeBillCollectMapper;
import com.google.common.collect.ImmutableMap;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import org.jeecgframework.poi.excel.entity.params.ExcelExportEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Primary
@Service
public class ChargeBillServiceCollectImpl implements ChargeBillCollectService {

	@Autowired
	private ChargeBillCollectMapper chargeBillCollectMapper;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public Map<String, Object> query(Map<String, Object> map) {
		String sql = "select table_name from user_tables where table_name like 'IDA_CHARGEBILL_DETAIL_%' and table_name not like '%TEST' ";
		List<Map<String, Object>> tableList = jdbcTemplate.queryForList(sql);
		List<String> dateList = new ArrayList<>();
		try {
			dateList = getDateList(map);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		List<Map<String, Object>> finalList = new ArrayList<>();
		Map<String, Object> resultMap = new HashMap<>();
		Map<String, Object> result = new HashMap<>();
		result.put("allcharge", "0");
		result.put("allback", "0");
		if ("1".equals(map.get("dateType").toString())) {
			result.put("mostmonth", "-月份");
			result.put("bviousmonth", "-");
		} else if ("2".equals(map.get("dateType").toString())) {
			result.put("mostmonth", "-年");
			result.put("bviousmonth", "-");
		} else if ("3".equals(map.get("dateType").toString())) {
			result.put("mostmonth", "-季度");
			result.put("bviousmonth", "-");
		}
		result.put("rate", "0%");
		result.put("mostdepart", "-");
		if ("3".equals(map.get("dateType").toString())) {
			result.put("startDate", map.get("startDate").toString() + "年Q1");
			result.put("endDate", map.get("endDate").toString() + "年Q4");
		} else {
			result.put("startDate", map.get("startDate").toString());
			result.put("endDate", map.get("endDate").toString());
		}
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
			map.put("unionSql", unionSql);
			List<Map<String, Object>> moneyList = chargeBillCollectMapper.getAllMoney(map);
			if (moneyList.get(0) != null && moneyList.get(0).get("ALLCHARGE") != null) {
				result.put("allcharge", moneyList.get(0).getOrDefault("ALLCHARGE", "0"));
			} else {
				result.put("allcharge", "0");
			}
			if (moneyList.get(1) != null && moneyList.get(1).get("ALLCHARGE") != null) { //为1的是allback
				result.put("allback", moneyList.get(1).getOrDefault("ALLCHARGE", "0"));
			} else {
				result.put("allback", "0");
			}
			if ("3".equals(map.get("dateType")) && chargeBillCollectMapper.getMonthMax(map).size() > 0) {
				List<Map<String, Object>> list = chargeBillCollectMapper.getMonthMax(map);
				String months = "";
				String max = list.get(0).get("TOTAL").toString();
				for (int i = 0; i < list.size(); i++) {
					String total = list.get(i).get("TOTAL").toString();
					if (max.equals(total)) {
						String date = list.get(i).get("TIME").toString();
						String[] dateArr = date.split("-");
						months += dateArr[0] + "年Q" + dateArr[1] + "、";
					}
				}
				result.put("mostmonth", months.substring(0, months.length() - 1) + "季度");
			} else if ("2".equals(map.get("dateType")) && chargeBillCollectMapper.getMonthMax(map).size() > 0) {
				List<Map<String, Object>> list = chargeBillCollectMapper.getMonthMax(map);
				String months = "";
				String max = list.get(0).get("TOTAL").toString();
				for (int i = 0; i < list.size(); i++) {
					String total = list.get(i).get("TOTAL").toString();
					if (max.equals(total)) {
						String date = list.get(i).get("TIME").toString();
						months += date + "、";
					}
				}
				result.put("mostmonth", months.substring(0, months.length() - 1) + "年");
			} else if ("1".equals(map.get("dateType")) && chargeBillCollectMapper.getMonthMax(map).size() > 0) {
				List<Map<String, Object>> list = chargeBillCollectMapper.getMonthMax(map);
				String months = "";
				String max = list.get(0).get("TOTAL").toString();
				for (int i = 0; i < list.size(); i++) {
					String total = list.get(i).get("TOTAL").toString();
					if (max.equals(total)) {
						String date = list.get(i).get("TIME").toString();
						months += date + "、";
					}
				}
				result.put("mostmonth", months.substring(0, months.length() - 1) + "月份");
			}
//			else {
//				result.put("mostmonth", "-");
//			}
			if ("3".equals(map.get("dateType")) && chargeBillCollectMapper.getYearMax(map).size() > 0) {
				List<Map<String, Object>> list = chargeBillCollectMapper.getYearMax(map);
				String months = "";
				String max = list.get(0).get("RATE").toString();
				for (int i = 0; i < list.size(); i++) {
					String total = list.get(i).get("RATE").toString();
					if (max.equals(total)) {
						String date = list.get(i).get("TIME").toString();
						String[] dateArr = date.split("-");
						months += dateArr[0] + "年Q" + dateArr[1] + "、";
					}
				}
				result.put("bviousmonth", months.substring(0, months.length() - 1) + "季度");
			} else if ("2".equals(map.get("dateType")) && chargeBillCollectMapper.getYearMax(map).size() > 0) {
				List<Map<String, Object>> list = chargeBillCollectMapper.getYearMax(map);
				String months = "";
				String max = list.get(0).get("RATE").toString();
				for (int i = 0; i < list.size(); i++) {
					String total = list.get(i).get("RATE").toString();
					if (max.equals(total)) {
						String date = list.get(i).get("TIME").toString();
						months += date + "、";
					}
				}
				result.put("bviousmonth", months.substring(0, months.length() - 1) + "年");
			} else if ("1".equals(map.get("dateType")) && chargeBillCollectMapper.getYearMax(map).size() > 0) {
				List<Map<String, Object>> list = chargeBillCollectMapper.getYearMax(map);
				String months = "";
				String max = list.get(0).get("RATE").toString();
				for (int i = 0; i < list.size(); i++) {
					String total = list.get(i).get("RATE").toString();
					if (max.equals(total)) {
						String date = list.get(i).get("TIME").toString();
						months += date + "、";
					}
				}
				result.put("bviousmonth", months.substring(0, months.length() - 1) + "月份");
			} else {
				result.put("bviousmonth", "-");
			}
			Double money = 0d;
			if (chargeBillCollectMapper.getDepartmentMoney(map).size() == 0) {
				result.put("mostdepart", "-");
			} else {
				result.put("mostdepart", "-");
				if (chargeBillCollectMapper.getDepartmentMoney(map).get(0).get("MONEY") != null) {

					List<Map<String, Object>> list = chargeBillCollectMapper.getDepartmentMoney(map);
					String departs = "";
					String max = list.get(0).get("MONEY").toString();
					for (int i = 0; i < list.size(); i++) {
						String total = list.get(i).get("MONEY").toString();
						if (max.equals(total)) {
							String depart = list.get(i).get("DEPARTMENT").toString();
							departs += depart + "、";
						}
					}
					result.put("mostdepart", departs.substring(0, departs.length() - 1));

					money = Double.valueOf(chargeBillCollectMapper.getDepartmentMoney(map).get(0)
							.getOrDefault("MONEY", "0").toString());
				}
			}
			Double allMoney = Double.valueOf(result.get("allcharge").toString());
			Double rate = 0d;
			if (allMoney > 0) {
				rate = money * 100 / allMoney;
				BigDecimal bg = new BigDecimal(rate);
				Double math = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
				if (math > 0)
					result.put("rate", math.toString() + "%");
				else
					result.put("rate", "0%");
			}
			List<Map<String, Object>> resultMapList = chargeBillCollectMapper.getData(map);
			resultMapList.addAll(chargeBillCollectMapper.getAllDepartData(map));
			Set<String> departSet = new HashSet<>();
			resultMapList.forEach(x -> {
				x.forEach((k, v) -> {
					if ("DEPARTMENT".equals(k))
						departSet.add(v.toString());
					if (v == null)
						x.put(k, "0");
				});
			});
			List<String> departList = new ArrayList<>(departSet);
			for (String depart : departList) {
				Map<String, Object> dataMap = new HashMap<>();
				Map<String, String> yearMonth = new HashMap<>();
				for (int i = 0; i < dateList.size(); i++) {
					dataMap.put(dateList.get(i), "0");
				}
				Double num = 0d;
				dataMap.put("科室", depart);
				for (Map<String, Object> x : resultMapList) {
					List<String> yearCompare = new ArrayList<>();
					List<String> monthCompare = new ArrayList<>();
					if (depart.equals(x.get("DEPARTMENT").toString()) && !"3".equals(map.get("dateType"))) {
						dataMap.put(x.get("TIME").toString(), x.get("MONEY"));
						Double nowMoney = Double.valueOf(x.get("MONEY").toString());
						Double yearMoney = Double.valueOf(x.get("YEARMONEY").toString());
						Double monthMoney = Double.valueOf(x.get("MONTHMONEY").toString());
						Double yearRate = 0d;
						Double monthRate = 0d;
//						Double yearMoneyCS = yearMoney == 0.0 ? 1.0 : yearMoney;
//						Double monthMoneyCS = monthMoney == 0.0 ? 1.0 : monthMoney;
						if (yearMoney > 0) {
							yearRate = (nowMoney - yearMoney) * 100 / Math.abs(yearMoney);
							BigDecimal bg = new BigDecimal(yearRate);
							yearRate = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
						}
						if (monthMoney > 0) {
							monthRate = (nowMoney - monthMoney) * 100 / Math.abs(monthMoney);
							BigDecimal bg1 = new BigDecimal(monthRate);
							monthRate = bg1.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
						}
						String yearResult = "";
						String monthResult = "";
						if (yearRate == 0d) {
							yearResult = "0%";
						} else {
							yearResult = yearRate.toString() + "%";
						}
						if (monthRate == 0d) {
							monthResult = "0%";
						} else {
							monthResult = monthRate.toString() + "%";
						}
						if (yearMoney == 0) {
							yearResult = "-";
						}
						if (monthMoney == 0) {
							monthResult = "-";
						}
						yearMonth.put(x.get("TIME").toString(), yearResult + "#" + monthResult);
						num += Double.valueOf(x.get("MONEY").toString());
					} else if (depart.equals(x.get("DEPARTMENT").toString()) && "3".equals(map.get("dateType"))) {
						String[] dateTime = x.get("TIME").toString().split("-");
						dataMap.put(dateTime[0] + "年Q" + dateTime[1], x.get("MONEY"));
						Double nowMoney = Double.valueOf(x.get("MONEY").toString());
						Double yearMoney = Double.valueOf(x.get("YEARMONEY").toString());
						Double monthMoney = Double.valueOf(x.get("MONTHMONEY").toString());
						Double yearRate = 0d;
						Double monthRate = 0d;
//						Double yearMoneyCS = yearMoney == 0.0 ? 1.0 : yearMoney;
//						Double monthMoneyCS = monthMoney == 0.0 ? 1.0 : monthMoney;
						if (yearMoney > 0) { //同比
							yearRate = (nowMoney - yearMoney) * 100 / Math.abs(yearMoney);
							BigDecimal bg = new BigDecimal(yearRate);
							yearRate = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
						}
						if (monthMoney > 0) { //环比
							monthRate = (nowMoney - monthMoney) * 100 / Math.abs(monthMoney);
							BigDecimal bg1 = new BigDecimal(monthRate);
							monthRate = bg1.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
						}
						String yearResult = "";
						String monthResult = "";
						if (yearRate == 0d) {
							yearResult = "0%";
						} else {
							yearResult = yearRate.toString() + "%";
						}
						if (monthRate == 0d) {
							monthResult = "0%";
						} else {
							monthResult = monthRate.toString() + "%";
						}
						if (yearMoney == 0) {
							yearResult = "-";
						}
						if (monthMoney == 0) {
							monthResult = "-";
						}
						yearMonth.put(dateTime[0] + "年Q" + dateTime[1], yearResult + "#" + monthResult);
						num += Double.valueOf(x.get("MONEY").toString());
					}
					for (int i = 1; i < dateList.size() - 1; i++) {
						if (yearMonth.get(dateList.get(i)) != null ) {
							if(!"2".equals(map.get("dateType").toString())){
								yearCompare.add(yearMonth.get(dateList.get(i)).split("#")[0]);
								monthCompare.add(yearMonth.get(dateList.get(i)).split("#")[1]);
							}else{ // =2
								yearCompare.add("-");
								monthCompare.add(yearMonth.get(dateList.get(i)).split("#")[1]);
							}

						} else {
							yearCompare.add("-");
							monthCompare.add("-");
						}
					}
					dataMap.put("yearCompare", yearCompare);
					dataMap.put("monthCompare", monthCompare);
				}
				BigDecimal bg = new BigDecimal(num);
				num = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
				dataMap.put("汇总", num);
				finalList.add(dataMap);
			}
		}
		resultMap.put("info", result);
		List<Map<String, String>> dateInfoList = new ArrayList<>();
		for (int i = 1; i < dateList.size() - 1; i++) {
			dateInfoList.add(ImmutableMap.of("KEY", dateList.get(i)));
		}
		resultMap.put("tableInfo", dateInfoList);
		resultMap.put("pageInfo", finalList);
		return resultMap;
	}

	@Override
	public Map<String, Object> download(Map<String, Object> map) {
		String sql = "select table_name from user_tables where table_name like 'IDA_CHARGEBILL_DETAIL_%' and table_name not like '%TEST' ";
		List<Map<String, Object>> tableList = jdbcTemplate.queryForList(sql);
		List<String> dateList = new ArrayList<>();
		try {
			dateList = getDateList(map);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Map<String, Object> result = new HashMap<>();
		result.put("allcharge", "0");
		result.put("allback", "0");
		result.put("mostmonth", "-");
		result.put("bviousmonth", "-");
		result.put("rate", "0%");
		result.put("mostdepart", "-");
		result.put("startDate", map.get("startDate").toString());
		result.put("endDate", map.get("endDate").toString());
		Map<String, Object> resultMap = new HashMap<>();
		List<Map<String, Object>> finalList = new ArrayList<>();
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
			map.put("unionSql", unionSql);
			List<Map<String, Object>> moneyList = chargeBillCollectMapper.getAllMoney(map);
			if (moneyList.get(0) != null && moneyList.get(0).get("ALLCHARGE") != null) {
				result.put("allcharge", chargeBillCollectMapper.getAllMoney(map).get(0).getOrDefault("ALLCHARGE", "0"));
			} else {
				result.put("allcharge", "0");
			}
			if (moneyList.get(1) != null && moneyList.get(1).get("ALLBACK") != null) {
				result.put("allback", chargeBillCollectMapper.getAllMoney(map).get(1).getOrDefault("ALLBACK", "0"));
			} else {
				result.put("allback", "0");
			}
			if ("3".equals(map.get("dateType")) && chargeBillCollectMapper.getMonthMax(map).size() > 0) {
				String date = chargeBillCollectMapper.getMonthMax(map).get(0).get("TIME").toString();
				String[] dateArr = date.split("-");
				result.put("mostmonth", dateArr[0] + "年Q" + dateArr[1]);
			} else if (!"3".equals(map.get("dateType")) && chargeBillCollectMapper.getMonthMax(map).size() > 0) {
				result.put("mostmonth", chargeBillCollectMapper.getMonthMax(map).get(0).get("TIME"));
			} else {
				result.put("mostmonth", "-");
			}
			if ("3".equals(map.get("dateType")) && chargeBillCollectMapper.getYearMax(map).size() > 0) {
				String date = chargeBillCollectMapper.getYearMax(map).get(0).get("TIME").toString();
				String[] dateArr = date.split("-");
				result.put("bviousmonth", dateArr[0] + "年Q" + dateArr[1]);
			} else if (!"3".equals(map.get("dateType")) && chargeBillCollectMapper.getYearMax(map).size() > 0) {
				result.put("bviousmonth", chargeBillCollectMapper.getYearMax(map).get(0).get("TIME"));
			} else {
				result.put("bviousmonth", "");
			}
			Double money = 0d;
			if (chargeBillCollectMapper.getDepartmentMoney(map).size() == 0) {
				result.put("mostdepart", "-");
			} else {
				result.put("mostdepart", chargeBillCollectMapper.getDepartmentMoney(map).get(0).get("DEPARTMENT"));
				if (chargeBillCollectMapper.getDepartmentMoney(map).get(0).get("MONEY") != null)
					money = Double.valueOf(chargeBillCollectMapper.getDepartmentMoney(map).get(0)
							.getOrDefault("MONEY", "0").toString());
			}
			Double allMoney = Double.valueOf(result.get("allcharge").toString());
			Double rate = 0d;
			if (allMoney > 0) {
				rate = money * 100 / allMoney;
				BigDecimal bg = new BigDecimal(rate);
				Double math = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
				if (math > 0)
					result.put("rate", math.toString() + "%");
				else
					result.put("rate", "0%");
			}
			resultMap.put("info", result);
			List<Map<String, Object>> resultMapList = chargeBillCollectMapper.getData(map);
			resultMapList.addAll(chargeBillCollectMapper.getAllDepartData(map));
			Set<String> departSet = new HashSet<>();
			resultMapList.forEach(x -> {
				x.forEach((k, v) -> {
					if ("DEPARTMENT".equals(k))
						departSet.add(v.toString());
					if (v == null)
						x.put(k, "0");
				});
			});
			List<String> departList = new ArrayList<>(departSet);
			for (String depart : departList) {
				Map<String, Object> dataMap = new HashMap<>();
				for (int i = 0; i < dateList.size(); i++) {
					dataMap.put(dateList.get(i), "0");
				}
				Double num = 0d;
				dataMap.put("科室", depart);
				for (Map<String, Object> x : resultMapList) {
					if (depart.equals(x.get("DEPARTMENT").toString()) && !"3".equals(map.get("dateType"))) {
						dataMap.put(x.get("TIME").toString(), x.get("MONEY"));
						num += Double.valueOf(x.get("MONEY").toString());
					} else if (depart.equals(x.get("DEPARTMENT").toString()) && "3".equals(map.get("dateType"))) {
						String[] dateTime = x.get("TIME").toString().split("-");
						dataMap.put(dateTime[0] + "年Q" + dateTime[1], x.get("MONEY"));
						num += Double.valueOf(x.get("MONEY").toString());
					}
				}
				BigDecimal bg = new BigDecimal(num);
				num = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
				dataMap.put("汇总", num);
				finalList.add(dataMap);
			}
		}
		resultMap.put("pageInfo", finalList);
		List<Map<String, Object>> list = finalList;
		List<Map<String, Object>> cols = new ArrayList<>();
		for (String str : dateList) {
			cols.add(ImmutableMap.of("COLUMN_NAME", str, "COLUMN_CODE", str, "DATA_TYPE", "VARCHAR2"));
		}
		List<ExcelExportEntity> entityList = new ArrayList<>();
		for (Map<String, Object> col : cols) {
			String name = col.get("COLUMN_NAME").toString();
			String key = col.get("COLUMN_CODE").toString();
			ExcelExportEntity excelExportEntity = new ExcelExportEntity(name, key);
			entityList.add(excelExportEntity);
		}
		Map<String, Object> listMap = new HashMap<>();
		listMap.put("list", list);
		listMap.put("entityList", entityList);
		return listMap;
	}

	public List<String> getDateList(Map<String, Object> map) throws ParseException {
		List<String> result = new LinkedList<>();
		result.add("科室");
		String startDate = map.get("startDate").toString();
		String endDate = map.get("endDate").toString();
		String dateType = map.get("dateType").toString();
		if (startDate != null && startDate.equals(endDate) && !"3".equals(dateType)) {
			result.add(startDate);
			result.add("汇总");
			return result;
		} else if (startDate != null && startDate.equals(endDate) && "3".equals(dateType)) {
			result.add(startDate + "年Q1");
			result.add(startDate + "年Q2");
			result.add(startDate + "年Q3");
			result.add(startDate + "年Q4");
			result.add("汇总");
			return result;
		}
		if ("1".equals(dateType)) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
			Calendar min = Calendar.getInstance();
			Calendar max = Calendar.getInstance();
			min.setTime(sdf.parse(startDate));
			min.set(min.get(Calendar.YEAR), min.get(Calendar.MONTH), 1);
			max.setTime(sdf.parse(endDate));
			max.set(max.get(Calendar.YEAR), max.get(Calendar.MONTH), 2);
			Calendar curr = min;
			while (curr.before(max)) {
				result.add(sdf.format(curr.getTime()));
				curr.add(Calendar.MONTH, 1);
			}
		} else if ("2".equals(dateType)) {
			Integer start = Integer.valueOf(startDate);
			Integer end = Integer.valueOf(endDate);
			for (int i = start; i < end + 1; i++) {
				result.add(String.valueOf(i));
			}
		} else if ("3".equals(dateType)) {
			Integer start = Integer.valueOf(startDate);
			Integer end = Integer.valueOf(endDate);
			Integer nowMonth = Integer.valueOf(new SimpleDateFormat("yyyy").format(new Date()));
			Integer now = Integer.valueOf(new SimpleDateFormat("M").format(new Date()));
			for (int i = start; i < end + 1; i++) {
				for (int j = 1; j < 5; j++) {
					result.add(i + "年Q" + j);
				}
			}
			if (nowMonth.equals(end)) {
				if (now >= 1 && now <= 3) {
					result.remove(end + "年Q2");
					result.remove(end + "年Q3");
					result.remove(end + "年Q4");
				} else if (now >= 4 && now <= 6) {
					result.remove(end + "年Q3");
					result.remove(end + "年Q4");
				} else if (now >= 7 && now <= 9) {
					result.remove(end + "年Q4");
				}
			}
		}
		if ("3".equals(dateType)) {
			if ("0".equals(map.get("flag").toString())) {
				result.add("汇总");
				List<String> date = new LinkedList<>();
				date.add("科室");
				date.addAll(result.subList(result.size() - 9, result.size() - 1));
				date.add("汇总");
				return date;
			} else {
				result.add("汇总");
				return result;
			}
		} else {
			result.add("汇总");
			return result;
		}
	}
}
