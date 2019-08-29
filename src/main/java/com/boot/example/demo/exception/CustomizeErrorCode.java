package com.boot.example.demo.exception;


/**
 *    CustomizeErrorCode  枚举类去实现 ICustomizeErrorCode 接口
 *    枚举之间用','号隔开
 */

public enum CustomizeErrorCode implements ICustomizeErrorCode {

    QUESTION_NOT_FOUND("你所要寻找的问题不在，请重新输入试试！！"),
    QUESTION_NOT_OFNH("个性设置！！");

    private  String message;

     CustomizeErrorCode(String message) {    //重载这个枚举类吧
        this.message = message;
    }
    @Override
    public String getMessage() {            //枚举类里面的方法  调用这个方法可以得到枚举类里面的message变量的值
        return message;
    }
}
