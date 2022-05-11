package com.krystal.blog.common;

import com.krystal.blog.common.beans.R;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@ControllerAdvice
@RestController
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public R exceptionHandler(Exception exception) {
        return R.error(400, exception.getMessage());
    }
}
