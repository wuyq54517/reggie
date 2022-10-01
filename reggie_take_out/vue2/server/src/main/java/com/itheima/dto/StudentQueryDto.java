package com.itheima.dto;

public record StudentQueryDto(String name, String sex, Integer minAge, Integer maxAge, int page, int size) {

    public int offset() {
        return (page - 1) * size;
    }

    public int limit() {
        return size;
    }
}
