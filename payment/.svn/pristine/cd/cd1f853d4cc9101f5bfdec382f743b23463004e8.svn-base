package com.firesoon.idaweb.web.chargeBillCollect;

import com.firesoon.commoncasshiro.action.BaseAction;
import com.firesoon.idaservice.chargeBillCollect.ChargeBillCollectService;
import com.firesoon.utils.FileUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
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
@RequestMapping(value = "/api/chargeBillCollect/")
public class ChargeBillCollectAction extends BaseAction {
	@Autowired
	private ChargeBillCollectService chargeBillCollectService;

	@SuppressWarnings("unchecked")
	@ApiOperation(value = "扣款汇总查询数据", notes = "扣款汇总查询数据")
	@RequestMapping(value = "/getData", method = RequestMethod.POST)
	public Object queryData(
			@ApiParam(value = "{\"startDate\" : \"2018-06\",\"endDate\" : \"2018-12\",\"pageNum\" : \"1\",\"pageSize\" : \"10\",\"dateType\" : \"1\",\"flag\" : \"0\"}") @RequestBody Map<String, Object> map) {
		String pageNum = (map.get("pageNum") == null) ? "1" : map.get("pageNum").toString();
		String pageSize = (map.get("pageSize") == null) ? "10" : map.get("pageSize").toString();
		PageHelper.startPage(Integer.parseInt(pageNum), Integer.parseInt(pageSize));
		Map<String, Object> result = chargeBillCollectService.query(map);
		List<Map<String, Object>> dataList = (List<Map<String, Object>>) result.get("pageInfo");
		dataList.sort((Map<String, Object> m1, Map<String, Object> m2) -> Double.valueOf(m2.get("汇总").toString())
				.compareTo(Double.valueOf(m1.get("汇总").toString())));
		result.put("pageInfo", new PageInfo<>(dataList));
		return succ(result);
	}

	@ApiOperation(value = "扣款汇总下载", notes = "扣款汇总下载")
	@GetMapping(value = "/download")
	public void download(
			@ApiParam(value = "{\"startDate\" : \"2018-06\",\"endDate\" : \"2018-12\",\"dateType\" : \"1\",\"flag\" : \"0\"}") @RequestParam Map<String, Object> map,
			HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
		Map<String, Object> result = chargeBillCollectService.download(map);
		String[] start = map.get("startDate").toString().split("-");
		String[] end = map.get("endDate").toString().split("-");
		String date = "";
		if (start.length > 1 && end.length > 1 && "1".equals(map.get("dateType").toString())) {
			date = start[0] + "年" + start[1] + "月份" + "-" + end[0] + "年" + end[1] + "月份";
		} else if ("2".equals(map.get("dateType").toString())) {
			date = start[0] + "年-" + end[0] + "年";
		} else if ("3".equals(map.get("dateType").toString())) {
			date = start[0] + "年Q1-" + end[0] + "年Q4";
		}
		String codedFileName = "";
		if ("1".equals(map.get("dateType").toString())) {
			codedFileName = URLEncoder.encode(date + "扣款汇总", "UTF-8");
		} else if ("2".equals(map.get("dateType").toString())) {
			codedFileName = URLEncoder.encode(date + "扣款汇总", "UTF-8");
		} else if ("3".equals(map.get("dateType").toString())) {
			codedFileName = URLEncoder.encode(date + "扣款汇总", "UTF-8");
		}
		@SuppressWarnings("unchecked")
		List<Map<String, Object>> list = (List<Map<String, Object>>) result.get("list");
		list.sort((Map<String, Object> m1, Map<String, Object> m2) -> Double.valueOf(m2.get("汇总").toString())
				.compareTo(Double.valueOf(m1.get("汇总").toString())));
		@SuppressWarnings("unchecked")
		List<ExcelExportEntity> entityList = (List<ExcelExportEntity>) result.get("entityList");
		FileUtil.exportExcel(new ExportParams(), entityList, list, codedFileName, response, request);
	}

}
