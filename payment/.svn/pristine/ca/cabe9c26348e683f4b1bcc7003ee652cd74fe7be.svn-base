
package com.firesoon.dto.tree;

import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: wenyuan.guan
 * @date: 2018/9/28 11:19
 */
public class TreeNode {
    /**
     * 节点对象id值
     */
    private String id;
    /**
     *节点显示值
     */
    private String text;
    /**
     * 是否子节点
     */
    private String leaf;
    /**
     * 层级
     */
    private String level;
    /**
     * 前端控件需要：功能同level
     */
    private String type = "0";
    /**
     * 顺序码
     */
    private String dorder;
    /**
     * 层次解析用
     */
    private String guid;
    /**
     * 父节点
     */
    private String parentGuid;
    /**
     * 节点是否展开：”closed“ 不展开；”open“ 展开；默认不展开
     */
    private String state = "closed";
    /**
     * 复选树，是否选中； 默认不选中
     */
    private boolean checked = false;
    /**
     * 全路径名
     */
    private String label;
    /**
     * 附加属性对象
     */
    private Map<String, Object> attributes;
    /**
     * 子节点
     */
    private List<TreeNode> children;

    public TreeNode(String id, String text, String parentGuid) {
        this.id = id;
        this.guid = id;
        this.text = text;
        this.parentGuid = parentGuid;
    }

    public TreeNode(String text, String parentGuid) {
        this.text = text;
        this.parentGuid = parentGuid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getLeaf() {
        return leaf;
    }

    public void setLeaf(String leaf) {
        this.leaf = leaf;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getDorder() {
        return dorder;
    }

    public void setDorder(String dorder) {
        this.dorder = dorder;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getParentGuid() {
        return parentGuid;
    }

    public void setParentGuid(String parentGuid) {
        this.parentGuid = parentGuid;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Map<String, Object> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public List<TreeNode> getChildren() {
        return children;
    }

    public void setChildren(List<TreeNode> children) {
        this.children = children;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
