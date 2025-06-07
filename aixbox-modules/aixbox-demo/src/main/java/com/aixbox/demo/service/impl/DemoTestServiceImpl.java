package com.aixbox.demo.service.impl;

import com.aixbox.common.core.pojo.PageResult;
import com.aixbox.common.core.utils.object.BeanUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.aixbox.demo.domain.entity.DemoTest;
import com.aixbox.demo.domain.vo.request.DemoTestPageReq;
import com.aixbox.demo.domain.vo.request.DemoTestSaveReq;
import com.aixbox.demo.domain.vo.request.DemoTestUpdateReq;
import com.aixbox.demo.mapper.DemoTestMapper;
import com.aixbox.demo.service.DemoTestService;

import java.util.List;

import static com.aixbox.common.core.exception.util.ServiceExceptionUtil.exception;
import static com.aixbox.demo.constant.ErrorCodeConstants.DEMO_TEST_NOT_EXISTS;

/**
* 测试Service实现类
*/
@RequiredArgsConstructor
@Service
public class DemoTestServiceImpl implements DemoTestService {

    private final DemoTestMapper demoTestMapper;

    /**
     * 新增测试
     * @param addReq 新增参数
     * @return 测试id
     */
    @Override
    public Long addDemoTest(DemoTestSaveReq addReq) {
        DemoTest  demoTest = BeanUtils.toBean(addReq, DemoTest.class);
        validEntityBeforeSave(demoTest);
        demoTestMapper.insert(demoTest);
        return demoTest.getId();
    }

    /**
     * 修改测试
     * @param updateReq 修改参数
     * @return 是否成功
     */
    @Override
    public Boolean updateDemoTest(DemoTestUpdateReq updateReq) {
        DemoTest demoTest = BeanUtils.toBean(updateReq, DemoTest.class);
        validEntityBeforeSave(demoTest);
        return demoTestMapper.updateById(demoTest) > 0;
    }

    /**
     * 删除测试
     * @param ids 测试id数组
     * @return 是否成功
     */
    @Override
    public Boolean deleteDemoTest(List<Long> ids) {
        validateDemoTestExists(ids);
        return demoTestMapper.deleteByIds(ids) > 0;
    }

    /**
     * 验证测试是否存在
     * @param ids 测试id数组
     */
    private void validateDemoTestExists(List<Long> ids) {
        Long count = demoTestMapper.countByIds(ids);
        if (count != ids.size()) {
            throw exception(DEMO_TEST_NOT_EXISTS);
        }
    }

    /**
     * 获取测试详细数据
     * @param id 测试id
     * @return 测试对象
     */
    @Override
    public DemoTest getDemoTest(Long id) {
        return demoTestMapper.selectById(id);
    }

    /**
     * 分页查询测试
     * @param pageReq 分页查询参数
     * @return 测试分页对象
     */
    @Override
    public PageResult<DemoTest> getDemoTestPage(DemoTestPageReq pageReq) {
        return demoTestMapper.selectPage(pageReq);
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(DemoTest entity){
        //TODO 做一些数据校验,如唯一约束
    }
}




