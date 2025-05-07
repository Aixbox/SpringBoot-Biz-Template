package com.aixbox.demo.service.impl;

import com.aixbox.common.core.pojo.PageResult;
import com.aixbox.common.core.utils.object.BeanUtils;
import com.aixbox.common.core.utils.object.MapstructUtils;
import com.aixbox.demo.domain.entity.DemoTest;
import com.aixbox.demo.domain.vo.request.DemoTestPageReqVO;
import com.aixbox.demo.domain.vo.request.DemoTestSaveReqVO;
import com.aixbox.demo.domain.vo.request.DemoTestUpdateReqVO;
import com.aixbox.demo.mapper.DemoTestMapper;
import com.aixbox.demo.service.DemoTestService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* 【请填写功能名称】 Service实现类
*/
@RequiredArgsConstructor
@Service
public class DemoTestServiceImpl implements DemoTestService {

    private final DemoTestMapper demoTestMapper;

    /**
     * 新增【请填写功能名称】
     * @param addReqVO 新增参数
     * @return 新增数据id
     */
    @Override
    public Long addDemoTest(DemoTestSaveReqVO addReqVO) {
        DemoTest demoTest = BeanUtils.toBean(addReqVO, DemoTest.class);
        demoTestMapper.insert(demoTest);
        return demoTest.getId();
    }

    /**
     * 修改【请填写功能名称】
     * @param updateReqVO 修改参数
     * @return 是否成功
     */
    @Override
    public Boolean updateDemoTest(DemoTestUpdateReqVO updateReqVO) {
        DemoTest demoTest = MapstructUtils.convert(updateReqVO, DemoTest.class);
        return demoTestMapper.updateById(demoTest) > 0;
    }

    /**
     * 删除【请填写功能名称】
     * @param ids 删除id数组
     * @return 是否成功
     */
    @Override
    public Boolean deleteDemoTest(List<Long> ids) {
        return demoTestMapper.deleteByIds(ids) > 0;
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
     * @param pageReqVO 分页查询参数
     * @return 【请填写功能名称】分页对象
     */
    @Override
    public PageResult<DemoTest> getDemoTestPage(DemoTestPageReqVO pageReqVO) {
        return demoTestMapper.selectPage(pageReqVO);
    }
}




