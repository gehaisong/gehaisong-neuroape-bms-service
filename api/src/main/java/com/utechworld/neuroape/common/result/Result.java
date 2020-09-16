package com.utechworld.neuroape.common.result;

import java.io.Serializable;

/**
 * controller层json视图数据格式封装.
 */
public class Result<V> implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer code;
    private String message;
    private V data;

    public static <V> Result<V> ok(V data) {
        return new Result(CodeEnum.SUCCESS.getValue(),data);
    }

    public static <V> Result<V> fail() {
        return new Result(CodeEnum.FAILED.getValue());
    }

    private Result(Integer code) {
        this.code = code;
    }
    private Result() {
    }

    private Result(Integer code, V data) {
        this(code, "", data);
    }

    public Result(Integer code, String message, V data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public V getData() {
        return data;
    }

    public Result<V> setCode(Integer code) {
        this.code = code;
        return this;
    }

    public Result<V> setMessage(String message) {
        this.message = message;
        return this;
    }

    public Result<V> setData(V data) {
        this.data = data;
        return this;
    }

}