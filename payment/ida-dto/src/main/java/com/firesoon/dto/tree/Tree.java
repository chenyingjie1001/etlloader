package com.firesoon.dto.tree;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.math.NumberUtils;

import java.util.*;

/**
 * @description:
 * @author: wenyuan.guan
 * @date: 2018/9/28 11:23
 */
public class Tree {

    /**
     * 解析LevelData
     *
     * @param datas
     * @return
     */
    public static <T extends TreeData> String toJSON(List<T> datas) {
        List<TreeNode> nodes = toTreeNode(datas, false);
        String json = JSON.toJSONString(nodes);
        return json;
    }

    /**
     * 转为单层树形数据
     *
     * @param datas
     * @return
     */
    public static <T extends TreeData> List<TreeNode> toSimpleTreeNode(List<T> datas) {
        List<TreeNode> nodes = new ArrayList<TreeNode>();
        for (T target : datas) {
            nodes.add(target.getTreeNode());
        }
        return nodes;
    }

    /**
     * 解析为树形结构的数据
     *
     * @param datas
     * @return
     */
    public static <T extends TreeData> List<TreeNode> toTreeNode(List<T> datas, boolean needRootNode) {
        Map<String, TreeNode> map = toMap(datas);
        List<TreeNode> nodes = parser(map, "0");
        if (needRootNode) {
            TreeNode rootNode = new TreeNode("0", "全部", "");
            rootNode.setChildren(nodes);
            List<TreeNode> rootList = new ArrayList<TreeNode>();
            rootList.add(rootNode);
            return rootList;
        }
        return nodes;
    }

    /**
     * 解析为树形结构的数据
     *
     * @param datas
     * @return
     */
    public static <T extends TreeData> List<TreeNode> toTreeNode(Map<String, TreeNode> map, boolean needRootNode) {

        List<TreeNode> nodes = parser(map, "0");
        if (needRootNode) {
            TreeNode rootNode = new TreeNode("0", "全部", "");
            rootNode.setChildren(nodes);
            List<TreeNode> rootList = new ArrayList<TreeNode>();
            rootList.add(rootNode);
            return rootList;
        }
        return nodes;
    }

    /**
     * 解析为MAP对象集合
     *
     * @param datas
     * @return
     */
    public static <T extends TreeData> Map<String, TreeNode> toMap(List<T> datas) {
        Map<String, TreeNode> map = new LinkedHashMap<String, TreeNode>();
        for (TreeData levelData : datas) {
            TreeNode node = levelData.getTreeNode();
            map.put(levelData.getTreeNode().getId(), node);
        }
        return map;
    }

    /**
     * 解析为分层结构的数据
     *
     * @param map
     * @param parentGuid
     * @return
     */
    public static List<TreeNode> parser(Map<String, TreeNode> map, String parentGuid) {
        Iterator<String> it = map.keySet().iterator();
        List<TreeNode> nodes = new ArrayList<TreeNode>();
        while (it.hasNext()) {
            String id = it.next();
            TreeNode node = map.get(id);
            String stepIn = parentGuid;
            if (stepIn.equals(node.getParentGuid())) {
                stepIn = node.getGuid();
                node.setChildren(parser(map, stepIn));
                if (node.getChildren() == null || node.getChildren().size() == 0) {
                    node.setState("open");
                }
                nodes.add(node);
            }
        }
        Collections.sort(nodes, new Comparator<TreeNode>() {
            public int compare(TreeNode node1, TreeNode node2) {
                String dorder1 = StringUtils.isEmpty(node1.getDorder()) ? "0" : node1.getDorder();
                String dorder2 = StringUtils.isEmpty(node2.getDorder()) ? "0" : node2.getDorder();
                int i = 0;
                if (NumberUtils.isNumber(dorder1) && NumberUtils.isNumber(dorder2)) {
                    i = new Double(dorder1).compareTo(new Double(dorder2));
                } else {
                    i = dorder1.compareTo(dorder2);
                }
                return i;
            }
        });
        return nodes;
    }
}
