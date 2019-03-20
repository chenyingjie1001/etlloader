package com.firesoon.dto.tree;

import java.io.Serializable;

/**
 * @description:
 * @author: wenyuan.guan
 * @date: 2018/9/28 11:21
 */
public abstract class TreeData implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 获取TreeNode
     *
     * @return
     */
    public abstract TreeNode getTreeNode();

//    public abstract String getId();
}
