package com.jdd.imadmin.api.component;

import com.jdd.imadmin.api.common.ResultModel;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @author @sailength on 2020/3/25.
 *         参数校验拦截器
 */
@ControllerAdvice
@Component
@Order(0)
public class ValidateExceptionHandler {


    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseBody
    public Object exceptionHandler(MethodArgumentNotValidException e, HttpServletRequest request) {
        StringBuffer msg = new StringBuffer();

        if (e instanceof MethodArgumentNotValidException) {
            for (DefaultMessageSourceResolvable ex : e.getBindingResult().getAllErrors()) {
                msg.append(ex.getDefaultMessage());
                msg.append("|");
            }
        }
        return ResultModel.newErrorModel(msg.toString());

    }
}
