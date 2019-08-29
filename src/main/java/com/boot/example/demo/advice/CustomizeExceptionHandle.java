package com.boot.example.demo.advice;


import com.boot.example.demo.exception.CustomizeException;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 *          springboot 自带的错误处理机制
 */

//这个注解是指这个类是处理其他controller抛出的异常
@ControllerAdvice
public class CustomizeExceptionHandle {

    //这个注解是指当controller中抛出这个指定的异常类的时候，都会转到这个方法中来处理异常
    @ExceptionHandler(Exception.class)
    ModelAndView handle(HttpServletRequest request, Throwable e, Model model) {

        if(e instanceof CustomizeException)
        {
            model.addAttribute("message",e.getMessage());
        }else {
            model.addAttribute("message","我也不知道啊");
        }
        return new ModelAndView("error");   //直接返回指定的model
    }

}
