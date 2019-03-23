package com.expr.shirodemo.model;

import org.springframework.stereotype.Component;

import java.util.HashMap;
@Component
public class ResultMap extends HashMap {

    public void ResultMap(){

    }

    public ResultMap fail(){
        this.put("result","fail");
        return this;
    }

    public ResultMap success(){
        this.put("result","success");
        return this;
    }

    public ResultMap message(Object message){
        this.put("message",message);
        return this;
    }

}
