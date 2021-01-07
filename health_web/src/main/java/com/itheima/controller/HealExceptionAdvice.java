package com.itheima.controller;

import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;
import com.itheima.entity.Result;
import com.itheima.exception.HealthException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class HealExceptionAdvice {

    private static final Logger log = LoggerFactory.getLogger(HealExceptionAdvice.class);

    /**
     * 业务异常的处理
     *
     * @param e
     * @return
     */
    @ExceptionHandler(HealthException.class)
    public Result handleMyException(HealthException e) {
        return new Result(false, e.getMessage());
    }

    /**
     * 未知异常处理
     *
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    public Result handleException(Exception e) {
        // 记录异常信息
        log.error("发生未知异常", e);
        return new Result(false, "发生未知异常，请联系管理员");
    }
}
