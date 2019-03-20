package com.firesoon.commoncasshiro.services.resource.impl;

import com.firesoon.commoncasshiro.services.resource.ResourceService;
import com.firesoon.dto.tree.Tree;
import com.firesoon.dto.tree.TreeNode;
import com.firesoon.utils.UserUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author create by yingjie.chen on 2018/11/22.
 * @version 2018/11/22 15:57
 */
@Slf4j
@Primary
@Service
public class ResourceServiceImpl implements ResourceService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

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

}
