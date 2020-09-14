package com.example.sakura.base;

public class BaseExcept extends Throwable {
    private  String msg;
    private  Integer status;

    public String getMsg() {
        return msg;
    }

    public Integer getStatus() {
        return status;
    }

    public BaseExcept(Integer status, String msg){
        this.status = status;
        this.msg = msg;
    }
}