package com.krystal.blog.common.enums;

import lombok.Getter;

@Getter
public enum ArticleDeletedEnum {

    STATUS0(0, "未删除"),
    STATUS1(1, "已删除"),
    ;

    ArticleDeletedEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private Integer code;
    private String msg;


}
