package com.firesoon.idaweb.web.common;

import com.firesoon.commoncasshiro.action.BaseAction;
import com.firesoon.idaservice.common.CommonService;
import com.firesoon.commoncasshiro.aop.annotation.IdaPointcut;
import com.firesoon.utils.PropertiesUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author create by yingjie.chen on 2018/11/22.
 * @version 2018/11/22 15:02
 */
@Api
@Slf4j
@RestController
@RequestMapping(value = "/api/common/", method = RequestMethod.POST)
public class CommonAction extends BaseAction {

	@Autowired
	private CommonService commonService;

//	@ApiOperation(value = "menu", notes = "menu")
//	@RequestMapping(value = "queryMenus")
//	public Object queryMenus(@ApiParam("{\"search\":\"ida\"}")@RequestBody Map<String, Object> params){
//		return succ(commonService.queryApp(params));
//	}



	@ApiOperation(value = "是否导入过", notes = "是否导入过")
	@RequestMapping(value = "isImport")
	public Object isImport(@ApiParam("{\"billtype\":\"3\"}")@RequestBody Map<String, Object> params){
		return succ(commonService.isImport(params));
	}

	@ApiOperation(value = "upload上传通用方法", notes = "upload上传通用方法")
	@RequestMapping(value = "upload")
	public Object upload(MultipartFile file, HttpServletRequest request) throws IOException {
		if (file.isEmpty()) {
			return succ("文件不能为空");
		}
		String filename = file.getOriginalFilename();
		String suffix = filename.substring(filename.lastIndexOf(".") + 1);
		if (suffix == null || !"CSV,XLSX,XLS,".contains(suffix.toUpperCase() + ",")) {
			throw new RuntimeException("不支持的文件类型！");
		}
		String filepath = PropertiesUtil.properties.getProperty("xlsfilePath") + filename;
		file.transferTo(new File(filepath));
		Map<String, Object> result = new HashMap<>();
		result.put("filename", filename);
		return succ(result);
	}

	@IdaPointcut
	@ApiOperation(value = "importXls", notes = "importXls")
	@RequestMapping(value = "import")
	public Object importXls(
			@ApiParam(value = "{\"filename\": \"市医保8月第［1］版.xls\",\"billtype\": \"1\",\"month\": \"2018-08\", \"bak\": \"这个是备注\"}") @RequestBody Map<String, Object> params) {
		Map<String, Object> data = new HashMap<>();
		try {
			data.put("total", commonService.importXls(params));
			data.put("msg", "导入成功");
		}catch(Exception e) {
			data.put("total", 0);
			data.put("msg", e.getMessage());
		}
		data.put("times", commonService.importTimes(params));
		return succ(data);
	}

	@ApiOperation(value = "billtypes", notes = "billtypes")
	@RequestMapping(value = "billtypes")
	public Object billtypes() {
		return succ(commonService.billtypes());
	}

	@ApiOperation(value = "existBilltype", notes = "existBilltype")
	@RequestMapping(value = "existBilltype")
	public Object existBilltype() {
		return succ(commonService.getExistBillType());
	}

}