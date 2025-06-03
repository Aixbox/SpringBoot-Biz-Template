package com.aixbox.demo.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.aixbox.common.core.pojo.PageResult;
import com.aixbox.common.core.utils.object.BeanUtils;
import com.aixbox.demo.domain.entity.DemoTest;
import com.aixbox.demo.domain.vo.request.DemoTestPageReq;
import com.aixbox.demo.domain.vo.request.DemoTestSaveReq;
import com.aixbox.demo.domain.vo.request.DemoTestUpdateReq;
import com.aixbox.demo.mapper.DemoTestMapper;
import com.aixbox.demo.service.DemoTestService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.aixbox.common.core.exception.util.ServiceExceptionUtil.exception;
import static com.aixbox.demo.constant.ErrorCodeConstants.DEMO_NOT_EXISTS;

/**
* 【请填写功能名称】 Service实现类
*/
@RequiredArgsConstructor
@Service
public class DemoTestServiceImpl implements DemoTestService {

    private final DemoTestMapper demoTestMapper;

    /**
     * 新增【请填写功能名称】
     * @param addReq 新增参数
     * @return 新增数据id
     */
    @Override
    public Long addDemoTest(DemoTestSaveReq addReq) {
        DemoTest demoTest = BeanUtils.toBean(addReq, DemoTest.class);
        validEntityBeforeSave(demoTest);
        demoTestMapper.insert(demoTest);
        return demoTest.getId();
    }

    /**
     * 修改【请填写功能名称】
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
     * 删除【请填写功能名称】
     * @param ids 删除id数组
     * @return 是否成功
     */
    @Override
    public Boolean deleteDemoTest(List<Long> ids) {
        validateDemo01ContactExists(ids);
        return demoTestMapper.deleteByIds(ids) > 0;
    }

    private void validateDemo01ContactExists(List<Long> ids) {
        List<DemoTest> list = demoTestMapper.selectByIds(ids);
        if (CollUtil.isEmpty(list) || list.size() != ids.size()) {
            throw exception(DEMO_NOT_EXISTS);
        }
    }

    /**
     * 获取【请填写功能名称】详细数据
     * @param id 数据id
     * @return 【请填写功能名称】对象
     */
    @Override
    public DemoTest getDemoTest(Long id) {
        return demoTestMapper.selectById(id);
    }

    /**
     * 分页查询【请填写功能名称】
     * @param pageReq 分页查询参数
     * @return 【请填写功能名称】分页对象
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




