package com.aixbox.demo.service;

import com.aixbox.common.core.pojo.PageResult;
import com.aixbox.demo.domain.entity.DemoTest;
import com.aixbox.demo.domain.vo.request.DemoTestPageReqVO;
import com.aixbox.demo.domain.vo.request.DemoTestSaveReq;
import com.aixbox.demo.domain.vo.request.DemoTestUpdateReq;

import java.util.List;

/**
* 【请填写功能名称】 Service接口
*/
public interface DemoTestService {

    /**
     * 新增【请填写功能名称】
     * @param addReq 新增参数
     * @return 新增数据id
     */
    Long addDemoTest(DemoTestSaveReq addReq);

    /**
     * 修改【请填写功能名称】
     * @param updateReq 修改参数
     * @return 是否成功
     */
    Boolean updateDemoTest(DemoTestUpdateReq updateReq);

    /**
     * 删除【请填写功能名称】
     * @param ids 删除id数组
     * @return 是否成功
     */
    Boolean deleteDemoTest(List<Long> ids);

    /**
     * 获取【请填写功能名称】详细数据
     * @param id 数据id
     * @return 【请填写功能名称】对象
     */
    DemoTest getDemoTest(Long id);

    /**
     * 分页查询【请填写功能名称】
     * @param pageReqVO 分页查询参数
     * @return 【请填写功能名称】分页对象
     */
    PageResult<DemoTest> getDemoTestPage(DemoTestPageReqVO pageReqVO);
}
