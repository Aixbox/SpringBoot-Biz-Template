package com.aixbox.demo.service;

import com.aixbox.common.core.pojo.PageResult;
import com.aixbox.demo.domain.entity.TestDemo;
import com.aixbox.demo.domain.vo.request.TestDemoPageReqVO;
import com.aixbox.demo.domain.vo.request.TestDemoSaveReqVO;
import com.aixbox.demo.domain.vo.request.TestDemoUpdateReqVO;

import java.util.List;

/**
* demo Service接口
*/
public interface TestDemoService {

    /**
     * 新增demo
     * @param addReqVO 新增参数
     * @return 新增数据id
     */
    Long addTestDemo(TestDemoSaveReqVO addReqVO);

    /**
     * 修改demo
     * @param updateReqVO 修改参数
     * @return 是否成功
     */
    Boolean updateTestDemo(TestDemoUpdateReqVO updateReqVO);

    /**
     * 删除demo
     * @param ids 删除id数组
     * @return 是否成功
     */
    Boolean deleteTestDemo(List<Long> ids);

    /**
     * 获取demo详细数据
     * @param id 数据id
     * @return demo对象
     */
    TestDemo getTestDemo(Long id);

    /**
     * 分页查询demo
     * @param pageReqVO 分页查询参数
     * @return demo分页对象
     */
    PageResult<TestDemo> getTestDemoPage(TestDemoPageReqVO pageReqVO);
}
