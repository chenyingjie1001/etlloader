package com.firesoon.idaweb.web.chargeBill;

import com.firesoon.commoncasshiro.action.BaseAction;
import com.firesoon.commoncasshiro.aop.annotation.IdaPointcut;
import com.firesoon.idaservice.chargeBill.ChargeBillService;
import com.firesoon.idaservice.chargeBillDetail.ChargeBillDetailService;
import com.firesoon.idaservice.importRecord.ImportRecordService;
import com.firesoon.utils.PropertiesUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import java.io.File;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Api
@RestController
@RequestMapping(value = "/api/chargeBill/", method = RequestMethod.POST)
public class ChargeBillAction extends BaseAction {
	@Autowired
	private ChargeBillService chargeBillService;

	@Autowired
	private ChargeBillDetailService chargeBillDetailService;

	@Autowired
	private ImportRecordService importRecordService;

	@IdaPointcut
	@ApiOperation(value = "查扣款单", notes = "查扣款单")
	@GetMapping(value = "/query")
	public Object query(
			@ApiParam(value = "{\"month\" : \"2018-11\",\"pageNum\" : \"1\",\"pageSize\" : \"10\"}") @RequestBody Map<String, Object> map) {
		String pageNum = (map.get("pageNum") == null) ? "1" : map.get("pageNum").toString();
		String pageSize = (map.get("pageSize") == null) ? "10" : map.get("pageSize").toString();
		PageHelper.startPage(Integer.parseInt(pageNum), Integer.parseInt(pageSize));
		return succ(new PageInfo<>(chargeBillService.query(map)));
	}

	@ApiOperation(value = "删扣款单", notes = "删扣款单")
	@GetMapping(value = "/delete")
	public Object delete(@ApiParam(value = "{\"month\" : \"2018-11\",\"billtype\" : \"1\",\"id\" : \"asdfs\"}") @RequestBody Map<String, Object> map) {
		List<Map<String, Object>> dataList = importRecordService.findAllAddress(map);
		if (dataList.size() > 0 && dataList.get(0) != null) {
			String path = PropertiesUtil.properties.getProperty("xlsfilePath");
			for (Map<String, Object> data : dataList) {
				String address = data.getOrDefault("FILE_ADDRESS", "").toString();
				File file = new File(path + address);
				if (file.exists()) {
					file.delete();
				}
			}
		}
		chargeBillService.delete(map);
		chargeBillDetailService.delete(map);
		importRecordService.delete(map);
		return succ();
	}

}
