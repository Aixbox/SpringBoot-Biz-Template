package com.aixbox.demo.mapper;

import com.aixbox.common.core.pojo.PageResult;
import com.aixbox.common.mybatis.core.dataobject.BaseDO;
import com.aixbox.common.mybatis.core.mapper.BaseMapperX;
import com.aixbox.common.mybatis.core.query.LambdaQueryWrapperX;
import org.apache.ibatis.annotations.Mapper;
import com.aixbox.demo.domain.entity.DemoTest;
import com.aixbox.demo.domain.vo.request.DemoTestPageReq;

import java.util.List;


/**
* demo Mapper接口
*/
@Mapper
public interface DemoTestMapper extends BaseMapperX<DemoTest> {
    /**
    * 分页查询
    * @param reqVO 请求参数
    * @return DemoTest分页对象
    */
    default PageResult<DemoTest> selectPage(DemoTestPageReq reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<DemoTest>()
                    .likeIfPresent(DemoTest::getName, reqVO.getName())
                    .eqIfPresent(DemoTest::getCreator, reqVO.getCreator())
                .orderByDesc(DemoTest::getId));    }

    default Long countByIds(List<Long> ids) {
        return selectCount(new LambdaQueryWrapperX<DemoTest>().in(DemoTest::getId, ids));
    }
}




