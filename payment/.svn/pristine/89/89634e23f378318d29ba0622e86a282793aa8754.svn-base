package com.firesoon.idaservice.common.impl;

import com.alibaba.druid.support.json.JSONUtils;
import com.firesoon.dto.tree.Tree;
import com.firesoon.dto.tree.TreeNode;
import com.firesoon.idaservice.common.CommonService;
import com.firesoon.paymentmapper.common.CommonMapper;
import com.firesoon.paymentmapper.importRecord.ImportRecordMapper;
import com.firesoon.utils.PropertiesUtil;
import com.firesoon.utils.StringUtil;
import com.firesoon.utils.UserUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.entity.result.ExcelImportResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author create by yingjie.chen on 2018/11/22.
 * @version 2018/11/22 15:57
 */
@Slf4j
@Primary
@Service
public class CommonServiceImpl implements CommonService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private CommonMapper commonMapper;

    @Autowired
    private ImportRecordMapper importRecordMapper;


    @Override
    public Map<String, Object> queryApp(Map<String, Object> mapSea) {
        String appCode = mapSea.get("search") + "";
        Map<String, Object> resultMap = new HashMap<>();
        String sql = "select *  from portal.base_application ba where ba.id in (select bra.app_id from portal.base_role_application bra  where bra.role_id in (select bur.role_id   from portal.base_user_role bur    where bur.user_id = "
                + UserUtil.getUser().getId() + ")) order by ba.dorder ";

        List<Map<String, Object>> appList = jdbcTemplate.queryForList(sql);
        String dorder = "dorder";
        if (appCode.equals("ida")) { // 财务按dorder排序，其他的应用按id排序
            dorder = "dorder";
        } else {
            dorder = "id";
        }
        List<Map<String, Object>> resList = jdbcTemplate.queryForList(
                "select *  from portal.base_resource br where br.id in(select resource_id from portal.base_role_resource brr ,portal.base_role br where br.id=brr.role_id and br.id in(select bur.role_id   from portal.base_user_role bur    where bur.user_id = " + UserUtil.getUser().getId() + ")) and resource_code like '"
                        + appCode + "_%' order by  " + dorder);

        Map<String, Object> mapUser = jdbcTemplate.queryForList("select * from portal.base_user where id= " +
                UserUtil.getUser().getId()).get(0);

        resultMap.put("appList", appList);
        resultMap.put("mapUser", mapUser);
        if (appCode.equals("ida")) {// 财务返回树状
            Map<String, TreeNode> mapTree = new LinkedHashMap<String, TreeNode>();
            for (Map<String, Object> map : resList) {
                TreeNode treeNode = new TreeNode(map.get("ID").toString(), map.get("RESOURCE_NAME").toString(),
                        map.get("PID").toString());
                treeNode.setAttributes(map);

                mapTree.put(map.get("ID").toString(), treeNode);
            }
            List<TreeNode> rootList = Tree.parser(mapTree, "0");
            resultMap.put("resList", rootList);
        } else {// 其他的直接返回
            resultMap.put("resList", resList);
        }
        return resultMap;
    }

    @Override
    public Boolean isImport(Map<String, Object> params) {
        boolean result = false;
        Map<String, Object> tables = commonMapper.isImport(params);
        if (Integer.parseInt(tables.get("table").toString()) > 0) {
            result = true;
        }
        return result;
    }

    @Override
    public Integer importTimes(Map<String, Object> params) {
        return importRecordMapper.findImportRecord(params).size();
    }


    @Override
    public List<Map<String, Object>> billtypes() {
        return commonMapper.billtypes();
    }

    @Override
    public List<Map<String, Object>> getExistBillType() {
        String sql = "select b.id,b.name from user_tables a,ida_chargebilltype_config b where table_name like 'IDA_CHARGEBILL_DETAIL_%' and table_name not like '%TEST' and substr(a.table_name,23) = b.id";
        List<Map<String, Object>> types = jdbcTemplate.queryForList(sql);
        return types;
    }

    @Override
    @Transactional
    public Integer importXls(Map<String, Object> params) {
        //成功条数
        int total = 0;
        //成功条数
        if (params.get("bak") == null) {
            params.put("bak", "");
        }
        insertOrUpdateBill(params);
        commonMapper.insertBillRecord(params);
        String areqsql = "select areaname \"areaname\" from ida_area where isuse = 1 and rownum = 1";
        Map<String, Object> area = jdbcTemplate.queryForMap(areqsql);

        String filename = PropertiesUtil.properties.getProperty("xlsfilePath") + params.get("filename");
        String month = params.get("month").toString();
        String billtype = params.get("billtype").toString();

        List<Map<String, Object>> list = null;
        ImportParams importParams = new ImportParams();
        importParams.setHeadRows(1);
        importParams.setTitleRows(0);
        if ("绍兴".equals(area.get("areaname"))) {
            // 市医保、柯桥职工特殊处理：类型分别为1和2
            if ("1".equals(billtype)) {
                importParams.setTitleRows(1);
                list = ExcelImportUtil.importExcel(new File(filename), Map.class, importParams);
            } else if ("2".equals(billtype)) {
                // 绍兴职工的没改时占了一行，手动修改后标题占了两行，需要特殊处理，使用wps没有这种情况
                importParams.setTitleRows(1);
                importParams.setImportFields(new String[]{"序号"});
                try {
                    ExcelImportResult<Map<String, Object>> res = ExcelImportUtil.importExcelVerify(new File(filename), Map.class, importParams);
                    if (!res.isVerfiyFail()) {
                        list = res.getList();
                    }
                } catch (Exception e) {
                    importParams.setTitleRows(2);
                    ExcelImportResult<Map<String, Object>> res = ExcelImportUtil.importExcelVerify(new File(filename), Map.class, importParams);
                    if (!res.isVerfiyFail()) {
                        list = res.getList();
                    }
                }
            } else {
                list = ExcelImportUtil.importExcel(new File(filename), Map.class, importParams);
            }
        } else {
            list = ExcelImportUtil.importExcel(new File(filename), Map.class, importParams);
        }
        //查找excle配置
        String sql = "select name \"name\", type \"type\", fieldname \"fieldname\", ISKEY \"key\" from ida_xls_view where billtype = '" + billtype + "'";
        log.debug("查找excle配置: " + sql);
        List<Map<String, Object>> xlsViews = jdbcTemplate.queryForList(sql);
        //第一步 先判断这个xls表有没建
        String tablename = "IDA_CHARGEBILL_DETAIL";
        if (StringUtils.isNotEmpty(billtype)) {
            tablename += "_" + billtype;
        }
        sql = "select count(1) \"number\" from user_tables where table_name = '" + tablename + "'";
        log.debug("判断表是否存在: " + sql);
        Map<String, Object> number = jdbcTemplate.queryForMap(sql);
        if (Integer.parseInt(number.get("number").toString()) > 0) {
            //表已经存在 不需要创建

        } else {
            createTable(xlsViews, tablename);
        }
        //第二步判断是否有数据
        // 确定明细的主键 做成配置
        List<Map<String, Object>> keys = new ArrayList<>();
        for (Map<String, Object> xlsView : xlsViews) {
            if ("1".equals(xlsView.get("key"))) {
                //是主键
                Map<String, Object> map = new HashMap<>();
                map.put("name", xlsView.get("name"));
                map.put("fieldname", xlsView.get("fieldname"));
                keys.add(map);
            }
        }
        if ("宁波".equals(area.get("areaname"))) {
            ningboAppeal(total, xlsViews, month, list, keys, tablename);
        } else if ("绍兴".equals(area.get("areaname"))) {
            if ("3".equals(billtype)) {
                shaoxingkeqiaoAppeal(total, xlsViews, month, list, keys, tablename);
            } else
                shaoxingAppeal(total, xlsViews, month, list, keys, tablename);
        }
        // 调用存储过程model.P_getDept('2018-08');
        String finalTablename = tablename;
        Thread thread = new Thread(() -> {
            jdbcTemplate.execute("call ida.P_cory_test('" + finalTablename + "')");
            jdbcTemplate.execute("call model.P_getDept('" + month + "', 'ida." + finalTablename + "')");
        });
        thread.start();
        return total;
    }


    @Override
    public void shaoxingkeqiaoAppeal(Integer total, List<Map<String, Object>> xlsViews, String month, List<Map<String, Object>> list, List<Map<String, Object>> keys, String tablename) {
        String sql = "select count(1) \"count\" from " + tablename + " t where t.month= '" + month + "'";
        log.debug("判断是否有当月数据: " + sql);
        String chargeMoney = "扣款金额";
        Map<String, Object> count = jdbcTemplate.queryForMap(sql);
//        List<Map<String, Object>> newList = list.stream().filter(t -> !"合计：".equals(t.get("序号"))).collect(Collectors.toList());
        if (Integer.parseInt(count.get("count").toString()) > 0) {
            //先插入一张临时表
            /*jdbcTemplate.execute("drop table ida_temp");
            String tablename = createTempTable(xlsViews);
            for (Map<String, Object> data : list) {
                insert2Tempdb(xlsViews, month, data, "1", tablename);
            }
            List<Map<String, Object>> templist = jdbcTemplate.queryForList("select * from ida_temp" );*/
            //当月的数据已经存在
            //先把所有数据isnew改成0 , 申请成功标记改为成功1
            String updateNewAndAppeal = "update " + tablename + " t set t.isnew = '0', " +
                    "t.isappeal = '1', " +
                    "t.updatedate=sysdate, " +
                    "t.updateBy='" + UserUtil.getUser().getUser_name() + "' " +
                    "where t.month = '" + month + "'";
            log.debug("先把所有数据isnew改成0 , 申请成功标记改为成功1：" + updateNewAndAppeal);
            jdbcTemplate.execute(updateNewAndAppeal);
            //判断有无新增的 标记为新增
            //判断有误减少的 标记为申请成功
            //判断是否扣除金额为0 标记为申请成功

            for (Map<String, Object> data : list) {
                //判断唯一条件的sql，没有主键就不必考虑这个了
                StringBuilder wsql = new StringBuilder();
                wsql.append(" 1=1 ");
                for (Map<String, Object> key : keys) {
//                    if (chargem > 0 && chargeMoney.equals(key.get("name"))) {
//                        //这条可以随意抵消一条 优先有appealstr申诉理由的
//                        wsql.append(" and t." + key.get("fieldname") + "= '" + StringUtil.null2Str("-" + data.get(key.get("name"))) + "' ");
//                    } else {
                    wsql.append(" and t." + key.get("fieldname") + "= '" + StringUtil.null2Str(data.get(key.get("name"))) + "' ");
//                    }
                }

                String checksql = "select * from " + tablename + " t where " + wsql.toString() + " and t.month = '" + month + "' and t.isappeal = '1'";
                log.debug("判断该条数据是否存在：" + checksql);
                List<Map<String, Object>> checkList = jdbcTemplate.queryForList(checksql);
                if (checkList.size() > 0) {
                    if (checkList.size() == 1) {
//                        if (chargem > 0) {
//                            //金额为正 且只有一条则为成功，啥都不需要做
//                        } else {
                        Map<String, Object> dmap = checkList.get(0);
                        String sqlx = "update " + tablename + " t set t.isappeal = '0' where id = '" + dmap.get("ID") + "'";
                        if (StringUtils.isNotEmpty(StringUtil.null2Str(dmap.get("APPEALSTR")))) {
                            sqlx = "update " + tablename + " t set t.isappeal = '-1' where id = '" + dmap.get("ID") + "'";
                        }
                        jdbcTemplate.execute(sqlx);
//                        }

                    } else if (checkList.size() > 1) {
                        checkList.sort(Comparator.comparing(o -> StringUtil.null2Str(o.get("ISWRITE"))));
                        for (Map<String, Object> dmap : checkList) {
//                            if (chargem > 0) {
//                                //金额为正 且只有一条则为成功，啥都不需要做
//                                break;
//                            } else {
                            String sqlx = "update " + tablename + " t set t.isappeal = '0' where id = '" + dmap.get("ID") + "'";
                            if (StringUtils.isNotEmpty(StringUtil.null2Str(dmap.get("APPEALSTR")))) {
                                sqlx = "update " + tablename + " t set t.isappeal = '-1' where id = '" + dmap.get("ID") + "'";
                            }
                            jdbcTemplate.execute(sqlx);
                            break;
//                            }
                        }
                    }

                } else {
                    //不存在
                    insert2db(xlsViews, month, data, "1", tablename);
                    total++;
                }

            }
        } else {
            //不存在
            for (Map<String, Object> data : list) {
                insert2db(xlsViews, month, data, "0", tablename);
                total++;
            }
            log.info("新增语句执行结束");
        }
    }

    @Override
    public void ningboAppeal(Integer total, List<Map<String, Object>> xlsViews, String month,
                             List<Map<String, Object>> list, List<Map<String, Object>> keys, String tablename) {
        String sql = "select count(1) \"count\" from " + tablename + " t where t.month= '" + month + "'";
        log.debug("判断是否有当月数据: " + sql);
        String chargeMoney = "剔除费用额";
        List<Map<String, Object>> less0List = list.stream().filter(m -> Double.parseDouble(StringUtil.null2Str(m.get(chargeMoney))) < 0).collect(Collectors.toList());
        List<Map<String, Object>> biger0List = list.stream().filter(m -> Double.parseDouble(StringUtil.null2Str(m.get(chargeMoney))) > 0).collect(Collectors.toList());
        Map<String, Object> count = jdbcTemplate.queryForMap(sql);
//        List<Map<String, Object>> newList = list.stream().filter(t -> !"合计：".equals(t.get("序号"))).collect(Collectors.toList());
        if (Integer.parseInt(count.get("count").toString()) > 0) {
            //先插入一张临时表
            /*jdbcTemplate.execute("drop table ida_temp");
            String tablename = createTempTable(xlsViews);
            for (Map<String, Object> data : list) {
                insert2Tempdb(xlsViews, month, data, "1", tablename);
            }
            List<Map<String, Object>> templist = jdbcTemplate.queryForList("select * from ida_temp" );*/
            //当月的数据已经存在
            //先把所有数据isnew改成0 , 申请成功标记改为成功1
            String updateNewAndAppeal = "update " + tablename + " t set t.isnew = '0', " +
                    "t.isappeal = '1', " +
                    "t.updatedate=sysdate, " +
                    "t.updateBy='" + UserUtil.getUser().getUser_name() + "' " +
                    "where t.month = '" + month + "'";
            log.debug("先把所有数据isnew改成0 , 申请成功标记改为成功1：" + updateNewAndAppeal);
            jdbcTemplate.execute(updateNewAndAppeal);
            //判断有无新增的 标记为新增
            //判断有误减少的 标记为申请成功
            //判断是否扣除金额为0 标记为申请成功

            for (Map<String, Object> data : less0List) {
                //判断唯一条件的sql，没有主键就不必考虑这个了
                StringBuilder wsql = new StringBuilder();
                wsql.append(" 1=1 ");
                //如果费用额度是正的
                Double chargem = Double.parseDouble(data.get(chargeMoney).toString());

                for (Map<String, Object> key : keys) {
//                    if (chargem > 0 && chargeMoney.equals(key.get("name"))) {
//                        //这条可以随意抵消一条 优先有appealstr申诉理由的
//                        wsql.append(" and t." + key.get("fieldname") + "= '" + StringUtil.null2Str("-" + data.get(key.get("name"))) + "' ");
//                    } else {
                    wsql.append(" and t." + key.get("fieldname") + "= '" + StringUtil.null2Str(data.get(key.get("name"))) + "' ");
//                    }
                }

                String checksql = "select * from " + tablename + " t where " + wsql.toString() + " and t.month = '" + month + "' and t.isappeal = '1'";
                log.debug("判断该条数据是否存在：" + checksql);
                List<Map<String, Object>> checkList = jdbcTemplate.queryForList(checksql);
                if (checkList.size() > 0) {
                    if (checkList.size() == 1) {
//                        if (chargem > 0) {
//                            //金额为正 且只有一条则为成功，啥都不需要做
//                        } else {
                        Map<String, Object> dmap = checkList.get(0);
                        String sqlx = "update " + tablename + " t set t.isappeal = '0' where id = '" + dmap.get("ID") + "'";
                        if (StringUtils.isNotEmpty(StringUtil.null2Str(dmap.get("APPEALSTR")))) {
                            sqlx = "update " + tablename + " t set t.isappeal = '-1' where id = '" + dmap.get("ID") + "'";
                        }
                        jdbcTemplate.execute(sqlx);
//                        }

                    } else if (checkList.size() > 1) {
                        checkList.sort(Comparator.comparing(o -> StringUtil.null2Str(o.get("ISWRITE"))));
                        for (Map<String, Object> dmap : checkList) {
//                            if (chargem > 0) {
//                                //金额为正 且只有一条则为成功，啥都不需要做
//                                break;
//                            } else {
                            String sqlx = "update " + tablename + " t set t.isappeal = '0' where id = '" + dmap.get("ID") + "'";
                            if (StringUtils.isNotEmpty(StringUtil.null2Str(dmap.get("APPEALSTR")))) {
                                sqlx = "update " + tablename + " t set t.isappeal = '-1' where id = '" + dmap.get("ID") + "'";
                            }
                            jdbcTemplate.execute(sqlx);
                            break;
//                            }
                        }
                    }

                } else if (chargem < 0) {
                    //不存在
                    insert2db(xlsViews, month, data, "1", tablename);
                    total++;
                }

            }
            //处理为正的
            doBigger0List(xlsViews, biger0List, chargeMoney, keys, month, tablename);
            doBigger0Group(xlsViews, tablename, keys, month, chargeMoney);
        } else {
            //不存在
            for (Map<String, Object> data : less0List) {
                insert2db(xlsViews, month, data, "0", tablename);
                total++;
            }
            doBigger0List(xlsViews, biger0List, chargeMoney, keys, month, tablename);
            log.info("新增语句执行结束");
            doBigger0Group(xlsViews, tablename, keys, month, chargeMoney);
        }
        new Thread(() -> updateItemAndReason(month, tablename)).start();
    }

    /**
     * 剩下的正数的分组处理
     */
    private void doBigger0Group(List<Map<String, Object>> xlsViews, String tablename, List<Map<String, Object>> keys, String month, String chargeMoney) {

        if (keys != null && keys.size() == 0) {
            //没有设置主键 无法判断
            return;
        }
        String chargeFiled = "";
        for (Map<String, Object> map : xlsViews) {
            String name = map.get("name").toString();
            if (chargeMoney.equals(name)) {
                chargeFiled = map.get("fieldname").toString();
                break;
            }
        }
        StringBuilder tj = new StringBuilder();
        for (int i = 0; i < keys.size(); i++) {
            Map<String, Object> key = keys.get(i);
            if (i == keys.size() - 1) {
                tj.append(" t." + key.get("fieldname"));
            }else
                tj.append(" t." + key.get("fieldname") + ", ");
        }
        StringBuilder sb = new StringBuilder();
        sb.append("select " + tj.toString() + ", sum(" + chargeFiled + ") as ssum" + " from " + tablename +
                " t where t.month = '" + month + "' and " + " t." + chargeFiled + " > 0 " + " group by " + tj.toString());
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sb.toString());
        //查出当月的大于0 的数据 进行分组 然后比对
        for (Map<String, Object> data : list) {
            StringBuilder wsql = new StringBuilder();
            wsql.append(" 1=1 ");
            //如果费用额度是正的
            Double chargem = Double.parseDouble(data.get("SSUM").toString());

            for (Map<String, Object> key : keys) {
                //这条可以随意抵消一条 优先有appealstr申诉理由的
                if (key.get("fieldname").equals(chargeFiled)) {
                    wsql.append(" and t." + key.get("fieldname") + "= '" + StringUtil.null2Str("-" + data.get("SSUM") + "' "));
                } else {
                    wsql.append(" and t." + key.get("fieldname") + "= '" + StringUtil.null2Str(data.get(key.get("fieldname"))) + "' ");
                }

            }
            String checksql = "select * from " + tablename + " t where " + wsql.toString() + " and t.month = '" + month + "' and t.isappeal != '1'";
            log.debug("判断该条数据是否存在：" + checksql);
            List<Map<String, Object>> checkList = jdbcTemplate.queryForList(checksql);
            StringBuilder sqld = new StringBuilder();
            sqld.append(" 1 = 1 ");
            if (checkList.size() > 0) {
                if (checkList.size() == 1) {
                    Map<String, Object> dmap = checkList.get(0);
                    String sqlx = "update " + tablename + " t set t.isappeal = '1' where id = '" + dmap.get("ID") + "'";
                    jdbcTemplate.execute(sqlx);
                } else if (checkList.size() > 1) {
                    checkList.sort((o1, o2) -> StringUtil.null2Str(o2.get("ISWRITE")).compareTo(StringUtil.null2Str(o1.get("ISWRITE"))));
                    for (Map<String, Object> dmap : checkList) {
                        String sqlx = "update " + tablename + " t set t.isappeal = '1' where id = '" + dmap.get("ID") + "'";
                        jdbcTemplate.execute(sqlx);
                        break;
                    }
                }
                //删除对应的正数的数据
                for (Map<String, Object> key : keys) {
                    sqld.append(" and t." + key.get("fieldname") + "= '" + StringUtil.null2Str(data.get(key.get("fieldname"))) + "' ");

                }
                String sql = "delete from " + tablename + " t where " + sqld.toString() + " and t.month = '" + month + "'";
                jdbcTemplate.execute(sql);
            }
        }

    }

    private void doBigger0List(List<Map<String, Object>> xlsViews, List<Map<String, Object>> biger0List, String chargeMoney, List<Map<String, Object>> keys, String month, String tablename) {
        for (Map<String, Object> data : biger0List) {
            StringBuilder wsql = new StringBuilder();
            wsql.append(" 1=1 ");
            //如果费用额度是正的
            Double chargem = Double.parseDouble(data.get(chargeMoney).toString());

            for (Map<String, Object> key : keys) {
                if (chargem > 0 && chargeMoney.equals(key.get("name"))) {
                    //这条可以随意抵消一条 优先有appealstr申诉理由的
                    wsql.append(" and t." + key.get("fieldname") + "= '" + StringUtil.null2Str("-" + data.get(key.get("name"))) + "' ");
                } else {
                    wsql.append(" and t." + key.get("fieldname") + "= '" + StringUtil.null2Str(data.get(key.get("name"))) + "' ");
                }
            }

            String checksql = "select * from " + tablename + " t where " + wsql.toString() + " and t.month = '" + month + "' and t.isappeal != '1'";
            log.debug("判断该条数据是否存在：" + checksql);
            List<Map<String, Object>> checkList = jdbcTemplate.queryForList(checksql);

            if (checkList.size() > 0) {
                if (checkList.size() == 1) {
                    Map<String, Object> dmap = checkList.get(0);
                    String sqlx = "update " + tablename + " t set t.isappeal = '1' where id = '" + dmap.get("ID") + "'";
                    jdbcTemplate.execute(sqlx);
                } else if (checkList.size() > 1) {
                    checkList.sort((o1, o2) -> StringUtil.null2Str(o2.get("ISWRITE")).compareTo(StringUtil.null2Str(o1.get("ISWRITE"))));
                    for (Map<String, Object> dmap : checkList) {
                        String sqlx = "update " + tablename + " t set t.isappeal = '1' where id = '" + dmap.get("ID") + "'";
                        jdbcTemplate.execute(sqlx);
                        break;
                    }
                }
            } else {
                //把多余的正数插入数据库
                insert2db(xlsViews, month, data, "0", tablename);
            }
        }
    }

    /**
     * 更新宁波的项目名称和扣款原因
     *
     * @param month
     */
    private void updateItemAndReason(String month, String tablename) {
        String sql = "select * from " + tablename + " a where a.month = '" + month + "' and a.item_name is null and a.charge_reason is null";
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
//        List<Map<String, Object>> list = new ArrayList<>();
//        Map<String, Object> map = new HashMap<>();
//        map.put("BAK", "V2.0票据：票据审核：扣减金额:超医保支付范围,项目:扣减项目,金额:扣款金额");
//        list.add(map);
        list.forEach(item -> {
            try {
                String bak = StringUtil.null2Str(item.get("BAK"));
                String usql = "";
                if (bak.contains("V2.0票据：V2.0自动扣除:")) {
                    String temp = "V2.0票据：V2.0自动扣除:";
                    String str = bak.substring(temp.length(), bak.length());
                    String[] strs = str.split(" ");
                    if (strs.length >= 2) {
                        usql = "update " + tablename + " t set t.item_name = '" + strs[0] + "', t.charge_reason = '" + strs[1] + "' where id = '" + item.get("ID") + "'";
                    } else {
                    /*try {
                        //第一个(
                        int fi = str.indexOf("(");
                        String arg1 = str.substring(0, fi);
                        //第二个(
                        int fs = str.indexOf(")") + 1;
                        //第三个(
                        int ft = str.lastIndexOf("(") - 1;
                        System.out.println(str);
                        String arg2 = str.substring(fs + 1, ft);
                        usql = "update "+tablename+" t set t.item_name = '" + arg1 + "', t.charge_reason = '" + arg2 + "' where id = '" + item.get("ID") + "'";
                    } catch (Exception e) {
                        e.printStackTrace();
                    }*/
                    }
                } else if (bak.contains("V2.0票据：V2.0智能审核:")) {
                    String temp = "V2.0票据：V2.0智能审核:";
                    String str = bak.substring(temp.length(), bak.length());
                    String[] strs = str.split(" ");
                    if (strs.length >= 2) {
                        usql = "update " + tablename + " t set t.item_name = '" + strs[0] + "', t.charge_reason = '" + strs[1] + "' where id = '" + item.get("ID") + "'";
                    }
                } else if (bak.contains("V2.0票据：票据审核：")) {
                    String temp = "V2.0票据：票据审核：";
                    String str = bak.substring(temp.length(), bak.length());
                    String[] args = str.split(",");
                    if (args.length >= 2) {
                        String[] strs = args[0].split(":");
                        String[] strss = args[1].split(":");
                        if (strs.length >= 2 && strss.length >= 2) {
                            usql = "update " + tablename + " t set t.item_name = '" + strss[1] + "', t.charge_reason = '" + strs[1] + "' where id = '" + item.get("ID") + "'";
                        }
                    }
                }
//            System.out.println(usql);
                if (!"".equals(usql)) {
                    jdbcTemplate.execute(usql);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

//    public static void main(String[] args) {
//        CommonServiceImpl service = new CommonServiceImpl();
//        service.updateItemAndReason("8");
//    }


    @Override
    public void shaoxingAppeal(Integer total, List<Map<String, Object>> xlsViews, String month,
                               List<Map<String, Object>> list, List<Map<String, Object>> keys, String tablename) {

//        String key = "审核意见书编码";
        String chargeMoney = "扣除金额";
        String feedbackComplaint = "反馈申诉";
        String sql = "select count(1) \"count\" from " + tablename + " t where t.month= '" + month + "'";
        log.debug("判断是否有当月数据: " + sql);
        Map<String, Object> count = jdbcTemplate.queryForMap(sql);
        List<Map<String, Object>> newList = list.stream().filter(t -> !"合计：".equals(t.get("序号"))).collect(Collectors.toList());
        if (Integer.parseInt(count.get("count").toString()) > 0) {
            //当月的数据已经存在
            //先把所有数据isnew改成0 , 申请成功标记改为成功1
            String updateNewAndAppeal = "update " + tablename + " t set t.isnew = '0', " +
                    "t.isappeal = '1', " +
                    "t.updatedate=sysdate, " +
                    "t.updateBy='" + UserUtil.getUser().getUser_name() + "' " +
                    "where t.month = '" + month + "'";
            log.debug("先把所有数据isnew改成0 , 申请成功标记改为成功1：" + updateNewAndAppeal);
            jdbcTemplate.execute(updateNewAndAppeal);
            //判断有无新增的 标记为新增
            //判断有误减少的 标记为申请成功
            //判断是否扣除金额为0 标记为申请成功
            for (Map<String, Object> data : newList) {
                //判断唯一条件的sql
                StringBuilder wsql = new StringBuilder();
                wsql.append(" 1=1 ");
                for (Map<String, Object> key : keys) {
                    wsql.append(" and t." + key.get("fieldname") + "= '" + StringUtil.null2Str(data.get(key.get("name"))) + "' ");
                }
                String charge_money = StringUtil.removeCurrency(StringUtil.null2Str(data.get(chargeMoney)));
                String feedback_complaint = StringUtil.null2Str(data.get(feedbackComplaint));

                String checksql = "select id, iswrite from " + tablename + " t where " + wsql.toString() + " and t.month = '" + month + "'";
                log.debug("判断该条数据是否存在：" + checksql);
                List<Map<String, Object>> list1 = jdbcTemplate.queryForList(checksql);
                if (list1 != null && list1.size() > 0) {
                    Map<String, Object> countMap = list1.get(0);
                    //存在
                    if (!StringUtils.isBlank(charge_money) && !"0".equals(charge_money)) {// 申请未成功标记改成0
                        //申请成功标记更新为0
                        //填写了申诉理由，但是不成功的即为失败
                        String updateAppeal = "update " + tablename + " t set t.isappeal = '0', ";
                        if (StringUtils.isNotEmpty(feedback_complaint)) {
                            updateAppeal += "t.appealstr = '" + feedback_complaint + "', ";
                        }
                        updateAppeal += "t.updatedate=sysdate, " +
                                "t.updateBy='" + UserUtil.getUser().getUser_name() + "' " +
                                "where id = '" + countMap.get("ID") + "'";
                        if ("1".equals(countMap.get("ISWRITE"))) {
                            updateAppeal = "update " + tablename + " t set t.isappeal = '-1', ";
                            if (StringUtils.isNotEmpty(feedback_complaint)) {
                                updateAppeal += "t.appealstr = '" + feedback_complaint + "', ";
                            }
                            updateAppeal += "t.updatedate=sysdate, " +
                                    "t.updateBy='" + UserUtil.getUser().getUser_name() + "' " +
                                    "where id = '" + countMap.get("ID") + "'";
                        }
                        log.debug("申请未成功标记改成0: " + updateAppeal);
                        jdbcTemplate.execute(updateAppeal);
                    } else if (!StringUtils.isBlank(charge_money) && "0".equals(charge_money)) {
                        //等于0
                        String updateMoney = "update " + tablename + " t set t.isappeal = '1', ";
                        if (StringUtils.isNotEmpty(feedback_complaint)) {
                            updateMoney += " t.appealstr = '" + feedback_complaint + "', ";
                        }
                        updateMoney += "t.updatedate=sysdate, " +
                                "t.updateBy='" + UserUtil.getUser().getUser_name() + "' " +
                                "where id = '" + countMap.get("ID") + "'";
                        log.debug("申请成功更改扣除金额，状态不需要改，批量的时候已经更新：" + updateMoney);
                        jdbcTemplate.execute(updateMoney);
                    }
                } else {
                    //不存在
                    insert2db(xlsViews, month, data, "1", tablename);
                    total++;
                }

            }

        } else {
            //不存在
            for (Map<String, Object> data : newList) {
                insert2db(xlsViews, month, data, "0", tablename);
                total++;
            }
            log.info("新增语句执行结束");
        }
    }

    private void insertOrUpdateBill(Map<String, Object> params) {
        String sql = "select count(1) \"time\" from ida_charge_bill where month = '" + params.get("month") + "' and billtype = '" + params.get("billtype") + "'";
        Map<String, Object> time = jdbcTemplate.queryForMap(sql);

        if (Integer.parseInt(time.get("time").toString()) > 0) {
            //update
            commonMapper.updateBill(params);
        } else {
            //insert
            commonMapper.insertBill(params);
        }
    }

    /**
     * 私有方法创建需要的表
     *
     * @param xlsViews
     */
    private void createTable(List<Map<String, Object>> xlsViews, String tablename) {
        StringBuilder sb = new StringBuilder();
        sb.append("create table " + tablename + "(ID VARCHAR2(64), month VARCHAR2(64), ");
        xlsViews.forEach(xls -> sb.append(xls.get("fieldname") + "  " + xls.get("type") + "(512), "));
        sb.append("deducteproject varchar2(64), ");  //扣款核算项目  需要数据开发去匹配
        sb.append("deductedept varchar2(64), ");  //扣款核算项目  需要数据开发去匹配
        sb.append("iswrite char(1), "); //是否填写   判断条件都是1是0否
        sb.append("appealstr varchar2(512), "); //申诉理由
        sb.append("appealimg clob, "); //申诉图片
        sb.append("appealdate date, "); //申诉时间
        sb.append("appealuser varchar2(32), "); //申诉人
        sb.append("uname varchar2(64), "); //图片name
        sb.append("isnew char(1), "); //是否新添加
        sb.append("isappeal varchar2(2), "); //是否申诉成功 0 未申诉， 1 申诉成功， -1申诉失败
        sb.append("createdate date, ");
        sb.append("updatedate date, ");
        sb.append("createby varchar2(32), ");
        sb.append("updateby varchar2(32) ");
        sb.append(")");
        log.debug("建表语句: " + sb.toString());
        jdbcTemplate.execute(sb.toString());
        xlsViews.forEach(xls -> jdbcTemplate.execute("comment on column " + tablename + "." + xls.get("fieldname") + " is '" + xls.get("name") + "'"));
        jdbcTemplate.execute("comment on column " + tablename + ".deducteproject is '扣款核算项目  需要数据开发去匹配'");
        jdbcTemplate.execute("comment on column " + tablename + ".iswrite is '是否填写   判断条件都是1是0否'");
        jdbcTemplate.execute("comment on column " + tablename + ".appealstr is '申诉理由'");
        jdbcTemplate.execute("comment on column " + tablename + ".appealimg is '申诉图片'");
        jdbcTemplate.execute("comment on column " + tablename + ".uname is '图片name'");
        jdbcTemplate.execute("comment on column " + tablename + ".isnew is '是否新添加'");
        jdbcTemplate.execute("comment on column " + tablename + ".isappeal is '是否申诉成功 0 未申诉， 1 申诉成功， -1申诉失败'");
        log.debug("建表语句执行结束");
    }

    /**
     * @param xlsViews
     */
    private String createTempTable(List<Map<String, Object>> xlsViews) {
        StringBuilder sb = new StringBuilder();
        String tablename = "ida_temp";
        sb.append("create table " + tablename + " (ID VARCHAR2(64), month VARCHAR2(64), ");
        xlsViews.forEach(xls -> sb.append(xls.get("fieldname") + "  " + xls.get("type") + "(512), "));
        sb.append("deducteproject varchar2(64), ");  //扣款核算项目  需要数据开发去匹配
        sb.append("deductedept varchar2(64), ");  //扣款核算项目  需要数据开发去匹配
        sb.append("iswrite char(1), "); //是否填写   判断条件都是1是0否
        sb.append("appealstr varchar2(512), "); //申诉理由
        sb.append("appealimg clob, "); //申诉图片
        sb.append("appealdate date, "); //申诉时间
        sb.append("appealuser varchar2(32), "); //申诉人
        sb.append("uname varchar2(64), "); //图片name
        sb.append("isnew char(1), "); //是否新添加
        sb.append("isappeal varchar2(2), "); //是否申诉成功 0 未申诉， 1 申诉成功， -1申诉失败
        sb.append("createdate date, ");
        sb.append("updatedate date, ");
        sb.append("createby varchar2(32), ");
        sb.append("updateby varchar2(32) ");
        sb.append(")");
        log.debug("建表语句: " + sb.toString());
        jdbcTemplate.execute(sb.toString());
        xlsViews.forEach(xls -> jdbcTemplate.execute("comment on column " + tablename + "." + xls.get("fieldname") + " is '" + xls.get("name") + "'"));
        jdbcTemplate.execute("comment on column " + tablename + ".deducteproject is '扣款核算项目  需要数据开发去匹配'");
        jdbcTemplate.execute("comment on column " + tablename + ".iswrite is '是否填写   判断条件都是1是0否'");
        jdbcTemplate.execute("comment on column " + tablename + ".appealstr is '申诉理由'");
        jdbcTemplate.execute("comment on column " + tablename + ".appealimg is '申诉图片'");
        jdbcTemplate.execute("comment on column " + tablename + ".uname is '图片name'");
        jdbcTemplate.execute("comment on column " + tablename + ".isnew is '是否新添加'");
        jdbcTemplate.execute("comment on column " + tablename + ".isappeal is '是否申诉成功 0 未申诉， 1 申诉成功， -1申诉失败'");
        log.info("临时表" + tablename + "创建成功");
        return tablename;
    }


    private void insert2Tempdb(List<Map<String, Object>> xlsViews, String month, Map<String, Object> data, String isnew, String tablename) {
        StringBuilder insertsql = new StringBuilder();
        insertsql.append("insert into " + tablename + " (id, month, ");
        xlsViews.forEach(xls -> insertsql.append(xls.get("fieldname") + ", "));
        insertsql.append("deducteproject, ");
        insertsql.append("deductedept, ");
        insertsql.append("iswrite, "); //是否填写   判断条件都是1是0否
        insertsql.append("appealstr, "); //申诉理由
        insertsql.append("appealimg, "); //申诉图片
        insertsql.append("appealdate, "); //申诉图片
        insertsql.append("appealuser, "); //申诉图片
        insertsql.append("uname, "); //图片name
        insertsql.append("isnew, "); //是否新添加
        insertsql.append("isappeal, "); //是否申诉
        insertsql.append("createdate, ");
        insertsql.append("updatedate, ");
        insertsql.append("createby, ");
        insertsql.append("updateby ");
        insertsql.append(")");
        insertsql.append("values(");
        insertsql.append("sys_guid(), '" + month + "', ");
        xlsViews.forEach(xls -> {
//            if (xls.get("fieldname").equals("department_name")) { //如果是科室
////                insertsql.append("getkeshi('" + StringUtil.null2Str(data.get(xls.get("name"))) + "'), ");
//                insertsql.append("'正在匹配...', ");
//            }
            //这个项目名称要当主键 所以必须不能更改
//            else if (xls.get("fieldname").equals("item_name")) { //如果是项目
//                insertsql.append("getprojectname('" + StringUtil.null2Str(data.get(xls.get("name"))) + "'), ");
//            }
//            else {
            insertsql.append("'" + StringUtil.removeCurrency(StringUtil.null2Str(data.get(xls.get("name")))) + "', ");
//            }
        });
        insertsql.append("'正在匹配...', '正在匹配...', '0', null, null, null, null, null, '" + isnew + "', '0', sysdate, null, '" + UserUtil.getUser().getUser_name() + "', null)");
//        log.debug("新增语句：" + insertsql.toString());
        jdbcTemplate.execute(insertsql.toString());
//        log.info("临时表"+tablename+"数据成功");
    }

    /**
     * 私有方法 插入数据到db
     *
     * @param xlsViews
     * @param month
     * @param data
     * @param isnew
     */
    private void insert2db(List<Map<String, Object>> xlsViews, String month, Map<String, Object> data, String isnew, String tablename) {
        StringBuilder insertsql = new StringBuilder();
        insertsql.append("insert into " + tablename + " (id, month, ");
        xlsViews.forEach(xls -> insertsql.append(xls.get("fieldname") + ", "));
        insertsql.append("deducteproject, ");
        insertsql.append("deductedept, ");
        insertsql.append("iswrite, "); //是否填写   判断条件都是1是0否
        insertsql.append("appealstr, "); //申诉理由
        insertsql.append("appealimg, "); //申诉图片
        insertsql.append("appealdate, "); //申诉图片
        insertsql.append("appealuser, "); //申诉图片
        insertsql.append("uname, "); //图片name
        insertsql.append("isnew, "); //是否新添加
        insertsql.append("isappeal, "); //是否申诉
        insertsql.append("createdate, ");
        insertsql.append("updatedate, ");
        insertsql.append("createby, ");
        insertsql.append("updateby ");
        insertsql.append(")");
        insertsql.append("values(");
        insertsql.append("sys_guid(), '" + month + "', ");
        xlsViews.forEach(xls -> {
//            if (xls.get("fieldname").equals("department_name")) { //如果是科室
////                insertsql.append("getkeshi('" + StringUtil.null2Str(data.get(xls.get("name"))) + "'), ");
//                insertsql.append("'正在匹配...', ");
//            }
            //这个项目名称要当主键 所以必须不能更改
//            else if (xls.get("fieldname").equals("item_name")) { //如果是项目
//                insertsql.append("getprojectname('" + StringUtil.null2Str(data.get(xls.get("name"))) + "'), ");
//            }
//            else {
            insertsql.append("'" + StringUtil.removeCurrency(StringUtil.null2Str(data.get(xls.get("name")))) + "', ");
//            }
        });
        insertsql.append("'正在匹配...', '正在匹配...', '0', null, null, null, null, null, '" + isnew + "', '0', sysdate, null, '" + UserUtil.getUser().getUser_name() + "', null)");
        log.debug("新增语句：" + insertsql.toString());
        jdbcTemplate.execute(insertsql.toString());
    }
}
