package com.krystal.blog.common.enums;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
public enum ArticleStatusEnum {

    STATUS0(0, "草稿"),
    STATUS1(1, "已发布"),
    STATUS2(2, "已修改"),
    ;

    ArticleStatusEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private Integer code;
    private String msg;


}
