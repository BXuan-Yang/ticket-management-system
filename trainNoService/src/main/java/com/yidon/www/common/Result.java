package com.yidon.www.common;

import com.yidon.www.constant.HttpConstant;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * @author HP
 */
@Data
@Builder
public class Result<T> {
    private Integer status;
    private String message;
    private T data;

    public static <T> Result<T> success() {
        return success(null);
    }


    public static <T> Result<T> success(T data) {
        return Result.<T>builder().data(data)
                .status(HttpConstant.HTTP_OK)
                .build();
    }

    public static <T extends Serializable> Result<T> fail(String message) {
        return fail(null, message);
    }

    public static <T> Result<T> fail(T data, String message) {
        return Result.<T>builder().data(data)
                .message(message)
                .status(HttpConstant.HTTP_FAIL)
                .build();
    }
}
