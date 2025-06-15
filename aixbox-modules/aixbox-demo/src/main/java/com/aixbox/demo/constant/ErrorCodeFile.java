package com.aixbox.demo.constant;


import com.aixbox.common.core.exception.ErrorCode;

/**
 * System 错误码枚举类
 *
 * system 系统，使用 1-          002-        000-        000 段
 *                 业务级异常    系统模块     业务类型      错误码
 */
public interface ErrorCodeFile {

    // TODO 待办：请将下面的错误码复制到模块的 ErrorCodeConstants 类中。注意，请给“TODO 补充编号”设置一个错误码编号！！！
    // ========== 测试模块 模块 1-002-000-??? ==========
    ErrorCode UPDATE_DEMO_TEST_ERROR = new ErrorCode(1_002_000_000, "修改测试失败");
    ErrorCode DELETE_DEMO_TEST_ERROR = new ErrorCode(1_002_000_001, "删除测试失败");
    ErrorCode DEMO_TEST_NOT_EXISTS = new ErrorCode(1_002_000_002, "测试不存在");
}
