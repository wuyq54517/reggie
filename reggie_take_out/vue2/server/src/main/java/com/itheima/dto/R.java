package com.itheima.dto;

public record R(int code, Object data, String message) {

    public static R ok(Object data) {
        return new R(200, data, null);
    }

    public static R error(int code, String message) {
        return new R(code, null, message);
    }
}
