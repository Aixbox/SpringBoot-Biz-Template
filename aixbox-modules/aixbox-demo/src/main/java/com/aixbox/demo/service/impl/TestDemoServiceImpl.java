package com.aixbox.demo.service.impl;

import com.aixbox.common.core.pojo.PageResult;
import com.aixbox.common.core.utils.object.BeanUtils;
import com.aixbox.common.core.utils.object.MapstructUtils;
import com.aixbox.demo.domain.vo.request.TestDemoPageReqVO;
import com.aixbox.demo.domain.vo.request.TestDemoSaveReqVO;
import com.aixbox.demo.domain.vo.request.TestDemoUpdateReqVO;
import com.aixbox.demo.domain.entity.TestDemo;
import com.aixbox.demo.mapper.TestDemoMapper;
import com.aixbox.demo.service.TestDemoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* demo Service实现类
*/
@RequiredArgsConstructor
@Service
public class TestDemoServiceImpl implements TestDemoService {

    private final TestDemoMapper testDemoMapper;

    /**
     * 新增demo
     * @param addReqVO 新增参数
     * @return 新增数据id
     */
    @Override
    public Long addTestDemo(TestDemoSaveReqVO addReqVO) {
        TestDemo testDemo = BeanUtils.toBean(addReqVO, TestDemo.class);
        testDemoMapper.insert(testDemo);
        return testDemo.getId();
    }

    /**
     * 修改demo
     * @param updateReqVO 修改参数
     * @return 是否成功
     */
    @Override
    public Boolean updateTestDemo(TestDemoUpdateReqVO updateReqVO) {
        TestDemo testDemo = MapstructUtils.convert(updateReqVO, TestDemo.class);
        return testDemoMapper.updateById(testDemo) > 0;
    }

    /**
     * 删除demo
     * @param ids 删除id数组
     * @return 是否成功
     */
    @Override
    public Boolean deleteTestDemo(List<Long> ids) {
        return testDemoMapper.deleteByIds(ids) > 0;
    }

    /**
     * 获取demo详细数据
     * @param id 数据id
     * @return demo对象
     */
    @Override
    public TestDemo getTestDemo(Long id) {
        return testDemoMapper.selectById(id);
    }

    /**
     * 分页查询demo
     * @param pageReqVO 分页查询参数
     * @return demo分页对象
     */
    @Override
    public PageResult<TestDemo> getTestDemoPage(TestDemoPageReqVO pageReqVO) {
        return testDemoMapper.selectPage(pageReqVO);
    }
}




