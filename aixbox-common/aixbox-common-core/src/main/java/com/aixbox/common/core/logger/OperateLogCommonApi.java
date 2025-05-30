package com.aixbox.common.core.logger;


import com.aixbox.common.core.logger.dto.OperateLogCreateReqDto;
import jakarta.validation.Valid;
import org.springframework.scheduling.annotation.Async;



/**
 * 操作日志 API 接口
 *
 * @author 芋道源码
 */
public interface OperateLogCommonApi {

    /**
     * 创建操作日志
     *
     * @param createReqDTO 请求
     */
    void createOperateLog(@Valid OperateLogCreateReqDto createReqDTO);

    /**
     * 【异步】创建操作日志
     *
     * @param createReqDTO 请求
     */
    @Async
    default void createOperateLogAsync(OperateLogCreateReqDto createReqDTO) {
        createOperateLog(createReqDTO);
    }

}
