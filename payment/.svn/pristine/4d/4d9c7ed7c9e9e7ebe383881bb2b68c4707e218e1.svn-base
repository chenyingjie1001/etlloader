package com.firesoon.idaweb.web.appealTemplate;

import com.firesoon.commoncasshiro.action.BaseAction;
import com.firesoon.idaservice.appealTemplate.AppealTemplateService;
import com.firesoon.utils.FileUtil;
import com.github.pagehelper.PageInfo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.params.ExcelExportEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Api
@RestController
@RequestMapping(value = "/api/appealTemplate/", method = { RequestMethod.GET, RequestMethod.POST })
public class AppealTemplateAction extends BaseAction {

	@Autowired
	private AppealTemplateService appealTemplateService;

	@ApiOperation(value = "查申诉说明模板", notes = "查申诉说明模板")
	@GetMapping(value = "/query")
	public Object query() {
		Map<String, Object> result = appealTemplateService.query();
		@SuppressWarnings("unchecked")
		List<Map<String, Object>> datas = (List<Map<String, Object>>) result.get("pageInfo");
		result.put("pageInfo", new PageInfo<>(datas));
		return succ(result);
	}

	@ApiOperation(value = "根据条件查申诉说明", notes = "根据条件查申诉说明")
	@GetMapping(value = "/queryByTerm")
	public Object queryByTerm(
			@ApiParam(value = "{\"rulename\" : \"规则名称\",\"itemcode\" : \"项目编码\",\"itemname\" : \"项目名称\",\"chargereason\" : \"原因\"}") @RequestBody Map<String, Object> map) {
		return succ(new LinkedList<>(appealTemplateService.queryByTerm(map)));
	}

	@ApiOperation(value = "新增申诉说明模板", notes = "新增申诉说明模板")
	@GetMapping(value = "/add")
	public Object add(
			@ApiParam(value = "{\"rulename\" : \"规则名称\",\"itemcode\" : \"项目编码\",\"itemname\" : \"项目名称\",\"chargereason\" : \"原因\",\"appealexplain\" : \"[说明1,说明2]\"}") @RequestBody Map<String, Object> map,
			HttpServletRequest request) {
		Map<String, Object> result = appealTemplateService.add(map);
		String resaultMessage = result.get("msg").toString();
		Boolean isSuccess = resaultMessage.contains("新增成功");
		if (isSuccess)
			return succ(result);
		else
			return failure(result);
	}

	@ApiOperation(value = "修改申诉说明模板", notes = "修改申诉说明模板")
	@GetMapping(value = "/update")
	public Object update(
			@ApiParam(value = "{\"id\" : \"12sed\",\"rulename\" : \"规则名称\",\"itemcode\" : \"项目编码\",\"itemname\" : \"项目名称\",\"chargereason\" : \"原因\",\"appealexplain\" : \"[说明1,说明2]\"}") @RequestBody Map<String, Object> map,
			HttpServletRequest request) {
		Map<String, Object> result = appealTemplateService.update(map);
		String resaultMessage = result.get("msg").toString();
		Boolean isSuccess = resaultMessage.contains("更新成功");
		if (isSuccess)
			return succ(result);
		else
			return failure(result);
	}

	@ApiOperation(value = "删除申诉说明模板", notes = "删除申诉说明模板")
	@GetMapping(value = "/delete")
	public Object delete(@ApiParam(value = "{\"id\" : \"12sed\"}") @RequestBody Map<String, Object> map,
			HttpServletRequest request) {
		appealTemplateService.delete(map);
		return succ();
	}

	@ApiOperation(value = "导出申诉说明模板", notes = "导出申诉说明模板")
	@GetMapping(value = "/explainDownload")
	public void explainDownload(HttpServletRequest request, HttpServletResponse response)
			throws UnsupportedEncodingException {
		Map<String, Object> result = appealTemplateService.explainDownload();
		String codedFileName = URLEncoder.encode("申诉说明配置模板", "UTF-8");
		@SuppressWarnings("unchecked")
		List<Map<String, Object>> list = (List<Map<String, Object>>) result.get("list");
		@SuppressWarnings("unchecked")
		List<ExcelExportEntity> entityList = (List<ExcelExportEntity>) result.get("entityList");
		FileUtil.exportExcel(new ExportParams(), entityList, list, codedFileName, response, request);
	}

	@ApiOperation(value = "导入申诉说明模板", notes = "导入申诉说明模板")
	@RequestMapping(value = "importExplainXls")
	public Object importExplainXls(
			@ApiParam(value = "{\"filename\": \"医保扣款反馈模板.xls\"}") @RequestBody Map<String, Object> params) {
		Map<String, Object> data = new HashMap<>();
		Integer[] result = appealTemplateService.importExplainXlsResult(params);
		data.put("total", result[0]);
		data.put("errorTotal", result[1]);
		data.put("msg", "导入成功");
		data.put("times", appealTemplateService.importExplainTimes(params));
		return succ(data);
	}

}
