package com.aixbox.system.domain.vo.response;


import cn.hutool.core.lang.tree.Tree;
import lombok.Data;

import java.util.List;

/**
 * 角色菜单列表树信息
 */
@Data
public class MenuTreeSelectResp {

    /**
     * 选中菜单列表
     */
    private List<Long> checkedKeys;

    /**
     * 菜单下拉树结构列表
     */
    private List<Tree<Long>> menus;

}
