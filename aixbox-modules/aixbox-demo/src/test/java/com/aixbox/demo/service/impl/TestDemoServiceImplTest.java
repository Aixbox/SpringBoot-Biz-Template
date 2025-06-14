package com.aixbox.demo.service.impl;

import com.aixbox.common.test.core.ut.BaseDbUnitTest;
import com.aixbox.demo.domain.entity.TestDemo;
import com.aixbox.demo.domain.vo.request.TestDemoPageReqVO;
import com.aixbox.demo.domain.vo.request.TestDemoSaveReqVO;
import com.aixbox.demo.domain.vo.request.TestDemoUpdateReqVO;
import com.aixbox.common.core.pojo.PageResult;
import com.aixbox.demo.mapper.TestDemoMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Import;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

import static com.aixbox.common.test.core.util.RandomUtils.randomPojo;
import static org.junit.jupiter.api.Assertions.*;

/**
 * TestDemo服务测试类
 */
@DisplayName("TestDemo服务测试")
@Import({TestDemoServiceImpl.class})
class TestDemoServiceImplTest extends BaseDbUnitTest {

    @Resource
    private TestDemoServiceImpl testDemoService;

    @Resource
    private TestDemoMapper testDemoMapper;

    @Nested
    @DisplayName("新增TestDemo测试")
    class AddTestDemoTests {

        @Test
        @DisplayName("新增TestDemo - 正常情况 - 应成功")
        void addTestDemo_WhenNormal_ShouldSuccess() {
            // 准备测试数据
            TestDemoSaveReqVO addReqVO = randomPojo(TestDemoSaveReqVO.class);

            // 执行测试
            Long resultId = testDemoService.addTestDemo(addReqVO);

            // 验证结果
            assertNotNull(resultId);
        }
    }

    @Nested
    @DisplayName("更新TestDemo测试")
    class UpdateTestDemoTests {

        @Test
        @DisplayName("更新TestDemo - 存在数据 - 应成功")
        void updateTestDemo_WhenExists_ShouldSuccess() {
            //准备参数
            TestDemo testDemo = randomPojo(TestDemo.class);
            testDemoMapper.insert(testDemo);

            // 准备测试数据
            TestDemoUpdateReqVO testDemoUpdateReqVO = randomPojo(TestDemoUpdateReqVO.class);
            testDemoUpdateReqVO.setId(testDemo.getId());

            // 执行测试
            Boolean result = testDemoService.updateTestDemo(testDemoUpdateReqVO);

            // 验证结果
            assertTrue(result);
        }
    }

    @Nested
    @DisplayName("删除TestDemo测试")
    class DeleteTestDemoTests {

        @Test
        @DisplayName("删除TestDemo - 存在数据 - 应成功")
        void deleteTestDemo_WhenExists_ShouldSuccess() {
            // 准备测试数据
            TestDemo testDemo1 = randomPojo(TestDemo.class);
            TestDemo testDemo2 = randomPojo(TestDemo.class);
            testDemoMapper.insertBatch(Arrays.asList(testDemo1, testDemo2));
            List<Long> ids = Arrays.asList(testDemo1.getId(), testDemo2.getId());

            // 执行测试
            Boolean result = testDemoService.deleteTestDemo(ids);

            // 验证结果
            assertTrue(result);
        }
    }

    @Nested
    @DisplayName("获取TestDemo测试")
    class GetTestDemoTests {

        @Test
        @DisplayName("获取TestDemo - 存在数据 - 应成功返回")
        void getTestDemo_WhenExists_ShouldReturnData() {
            // 准备测试数据
            TestDemo testDemo = randomPojo(TestDemo.class);
            testDemoMapper.insert(testDemo);

            // 执行测试
            TestDemo result = testDemoService.getTestDemo(testDemo.getId());

            // 验证结果
            assertNotNull(result);
            assertEquals(testDemo.getId(), result.getId());
        }
    }

    @Nested
    @DisplayName("分页查询TestDemo测试")
    class GetTestDemoPageTests {

        @Test
        @DisplayName("分页查询TestDemo - 有数据 - 应成功返回")
        void getTestDemoPage_WhenHasData_ShouldReturnPage() {
            // 准备测试数据
            TestDemo testDemo1 = randomPojo(TestDemo.class);
            testDemoMapper.insert(testDemo1);


            TestDemoPageReqVO testDemoPageReqVO = new TestDemoPageReqVO();
            testDemoPageReqVO.setPageNo(1);
            testDemoPageReqVO.setPageSize(10);
            // 执行测试
            PageResult<TestDemo> result = testDemoService.getTestDemoPage(testDemoPageReqVO);

            // 验证结果
            assertNotNull(result);
            assertEquals(1, result.getTotal());
            assertEquals(testDemo1.getId(), result.getList().get(0).getId());
        }
    }
}