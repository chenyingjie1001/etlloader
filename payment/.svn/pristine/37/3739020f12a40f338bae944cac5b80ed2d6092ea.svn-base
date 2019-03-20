package com.firesoon.idaweb.web.chargeBillAnalysis;

import com.firesoon.commoncasshiro.action.BaseAction;
import com.firesoon.idaservice.chargeBillAnalysis.ChargeBillAnalysisService;
import com.firesoon.idaservice.chargeBillDetail.ChargeBillDetailService;
import com.firesoon.utils.FileUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
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
@RequestMapping(value = "/api/chargeBillAnalysis/", method = { RequestMethod.POST, RequestMethod.GET })
public class ChargeBillAnalysisAction extends BaseAction {
	@Autowired
	private ChargeBillAnalysisService chargeBillAnalysisService;

	@Autowired
	private ChargeBillDetailService chargeBillDetailService;

	@SuppressWarnings("unchecked")
	@ApiOperation(value = "扣款分析获取数据", notes = "扣款分析获取数据")
	// @GetMapping(value = "/getItemData")
	@GetMapping(value = "/getData")
	public Object getItemData(
			@ApiParam(value = "{\"startDate\" : \"2018-06\",\"endDate\" : \"2018-12\",\"pageNum\" : \"1\",\"pageSize\" : \"10\",\"queryType\" : \"1\",\"orderby\" : \"ALLCHARGE\" : \"desc\" : \"1\"}") @RequestBody Map<String, Object> map) {
		String pageNum = (map.get("pageNum") == null) ? "1" : map.get("pageNum").toString();
		String pageSize = (map.get("pageSize") == null) ? "10" : map.get("pageSize").toString();
		PageHelper.startPage(Integer.parseInt(pageNum), Integer.parseInt(pageSize));
		Map<String, Object> result = new HashMap<>();
		if ("1".equals(map.get("queryType").toString())) {
			result = chargeBillAnalysisService.queryDepartDetail(map);
		} else if ("2".equals(map.get("queryType").toString())) {
			result = chargeBillAnalysisService.queryItemDetail(map);
		} else if ("3".equals(map.get("queryType").toString())) {
			result = chargeBillAnalysisService.queryReasonDetail(map);
		}
		String orderBy = (map.get("orderby") == null) ? "ALLCHARGE" : map.get("orderby").toString();
		String orderType = (map.get("desc") == null) ? "1" : map.get("desc").toString();
		List<Map<String, Object>> dataList = (List<Map<String, Object>>) result.get("pageInfo");
		if (dataList != null && dataList.size() > 0) {
			if ("0".equals(orderType)) {
				dataList.sort((Map<String, Object> m1, Map<String, Object> m2) -> Double
						.valueOf(m1.get(orderBy).toString().replace("%", ""))
						.compareTo(Double.valueOf(m2.get(orderBy).toString().replaceAll("%", ""))));
				result.put("pageInfo", new PageInfo<>(dataList));
			} else if ("1".equals(orderType)) {
				dataList.sort((Map<String, Object> m1, Map<String, Object> m2) -> Double
						.valueOf(m2.get(orderBy).toString().replace("%", ""))
						.compareTo(Double.valueOf(m1.get(orderBy).toString().replace("%", ""))));
				result.put("pageInfo", new PageInfo<>(dataList));
			}

		}
		return succ(result);
	}

//	@SuppressWarnings("unchecked")
//	@ApiOperation(value = "扣款分析原因获取数据", notes = "扣款分析原因获取数据")
//	@GetMapping(value = "/getReasonData")
//	public Object getReasonData(
//			@ApiParam(value = "{\"startDate\" : \"2018-06\",\"endDate\" : \"2018-12\",\"pageNum\" : \"1\",\"pageSize\" : \"10\"}") @RequestBody Map<String, Object> map) {
//		String pageNum = (map.get("pageNum") == null) ? "1" : map.get("pageNum").toString();
//		String pageSize = (map.get("pageSize") == null) ? "10" : map.get("pageSize").toString();
//		PageHelper.startPage(Integer.parseInt(pageNum), Integer.parseInt(pageSize));
//		Map<String, Object> result = chargeBillAnalysisService.queryReasonDetail(map);
//		result.put("pageInfo", new PageInfo<>((List<Map<String, Object>>) result.get("pageInfo")));
//		return succ(result);
//	}
//	
//	@SuppressWarnings("unchecked")
//	@ApiOperation(value = "扣款分析科室获取数据", notes = "扣款分析科室获取数据")
//	@GetMapping(value = "/getDepartData")
//	public Object getDepartData(
//			@ApiParam(value = "{\"startDate\" : \"2018-06\",\"endDate\" : \"2018-12\",\"pageNum\" : \"1\",\"pageSize\" : \"10\"}") @RequestBody Map<String, Object> map) {
//		String pageNum = (map.get("pageNum") == null) ? "1" : map.get("pageNum").toString();
//		String pageSize = (map.get("pageSize") == null) ? "10" : map.get("pageSize").toString();
//		PageHelper.startPage(Integer.parseInt(pageNum), Integer.parseInt(pageSize));
//		Map<String, Object> result = chargeBillAnalysisService.queryDepartDetail(map);
//		result.put("pageInfo", new PageInfo<>((List<Map<String, Object>>) result.get("pageInfo")));
//		return succ(result);
//	}

	@SuppressWarnings("unchecked")
	@ApiOperation(value = "扣款分析下钻", notes = "扣款分析下钻")
	@GetMapping(value = "/getDownData")
	public Object getDownData(
			@ApiParam(value = "{\"startDate\" : \"2018-06\",\"endDate\" : \"2018-12\",\"pageNum\" : \"1\",\"pageSize\" : \"10\",\"param\" : \"參數\",\"allMoney\" : \"123.26\",\"queryType\" : \"1\",\"dataType\" : \"1\"}") @RequestBody Map<String, Object> map) {
		String pageNum = (map.get("pageNum") == null) ? "1" : map.get("pageNum").toString();
		String pageSize = (map.get("pageSize") == null) ? "10" : map.get("pageSize").toString();
		PageHelper.startPage(Integer.parseInt(pageNum), Integer.parseInt(pageSize));
		Map<String, Object> result = new HashMap<>();
		if ("1".equals(map.get("queryType").toString()) && "2".equals(map.get("dataType").toString())) {
			result = chargeBillAnalysisService.getDepartDoctorData(map);
		} else if ("1".equals(map.get("queryType").toString()) && "4".equals(map.get("dataType").toString())) {
			result = chargeBillAnalysisService.getDepartReasonData(map);
		} else if ("1".equals(map.get("queryType").toString()) && "3".equals(map.get("dataType").toString())) {
			result = chargeBillAnalysisService.getDepartItemData(map);
		} else if ("2".equals(map.get("queryType").toString()) && "2".equals(map.get("dataType").toString())) {
			result = chargeBillAnalysisService.getItemDoctorData(map);
		} else if ("2".equals(map.get("queryType").toString()) && "1".equals(map.get("dataType").toString())) {
			result = chargeBillAnalysisService.getItemDepartmentData(map);
		} else if ("3".equals(map.get("queryType").toString()) && "1".equals(map.get("dataType").toString())) {
			result = chargeBillAnalysisService.getReasonDepartmentData(map);
		} else if ("3".equals(map.get("queryType").toString()) && "2".equals(map.get("dataType").toString())) {
			result = chargeBillAnalysisService.getReasonDoctorData(map);
		} else if ("3".equals(map.get("queryType").toString()) && "3".equals(map.get("dataType").toString())) {
			result = chargeBillAnalysisService.getReasonItemData(map);
		}
		result.put("pageInfo", new PageInfo<>((List<Map<String, Object>>) result.get("pageInfo")));
		return succ(result);
	}

//	@SuppressWarnings("unchecked")
//	@ApiOperation(value = "扣款分析项目下钻医生", notes = "扣款分析项目下钻医生")
//	@GetMapping(value = "/getItemDownDoctor")
//	public Object getItemDownDoctor(
//			@ApiParam(value = "{\"startDate\" : \"2018-06\",\"endDate\" : \"2018-12\",\"pageNum\" : \"1\",\"pageSize\" : \"10\",\"itemname\" : \"项目\",\"allMoney\" : \"123.26\"}") @RequestBody Map<String, Object> map) {
//		String pageNum = (map.get("pageNum") == null) ? "1" : map.get("pageNum").toString();
//		String pageSize = (map.get("pageSize") == null) ? "10" : map.get("pageSize").toString();
//		PageHelper.startPage(Integer.parseInt(pageNum), Integer.parseInt(pageSize));
//		Map<String, Object> result = chargeBillAnalysisService.getItemDoctorData(map);
//		result.put("pageInfo", new PageInfo<>((List<Map<String, Object>>) result.get("pageInfo")));
//		return succ(result);
//	}
//
//	
//	
//	@SuppressWarnings("unchecked")
//	@ApiOperation(value = "扣款分析项目下钻科室", notes = "扣款分析项目下钻科室")
//	@GetMapping(value = "/getItemDownDepart")
//	public Object getItemDownDepart(
//			@ApiParam(value = "{\"startDate\" : \"2018-06\",\"endDate\" : \"2018-12\",\"pageNum\" : \"1\",\"pageSize\" : \"10\",\"itemname\" : \"项目\",\"allMoney\" : \"123.26\"}") @RequestBody Map<String, Object> map) {
//		String pageNum = (map.get("pageNum") == null) ? "1" : map.get("pageNum").toString();
//		String pageSize = (map.get("pageSize") == null) ? "10" : map.get("pageSize").toString();
//		PageHelper.startPage(Integer.parseInt(pageNum), Integer.parseInt(pageSize));
//		Map<String, Object> result = chargeBillAnalysisService.getItemDepartmentData(map);
//		result.put("pageInfo", new PageInfo<>((List<Map<String, Object>>) result.get("pageInfo")));
//		return succ(result);
//	}

//
//	@SuppressWarnings("unchecked")
//	@ApiOperation(value = "扣款分析原因下钻科室", notes = "扣款分析原因下钻科室")
//	@GetMapping(value = "/getReasonDownDepart")
//	public Object getReasonDownDepart(
//			@ApiParam(value = "{\"startDate\" : \"2018-06\",\"endDate\" : \"2018-12\",\"pageNum\" : \"1\",\"pageSize\" : \"10\",\"chargereason\" : \"原因\",\"allMoney\" : \"123.26\"}") @RequestBody Map<String, Object> map) {
//		String pageNum = (map.get("pageNum") == null) ? "1" : map.get("pageNum").toString();
//		String pageSize = (map.get("pageSize") == null) ? "10" : map.get("pageSize").toString();
//		PageHelper.startPage(Integer.parseInt(pageNum), Integer.parseInt(pageSize));
//		Map<String, Object> result = chargeBillAnalysisService.getReasonDepartmentData(map);
//		result.put("pageInfo", new PageInfo<>((List<Map<String, Object>>) result.get("pageInfo")));
//		return succ(result);
//	}
//
//	@SuppressWarnings("unchecked")
//	@ApiOperation(value = "扣款分析原因下钻医生", notes = "扣款分析原因下钻医生")
//	@GetMapping(value = "/getReasonDownDoctor")
//	public Object getReasonDownDoctor(
//			@ApiParam(value = "{\"startDate\" : \"2018-06\",\"endDate\" : \"2018-12\",\"pageNum\" : \"1\",\"pageSize\" : \"10\",\"chargereason\" : \"原因\",\"allMoney\" : \"123.26\"}") @RequestBody Map<String, Object> map) {
//		String pageNum = (map.get("pageNum") == null) ? "1" : map.get("pageNum").toString();
//		String pageSize = (map.get("pageSize") == null) ? "10" : map.get("pageSize").toString();
//		PageHelper.startPage(Integer.parseInt(pageNum), Integer.parseInt(pageSize));
//		Map<String, Object> result = chargeBillAnalysisService.getReasonDoctorData(map);
//		result.put("pageInfo", new PageInfo<>((List<Map<String, Object>>) result.get("pageInfo")));
//		return succ(result);
//	}
//
//	@SuppressWarnings("unchecked")
//	@ApiOperation(value = "扣款分析原因下钻项目", notes = "扣款分析原因下钻项目")
//	@GetMapping(value = "/getReasonDownItem")
//	public Object getReasonDownItem(
//			@ApiParam(value = "{\"startDate\" : \"2018-06\",\"endDate\" : \"2018-12\",\"pageNum\" : \"1\",\"pageSize\" : \"10\",\"chargereason\" : \"原因\",\"allMoney\" : \"123.26\"}") @RequestBody Map<String, Object> map) {
//		String pageNum = (map.get("pageNum") == null) ? "1" : map.get("pageNum").toString();
//		String pageSize = (map.get("pageSize") == null) ? "10" : map.get("pageSize").toString();
//		PageHelper.startPage(Integer.parseInt(pageNum), Integer.parseInt(pageSize));
//		Map<String, Object> result = chargeBillAnalysisService.getReasonItemData(map);
//		result.put("pageInfo", new PageInfo<>((List<Map<String, Object>>) result.get("pageInfo")));
//		return succ(result);
//	}

//	@SuppressWarnings("unchecked")
//	@ApiOperation(value = "扣款分析科室下钻医生", notes = "扣款分析原因下钻医生")
//	@GetMapping(value = "/getDepartDownDoctor")
//	public Object getDepartDownDoctor(
//			@ApiParam(value = "{\"startDate\" : \"2018-06\",\"endDate\" : \"2018-12\",\"pageNum\" : \"1\",\"pageSize\" : \"10\",\"depart\" : \"主科室\",\"allMoney\" : \"123.26\"}") @RequestBody Map<String, Object> map) {
//		String pageNum = (map.get("pageNum") == null) ? "1" : map.get("pageNum").toString();
//		String pageSize = (map.get("pageSize") == null) ? "10" : map.get("pageSize").toString();
//		PageHelper.startPage(Integer.parseInt(pageNum), Integer.parseInt(pageSize));
//		Map<String, Object> result = chargeBillAnalysisService.getDepartDoctorData(map);
//		result.put("pageInfo", new PageInfo<>((List<Map<String, Object>>) result.get("pageInfo")));
//		return succ(result);
//	}
//
//	@SuppressWarnings("unchecked")
//	@ApiOperation(value = "扣款分析科室下钻原因", notes = "扣款分析科室下钻原因")
//	@GetMapping(value = "/getDepartDownReason")
//	public Object getDepartDownReason(
//			@ApiParam(value = "{\"startDate\" : \"2018-06\",\"endDate\" : \"2018-12\",\"pageNum\" : \"1\",\"pageSize\" : \"10\",\"depart\" : \"主科室\",\"allMoney\" : \"123.26\"}") @RequestBody Map<String, Object> map) {
//		String pageNum = (map.get("pageNum") == null) ? "1" : map.get("pageNum").toString();
//		String pageSize = (map.get("pageSize") == null) ? "10" : map.get("pageSize").toString();
//		PageHelper.startPage(Integer.parseInt(pageNum), Integer.parseInt(pageSize));
//		Map<String, Object> result = chargeBillAnalysisService.getDepartReasonData(map);
//		result.put("pageInfo", new PageInfo<>((List<Map<String, Object>>) result.get("pageInfo")));
//		return succ(result);
//	}
//
//	@SuppressWarnings("unchecked")
//	@ApiOperation(value = "扣款分析科室下钻项目", notes = "扣款分析科室下钻项目")
//	@GetMapping(value = "/getDepartDownItem")
//	public Object getDepartDownItem(
//			@ApiParam(value = "{\"startDate\" : \"2018-06\",\"endDate\" : \"2018-12\",\"pageNum\" : \"1\",\"pageSize\" : \"10\",\"depart\" : \"主科室\",\"allMoney\" : \"123.26\"}") @RequestBody Map<String, Object> map) {
//		String pageNum = (map.get("pageNum") == null) ? "1" : map.get("pageNum").toString();
//		String pageSize = (map.get("pageSize") == null) ? "10" : map.get("pageSize").toString();
//		PageHelper.startPage(Integer.parseInt(pageNum), Integer.parseInt(pageSize));
//		Map<String, Object> result = chargeBillAnalysisService.getDepartItemData(map);
//		result.put("pageInfo", new PageInfo<>((List<Map<String, Object>>) result.get("pageInfo")));
//		return succ(result);
//	}

	@ApiOperation(value = "扣款分析下载", notes = "扣款分析下载")
	@GetMapping(value = "/download")
	public void download(
			@ApiParam(value = "{\"startDate\" : \"2018-06\",\"endDate\" : \"2018-12\",\"downloadType\" : \"1\",\"orderby\" : \"ALLCHARGE\",\"desc\" : \"0\"}") @RequestParam Map<String, Object> map,
			HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
		Map<String, Object> result = chargeBillAnalysisService.dataDownload(map);
		String codedFileName = "";
		String[] start = map.get("startDate").toString().split("-");
		String[] end = map.get("endDate").toString().split("-");
		String date = "";
		if ("1".equals(map.get("downloadType").toString())) {
			date = start[0] + "年" + start[1] + "月份" + "-" + end[0] + "年" + end[1] + "月份";
			codedFileName = URLEncoder.encode(date + "扣款分析-按科室分析", "UTF-8");
		} else if ("2".equals(map.get("downloadType").toString())) {
			date = start[0] + "年" + start[1] + "月份" + "-" + end[0] + "年" + end[1] + "月份";
			codedFileName = URLEncoder.encode(date + "扣款分析-按扣款项目分析", "UTF-8");
		} else if ("3".equals(map.get("downloadType").toString())) {
			date = start[0] + "年" + start[1] + "月份" + "-" + end[0] + "年" + end[1] + "月份";
			codedFileName = URLEncoder.encode(date + "扣款分析-按扣款原因分析", "UTF-8");
		}
		String orderBy = (map.get("orderby") == null) ? "ALLCHARGE" : map.get("orderby").toString();
		String orderType = (map.get("desc") == null) ? "1" : map.get("desc").toString();
		@SuppressWarnings("unchecked")
		List<Map<String, Object>> list = (List<Map<String, Object>>) result.get("list");
		if (list != null && list.size() > 0) {
			if ("0".equals(orderType)) {
				list.sort((Map<String, Object> m1, Map<String, Object> m2) -> Double
						.valueOf(m1.get(orderBy).toString().replace("%", ""))
						.compareTo(Double.valueOf(m2.get(orderBy).toString().replaceAll("%", ""))));
			} else if ("1".equals(orderType)) {
				list.sort((Map<String, Object> m1, Map<String, Object> m2) -> Double
						.valueOf(m2.get(orderBy).toString().replace("%", ""))
						.compareTo(Double.valueOf(m1.get(orderBy).toString().replace("%", ""))));
			}
		}
		// List<Map<String, Object>> list = (List<Map<String, Object>>)
		// result.get("list");
		// list.sort((Map<String, Object> m1, Map<String, Object> m2) ->
		// Double.valueOf(m2.get("ALLCHARGE").toString())
		// .compareTo(Double.valueOf(m1.get("ALLCHARGE").toString())));
		@SuppressWarnings("unchecked")
		List<ExcelExportEntity> entityList = (List<ExcelExportEntity>) result.get("entityList");
		FileUtil.exportExcel(new ExportParams(), entityList, list, codedFileName, response, request);
	}

	@ApiOperation(value = "获取默认时间", notes = "获取默认时间")
	@GetMapping(value = "/getInitialMonth")
	public Object getInitialMonth() {
		return succ(chargeBillDetailService.getSectionLevelMonth());
	}

	@SuppressWarnings("unchecked")
	@ApiOperation(value = "扣款分析科室右钻", notes = "扣款分析科室右钻")
	@GetMapping(value = "/getDepartDetail")
	public Object getDepartDetail(
			@ApiParam(value = "{\"startDate\" : \"2018-06\",\"endDate\" : \"2018-12\",\"pageNum\" : \"1\",\"pageSize\" : \"10\",\"param\" : \"參數\",\"allMoney\" : \"123.26\",\"dataType\" : \"1\",\"department\" : \"主科室\"}") @RequestBody Map<String, Object> map) {
		String pageNum = (map.get("pageNum") == null) ? "1" : map.get("pageNum").toString();
		String pageSize = (map.get("pageSize") == null) ? "10" : map.get("pageSize").toString();
		PageHelper.startPage(Integer.parseInt(pageNum), Integer.parseInt(pageSize));
		Map<String, Object> result = new HashMap<>();
		;
		if ("2".equals(map.get("dataType").toString())) {
			result = chargeBillAnalysisService.getDepartDoctorDetail(map);
		} else if ("3".equals(map.get("dataType").toString())) {
			result = chargeBillAnalysisService.getDepartItemDetail(map);
		} else if ("4".equals(map.get("dataType").toString())) {
			result = chargeBillAnalysisService.getDepartReasonDetail(map);
		}
		result.put("pageInfo", (List<Map<String, Object>>) result.get("pageInfo"));
		return succ(result);
	}

	@SuppressWarnings("unchecked")
	@ApiOperation(value = "扣款分析科室右钻额外", notes = "扣款分析科室右钻额外")
	@GetMapping(value = "/getDepartDetailUltra")
	public Object getDepartDetailUltra(
			@ApiParam(value = "{\"startDate\" : \"2018-06\",\"endDate\" : \"2018-12\",\"pageNum\" : \"1\",\"pageSize\" : \"10\",\"param\" : \"參數\",\"allMoney\" : \"123.26\",\"ultraType\" : \"1\",\"department\" : \"主科室\"}") @RequestBody Map<String, Object> map) {
		String pageNum = (map.get("pageNum") == null) ? "1" : map.get("pageNum").toString();
		String pageSize = (map.get("pageSize") == null) ? "10" : map.get("pageSize").toString();
		PageHelper.startPage(Integer.parseInt(pageNum), Integer.parseInt(pageSize));
		Map<String, Object> result = new HashMap<>();
		if ("ITEM_NAME".equals(map.get("ultraType").toString())) {
			result = chargeBillAnalysisService.getUtrlaDoctor(map);
		} else if ("CHARGE_REASON".equals(map.get("ultraType").toString())) {
			result = chargeBillAnalysisService.getUtrlaReason(map);
		}
		result.put("pageInfo", (List<Map<String, Object>>) result.get("pageInfo"));
		return succ(result);
	}

}
