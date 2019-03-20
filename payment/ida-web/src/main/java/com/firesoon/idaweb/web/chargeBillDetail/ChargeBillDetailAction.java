package com.firesoon.idaweb.web.chargeBillDetail;

import com.firesoon.commoncasshiro.action.BaseAction;
import com.firesoon.commoncasshiro.aop.annotation.IdaPointcut;
import com.firesoon.idaservice.chargeBillDetail.ChargeBillDetailService;
import com.firesoon.utils.FileUtil;
import com.github.pagehelper.PageInfo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Api
@RestController
@RequestMapping(value = "/api/chargeBillDetail/", method = { RequestMethod.POST, RequestMethod.POST })
public class ChargeBillDetailAction extends BaseAction {

	@Autowired
	private ChargeBillDetailService chargeBillDetailService;
	
	@IdaPointcut
	@ApiOperation(value = "查询筛选条件", notes = "查询筛选条件")
	@RequestMapping(value = "/queryCondit")
	public Object queryCondit(@RequestBody Map<String, Object> map) {
		List<Map<String, Object>> result = chargeBillDetailService.queryCondit(map);
		return succ(result);
	}
	
	@ApiOperation(value = "根据id修改筛选条件的状态", notes = "根据id修改筛选条件的状态")
	@RequestMapping(value = "/updateConditState")
	public Object updateConditState(@ApiParam(value = "{\"conditionId\":1, \"status\":-1}")@RequestBody Map<String, Object> map) {
		String res = chargeBillDetailService.updateConditState(map);
		return succ(res);
	}

	@IdaPointcut
	@ApiOperation(value = "查扣款单明细", notes = "查扣款单明细")
	@RequestMapping(value = "/query")
	public Object query(
			@ApiParam(value = "{\"billtype\" : \"2\",\"month\" : \"2018-11\",\"pageNum\" : \"1\",\"pageSize\" : \"10\",\"iswrite\" : \"0\",\"isappeal\" : \"0,1\",\"itemname\" : \"医疗,运输\",\"chargeDepartments\" : \"骨科,皮肤科\",\"excelDepartments\" : \"骨科,皮肤科\", \"kkxm\":\"扣款项目,2\", \"reason\":\"原因,2\", \"jyfs\":\"'xxx','xx'\", \"cblx\":\"'xx', 'xxx'\", \"content\":\"xxx\"}") @RequestBody Map<String, Object> map) {
		Map<String, Object> result = chargeBillDetailService.query(map);
		@SuppressWarnings("unchecked")
		List<Map<String, Object>> list = (List<Map<String, Object>>) result.get("pageInfo");
		result.put("pageInfo", new PageInfo<>(list));
		return succ(result);
	}
	
	@IdaPointcut
	@ApiOperation(value = "查询所有就医方式", notes = "查询所有就医方式")
	@RequestMapping(value = "/allJyfs")
	public Object allJyfs(@ApiParam(value = "{\"billtype\" : \"1\",\"month\" : \"2018-11\"}") @RequestBody Map<String, Object> map) {
		return succ(chargeBillDetailService.allJyfs(map));
	}
	
	@IdaPointcut
	@ApiOperation(value = "查询所有参保类型", notes = "查询所有参保类型")
	@RequestMapping(value = "/allCblx")
	public Object allCblx(@ApiParam(value = "{\"billtype\" : \"1\",\"month\" : \"2018-11\"}") @RequestBody Map<String, Object> map) {
		return succ(chargeBillDetailService.allCblx(map));
	}

	@IdaPointcut
	@ApiOperation(value = "查所有扣款科室", notes = "查所有扣款科室")
	@RequestMapping(value = "/allDepartment")
	public Object allDepartment(@ApiParam(value = "{\"billtype\" : \"2\",\"month\" : \"2018-11\"}") @RequestBody Map<String, Object> map) {
		return succ(chargeBillDetailService.allDepartment(map));
	}

	@IdaPointcut
	@ApiOperation(value = "查所有原扣款科室", notes = "查所有原扣款科室")
	@RequestMapping(value = "/allExcelDepartment")
	public Object allExcelDepartment(
			@ApiParam(value = "{\"billtype\" : \"2\",\"month\" : \"2018-11\"}") @RequestBody Map<String, Object> map) {
		return succ(chargeBillDetailService.allExcelDepartment(map));
	}
	
	@IdaPointcut
	@ApiOperation(value = "查所有扣款核算项目", notes = "查所有扣款核算项目")
	@RequestMapping(value = "/allItem")
	public Object allItem(@ApiParam(value = "{\"billtype\" : \"2\",\"month\": \"2018-11\"}") @RequestBody Map<String, Object> map) {
		return succ(chargeBillDetailService.allItem(map));
	}

	@IdaPointcut
	@ApiOperation(value = "查所有扣款项目", notes = "查所有扣款项目")
	@RequestMapping(value = "/allItemname")
	public Object allItemname(@ApiParam(value = "{\"billtype\" : \"2\",\"month\": \"2018-11\"}") @RequestBody Map<String, Object> map) {
		return succ(chargeBillDetailService.allItemname(map));
	}

	@IdaPointcut
	@ApiOperation(value = "查所有扣款原因", notes = "查所有扣款原因")
	@RequestMapping(value = "/allchargereason")
	public Object allchargereason(@ApiParam(value = "{\"billtype\" : \"2\",\"month\": \"2018-11\"}") @RequestBody Map<String, Object> map) {
		return succ(chargeBillDetailService.allchargereason(map));
	}

	@ApiOperation(value = "更改扣款科室", notes = "更改扣款科室")
	@RequestMapping(value = "/changeDepartment")
	public Object changeDepartment(
			@ApiParam(value = "{\"billtype\" : \"2\",\"id\" : \"7B8FE6184D98B098E05010AC49033255\",\"department\" : \"骨科\"}") @RequestBody Map<String, Object> map) {
		chargeBillDetailService.updateDepartment(map);
		return succ();
	}

	@ApiOperation(value = "查申诉信息", notes = "查申诉信息")
	@RequestMapping(value = "/getAppealMessage")
	public Object getAppealMessage(@ApiParam(value = "{\"billtype\" : \"2\",\"id\" : \"扣款单明细几句id\"}") @RequestBody Map<String, Object> map) {
		return succ(chargeBillDetailService.getAppealMessage(map));
	}

	@IdaPointcut
	@ApiOperation(value = "申诉", notes = "申诉")
	@RequestMapping(value = "/appeal")
	public Object appeal(
			@ApiParam(value = "{\"billtype\" : \"2\",\"id\" : \"扣款单明细id\",\"appealtext\" : \"理由\",\"appealimg\" : \"[图片1Base64,图片2Base64]\"}") @RequestBody Map<String, Object> map) {
		chargeBillDetailService.updateWrite(map);
		return succ();
	}

	@ApiOperation(value = "获取科级月份", notes = "获取科级月份")
	@RequestMapping(value = "/getSectionLevelMonth")
	public Object getSectionLevelMonth() {
		return succ(chargeBillDetailService.getSectionLevelMonth());
	}

	@IdaPointcut
	@ApiOperation(value = "科级导出", notes = "科级导出")
	@GetMapping(value = "/levelDownload")
	public void levelDownload(
			@ApiParam(value = "{\"billtype\" : \"2\",\"month\" : \"2018-11\",\"iswrite\" : \"0\",\"isappeal\" : \"0\",\"itemname\" : \"医疗\",\"chargeDepartments\" : \"骨科,皮肤科\",\"excelDepartments\" : \"骨科,皮肤科\"}") @RequestParam Map<String, Object> map,
			HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object>result = chargeBillDetailService.levelDownload(map);
		@SuppressWarnings("unchecked")
		List<Map<String, Object>> list = (List<Map<String, Object>>) result.get("list");
		@SuppressWarnings("unchecked")
		List<ExcelExportEntity> entityList =  (List<ExcelExportEntity>) result.get("entityList");
		String codedFileName = map.get("month").toString() + "扣款单";
		FileUtil.exportExcel(new ExportParams(), entityList, list, codedFileName, response, request);
	}

	@ApiOperation(value = "查所有医院科室配置", notes = "查所有医院科室配置")
	@RequestMapping(value = "/allDepartmentConfig")
	public Object allDepartmentConfig() {
		return succ(chargeBillDetailService.allDepartmentConfig());
	}
	
}