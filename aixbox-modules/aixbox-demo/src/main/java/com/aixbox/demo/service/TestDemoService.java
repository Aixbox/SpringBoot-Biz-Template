package com.aixbox.demo.service;

import com.aixbox.common.core.pojo.PageResult;
import com.aixbox.demo.domain.entity.TestDemo;
import com.aixbox.demo.domain.vo.request.TestDemoPageReqVO;
import com.aixbox.demo.domain.vo.request.TestDemoSaveReqVO;
import com.aixbox.demo.domain.vo.request.TestDemoUpdateReqVO;
import com.baomidou.mybatisplus.extension.service.IService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

/**
*
*/
public interface TestDemoService {

    Long addTestDemo(TestDemoSaveReqVO addReqVO);

    Boolean updateTestDemo(TestDemoUpdateReqVO updateReqVO);

    Boolean deleteTestDemo(List<Long> ids);

    TestDemo getTestDemo(Long id);

    PageResult<TestDemo> getTestDemoPage(TestDemoPageReqVO pageReqVO);
}
