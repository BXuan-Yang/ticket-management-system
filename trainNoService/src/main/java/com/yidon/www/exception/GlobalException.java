package com.yidon.www.exception;

/**
 * @author wuLinTao
 * 时间：2023-01-06 13:18
 */
public class GlobalException extends RuntimeException {

    private String msg;
    private Integer status;

    public GlobalException(String msg){
        super(msg);
        this.msg = msg;
    }

    public GlobalException(Integer status,String msg){
        super(msg);
        this.msg = msg;
        this.status = status;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

}
