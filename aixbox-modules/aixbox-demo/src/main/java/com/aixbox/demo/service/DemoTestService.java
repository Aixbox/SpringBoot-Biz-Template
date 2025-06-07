package com.aixbox.demo.service;

import com.aixbox.common.core.pojo.PageResult;
import com.aixbox.demo.domain.entity.DemoTest;
import com.aixbox.demo.domain.vo.request.DemoTestPageReq;
import com.aixbox.demo.domain.vo.request.DemoTestSaveReq;
import com.aixbox.demo.domain.vo.request.DemoTestUpdateReq;

import java.util.List;

/**
 * 测试Service接口
 */
public interface DemoTestService {

    /**
     * 新增测试
     * @param addReq 新增参数
     * @return 测试id
     */
     Long addDemoTest(DemoTestSaveReq addReq);

    /**
     * 修改测试
     * @param updateReq 修改参数
     * @return 是否成功
     */
    Boolean updateDemoTest(DemoTestUpdateReq updateReq);

    /**
     * 删除测试
     * @param ids 删除id数组
     * @return 是否成功
     */
    Boolean deleteDemoTest(List<Long> ids);

    /**
     * 获取测试详细数据
     * @param id 数据id
     * @return 测试对象
     */
     DemoTest getDemoTest(Long id);

    /**
     * 分页查询测试
     * @param pageReq 分页查询参数
     * @return 测试分页对象
     */
    PageResult<DemoTest> getDemoTestPage(DemoTestPageReq pageReq);
}
