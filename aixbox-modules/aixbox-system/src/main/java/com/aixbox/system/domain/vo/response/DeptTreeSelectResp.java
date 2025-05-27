package com.aixbox.system.domain.vo.response;


import cn.hutool.core.lang.tree.Tree;
import lombok.Data;

import java.util.List;

/**
 * 角色部门列表树信息
 */
@Data
public class DeptTreeSelectResp {

    /**
     * 选中部门列表
     */
    private List<Long> checkedKeys;

    /**
     * 下拉树结构列表
     */
    private List<Tree<Long>> depts;

}
