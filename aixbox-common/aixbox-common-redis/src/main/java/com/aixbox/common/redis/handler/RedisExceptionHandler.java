package com.aixbox.common.redis.handler;


import cn.hutool.http.HttpStatus;
import com.aixbox.common.core.pojo.CommonResult;
import com.baomidou.lock.exception.LockFailureException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Redis异常处理器
 */
@Slf4j
@RestControllerAdvice
public class RedisExceptionHandler {

    /**
     * 分布式锁Lock4j异常
     */
    @ExceptionHandler(LockFailureException.class)
    public CommonResult<Void> handleLockFailureException(LockFailureException e,
                                                         HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        log.error("获取锁失败了'{}',发生Lock4j异常.", requestURI, e);
        return CommonResult.error(HttpStatus.HTTP_UNAVAILABLE, "业务处理中，请稍后再试...");
    }

}
