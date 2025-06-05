package com.aixbox.demo.service;

import com.aixbox.common.core.pojo.PageResult;
import com.aixbox.demo.domain.entity.DemoTest;
import com.aixbox.demo.domain.vo.request.DemoTestPageReq;
import com.aixbox.demo.domain.vo.request.DemoTestSaveReq;
import com.aixbox.demo.domain.vo.request.DemoTestUpdateReq;

import java.util.List;

/**
 * demoService接口
 */
public interface DemoTestService {

    /**
     * 新增demo
     * @param addReq 新增参数
     * @return demoid
     */
     Long addDemoTest(DemoTestSaveReq addReq);

    /**
     * 修改demo
     * @param updateReq 修改参数
     * @return 是否成功
     */
    Boolean updateDemoTest(DemoTestUpdateReq updateReq);

    /**
     * 删除demo
     * @param ids 删除id数组
     * @return 是否成功
     */
    Boolean deleteDemoTest(List<Long> ids);

    /**
     * 获取demo详细数据
     * @param id 数据id
     * @return demo对象
     */
     DemoTest getDemoTest(Long id);

    /**
     * 分页查询demo
     * @param pageReq 分页查询参数
     * @return demo分页对象
     */
    PageResult<DemoTest> getDemoTestPage(DemoTestPageReq pageReq);
}
