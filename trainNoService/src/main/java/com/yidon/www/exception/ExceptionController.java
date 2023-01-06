package com.yidon.www.exception;

import com.yidon.www.common.Result;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author wuLinTao
 * 时间：2023-01-06 13:19
 */
@RestControllerAdvice
public class ExceptionController {

    @ExceptionHandler(GlobalException.class)
    public Result globalExceptionController(GlobalException e){
        return Result.fail(e.getMessage());
    }
}
