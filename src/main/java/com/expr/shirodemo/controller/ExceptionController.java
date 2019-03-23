package com.expr.shirodemo.controller;

import com.expr.shirodemo.model.ResultMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import javax.security.auth.login.AccountException;

@RestController
public class ExceptionController {

    private final ResultMap resultMap;

    @Autowired
    public ExceptionController(ResultMap resultMap){
        this.resultMap = resultMap;
    }

    @ExceptionHandler(AccountException.class)
    public ResultMap handleShiroExcption(Exception e){
        return resultMap.fail().message(e.getMessage());
    }
}
