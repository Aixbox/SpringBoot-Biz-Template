package com.aixbox.system.constant;


import com.aixbox.common.core.exception.ErrorCode;

import static net.sf.jsqlparser.util.validation.metadata.NamedObject.role;

/**
 * System 错误码枚举类
 *
 * system 系统，使用 1-          002-        000-        000 段
 *                 业务级异常    系统模块     业务类型      错误码
 */
public interface ErrorCodeConstants {

    // ========== Auth模块 模块 1-002-000-??? ==========
    ErrorCode AUTH_GRANT_TYPE_ERROR = new ErrorCode(1_002_000_000, "认证权限类型错误");
    ErrorCode AUTH_GRANT_TYPE_BLOCKED = new ErrorCode(1_002_000_001, "认证权限类型已禁用");
    ErrorCode AUTH_TYPE_ERROR = new ErrorCode(1_002_000_002, "授权类型不正确!");
    ErrorCode AUTH_PASSWORD_ERROR = new ErrorCode(1_002_000_003, "密码输入错误{}次，帐户锁定{}分钟");
    ErrorCode AUTH_PASSWORD_ERROR_RETRY_LIMIT_EXCEED = new ErrorCode(1_002_000_004, "密码输入错误{}次");
    ErrorCode AUTH_REGISTER_ERROR = new ErrorCode(1_002_000_005, "注册失败，请联系系统管理人员");
    ErrorCode AUTH_PLATFORM_ERROR = new ErrorCode(1_002_000_006, "平台账号暂不支持");
    ErrorCode AUTH_RESPONSE_ERROR = new ErrorCode(1_002_000_007, "授权响应失败");
    ErrorCode AUTH_LOGIN_ERROR = new ErrorCode(1_002_000_008, "登录失败");
    ErrorCode AUTH_SOCIAL_ERROR = new ErrorCode(1_002_000_009, "你还没有绑定第三方账号，绑定后才可以登录！");

    // ========== 用户模块 模块 1-002-001-??? ==========
    ErrorCode USERNAME_NOT_EXIST = new ErrorCode(1_002_001_000, "对不起, 您的账号：{} 不存在");
    ErrorCode USERNAME_DISABLED = new ErrorCode(1_002_001_001, "对不起，您的账号：{} 已禁用，请联系管理员");
    ErrorCode USERNAME_EXIST = new ErrorCode(1_002_001_002, "保存用户 {} 失败，注册账号已存在");
    ErrorCode USERNAME_NO_PERMISSION = new ErrorCode(1_002_001_003, "没有权限访问用户数据!");
    ErrorCode USERNAME_SUPER_ADMIN = new ErrorCode(1_002_001_004, "不允许操作超级管理员用户");
    ErrorCode USERNAME_NOT_EXIST_OR_NOT_ENABLE = new ErrorCode(1_002_001_005, "新增用户'{}'失败，登录账号已存在");
    ErrorCode PHONE_EXIST = new ErrorCode(1_002_001_006, "新增用户'{}'失败，手机号码已存在");
    ErrorCode EMAIL_EXIST = new ErrorCode(1_002_001_007, "新增用户'{}'失败，邮箱账号已存在");
    ErrorCode ADD_USER_ERROR = new ErrorCode(1_002_001_008, "新增用户失败");
    ErrorCode UPDATE_EMAIL_EXIST = new ErrorCode(1_002_001_009, "修改用户'{}'失败，邮箱账号已存在");
    ErrorCode UPDATE_USERNAME_EXIST = new ErrorCode(1_002_001_010, "修改用户'{}'失败，登录账号已存在");
    ErrorCode UPDATE_PHONE_EXIST = new ErrorCode(1_002_001_011, "修改用户'{}'失败，手机号码已存在");
    ErrorCode UPDATE_ERROR = new ErrorCode(1_002_001_012, "修改用户失败");
    ErrorCode UPDATE_STATUS_ERROR = new ErrorCode(1_002_001_013, "用户状态修改失败");
    ErrorCode DELETE_CURRENT_USER_ERROR = new ErrorCode(1_002_001_014, "当前用户不能删除");
    ErrorCode DELETE_USER_ERROR = new ErrorCode(1_002_001_015, "删除用户失败!");
    ErrorCode RESET_PASSWORD_ERROR = new ErrorCode(1_002_001_016, "重置密码失败!");

    // ========== 角色模块 模块 1-002-002-??? ==========
    ErrorCode BULK_REVOKE_USER_ERROR = new ErrorCode(1_002_002_000, "批量取消授权用户失败");
    ErrorCode REVOKE_USER_ERROR = new ErrorCode(1_002_002_001, "取消授权用户失败");
    ErrorCode BULK_AUTH_USER_ERROR = new ErrorCode(1_002_002_002, "批量选择用户授权失败");
    ErrorCode CHANGE_STATUS_ERROR = new ErrorCode(1_002_002_003, "状态修改失败");
    ErrorCode UPDATE_DATA_SCOPE_ERROR = new ErrorCode(1_002_002_004, "修改保存数据权限失败");
    ErrorCode ROLE_NAME_EXIST = new ErrorCode(1_002_002_005, "新增角色'{}'失败，角色名称已存在");
    ErrorCode UPDATE_ROLE_NAME_EXIST = new ErrorCode(1_002_002_006, "修改角色'{}'失败，角色名称已存在");
    ErrorCode ROLE_KEY_EXIST = new ErrorCode(1_002_002_007, "新增角色'{}'失败，角色权限已存在");
    ErrorCode UPDATE_ROLE_KEY_EXIST = new ErrorCode(1_002_002_008, "新增角色'{}'失败，角色权限已存在");
    ErrorCode UPDATE_ROLE_ERROR = new ErrorCode(1_002_002_009, "修改角色'{}'失败，请联系管理员");

    // ========== 字典模块 模块 1-002-003-??? ==========
    ErrorCode DICT_VALUE_EXIST = new ErrorCode(1_002_003_000, "新增字典数据'{}'失败，字典键值已存在");
    ErrorCode DICT_TYPE_EXIST = new ErrorCode(1_002_003_001, "新增字典'{}'失败，字典类型已存在");
    ErrorCode UPDATE_DICT_TYPE_EXIST = new ErrorCode(1_002_003_002, "修改字典'{}'失败，字典类型已存在");
    ErrorCode CANNOT_BE_DELETED = new ErrorCode(1_002_003_003, "{}已分配,不能删除");

    // ========== 菜单模块 模块 1-002-004-??? ==========
    ErrorCode MENU_NAME_EXIST = new ErrorCode(1_002_004_000, "新增菜单'{}'失败，菜单名称已存在");
    ErrorCode ADDRESS_NOT_HTTP = new ErrorCode(1_002_004_001, "新增菜单'{}'失败，地址必须以http(s)://开头");
    ErrorCode UPDATE_MENU_NAME_EXIST = new ErrorCode(1_002_004_002, "修改菜单'{}'失败，菜单名称已存在");
    ErrorCode UPDATE_MENU_ADDRESS_NOT_HTTP = new ErrorCode(1_002_004_003, "修改菜单'{}'失败，地址必须以http(s)://开头");
    ErrorCode UPDATE_MENU_PARENT_ERROR = new ErrorCode(1_002_004_004, "修改菜单'{}'失败，上级菜单不能选择自己");
    ErrorCode UPDATE_MENU_ERROR = new ErrorCode(1_002_004_005, "修改菜单失败");
    ErrorCode EXIST_CHILD_MENU = new ErrorCode(1_002_004_006, "存在子菜单,不允许删除");
    ErrorCode MENU_EXIST_ROLE = new ErrorCode(1_002_004_007, "菜单已分配,不允许删除");

    // ========== 部门模块 模块 1-002-005-??? ==========
    ErrorCode NO_DEPT_PERMISSION = new ErrorCode(1_002_005_000, "没有权限访问部门数据！");
    ErrorCode DEPT_NAME_EXIST = new ErrorCode(1_002_005_001, "新增部门'{}'失败，部门名称已存在");
    ErrorCode ADD_DEPT_ERROR = new ErrorCode(1_002_005_002, "新增部门失败");
    ErrorCode UPDATE_DEPT_NAME_EXIST = new ErrorCode(1_002_005_003, "修改部门'{}'失败，部门名称已存在");
    ErrorCode UPDATE_DEPT_PARENT_ERROR = new ErrorCode(1_002_005_004, "修改部门'{}'失败，上级部门不能是自己");
    ErrorCode DEPT_EXIST_CHILD = new ErrorCode(1_002_005_004, "该部门包含未停用的子部门!");
    ErrorCode DEPT_EXIST_USER = new ErrorCode(1_002_005_005, "该部门下存在已分配用户，不能禁用!");
    ErrorCode UPDATE_DEPT_ERROR = new ErrorCode(1_002_005_006, "修改部门失败");
    ErrorCode EXIST_CHILD_DEPT = new ErrorCode(1_002_005_006, "存在下级部门,不允许删除");
    ErrorCode EXIST_USER = new ErrorCode(1_002_005_006, "部门存在用户,不允许删除");
    ErrorCode EXIST_POST = new ErrorCode(1_002_005_006, "部门存在岗位,不允许删除");
    ErrorCode DELETE_DEPT_ERROR = new ErrorCode(1_002_005_006, "删除部门失败");

    // ========== 岗位模块 模块 1-002-006-??? ==========
    ErrorCode POST_NAME_EXIST = new ErrorCode(1_002_006_000, "新增岗位'{}'失败，岗位名称已存在");
    ErrorCode POST_CODE_EXIST = new ErrorCode(1_002_006_000, "新增岗位'{}'失败，岗位编码已存在");
    ErrorCode UPDATE_POST_NAME_EXIST = new ErrorCode(1_002_006_000, "修改岗位'{}'失败，岗位名称已存在");
    ErrorCode UPDATE_POST_CODE_EXIST = new ErrorCode(1_002_006_000, "修改岗位'{}'失败，岗位编码已存在");
    ErrorCode UPDATE_POST_ERROR = new ErrorCode(1_002_006_000, "该岗位下存在已分配用户，不能禁用!");

    //  ========== 系统配置模块 模块 1-002-007-??? ==========
    ErrorCode CONFIG_KEY_EXIST = new ErrorCode(1_002_007_000, "新增参数'{}'失败，参数键名已存在");
    ErrorCode UPDATE_CONFIG_KEY_EXIST = new ErrorCode(1_002_007_000, "修改参数''失败，参数键名已存在");

    //  ========== 客户端模块 模块 1-002-008-??? ==========
    ErrorCode UPDATE_CLIENT_STATUS_ERROR = new ErrorCode(1_002_008_000, "修改客户端状态失败");
    ErrorCode DELETE_CLIENT_ERROR = new ErrorCode(1_002_008_000, "删除客户端失败");


}
