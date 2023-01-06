package com.yidon.www.exception;

/**
 * @author wuLinTao
 * 时间：2023-01-06 13:18
 */
public class GlobalException extends RuntimeException {

    private String msg;

    public GlobalException(String msg){
        super(msg);
        this.msg = msg;
    }

}
