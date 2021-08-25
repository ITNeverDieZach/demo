package com.zachcc.shiro.exception.handler;


import com.zachcc.common.common.Result;
import com.zachcc.common.common.ResultCode;
import com.zachcc.shiro.exception.CustomException;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

/**
 * 全局异常处理器
 *
 * @author pyy
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {


    /**
     * 处理自定义异常
     */
    @ExceptionHandler(CustomException.class)
    public Result handleException(CustomException e) {
        // 打印异常信息
        log.error("### 异常信息:{} ###", e.getMessage());
        return new Result(e.getResultCode());
    }

    /**
     * 参数错误异常
     */
    @ExceptionHandler({MethodArgumentNotValidException.class, BindException.class})
    public Result handleException(Exception e) {

        if (e instanceof MethodArgumentNotValidException) {
            MethodArgumentNotValidException validException = (MethodArgumentNotValidException) e;
            BindingResult result = validException.getBindingResult();
            StringBuffer errorMsg = new StringBuffer();
            if (result.hasErrors()) {
                List<ObjectError> errors = result.getAllErrors();
                errors.forEach(p -> {
                    FieldError fieldError = (FieldError) p;
                    errorMsg.append(fieldError.getDefaultMessage()).append(",");
                    log.error("### 请求参数错误：{" + fieldError.getObjectName() + "},field{" + fieldError.getField() + "},errorMessage{" + fieldError.getDefaultMessage() + "}");
                });
            }
        } else if (e instanceof BindException) {
            BindException bindException = (BindException) e;
            if (bindException.hasErrors()) {
                log.error("### 请求参数错误: {}", bindException.getAllErrors());
            }
        }

        return new Result(ResultCode.PARAM_IS_INVALID);
    }

    @ExceptionHandler(AuthenticationException.class)
    public Result handleException(AuthenticationException e) {
        CustomException e2 = (CustomException) e.getCause();
        return new Result(e2.getResultCode());
    }

    /**
     * 处理所有不可知的异常
     */
    @ExceptionHandler(Exception.class)
    public Result handleOtherException(Exception e) {
        //打印异常堆栈信息
        e.printStackTrace();
        // 打印异常信息
        log.info(e.toString());
        log.error("### 不可知的异常:{} ###", e.getMessage());
        return new Result(ResultCode.SYSTEM_INNER_ERROR);
    }

}
