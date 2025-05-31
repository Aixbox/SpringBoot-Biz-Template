package com.aixbox.system.domain.vo.request.user;


import com.aixbox.common.core.pojo.PageParam;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * TestDemo 分页参数
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysUserPageReqVO extends PageParam {

    /**
     * 用户账号
     */
    @ExcelProperty(value = "登录名称")
    private String userName;
    /**
     * 用户昵称
     */
    @ExcelProperty(value = "用户名称")
    private String nickName;
    /**
     * 手机号码
     */
    private String phonenumber;
    /**
     * 账号状态（0正常 1停用）
     */
    private String status;


}
