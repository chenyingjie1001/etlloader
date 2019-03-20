package com.firesoon.idaservice.appealTemplate.impl;

import com.firesoon.idaservice.appealTemplate.AppealTemplateService;
import com.firesoon.paymentmapper.appealTemplate.AppealTemplateMapper;
import com.firesoon.utils.PropertiesUtil;
import com.firesoon.utils.StringUtil;
import com.google.common.collect.ImmutableMap;
import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.fastjson.JSONArray;

import java.io.File;
import java.sql.Clob;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.UUID;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.entity.params.ExcelExportEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Primary
@Service
public class AppealTemplateServiceImpl implements AppealTemplateService {

	@Autowired
	private AppealTemplateMapper appealTemplateMapper;

	@Override
	public Map<String, Object> query() {
		List<Map<String, Object>> datas = appealTemplateMapper.findAppealTemplate(new HashMap<>());
		List<Map<String, Object>> num = appealTemplateMapper.findMax();
		Integer max = 0;
		if (num.get(0) != null) {
			max = Integer.parseInt(num.get(0).getOrDefault("MAX", 0).toString());
		}
		List<Map<String, Object>> excelTable = new ArrayList<>(Arrays.asList(
				ImmutableMap.of("COLUMN_NAME", "项目名称", "COLUMN_CODE", "ITEM_NAME", "DATA_TYPE", "VARCHAR2"),
				ImmutableMap.of("COLUMN_NAME", "项目编码", "COLUMN_CODE", "ITEM_CODE", "DATA_TYPE", "VARCHAR2"),
				ImmutableMap.of("COLUMN_NAME", "规则名称", "COLUMN_CODE", "RULE_NAME", "DATA_TYPE", "VARCHAR2"),
				ImmutableMap.of("COLUMN_NAME", "扣款原因", "COLUMN_CODE", "CHARGEREASON", "DATA_TYPE", "VARCHAR2")));
		for (int i = 1; i < max + 1; i++) {
			excelTable.add(ImmutableMap.of("COLUMN_NAME", "申诉说明" + i, "COLUMN_CODE", "APPEALEXPLAIN_" + i, "DATA_TYPE",
					"VARCHAR2"));
		}
		for (Map<String, Object> data : datas) {
			String dataArray = StringUtil.Clob2String((Clob) data.getOrDefault("APPEALEXPLAIN", "[]"));
			JSONArray jsonArray = JSONArray.parseArray(dataArray);
			data.put("APPEALEXPLAIN", "");
			if (jsonArray != null) {
				for (int i = 1; i < max + 1; i++) {
					if (i < jsonArray.size() + 1)
						data.put("APPEALEXPLAIN_" + i, jsonArray.get(i - 1));
					else
						data.put("APPEALEXPLAIN_" + i, "");
				}
			}
		}
		Map<String, Object> result = new HashMap<>();
		result.put("pageInfo", datas);
		result.put("excelTable", excelTable);
		return result;
	}

	@Override
	public Set<String> queryByTerm(Map<String, Object> map) {
		Set<String> resultSet = new LinkedHashSet<>();
		if (map.getOrDefault("rulename", "").toString().length() > 0
				|| map.getOrDefault("itemcode", "").toString().length() > 0
				|| map.getOrDefault("itemname", "").toString().length() > 0
				|| map.getOrDefault("chargereason", "").toString().length() > 0) {
			List<Map<String, Object>> datas = appealTemplateMapper.findAppealTemplate(new HashMap<>());
			for (Map<String, Object> data : datas) {
				boolean isRule = false;
				boolean isItem = false;
				boolean isItemname = false;
				boolean isreason = false;
				if(data.get("RULE_NAME") != null && StringUtils.isNotEmpty(data.get("RULE_NAME").toString())){
					if(data.get("RULE_NAME").equals(map.get("rulename"))){
						isRule = true;
					}
				}else {
					isRule = true;
				}
				if(data.get("ITEM_CODE") != null && StringUtils.isNotEmpty(data.get("ITEM_CODE").toString())){
					if(data.get("ITEM_CODE").equals(map.get("itemcode"))){
						isItem = true;
					}
				}else{
					isItem = true;
				}
				if(data.get("ITEM_NAME") != null && StringUtils.isNotEmpty(data.get("ITEM_NAME").toString())){
					if(data.get("ITEM_NAME").equals(map.get("itemname"))){
						isItemname = true;
					}
				}else{
					isItemname = true;
				}
				if(data.get("CHARGEREASON") != null && StringUtils.isNotEmpty(data.get("CHARGEREASON").toString())){
					if(data.get("CHARGEREASON").equals(map.get("chargereason"))){
						isreason = true;
					}
				}else{
					isreason = true;
				}
				if(isRule && isItem && isItemname && isreason){
					String dataArray = StringUtil.Clob2String((Clob) data.getOrDefault("APPEALEXPLAIN", "[]"));
					JSONArray jsonArray = JSONArray.parseArray(dataArray);
					for (int i = 1; i < jsonArray.size() + 1; i++) {
						resultSet.add((String) jsonArray.get(i - 1));
					}
				}

			}
		}
		return resultSet;
	}

	@Override
	public Map<String, Object> add(Map<String, Object> map) {
		Integer dataNum = appealTemplateMapper.findUniqTemplate(map).size();
		String data = JSONUtils.toJSONString(map.getOrDefault("appealexplain", "[]"));
		JSONArray jsonArray = JSONArray.parseArray(data);
		Map<String, Object> dataResult = new HashMap<>();
		if (dataNum > 0) {
			dataResult.put("msg", "该四个字段决定的申诉配置已存在，新增失败");
		} else {
			if (jsonArray.size() > 0) {
				map.put("appealexplain", "[]");
				map.put("appealexplainnum", jsonArray.size());
				map.put("id", UUID.randomUUID().toString().replace("-", "").toLowerCase());
				appealTemplateMapper.insertAppealTemplate(map);
				map.put("appealexplain", data);
				appealTemplateMapper.updateAppealTemplate(map);
			} else {
				map.put("appealexplain", "[]");
				map.put("appealexplainnum", 0);
				map.put("id", UUID.randomUUID().toString().replace("-", "").toLowerCase());
				appealTemplateMapper.insertAppealTemplate(map);
			}
			dataResult.put("msg", "新增成功");
		}
		return dataResult;
	}

	@Override
	public Map<String, Object> update(Map<String, Object> map) {
		List<Map<String, Object>> checkData = appealTemplateMapper.findUniqTemplate(map);
		Integer dataNum = checkData.size();
		String dataId = "";
		String mapId = "no";
		if (dataNum > 0) {
			dataId = checkData.get(0).get("ID").toString();
			mapId = map.getOrDefault("id", "").toString();
		}
		String data = JSONUtils.toJSONString(map.getOrDefault("appealexplain", "[]"));
		JSONArray jsonArray = JSONArray.parseArray(data);
		Map<String, Object> dataResult = new HashMap<>();
		if (dataNum > 0 && !dataId.equals(mapId)) {
			dataResult.put("msg", "该四个字段决定的申诉配置已存在，更新失败");
		} else {
			map.put("appealexplain", data);
			map.put("appealexplainnum", jsonArray.size());
			appealTemplateMapper.updateAppealTemplate(map);
			dataResult.put("msg", "更新成功");
		}
		return dataResult;
	}

	@Override
	public void delete(Map<String, Object> map) {
		appealTemplateMapper.deleteAppealTemplate(map);
	}

	@Override
	public Map<String, Object> explainDownload() {
		List<Map<String, Object>> list = appealTemplateMapper.findAppealTemplate(new HashMap<>());
		List<Map<String, Object>> num = appealTemplateMapper.findMax();
		Integer max = 0;
		if (num.get(0) != null) {
			max = Integer.parseInt(num.get(0).getOrDefault("MAX", 0).toString());
		}
		List<Map<String, Object>> cols = new ArrayList<>(Arrays.asList(
				ImmutableMap.of("COLUMN_NAME", "项目名称", "COLUMN_CODE", "ITEM_NAME", "DATA_TYPE", "VARCHAR2"),
				ImmutableMap.of("COLUMN_NAME", "项目编码", "COLUMN_CODE", "ITEM_CODE", "DATA_TYPE", "VARCHAR2"),
				ImmutableMap.of("COLUMN_NAME", "规则名称", "COLUMN_CODE", "RULE_NAME", "DATA_TYPE", "VARCHAR2"),
				ImmutableMap.of("COLUMN_NAME", "扣款原因", "COLUMN_CODE", "CHARGEREASON", "DATA_TYPE", "VARCHAR2")));
		for (int i = 1; i < max + 1; i++) {
			cols.add(ImmutableMap.of("COLUMN_NAME", "申诉说明" + i, "COLUMN_CODE", "APPEALEXPLAIN_" + i, "DATA_TYPE",
					"VARCHAR2"));
		}
		if (cols.size() == 4) {
			cols.add(
					ImmutableMap.of("COLUMN_NAME", "申诉说明1", "COLUMN_CODE", "APPEALEXPLAIN_1", "DATA_TYPE", "VARCHAR2"));
		}
		for (Map<String, Object> data : list) {
			String dataArray = StringUtil.Clob2String((Clob) data.getOrDefault("APPEALEXPLAIN", "[]"));
			JSONArray jsonArray = JSONArray.parseArray(dataArray);
			data.put("APPEALEXPLAIN", "");
			if (jsonArray != null) {
				for (int i = 1; i < max + 1; i++) {
					if (i < jsonArray.size() + 1)
						data.put("APPEALEXPLAIN_" + i, jsonArray.get(i - 1));
					else
						data.put("APPEALEXPLAIN_" + i, "");
				}
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
	public Integer[] importExplainXlsResult(Map<String, Object> params) {
		int total = 0;
		int errorTotal = 0;
		String filename = PropertiesUtil.properties.getProperty("xlsfilePath") + params.get("filename");
		ImportParams importParams = new ImportParams();
		importParams.setHeadRows(1);
		importParams.setTitleRows(0);
		List<Map<String, Object>> newList = ExcelImportUtil.importExcel(new File(filename), Map.class, importParams);
		for (Map<String, Object> data : newList) {
			String allExplain = "[";
			Map<Integer, String> indexMap = new TreeMap<Integer, String>(
					(Integer obj1, Integer obj2) -> obj1.compareTo(obj2));
			for (Entry<String, Object> entry : data.entrySet()) {
				if (entry.getKey().startsWith("申诉说明")) {
					Integer index = Integer.parseInt(entry.getKey().split("申诉说明")[1]);
					if (entry.getValue() != null) {
						indexMap.put(index, "\"" + entry.getValue().toString().trim() + "\",");
					}
				}
			}
			Set<String> resultSet = new LinkedHashSet<>();
			for (Entry<Integer, String> entry : indexMap.entrySet()) {
				resultSet.add(entry.getValue());
			}
			List<String> resultList = new ArrayList<>(resultSet);
			for (int i = 0; i < resultList.size(); i++) {
				allExplain += resultList.get(i);
			}
			Boolean isComplete = (data.get("规则名称") != null) || (data.get("项目名称") != null) || (data.get("项目编码") != null)
					|| (data.get("扣款原因") != null);
			data.forEach((k, v) -> {
				if (v == null)
					data.put(k, "");
			});
			if (allExplain.length() > 0 && isComplete) {
				if ("[".equals(allExplain))
					data.put("appealexplain", "[]");
				else
					data.put("appealexplain", allExplain.substring(0, allExplain.length() - 1) + "]");
				data.put("appealexplainnum", resultList.size());
				data.put("rulename", data.get("规则名称").toString());
				data.put("itemname", data.get("项目名称"));
				data.put("itemcode", data.getOrDefault("项目编码", "").toString());
				data.put("chargereason", data.getOrDefault("扣款原因", "").toString());
				List<Map<String, Object>> countNum = appealTemplateMapper.countAppealTemplate(data);
				Integer recordNum = Integer.parseInt(countNum.get(0).get("NUM").toString());
				if (recordNum > 0) {
					appealTemplateMapper.updateTemplate(data);
				} else {
					String explainOld = data.get("appealexplain").toString();
					data.put("appealexplain", "");
					data.put("id", UUID.randomUUID().toString().replace("-", "").toLowerCase());
					appealTemplateMapper.insertAppealTemplate(data);
					data.put("appealexplain", explainOld);
					appealTemplateMapper.updateTemplate(data);
					total++;
				}
			} else {
				errorTotal++;
			}
		}
		return new Integer[] { total, errorTotal };
	}

	@Override
	public Integer importExplainTimes(Map<String, Object> params) {
		return appealTemplateMapper.findAppealTemplate(params).size();
	}

}
