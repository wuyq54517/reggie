package com.itheima.controller;

import com.itheima.dto.R;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    public R handler401(Exception401 e) {
        return R.error(401, e.getMessage());
    }
}
