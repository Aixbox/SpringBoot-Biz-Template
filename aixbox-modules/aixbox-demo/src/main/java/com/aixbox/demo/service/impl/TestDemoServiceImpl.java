package com.aixbox.demo.service.impl;

import com.aixbox.common.core.pojo.PageResult;
import com.aixbox.common.core.utils.object.BeanUtils;
import com.aixbox.common.core.utils.object.MapstructUtils;
import com.aixbox.demo.domain.vo.request.TestDemoPageReqVO;
import com.aixbox.demo.domain.vo.request.TestDemoSaveReqVO;
import com.aixbox.demo.domain.vo.request.TestDemoUpdateReqVO;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.aixbox.demo.domain.entity.TestDemo;
import com.aixbox.demo.service.TestDemoService;
import com.aixbox.demo.mapper.TestDemoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
*
*/
@RequiredArgsConstructor
@Service
public class TestDemoServiceImpl implements TestDemoService {

    private final TestDemoMapper testDemoMapper;

    @Override
    public Long addTestDemo(TestDemoSaveReqVO addReqVO) {
        TestDemo testDemo = BeanUtils.toBean(addReqVO, TestDemo.class);
        testDemoMapper.insert(testDemo);
        return testDemo.getId();
    }

    @Override
    public Boolean updateTestDemo(TestDemoUpdateReqVO updateReqVO) {
        TestDemo testDemo = MapstructUtils.convert(updateReqVO, TestDemo.class);
        return testDemoMapper.updateById(testDemo) > 0;
    }

    @Override
    public Boolean deleteTestDemo(List<Long> ids) {
        return testDemoMapper.deleteByIds(ids) > 0;
    }

    @Override
    public TestDemo getTestDemo(Long id) {
        return testDemoMapper.selectById(id);
    }

    @Override
    public PageResult<TestDemo> getTestDemoPage(TestDemoPageReqVO pageReqVO) {
        return testDemoMapper.selectPage(pageReqVO);
    }
}




