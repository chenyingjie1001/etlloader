package com.firesoon.idaweb.web.assessment;

import com.firesoon.commoncasshiro.action.BaseAction;
import com.firesoon.idaservice.assessment.AssessmentService;
import com.firesoon.utils.FileUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.params.ExcelExportEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author create by yingjie.chen on 2019/1/25.
 * @version 2019/1/25 10:24
 */
@Api
@Slf4j
@RestController
@RequestMapping("/api/assess/")
public class AssessmentAction extends BaseAction {

    @Autowired
    private AssessmentService assessmentService;

    @ApiOperation(value = "月度查询", notes = "月度查询")
    @RequestMapping(value = "months", method = RequestMethod.POST)
    public Object months(@ApiParam(value = "{\"month\":\"2019-01\", \"pageNum\":\"1\", \"pageSize\":\"10\"}") @RequestBody Map<String, Object> params){
        PageHelper.startPage(Integer.parseInt(params.get("pageNum").toString()), Integer.parseInt(params.get("pageSize").toString()));
        return succ(new PageInfo<>(assessmentService.listForMonth(params)));
    }
    @ApiOperation(value = "月度查询-下载", notes = "月度查询-下载")
    @RequestMapping(value = "monthsForDownload", method = RequestMethod.GET)
    public void monthsForDownload(HttpServletRequest request, HttpServletResponse response, @RequestParam Map<String, Object> params){
        List<Map<String, Object>> data = assessmentService.listForMonth(params);
        ExcelExportEntity entity1 = new ExcelExportEntity("科室", "DEPARTMENT");
        ExcelExportEntity entity2 = new ExcelExportEntity("扣款金额", "CHATGE_MONEY");
        ExcelExportEntity entity3 = new ExcelExportEntity("考核得分", "ASSESS_SCORE");
        ExcelExportEntity entity4 = new ExcelExportEntity("劣质成本得分", "POORCOST_SCORE");
        ArrayList<ExcelExportEntity> entitylist = new ArrayList<>();
        entitylist.add(entity1);
        entitylist.add(entity2);
        entitylist.add(entity3);
        entitylist.add(entity4);
        String filename;
        try{
            String[] args = params.get("month").toString().split("-");
            filename = args[0]+"年"+args[1]+"月考核表";
        }catch (Exception e){
            throw new RuntimeException("月份参数为空！");
        }
        FileUtil.exportExcel(new ExportParams(), entitylist, data, filename, response, request);
    }


    @ApiOperation(value = "年度查询", notes = "年度查询")
    @RequestMapping(value = "years", method = RequestMethod.POST)
    public Object years(@ApiParam(value = "{\"year\":\"2018\", \"pageNum\":\"1\", \"pageSize\":\"10\"}") @RequestBody Map<String, Object> params){
        PageHelper.startPage(Integer.parseInt(params.get("pageNum").toString()), Integer.parseInt(params.get("pageSize").toString()));
        return succ(new PageInfo<>(assessmentService.listForYear(params)));
    }

    @ApiOperation(value = "年度查询-下载", notes = "年度查询-下载")
    @RequestMapping(value = "yearsForDownload", method = RequestMethod.GET)
    public void yearsForDownload(HttpServletRequest request, HttpServletResponse response, @RequestParam Map<String, Object> params){
        List<Map<String, Object>> data = assessmentService.listForYear(params);
        ExcelExportEntity entity1 = new ExcelExportEntity("科室", "DEPARTMENT");
        ExcelExportEntity entity2 = new ExcelExportEntity("扣款金额", "CHATGE_MONEY");
        ExcelExportEntity entity3 = new ExcelExportEntity("考核得分", "ASSESS_SCORE");
        ExcelExportEntity entity4 = new ExcelExportEntity("劣质成本得分", "POORCOST_SCORE");
        ArrayList<ExcelExportEntity> entitylist = new ArrayList<>();
        entitylist.add(entity1);
        entitylist.add(entity2);
        entitylist.add(entity3);
        entitylist.add(entity4);
        String filename = params.get("year")+"年考核表";
        FileUtil.exportExcel(new ExportParams(), entitylist, data, filename, response, request);
    }

}
