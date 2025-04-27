package com.aixbox.demo.mapper;

import com.aixbox.common.core.pojo.PageResult;
import com.aixbox.common.mybatis.core.dataobject.BaseDO;
import com.aixbox.common.mybatis.core.mapper.BaseMapperX;
import com.aixbox.common.mybatis.core.query.LambdaQueryWrapperX;
import com.aixbox.demo.domain.entity.TestDemo;
import com.aixbox.demo.domain.vo.request.TestDemoPageReqVO;
import org.apache.ibatis.annotations.Mapper;

/**
* demo测试Mapper接口
*/
@Mapper
public interface TestDemoMapper extends BaseMapperX<TestDemo> {

    default PageResult<TestDemo> selectPage(TestDemoPageReqVO reqVO) {

        return selectPage(reqVO, new LambdaQueryWrapperX<TestDemo>()
                .likeIfPresent(TestDemo::getValue, reqVO.getValue())
                .likeIfPresent(TestDemo::getTestKey, reqVO.getTestKey())
                .orderByDesc(BaseDO::getCreateTime));
    }

}




