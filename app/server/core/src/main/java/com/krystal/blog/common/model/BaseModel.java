package com.krystal.blog.common.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class BaseModel implements Serializable {

    private Date createTime;
    private Date updateTime;
    private Integer deleted;
}
